package me.depickcator.ascension.Settings.BuildCustom.Buttons;

import me.depickcator.ascension.Settings.BuildCustom.BuildCustom;
import me.depickcator.ascension.Settings.BuildCustom.ScalarButton;
import org.bukkit.Material;

public class CustomItems extends ScalarButton {
    public CustomItems() {
        super(Material.ORANGE_STAINED_GLASS_PANE, "Custom Items", 5);
    }


    @Override
    public boolean build(BuildCustom custom) {
        custom.setItemDistribution(3, getNumber());
        return true;
    }
}
