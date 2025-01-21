package me.depickcator.ascension.Lobby;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Uncraftable.EnlightenedNugget;
import me.depickcator.ascension.Items.Uncraftable.HadesBook.HadesBook;
import me.depickcator.ascension.Items.Uncraftable.XPTome.XPTome;
import me.depickcator.ascension.Utility.ArmorStandUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.TextDisplay;

import java.util.ArrayList;
import java.util.List;

public class NPCBoard extends Boards {
    public NPCBoard() {
        initOtherDisplays();
    }
    @Override
    protected TextDisplay initTextDisplay() {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText("Menus", TextUtil.YELLOW, true, false)
//                TextUtil.makeText("\n Items that can only be earned through different objectives", TextUtil.AQUA)
        ));
        Location spawn = Ascension.getSpawn();
        Location loc = new Location(plugin.getWorld(), spawn.getX() , spawn.getY() + 105.4, spawn.getZ() - 18.45);

        return ArmorStandUtil.makeTextDisplay(loc, text, 0, 0, 450);
    }

    private void initOtherDisplays() {

    }

}
