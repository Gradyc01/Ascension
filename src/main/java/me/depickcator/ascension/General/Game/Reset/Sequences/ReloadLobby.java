package me.depickcator.ascension.General.Game.Reset.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import org.bukkit.GameRule;
import org.bukkit.World;

public class ReloadLobby extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        plugin.getLobby().updateLobby();

        game.callback();
    }
}
