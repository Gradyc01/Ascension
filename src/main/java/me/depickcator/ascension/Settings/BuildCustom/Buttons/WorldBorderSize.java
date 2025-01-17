package me.depickcator.ascension.Settings.BuildCustom.Buttons;

import me.depickcator.ascension.Settings.BuildCustom.BuildCustom;
import me.depickcator.ascension.Settings.BuildCustom.ScalarButton;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class WorldBorderSize extends ScalarButton {
    public WorldBorderSize() {
        super(Material.GRASS_BLOCK, "World Border Size", 1000, 500, 5000);
    }


    @Override
    public boolean build(BuildCustom custom) {
        int size = getNumber();
        custom.setWorldBorderSize(size);
        return true;
    }

    @Override
    public List<Integer> getIncrementSpread() {
        return new ArrayList<>(List.of(
                -500,
                -250,
                -100,
                100,
                250,
                500
        ));

    }
}
