package me.depickcator.ascension.General.Game.ReSeed;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Load.LoadGame;
import me.depickcator.ascension.General.Game.ReSeed.Sequences.TakeNewSeed;
import me.depickcator.ascension.General.Game.Relaunch.GameRelaunch;
import me.depickcator.ascension.General.Game.Relaunch.Sequences.DeleteWorld;
import me.depickcator.ascension.General.Game.Relaunch.Sequences.KickPlayers;
import me.depickcator.ascension.General.Game.Relaunch.Sequences.UnloadWorld;
import me.depickcator.ascension.Persistence.SettingsReader;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ReSeed extends GameLauncher {

    public ReSeed() {
        super();
        plugin.getServer().setWhitelist(true);
        start();
    }
    @Override
    protected List<GameSequences> initSequence() {
        List<World> worlds = new ArrayList<>(List.of(
                plugin.getWorld(),
                plugin.getNether(),
                Bukkit.getWorld("world_the_end")));
        return List.of(
                new KickPlayers(),
                new UnloadWorld(worlds),
                new DeleteWorld(worlds),
                new TakeNewSeed()
        );
    }

    @Override
    protected boolean canStart() {
        return true;
    }

    @Override
    protected void end() {
        plugin.getServer().setWhitelist(false);
        new SettingsReader();
//        new GameRelaunch();
//        Runtime.getRuntime().halt(0);
//        plugin.getServer().restart();
    }
}
