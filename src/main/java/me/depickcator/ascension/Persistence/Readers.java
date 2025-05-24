package me.depickcator.ascension.Persistence;

import com.google.gson.JsonArray;
import me.depickcator.ascension.Ascension;
import org.bukkit.Location;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public abstract class Readers {
    //          read source file as string and returns;
    //          if error occur throw IOException
    protected String readFile(String saved) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(saved), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    protected Location parseOutLocation(JsonArray arr) {
        return new Location(
                Ascension.getInstance().getWorld(),
                arr.get(0).getAsDouble(),
                arr.get(1).getAsDouble(),
                arr.get(2).getAsDouble());
    }
}
