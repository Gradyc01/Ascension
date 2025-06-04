package me.depickcator.ascension.Persistence;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.depickcator.ascension.Utility.TextUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AscensionStats {
    private final Gson gson;
    public AscensionStats() {
        this.gson = new Gson();
    }

    // Method to read JSON files and collect stats
    public List<PlayerStats> collectStats(String directoryPath) throws IOException {
        List<PlayerStats> playerStats = new ArrayList<>();

        // Get all JSON files in the directory
        List<Path> jsonFiles = getJsonFilesFromDirectory(directoryPath);

        for (Path jsonFile : jsonFiles) {
            // Read the file into a JsonObject
            try (FileReader reader = new FileReader(jsonFile.toFile())) {
                JsonObject json = gson.fromJson(reader, JsonObject.class);
                String fileName = jsonFile.getFileName().toString();
                UUID playerUUID = UUID.fromString(fileName.replace(".json", ""));
                // Extract stats from JSON object
                PlayerStats stats = new PlayerStats(playerUUID, getPlayerName(json));
                stats.addStat("kills", json.get("kills").getAsInt());
                stats.addStat("wins", json.get("wins").getAsInt());
                stats.addStat("items_obtained", json.get("items_obtained").getAsInt());


                // The key is the filename (UUID)
//                playerStatsMap.put(playerUUID, stats);
                playerStats.add(stats);
            }
        }

//        return playerStatsMap;
        return playerStats;
    }

    /*Gets a List of playerStats and the Stat statname,
    Returns a List of Map entries containing the UUIDs and the stat number*/
    public List<Map.Entry<PlayerStats, Integer>> getSortedStatList(String statName, List<PlayerStats> playerStats) {
        Map<PlayerStats, Integer> playerKillsMap = new HashMap<>();
        /*Pair is Stat Value, ranking*/
        Map<UUID, Pair<Integer, Integer>> uuidStatWithRankingMap = new HashMap<>();

        for (PlayerStats player : playerStats) {
            playerKillsMap.put(player, player.getStat(statName));
        }

        List<Map.Entry<PlayerStats, Integer>> sortedList = new ArrayList<>(playerKillsMap.entrySet());
        sortedList.sort((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));

        for (int i = 0; i < sortedList.size(); i++) {
            Map.Entry<PlayerStats, Integer> entry = sortedList.get(i);
            UUID uuid = entry.getKey().getUUID();
            Integer statValue = entry.getValue();
            uuidStatWithRankingMap.put(uuid, new ImmutablePair<>(statValue, i + 1));  // Store rank as 1-based index
        }
        cacheStatsAndRankings(statName, uuidStatWithRankingMap);
        return sortedList;
    }

    /*Returns a Value and Ranking pair for UUID playerUUID and Stat statName*/
    public Pair<Integer, Integer> readStatsAndRankings(String statName, UUID playerUUID) {
        String destination = "./plugins/Ascension/Rankings/" + statName + ".json";
        try {
            JsonObject jsonObject = JsonParser.parseString(readFile(destination)).getAsJsonObject();
            JsonObject data = jsonObject.get(playerUUID.toString()).getAsJsonObject();
            int value = data.get("value").getAsInt();
            int ranking = data.get("ranking").getAsInt();
            return new ImmutablePair<>(value, ranking);
        } catch (Exception e) {
            TextUtil.debugText("[Error] in readStatsAndRankings()" + e.getMessage());
        }
        return null;
    }

    /*Caches stats in destination Map is UUID, with Stat Value and Ranking as the pair*/
    private void cacheStatsAndRankings(String statName, Map<UUID, Pair<Integer, Integer>> uuidStatRankingsMap) {
        String destination = "./plugins/Ascension/Rankings/" + statName + ".json";
        JsonObject jsonObject = new JsonObject();

        JsonWriter writer = new JsonWriter(destination);
        try {
            writer.open();
            for (Map.Entry<UUID, Pair<Integer, Integer>> entry : uuidStatRankingsMap.entrySet()) {
                JsonObject playerStats = new JsonObject();
                playerStats.addProperty("value", entry.getValue().getLeft());
                playerStats.addProperty("ranking", entry.getValue().getRight());
                jsonObject.add(entry.getKey().toString(), playerStats);
            }
            writer.write(jsonObject);
            writer.close();
        } catch (IOException e) {
            TextUtil.debugText("[Error] in cacheStatsAndRankings()" + e.getMessage());
        }
    }


    private void addStatFromJsonObject(String statName, PlayerStats stats, JsonObject json) {
        stats.addStat(statName, json.get(statName).getAsInt());
    }


    private String getPlayerName(JsonObject json) {
        if (json.get("name") == null ) {
            return "Unknown";
        }
        return json.get("name").getAsString();
    }

    private String readFile(String saved) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(saved), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // Helper method to get JSON files from the directory
    private List<Path> getJsonFilesFromDirectory(String directoryPath) throws IOException {
        try {
            return Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(file -> file.toString().endsWith(".json"))
                    .collect(Collectors.toList());
        } catch (NoSuchFileException e) {
            TextUtil.debugText(e.getMessage());
            return Collections.emptyList();
        }
    }
}
