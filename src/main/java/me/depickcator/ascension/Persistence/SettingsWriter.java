package me.depickcator.ascension.Persistence;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Settings.Settings;
import org.bukkit.Location;

//import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SettingsWriter {
    private String destination;
    private PrintWriter writer;
    private final Settings settings;
    private final Location location;

    //EFFECTS: writes to the destination file
    public SettingsWriter(Location location, Settings settings) {
        this.destination = "./plugins/Ascension/ascension_save_file.json";
        this.settings = settings;
        this.location = location;
    }

    public SettingsWriter() {
        this(Ascension.getSpawn(), Ascension.getInstance().getSettingsUI().getSettings());
    }

    //MODIFIES: this
    //EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    //         be opened for writing
    public void open() throws FileNotFoundException {
//        writer = new PrintWriter(new File(destination));
        File file = new File(destination);
        File parentDir = file.getParentFile();


        // If the parent directory doesn't exist, create it
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();  // Creates the necessary directories if they don't exist
        }

        writer = new PrintWriter(file);
    }

    //MODIFIES: this
    //EFFECTS: writes Ascension into a JSON file
    public void write() {
        JsonObject json = new JsonObject();
        json.add("Spawn", writeCoordinates(location));
        json.add("Settings", new JsonPrimitive(settings.getName()));

        saveToFile(json.toString());
    }

    private JsonArray writeCoordinates(Location loc) {
        JsonArray coords = new JsonArray(3);
        coords.add(loc.getX());
        coords.add(loc.getY());
        coords.add(loc.getZ());
        return coords;
    }

    //MODIFIES: this
    //EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes the string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
