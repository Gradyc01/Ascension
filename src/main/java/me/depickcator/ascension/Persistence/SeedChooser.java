package me.depickcator.ascension.Persistence;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.*;
import java.util.InvalidPropertiesFormatException;
import java.util.Random;

public class SeedChooser extends Readers {
    public SeedChooser() {

    }

    public void findSeed() {
        try {
            String jsonData = readFile("./plugins/Ascension/ascension_seeds.json");
            JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
            JsonArray seeds = jsonObject.getAsJsonArray("seeds");
            JsonArray spawnCoords = jsonObject.getAsJsonArray("spawnCoords");
            if (seeds.size() != spawnCoords.size() || seeds.isEmpty()) throw new InvalidPropertiesFormatException("Seed list contains invalid number of seeds");
            Random r = new Random();
            TextUtil.debugText("Number of seeds: " + seeds.size());
            int seedIndex = r.nextInt(seeds.size());
            setSeed(seeds.get(seedIndex).getAsString());
            changeSeedInConfig(seeds.get(seedIndex).getAsString());
            SettingsWriter writer = new SettingsWriter(
                    parseOutLocation(spawnCoords.get(seedIndex).getAsJsonArray()),
                    Ascension.getInstance().getSettingsUI().getSettings());
            writer.open();
            writer.write();
            writer.close();
        } catch (IOException e) {
            TextUtil.debugText("Could not find file");
        }


    }

    private boolean changeSeedInConfig(String newSeed) {
        File serverPropertiesFile = new File(Ascension.getInstance().getServer().getWorldContainer(), "server.properties");
        try {
            // Read the file into memory
            BufferedReader reader = new BufferedReader(new FileReader(serverPropertiesFile));
            StringBuilder fileContent = new StringBuilder();
            String line;

            boolean seedChanged = false;

            // Go through each line and look for the 'level-seed=' line
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("level-seed=")) {
                    line = "level-seed=" + newSeed;
                    seedChanged = true;
                }
                fileContent.append(line).append("\n");
            }
            reader.close();

            if (!seedChanged) {
                // If no 'level-seed' line found, add it at the end
                fileContent.append("level-seed=").append(newSeed).append("\n");
            }

            // Write the updated content back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(serverPropertiesFile));
            writer.write(fileContent.toString());
            writer.close();

            return true;
        } catch (IOException e) {
            TextUtil.debugText("Failed to change Seed");
//            getLogger().log(Level.SEVERE, "Failed to change seed", e);
            return false;
        }
    }
    public void setSeed(String seed) {
        Ascension plugin = Ascension.getInstance();
        World overworld = createWorld("world_new", World.Environment.NORMAL, seed);
        World nether = createWorld("world_nether", World.Environment.NETHER, seed);
        createWorld("world_the_end", World.Environment.THE_END, seed);
        new File(Bukkit.getWorldContainer(), "world_new").renameTo(new File(Bukkit.getWorldContainer(), "world"));
        plugin.setWorld(overworld);
        plugin.setWorld(nether);
    }
//
    private World createWorld(String worldName, World.Environment environment, String seed) {
        WorldCreator wc = new WorldCreator(worldName);
        wc.seed(Long.parseLong(seed));
        wc.environment(environment);
        World w = Bukkit.createWorld(wc);
        Bukkit.unloadWorld(w, true);
        return w;
//        wc.createWorld();
    }

//    public void setSeed(String seed) {
//        // Path to the server.properties file
//        TextUtil.debugText("Setting seed: " + seed);
//        String filePath = "server.properties";
//
//        // Create a Properties object
//        Properties properties = new Properties();
//        TextUtil.debugText("File path: " + new File(filePath).getAbsolutePath());
//
//
//        try (FileInputStream input = new FileInputStream(filePath)) {
//            // Load the properties from the file
//            properties.load(input);
//            TextUtil.debugText("Iterating through File Input Stream for Seed: " + seed);
//
//            // Access specific properties using the getProperty() method
//            String propertyValue = properties.getProperty("level-seed");
//
//
//            TextUtil.debugText("Old seed: " + properties.getProperty("level-seed"));
//            properties.setProperty("level-seed", seed);
//            TextUtil.debugText("New seed: " + properties.getProperty("level-seed"));
//
//            try (FileOutputStream output = new FileOutputStream(filePath)) {
//                TextUtil.debugText("Iterating through File Output Stream for Seed: " + seed);
//                properties.store(output, "Updated level-seed value"); // Optional comment
//            }
//
//        } catch (IOException e) {
//            TextUtil.debugText("Could not find file for setting the seed");
//            e.printStackTrace();
//        }
//    }
}

