package org.bingo4.General.Game.Reset.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Items.UnlockRecommender;

public class ResetBackpacks extends GameSequences {
    @Override
    public void run(GameLauncher game) {
//        Backpack.getInstance().resetBackpacks();
//        AscensionRewardBundle.getInstance().resetRewardBundles();
        plugin.getSettingsUI().getSettings().getTimeline().resetTimeline();
        UnlockRecommender.getInstance().clearRecipes();
        game.callback(5);
    }
}
