package org.bingo4.Settings.BuildCustom.Buttons;

import org.bingo4.Settings.BuildCustom.BuildCustom;
import org.bingo4.Settings.BuildCustom.ScalarButton;
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
