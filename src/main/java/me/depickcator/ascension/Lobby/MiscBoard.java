package me.depickcator.ascension.Lobby;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Lobby.NPCs.WeaponsValues.WeaponValuesNPC;
import me.depickcator.ascension.Utility.ArmorStandUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.Location;
import org.bukkit.entity.TextDisplay;

import java.util.ArrayList;
import java.util.List;

public class MiscBoard extends Boards {
    public MiscBoard() {
        initOtherDisplays();
    }
    @Override
    protected TextDisplay initTextDisplay() {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText("Miscellaneous", TextUtil.YELLOW, true, false),
                TextUtil.makeText("\n[Right Click] ", TextUtil.GRAY).append(TextUtil.makeText("to view"))
//                TextUtil.makeText("\n Items that can only be earned through different objectives", TextUtil.AQUA)
        ));
        Location spawn = Ascension.getSpawn();
        Location loc = new Location(plugin.getWorld(), spawn.getX() + 13.3 , spawn.getY() + 104.5, spawn.getZ() - 13.3);

        return ArmorStandUtil.makeTextDisplay(loc, text, 45, 0, 450);
    }

    private void initOtherDisplays() {
        Location spawn = Ascension.getSpawn();
        double x = spawn.getX();
        double y = spawn.getY() + 101;
        double z = spawn.getZ();
        new WeaponValuesNPC(x + 13, y, z - 13, new ImmutablePair<>(45, 0));
    }

}
