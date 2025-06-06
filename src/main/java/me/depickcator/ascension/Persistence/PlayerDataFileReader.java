package me.depickcator.ascension.Persistence;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.depickcator.ascension.Player.Data.PlayerStats;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PlayerDataFileReader {
    private final Player player;
    private JsonObject playerData;
    public PlayerDataFileReader(Player p) {
        this.player = p;
    }

    /*Turns the playerData into a JsonObject
    * Returns true if there is any player data */
    public boolean read() {
        String destination = "./plugins/Ascension/PlayerData/" + player.getUniqueId() + ".json";
        try {
            String jsonData = readFile(destination);
            JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
            playerData = jsonObject;
            return true;
        } catch (IOException e) {
            TextUtil.debugText(player.getName() + " has no file or is logging in for the first time");
        };
        return false;
    }

    public void setPlayerSettings(PlayerStats playerStats) {
        playerStats.setSetting(PlayerStats.foodDropsKey, readBoolean(playerData, PlayerStats.foodDropsKey));
        playerStats.setSetting(PlayerStats.nightVisionKey, readBoolean(playerData, PlayerStats.nightVisionKey));
        playerStats.setSetting(PlayerStats.autoPurchaseUnlocks, readBoolean(playerData, PlayerStats.autoPurchaseUnlocks));
    }

    private boolean readBoolean(JsonObject jsonObject, String key) {
        JsonElement element = jsonObject.get(key);
        if (element == null) return false;
        return element.getAsBoolean();
    }


    private String readFile(String saved) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(saved), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }


}
