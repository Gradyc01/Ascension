package me.depickcator.ascension.Settings.BuildCustom.Buttons;

import me.depickcator.ascension.Settings.BuildCustom.BuildCustom;
import me.depickcator.ascension.Settings.BuildCustom.ScalarButton;
import org.bukkit.Material;

public class GameScoreRequirement extends ScalarButton {
    public GameScoreRequirement() {
        super(Material.END_PORTAL_FRAME, "Score Requirement", 25, 1,100);
    }


    @Override
    public boolean build(BuildCustom custom) {
        custom.setAscensionGameScoreRequirement(getNumber());
        return true;
    }
}
