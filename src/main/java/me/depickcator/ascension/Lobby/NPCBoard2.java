package me.depickcator.ascension.Lobby;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Lobby.NPCs.*;
import me.depickcator.ascension.Utility.DisplayUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.Location;
import org.bukkit.entity.TextDisplay;

import java.util.ArrayList;
import java.util.List;

public class NPCBoard2 extends Boards {
    public NPCBoard2() {
        initOtherDisplays();
    }
    @Override
    protected TextDisplay initTextDisplay() {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText("Menus", TextUtil.YELLOW, true, false),
                TextUtil.makeText("\n[Right Click] ", TextUtil.GRAY).append(TextUtil.makeText("to view Menus"))
//                TextUtil.makeText("\n Items that can only be earned through different objectives", TextUtil.AQUA)
        ));
        Location loc = new Location(plugin.getSpawnWorld(), spawn.getX() - 13.3 , spawn.getY() + 104.5, spawn.getZ() + 13.3);

        return DisplayUtil.makeTextDisplay(loc, text, 225, 0, 450);
    }

    private void initOtherDisplays() {
        double x = spawn.getX();
        double y = spawn.getY() + 101;
        double z = spawn.getZ();
        new ViewBoard(x - 13, y, z + 13, new ImmutablePair<>(225, 0));
        new ViewUnlocks(x - 11, y, z + 15, new ImmutablePair<>(225, 0));
        new ViewMap(x - 15, y, z + 11, new ImmutablePair<>(225,0));
    }

}
