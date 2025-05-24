package me.depickcator.ascension.Persistence;

import com.google.gson.*;
import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.Load.LoadGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads bracket from JSON data stored in file
public class SettingsReader extends Readers  {
    private final Ascension plugin;
    private final String saved;
    private String settingsName;
    private Location spawnCoords;

    //makes reader
    public SettingsReader() {
        plugin = Ascension.getInstance();
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
        new LoadGame(spawnCoords);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "settings " + settingsName + " false");
//        plugin.getSettingsUI().setSettings();

    }




    /* consumes a JsonObject and returns bracket*/
    public void parseOutInformation(JsonObject jsonObject) {
        settingsName = jsonObject.get("Settings").getAsString();
        JsonArray coords = jsonObject.getAsJsonArray("Spawn");
        spawnCoords = parseOutLocation(coords);
    }


}
