package org.bingo4.Persistence;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bingo4.Bingo4;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Talks to the AscensionWorlds GitHub repository.
 *  - downloadFolder(): mirrors a folder of the repo into a local folder (blocking, call async)
 *  - commitChangedFiles(): commits changed local files back as ONE commit (blocking, call async)
 */
public class AscensionCloud {

    // ===================== CREDENTIALS / CONFIG (edit these) =====================
    /** The token is read from ./plugins/Ascension/env.properties (next to ascension_save_file.json):
     *      GITHUB_TOKEN=github_pat_xxxxxxxx
     *  Fine-grained Personal Access Token with "Contents: Read and write" on Gradyc01/AscensionWorlds.
     *  Only needed for committing (and for downloading if the repo is private). */
    public static final String ENV_FILE_NAME = "env.properties";
    public static final String TOKEN_KEY = "GITHUB_TOKEN";
    /** Name/email shown on the commits. */
    public static final String COMMITTER_NAME = "Ascension Server";
    public static final String COMMITTER_EMAIL = "ascension-server@users.noreply.github.com";
    // ============================================================================

    public static final String OWNER = "Gradyc01";
    public static final String REPO = "AscensionWorlds";
    public static final String BRANCH = "main";
    /** Folder inside the repo that mirrors ./plugins/Ascension */
    public static final String REPO_FOLDER = "Ascension";
    /** Local folder that mirrors REPO_FOLDER */
    public static final Path LOCAL_ROOT = Paths.get("./plugins/Ascension");

    private static final String API = "https://api.github.com/repos/" + OWNER + "/" + REPO;

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public boolean hasToken() {
        String t = token();
        return t != null && !t.isBlank();
    }

    /** Reads the token from env.properties on every call, so edits apply without a restart. Null if missing. */
    private String token() {
        Path file = LOCAL_ROOT.resolve(ENV_FILE_NAME);
        if (!Files.isRegularFile(file)) return null;
        Properties props = new Properties();
        try (java.io.Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            props.load(reader);
        } catch (IOException e) {
            Bingo4.getInstance().getLogger().warning("Could not read " + file + ": " + e.getMessage());
            return null;
        }
        String t = props.getProperty(TOKEN_KEY);
        return t == null ? null : t.trim();
    }

    // ------------------------------------------------------------------ download

