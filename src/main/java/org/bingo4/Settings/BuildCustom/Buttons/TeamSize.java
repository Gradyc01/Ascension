package org.bingo4.Settings.BuildCustom.Buttons;

import org.bingo4.Settings.BuildCustom.BuildCustom;
import org.bingo4.Settings.BuildCustom.ScalarButton;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class TeamSize extends ScalarButton {
    public TeamSize() {
        super(Material.PLAYER_HEAD, "Team Size", 3, 1, 25);
    }


    @Override
    public boolean build(BuildCustom custom) {
        custom.setTeamSize(getNumber());
        return true;
    }

    @Override
    public List<Integer> getIncrementSpread() {
        return new ArrayList<>(List.of(
                -5,
                -2,
                -1,
                1,
                2,
                5
        ));

    }
}
