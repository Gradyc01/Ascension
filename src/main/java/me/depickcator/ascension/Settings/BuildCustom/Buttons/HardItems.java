package me.depickcator.ascension.Settings.BuildCustom.Buttons;

import me.depickcator.ascension.Settings.BuildCustom.BuildCustom;
import me.depickcator.ascension.Settings.BuildCustom.ScalarButton;
import org.bukkit.Material;

public class HardItems extends ScalarButton {
    public HardItems() {
        super(Material.RED_STAINED_GLASS_PANE, "Hard Items", 5);
    }


    @Override
    public boolean build(BuildCustom custom) {
        custom.setItemDistribution(2, getNumber());
        return true;
    }
}
