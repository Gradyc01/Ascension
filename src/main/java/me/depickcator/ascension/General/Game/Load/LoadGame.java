package me.depickcator.ascension.General.Game.Load;

import it.unimi.dsi.fastutil.io.TextIO;
import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Load.Sequences.*;
import me.depickcator.ascension.Persistence.SettingsWriter;
import me.depickcator.ascension.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.List;

public class LoadGame extends GameLauncher {

    private ArmorStand spawnCoordsArmorStand;
    public final static String spawnCoordsArmorStandName = "SpawnCoords";
    private Location spawnCoordsLocation;
    private Location lobbyCoordsLocation;
    private long seed;
    public LoadGame(Location gameSpawnCoords, Location lobbyCoords, long seed) {
        super();
        this.spawnCoordsLocation = gameSpawnCoords;
        this.lobbyCoordsLocation = lobbyCoords;
        this.seed = seed;
        start();
    }

    @Override
    protected List<GameSequences> initSequence() {
        return List.of(
//                new BuildWorlds(spawnCoordsLocation, seed), //TODO: Move to ResetGame???
//                new SetSpawn(spawnCoordsLocation), //TODO: Move to ResetGame???
                new ClearEntityInteractions(),
                new SetBingoData(),
                new DeletePreviousIterations(),
//                new CenterSettingsConfigurations(), //TODO: Move to ResetGame???
//                new BuildPlatform(), //TODO: Move to ResetGame???
                new BuildLobby(lobbyCoordsLocation),
                new CallResetGame()
//                new CallLocationCheck() //TODO: Move to ResetGame???
        );
    }

    @Override
    protected boolean canStart() {
        return true;
    }

    @Override
    protected void end() {
        TextUtil.debugText("Game has finished loading!");
    }

    public ArmorStand getSpawnCoordsArmorStand() {
        return spawnCoordsArmorStand;
    }

    public void setSpawnCoordsArmorStand(ArmorStand spawnCoordsArmorStand) {
        this.spawnCoordsArmorStand = spawnCoordsArmorStand;
    }

    public long getSeed() {
        return seed;
    }

    public Location getSpawnCoordsLocation() {
        return spawnCoordsLocation;
    }

    public void setSpawnCoordsLocation(Location spawnCoordsLocation) {
        this.spawnCoordsLocation = spawnCoordsLocation;
        TextUtil.debugText("SpawnCoordsLocation: " + spawnCoordsLocation.getWorld().getName());
    }
}
