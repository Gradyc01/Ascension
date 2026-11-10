package org.bingo4.Persistence;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bingo4.Bingo4;
import org.bingo4.General.Game.Load.LoadGame;
import org.bingo4.commands.SetSetting;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.IOException;

// Represents a reader that reads bracket from JSON data stored in file
public class SettingsReader extends Readers  {
    private final Bingo4 plugin;
    private final String saved;
    private String settingsName;
    private Location spawnCoords;
    private Location lobbyCoords;
    private long seed;

    //makes reader
    public SettingsReader() {
        plugin = Bingo4.getInstance();
        this.saved = "./plugins/Ascension/ascension_save_file.json";
    }

    //          reads a bracket from file and returns it;
    //          if error occur throw IOException
    public void read() throws IOException {
        String jsonData = readFile(saved);
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        parseOutInformation(jsonObject);
        launch();
    }

    private void launch() {
        new LoadGame(spawnCoords, lobbyCoords, seed);
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "settings " + settingsName + " false");
//        plugin.getSettingsUI().setSettings();
        SetSetting.runSettingMode(settingsName, false);

    }




    /* consumes a JsonObject and returns bracket*/
    public void parseOutInformation(JsonObject jsonObject) {
        settingsName = jsonObject.get("Settings").getAsString();
        JsonArray coords = jsonObject.getAsJsonArray("Spawn");
        spawnCoords = parseOutLocation(coords);
        JsonArray lobby = jsonObject.getAsJsonArray("Lobby");
        lobbyCoords = parseOutLocation(lobby);
        seed = jsonObject.getAsJsonPrimitive("Seed").getAsLong();
    }


}
