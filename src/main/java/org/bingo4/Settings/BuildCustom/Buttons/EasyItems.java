package org.bingo4.Settings.BuildCustom.Buttons;

import org.bingo4.Settings.BuildCustom.BuildCustom;
import org.bingo4.Settings.BuildCustom.ScalarButton;
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
