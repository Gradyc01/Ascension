package org.bingo4.Settings.BuildCustom.Buttons;

import org.bingo4.Settings.BuildCustom.BuildCustom;
import org.bingo4.Settings.BuildCustom.ScalarButton;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class AscensionTimer extends ScalarButton {
    public AscensionTimer() {
        super(Material.OMINOUS_TRIAL_KEY, "Bingo4 Timer", 900, 0, 1800);
    }


    @Override
    public boolean build(BuildCustom custom) {
        custom.setAscensionTimer(getNumber());
        return true;
    }

    @Override
    public List<Integer> getIncrementSpread() {
        return new ArrayList<>(List.of(
                -120,
                -60,
                -30,
                30,
                60,
                120
        ));

    }
}
