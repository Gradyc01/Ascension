package me.depickcator.ascension.Persistence;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.Reset.ResetGame;
import me.depickcator.ascension.Utility.TextUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.*;
import java.util.InvalidPropertiesFormatException;
import java.util.Random;

public class SeedChooser extends Readers {
    public SeedChooser() {

    }

    public Pair<Location, Long> findSeed() {
        try {
            String jsonData = readFile("./plugins/Ascension/ascension_seeds.json");
            JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
            JsonArray seeds = jsonObject.get("Seeds").getAsJsonArray();
            if (seeds.isEmpty())
                throw new InvalidPropertiesFormatException("Seed list contains invalid number of seeds");
            Random r = new Random();
            TextUtil.debugText("Number of seeds: " + seeds.size());
            int seedIndex = r.nextInt(seeds.size());
            JsonObject newLocation = seeds.get(seedIndex).getAsJsonObject();
            long seed = newLocation.get("Seed").getAsLong();
            Location loc = parseOutLocation(newLocation.get("SpawnCoords").getAsJsonArray());
            return new ImmutablePair<>(loc, seed);

//            SettingsWriter writer = new SettingsWriter(
//                    parseOutLocation(spawnCoords.get(seedIndex).getAsJsonArray()),
//                    Ascension.getInstance().getSettingsUI().getSettings());
//            writer.open();
//            writer.write();
//            writer.close();
        } catch (IOException e) {
            TextUtil.debugText("Could not find file, returning old pair");
            return Pair.of(Ascension.getSpawn(), Ascension.getInstance().getWorld().getSeed());
        }
    }
}

