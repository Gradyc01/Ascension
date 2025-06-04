package me.depickcator.ascension.Persistence;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerStats;
import me.depickcator.ascension.Teams.TeamStats;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class PlayerDataWriter {
    private final HashMap<String, Integer> intDataMap;
    private final HashMap<String, String> stringDataMap;
    private final PlayerData playerData;
    private final Player player;
    private final String destination;

    public PlayerDataWriter(PlayerData playerData) {
        this.playerData = playerData;
        this.player = playerData.getPlayer();
        intDataMap = initIntDataMap(this.playerData);
        stringDataMap = initStringDataMap(this.playerData);
        this.destination = "./plugins/Ascension/PlayerData/" + this.player.getUniqueId() + ".json";
        initializeWriter();
    }

    private HashMap<String, Integer> initIntDataMap(PlayerData playerData) {
        TeamStats teamStats = playerData.getPlayerTeam().getTeam().getTeamStats();
        PlayerStats pDStats = playerData.getPlayerStats();

        HashMap<String, Integer> intDataMap = new HashMap<>();
        intDataMap.put("deaths", pDStats.getDeaths());
        intDataMap.put("kills", pDStats.getKills());
        intDataMap.put("items_obtained", pDStats.getItemsObtained());
        intDataMap.put("wins", teamStats.isWin() ? 1 : 0);
        intDataMap.put("losses", teamStats.isWin() ? 0 : 1);
        intDataMap.put("total_game_score", teamStats.getGameScore());
        return intDataMap;
    }

    private HashMap<String, String> initStringDataMap(PlayerData playerData) {
        HashMap<String, String> stringDataMap = new HashMap<>();
        stringDataMap.put("name", playerData.getPlayer().getName());
        return stringDataMap;
    }

    public void writeNewData() {
        writeStats();
    }

    private void initializeWriter() {
        TextUtil.debugText("Initializing Writer for player: " + player.getName());
        try {
            String jsonData = readFile(destination);
            JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
            readStats(jsonObject);
        } catch (IOException e) {
            TextUtil.debugText("Player: "+ player.getName() + "'s Data is not found or is logging in for the first time");
        }
    }

    private void writeStats() {
        JsonObject json = new JsonObject();
        for (HashMap.Entry<String, Integer> entry : intDataMap.entrySet()) {
            json.addProperty(entry.getKey(), entry.getValue());
        }
        for (HashMap.Entry<String, String> entry : stringDataMap.entrySet()) {
            json.addProperty(entry.getKey(), entry.getValue());
        }
        JsonWriter jsonWriter = new JsonWriter(destination);
        try {
            jsonWriter.open();
            jsonWriter.write(json);
            jsonWriter.close();
        } catch (IOException e) {
            TextUtil.debugText("Failed to write stats to file");
        }

    }

    private void readStats(JsonObject jsonObject) {
        HashMap<String, Integer> intDataMap = new HashMap<>(this.intDataMap);
        for (HashMap.Entry<String, Integer> entry : intDataMap.entrySet()) {
            this.intDataMap.put(entry.getKey(), readStat(entry.getKey(), jsonObject) + entry.getValue());
        }
    }

    private int readStat(String stat, JsonObject jsonObject) {
        try {
            return jsonObject.get(stat).getAsInt();
        } catch (NullPointerException e) {
            TextUtil.debugText("[ERROR] Caught a null pointer on stat: " + stat + "Msg: " + e.getMessage());
            return 0;
        }
    }

    private String readFile(String saved) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(saved), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }


}
