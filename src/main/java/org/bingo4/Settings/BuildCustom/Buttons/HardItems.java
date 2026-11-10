package org.bingo4.Settings.BuildCustom.Buttons;

import org.bingo4.Settings.BuildCustom.BuildCustom;
import org.bingo4.Settings.BuildCustom.ScalarButton;
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
