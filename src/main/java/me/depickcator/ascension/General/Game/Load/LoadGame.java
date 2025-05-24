package me.depickcator.ascension.General.Game.Load;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Load.Sequences.*;
import me.depickcator.ascension.Persistence.SettingsWriter;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.List;

public class LoadGame extends GameLauncher {

    private ArmorStand spawnCoordsArmorStand;
    public final static String spawnCoordsArmorStandName = "SpawnCoords";
    private Location spawnCoordsLocation;
    public LoadGame(Location loc) {
        super();
        this.spawnCoordsLocation = loc;
        start();
    }

    @Override
    protected List<GameSequences> initSequence() {
        return List.of(
                new ClearEntityInteractions(),
                new SetBingoData(),
                new DeletePreviousIterations(),
                new SetSpawn(),
                new CenterSettingsConfigurations(),
                new BuildPlatform(),
                new BuildLobby(),
                new CallResetGame(),
                new CallLocationCheck()
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

    public Location getSpawnCoordsLocation() {
        return spawnCoordsLocation;
    }
}
