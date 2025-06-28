package me.depickcator.ascension.General.Game.Reset;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.General.Game.Load.Sequences.*;
import me.depickcator.ascension.General.Game.Relaunch.Sequences.UnloadWorld;
import me.depickcator.ascension.General.Game.Reset.Sequences.*;
import me.depickcator.ascension.General.Game.Start.Sequences.SetWorldBorder;
import me.depickcator.ascension.Persistence.SettingsWriter;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ResetGame extends GameLauncher {

    private final boolean saveStats;
    private final boolean rebuild;
    private final Location spawnLocation;
    private final long seed;
    public ResetGame(boolean saveStats, Location rebuildSpot, long seed) {
        super();
        this.saveStats = saveStats;
        this.rebuild = true;
        this.seed = seed;
        this.spawnLocation = rebuildSpot;
        start();
    }

    public ResetGame(boolean saveStats) {
        super();
        this.saveStats = saveStats;
        this.rebuild = false;
        this.spawnLocation = null;
        this.seed = -100;
        start();
    }

    public ResetGame() {
        this(false);
    }
    @Override
    protected List<GameSequences> initSequence() {
        List<World> worlds = new ArrayList<>();
        boolean reLocationCheck = true;
        if (!plugin.getWorld().getName().equals("world")) {
            worlds.add(plugin.getWorld());
            reLocationCheck = plugin.getWorld().getSeed() != seed;
            worlds.add(plugin.getNether());
        }

        List<GameSequences> sequences = new ArrayList<>(List.of(
                new ResetBackpacks(),
                new ReloadLobby(),
                new ResetTeams(),
                new ResetPlayers(),
                new SetWorldBorder(29999984),
                new LoadGameRules()
        ));
        if (saveStats) {
            sequences.addFirst(new SaveGameStatistics());
        }
        if (rebuild) {
            List<GameSequences> buildSequences = new ArrayList<>(List.of(
                    new BuildWorlds(spawnLocation, seed),
                    new SetSpawn(spawnLocation),
                    new CenterSettingsConfigurations(),
                    new BuildPlatform()
            ));
            if (reLocationCheck) buildSequences.add(new CallLocationCheck());
            sequences.addAll(0, buildSequences);
            sequences.addAll(List.of(
                    new UnloadWorld(worlds),
                    new DeleteUnusedWorlds()
            ));
        }
        return sequences;
    }

    @Override
    protected boolean canStart() {
        plugin.getGameState().setCurrentState(GameStates.LOBBY_NORMAL);

//        plugin.getSettingsUI().getSettings().getTimeline().resetTimeline();//TODO: Temporarily put this line in ResetBackpacks
        return true;
    }

    @Override
    protected void end() {
        TextUtil.debugText("Game has been Reset!");
        plugin.getGameState().setCurrentState(GameStates.LOBBY_NORMAL);

        SettingsWriter writer = new SettingsWriter();
        try {
            writer.open();
        } catch (Exception e) {
            TextUtil.debugText("Failed to open file" + e.getMessage());

        }
        writer.write();
        writer.close();
    }
}
