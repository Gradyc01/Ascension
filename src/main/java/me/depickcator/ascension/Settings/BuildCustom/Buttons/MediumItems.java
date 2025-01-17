package me.depickcator.ascension.Settings.BuildCustom.Buttons;

import me.depickcator.ascension.Settings.BuildCustom.BuildCustom;
import me.depickcator.ascension.Settings.BuildCustom.ScalarButton;
import org.bukkit.Material;

public class MediumItems extends ScalarButton {
    public MediumItems() {
        super(Material.YELLOW_STAINED_GLASS_PANE, "Medium Items", 10);
    }


    @Override
    public boolean build(BuildCustom custom) {
        custom.setItemDistribution(1, getNumber());
        return true;
    }
}
