package me.depickcator.ascension.General.Game.Reset.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Items.Craftable.Unlocks.Backpack;
import me.depickcator.ascension.Items.Uncraftable.AscensionRewardBundle;
import me.depickcator.ascension.Items.UnlockRecommender;

public class ResetBackpacks extends GameSequences {
    @Override
    public void run(GameLauncher game) {
//        Backpack.getInstance().resetBackpacks();
        UnlockRecommender.getInstance().resetCraftCodes();
        AscensionRewardBundle.getInstance().resetRewardBundles();
        plugin.getSettingsUI().getSettings().getTimeline().resetTimeline();
        plugin.getSettingsUI().getSettings().getTimeline().getSoulShops().reset();
        game.callback(5);
    }
}
