package me.depickcator.ascension.Settings.BuildCustom.Buttons;

import me.depickcator.ascension.Settings.BuildCustom.BuildCustom;
import me.depickcator.ascension.Settings.BuildCustom.ScalarButton;
import org.bukkit.Material;

public class EasyItems extends ScalarButton {
    public EasyItems() {
        super(Material.GREEN_STAINED_GLASS_PANE, "Easy Items", 5);
    }


    @Override
    public boolean build(BuildCustom custom) {
        custom.setItemDistribution(0, getNumber());
        return true;
    }
}
