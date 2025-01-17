package me.depickcator.ascension.Settings.BuildCustom.Buttons;

import me.depickcator.ascension.Settings.BuildCustom.BuildCustom;
import me.depickcator.ascension.Settings.BuildCustom.ScalarButton;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class AscensionTimer extends ScalarButton {
    public AscensionTimer() {
        super(Material.OMINOUS_TRIAL_KEY, "Ascension Timer", 900, 0, 1800);
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
