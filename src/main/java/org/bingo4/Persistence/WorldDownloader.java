package org.bingo4.Persistence;

import org.bingo4.Bingo4;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Duration;
import java.util.Comparator;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class WorldDownloader {

    private static final String OWNER = "Gradyc01";
    private static final String REPO = "AscensionWorlds";
    private static final String BRANCH = "main";

    private final JavaPlugin plugin = Bingo4.getInstance();

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public WorldDownloader() {
    }

    /**
     * Downloads one selected seed asynchronously.
     *
     * Returns true when the world is ready.
     */
    public CompletableFuture<Boolean> downloadWorld(String seed, String uuid) {

        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            try {
                downloadWorldInternal(seed, uuid);
                future.complete(true);

            } catch (Exception e) {

                plugin.getLogger().severe(
                        "Failed to download world " + seed
                );

                e.printStackTrace();

                future.complete(false);
            }
        });

        return future;
    }

    /**
     * Handles the actual GitHub download.
     */
    private void downloadWorldInternal(String seed, String uuid) throws Exception {

        String seedName = String.valueOf(seed);

        Path worldContainer = Bukkit.getServer()
                .getLevelDirectory()
                .resolve("dimensions")
                .resolve(Bingo4.getInstance().getName())
                .toAbsolutePath()
                .normalize();

        Path destination = worldContainer.resolve(uuid).normalize();

        if (!destination.startsWith(worldContainer)) {
            throw new IllegalArgumentException("Invalid seed path");
        }

        // Don't download an existing world again.
        if (Files.exists(destination)) {

            if (Files.exists(destination.resolve("level.dat"))) {
                plugin.getLogger().info(
                        "World already exists. Skipping download: " + seed
                );
                return;
            }

            throw new IllegalStateException(
                    "World folder exists but level.dat is missing: "
                            + destination
            );
        }

        Path temporary = worldContainer.resolve(
                seedName + ".downloading"
        ).normalize();

        if (!temporary.startsWith(worldContainer)) {
            throw new IllegalArgumentException("Invalid temporary path");
        }

        // Remove any incomplete previous download.
        deleteDirectory(temporary);
        Files.createDirectories(temporary);

        try {

            plugin.getLogger().info(
                    "Fetching GitHub world files for seed: " + seed
            );

            // Retrieve the complete repository file tree.
            String treeUrl =
                    "https://api.github.com/repos/"
                            + OWNER + "/" + REPO
                            + "/git/trees/" + BRANCH
                            + "?recursive=1";

            HttpRequest treeRequest = HttpRequest.newBuilder()
                    .uri(URI.create(treeUrl))
                    .header("Accept", "application/vnd.github+json")
                    .header("User-Agent", "Paper-WorldDownloader")
                    .timeout(Duration.ofSeconds(60))
                    .GET()
                    .build();

            HttpResponse<String> treeResponse = http.send(
                    treeRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (treeResponse.statusCode() != 200) {
                throw new IllegalStateException(
                        "GitHub API returned "
                                + treeResponse.statusCode()
                                + ": " + treeResponse.body()
                );
            }

            JsonObject json = JsonParser.parseString(
                    treeResponse.body()
            ).getAsJsonObject();

            if (json.has("truncated")
                    && json.get("truncated").getAsBoolean()) {

                throw new IllegalStateException(
                        "GitHub repository tree was truncated"
                );
            }

            JsonArray tree = json.getAsJsonArray("tree");

            String prefix = "worlds/" + seedName + "/";

            int downloaded = 0;
            long totalBytes = 0;

            // Download only files belonging to the selected seed.
            for (int i = 0; i < tree.size(); i++) {

                JsonObject entry = tree.get(i).getAsJsonObject();

                if (!entry.get("type").getAsString().equals("blob")) {
                    continue;
                }

                String filePath = entry.get("path").getAsString();

                if (!filePath.startsWith(prefix)) {
                    continue;
                }

                String relativePath = filePath.substring(
                        prefix.length()
                );

                if (relativePath.isEmpty()) {
                    continue;
                }

                Path file = temporary.resolve(relativePath).normalize();

                if (!file.startsWith(temporary)) {
                    throw new IllegalStateException(
                            "Invalid repository path: " + filePath
                    );
                }

                Files.createDirectories(file.getParent());

                // Encode each path segment separately.
                String encodedPath = java.util.Arrays.stream(
                        filePath.split("/")
                ).map(part -> URLEncoder.encode(
                        part, StandardCharsets.UTF_8
                ).replace("+", "%20")).collect(
                        java.util.stream.Collectors.joining("/")
                );

                String rawUrl =
                        "https://raw.githubusercontent.com/"
                                + OWNER + "/" + REPO + "/"
                                + BRANCH + "/" + encodedPath;

                HttpRequest fileRequest = HttpRequest.newBuilder()
                        .uri(URI.create(rawUrl))
                        .header("User-Agent", "Paper-WorldDownloader")
                        .timeout(Duration.ofMinutes(5))
                        .GET()
                        .build();

                HttpResponse<InputStream> fileResponse = http.send(
                        fileRequest,
                        HttpResponse.BodyHandlers.ofInputStream()
                );

                try (InputStream input = fileResponse.body()) {

                    if (fileResponse.statusCode() != 200) {
                        throw new IllegalStateException(
                                "Failed downloading " + filePath
                                        + ". HTTP "
                                        + fileResponse.statusCode()
                        );
                    }

                    Files.copy(
                            input,
                            file,
                            StandardCopyOption.REPLACE_EXISTING
                    );
                }

                // Verify the downloaded file against GitHub's file size.
                long expectedSize = entry.get("size").getAsLong();
                long actualSize = Files.size(file);

                if (actualSize != expectedSize) {

                    throw new IllegalStateException(
                            "File size mismatch: " + filePath
                                    + " | Expected: " + expectedSize
                                    + " | Received: " + actualSize
                    );
                }

                if (actualSize == 0) {
                    throw new IllegalStateException(
                            "Empty file detected: " + filePath
                    );
                }

                downloaded++;
                totalBytes += actualSize;

                plugin.getLogger().info(
                        "Downloaded: " + relativePath
                                + " (" + actualSize + " bytes)"
                );
            }

            // Ensure files were actually found.
            if (downloaded == 0) {
                throw new IllegalStateException(
                        "No files found for seed: " + seed
                );
            }

            // Install the completed world.
            try {
                Files.move(
                        temporary,
                        destination,
                        StandardCopyOption.ATOMIC_MOVE
                );
                plugin.getLogger().info(String.format("Destination: %s", destination));

            } catch (AtomicMoveNotSupportedException e) {

                Files.move(temporary, destination);
            }

            plugin.getLogger().info(
                    "===================================="
            );

            plugin.getLogger().info(
                    "World download completed!"
            );

            plugin.getLogger().info(
                    "Seed: " + seed
            );

            plugin.getLogger().info(
                    "Files: " + downloaded
            );

            plugin.getLogger().info(
                    "Total bytes: " + totalBytes
            );

            plugin.getLogger().info(
                    "Destination: " + destination
            );

            plugin.getLogger().info(
                    "===================================="
            );

        } catch (Exception e) {

            // Never leave a partially downloaded world behind.
            deleteDirectory(temporary);

            throw e;
        }
    }

    /**
     * Recursively deletes a directory.
     */
    private void deleteDirectory(Path directory) throws Exception {

        if (!Files.exists(directory)) {
            return;
        }

        try (Stream<Path> paths = Files.walk(directory)) {

            for (Path path : paths.sorted(
                    Comparator.reverseOrder()
            ).toList()) {

                Files.deleteIfExists(path);
            }
        }
    }
}