    /**
     * Downloads every file under repoPath (e.g. "Ascension" or "Ascension/PlayerData")
     * into localDir, overwriting files that differ. Files only present locally are kept.
     * Blocking: run on an async thread. Returns number of files written.
     */
    public int downloadFolder(String repoPath, Path localDir) throws Exception {
        Path root = localDir.toAbsolutePath().normalize();
        Files.createDirectories(root);

        JsonObject tree = getJson(API + "/git/trees/" + BRANCH + ":" + encodePath(repoPath) + "?recursive=1");
        if (tree.has("truncated") && tree.get("truncated").getAsBoolean()) {
            throw new IllegalStateException("GitHub tree for " + repoPath + " was truncated");
        }

        int written = 0;
        for (JsonElement el : tree.getAsJsonArray("tree")) {
            JsonObject entry = el.getAsJsonObject();
            if (!entry.get("type").getAsString().equals("blob")) continue;

            String relative = entry.get("path").getAsString();
            String sha = entry.get("sha").getAsString();
            Path file = root.resolve(relative).normalize();
            if (!file.startsWith(root)) {
                throw new IllegalStateException("Invalid repository path: " + relative);
            }

            // Skip files that are already identical.
            if (Files.isRegularFile(file) && gitBlobSha(Files.readAllBytes(file)).equals(sha)) continue;

            Files.createDirectories(file.getParent());
            Path tmp = file.resolveSibling(file.getFileName() + ".downloading");

            HttpRequest req = base(API + "/git/blobs/" + sha)
                    .header("Accept", "application/vnd.github.raw")
                    .timeout(Duration.ofMinutes(5))
                    .GET().build();
            HttpResponse<InputStream> res = http.send(req, HttpResponse.BodyHandlers.ofInputStream());
            try (InputStream in = res.body()) {
                if (res.statusCode() != 200) {
                    throw new IllegalStateException("Failed downloading " + relative + " HTTP " + res.statusCode());
                }
                Files.copy(in, tmp, StandardCopyOption.REPLACE_EXISTING);
            }
            if (!gitBlobSha(Files.readAllBytes(tmp)).equals(sha)) {
                Files.deleteIfExists(tmp);
                throw new IllegalStateException("Checksum mismatch for " + relative);
            }
            try {
                Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING);
            }
            written++;
        }
        return written;
    }

    // ------------------------------------------------------------------ commit

    /**
     * Commits every file under localDir whose content differs from the repo's copy
     * (stored at repoPath/<relative path>) as a single commit.
     * Blocking: run on an async thread. Returns number of files committed (0 = nothing changed).
     */
    public int commitChangedFiles(Path localDir, String repoPath, String message) throws Exception {
        if (!hasToken()) throw new IllegalStateException(TOKEN_KEY + " missing from " + ENV_FILE_NAME);

        Path root = localDir.toAbsolutePath().normalize();
        if (!Files.isDirectory(root)) return 0;

        List<Path> locals;
        try (Stream<Path> walk = Files.walk(root)) {
            locals = walk.filter(Files::isRegularFile)
                    .filter(p -> !p.getFileName().toString().endsWith(".downloading"))
                    .collect(Collectors.toList());
        }

        // Retry if someone else pushed in between (non fast-forward).
        for (int attempt = 1; attempt <= 3; attempt++) {
            String headSha = getJson(API + "/git/ref/heads/" + BRANCH).getAsJsonObject("object").get("sha").getAsString();
            String baseTree = getJson(API + "/git/commits/" + headSha).getAsJsonObject("tree").get("sha").getAsString();
            Map<String, String> remoteShas = remoteShas(baseTree, repoPath);

            JsonArray treeEntries = new JsonArray();
            for (Path file : locals) {
                byte[] data = Files.readAllBytes(file);
                String relative = root.relativize(file).toString().replace('\\', '/');
                String repoFile = repoPath + "/" + relative;
                if (gitBlobSha(data).equals(remoteShas.get(repoFile))) continue; // unchanged

                JsonObject blobBody = new JsonObject();
                blobBody.addProperty("content", Base64.getEncoder().encodeToString(data));
                blobBody.addProperty("encoding", "base64");
                String blobSha = send("POST", API + "/git/blobs", blobBody, 201).get("sha").getAsString();

                JsonObject t = new JsonObject();
                t.addProperty("path", repoFile);
                t.addProperty("mode", "100644");
                t.addProperty("type", "blob");
                t.addProperty("sha", blobSha);
                treeEntries.add(t);
            }
            if (treeEntries.isEmpty()) return 0;

            JsonObject treeBody = new JsonObject();
            treeBody.addProperty("base_tree", baseTree);
            treeBody.add("tree", treeEntries);
            String newTree = send("POST", API + "/git/trees", treeBody, 201).get("sha").getAsString();

            JsonObject who = new JsonObject();
            who.addProperty("name", COMMITTER_NAME);
            who.addProperty("email", COMMITTER_EMAIL);
            JsonObject commitBody = new JsonObject();
            commitBody.addProperty("message", message);
            commitBody.addProperty("tree", newTree);
            JsonArray parents = new JsonArray();
            parents.add(headSha);
            commitBody.add("parents", parents);
            commitBody.add("author", who);
            commitBody.add("committer", who);
            String newCommit = send("POST", API + "/git/commits", commitBody, 201).get("sha").getAsString();

            JsonObject refBody = new JsonObject();
            refBody.addProperty("sha", newCommit);
            refBody.addProperty("force", false);
            try {
                send("PATCH", API + "/git/refs/heads/" + BRANCH, refBody, 200);
                return treeEntries.size();
            } catch (HttpStatusException e) {
                if (e.status != 422 || attempt == 3) throw e;
                Bingo4.getInstance().getLogger().warning("Branch moved during commit, retrying (" + attempt + "/3)");
            }
        }
        return 0;
    }

    /** repoFilePath -> blob sha for every file under repoPath in the given tree. Empty if the folder doesn't exist. */
    private Map<String, String> remoteShas(String treeSha, String repoPath) throws Exception {
        Map<String, String> map = new HashMap<>();
        HttpResponse<String> res = http.send(base(API + "/git/trees/" + treeSha + "?recursive=1").GET().build(),
                HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() != 200) throw new HttpStatusException(res.statusCode(), res.body());
        JsonObject json = JsonParser.parseString(res.body()).getAsJsonObject();
        // If truncated we simply treat everything as changed (safe, just commits more).
        for (JsonElement el : json.getAsJsonArray("tree")) {
            JsonObject e = el.getAsJsonObject();
            if (!e.get("type").getAsString().equals("blob")) continue;
            String path = e.get("path").getAsString();
            if (path.startsWith(repoPath + "/")) map.put(path, e.get("sha").getAsString());
        }
        return map;
    }

    // ------------------------------------------------------------------ http helpers

    private HttpRequest.Builder base(String url) {
        HttpRequest.Builder b = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/vnd.github+json")
                .header("X-GitHub-Api-Version", "2022-11-28")
                .header("User-Agent", "Ascension-Cloud")
                .timeout(Duration.ofSeconds(60));
        String t = token();
        if (t != null && !t.isBlank()) b.header("Authorization", "Bearer " + t);
        return b;
    }

    private JsonObject getJson(String url) throws Exception {
        HttpResponse<String> res = http.send(base(url).GET().build(), HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() != 200) throw new HttpStatusException(res.statusCode(), res.body());
        return JsonParser.parseString(res.body()).getAsJsonObject();
    }

    private JsonObject send(String method, String url, JsonObject body, int expected) throws Exception {
        HttpRequest req = base(url)
                .header("Content-Type", "application/json")
                .timeout(Duration.ofMinutes(2))
                .method(method, HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();
        HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() != expected) throw new HttpStatusException(res.statusCode(), res.body());
        return JsonParser.parseString(res.body()).getAsJsonObject();
    }

    private static String encodePath(String path) {
        return Arrays.stream(path.split("/"))
                .map(p -> URLEncoder.encode(p, StandardCharsets.UTF_8).replace("+", "%20"))
                .collect(Collectors.joining("/"));
    }

    /** Same hash git uses for a file: sha1("blob <len>\0" + bytes). */
    public static String gitBlobSha(byte[] data) throws Exception {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        sha1.update(("blob " + data.length + "\0").getBytes(StandardCharsets.UTF_8));
        sha1.update(data);
        StringBuilder sb = new StringBuilder();
        for (byte b : sha1.digest()) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static class HttpStatusException extends IOException {
        public final int status;
        public HttpStatusException(int status, String body) {
            super("GitHub returned HTTP " + status + ": " + body);
            this.status = status;
        }
    }
}