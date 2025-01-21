package me.depickcator.ascension.Lobby;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.ArmorStandUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.TextDisplay;

import java.util.ArrayList;
import java.util.List;

public class WelcomeBoard extends Boards {
    public WelcomeBoard() {

    }
    @Override
    protected TextDisplay initTextDisplay() {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText("ASCENSION", TextUtil.YELLOW, true, false),
                TextUtil.makeText("\n\n\n Build up enlightenment by gathering items on the Board and fighting over objectives", TextUtil.AQUA),
                TextUtil.makeText("\n\n Earn Souls and rewards from leveling skills to unlock Crafts, etc.", TextUtil.AQUA),
                TextUtil.makeText("\n\n Properly equip yourself to defend against the other opponents", TextUtil.AQUA),
                TextUtil.makeText("\n\n Bring an offering to ", TextUtil.AQUA)
                        .append(TextUtil.makeText("the Gatekeeper", TextUtil.GRAY))
                        .append(TextUtil.makeText(" to began your Ascension", TextUtil.AQUA))
        ));
        Location spawn = Ascension.getSpawn();
        Location loc = new Location(plugin.getWorld(), spawn.getX(), spawn.getY() + 102.2, spawn.getZ() + 18.45);
        return ArmorStandUtil.makeTextDisplay(loc, text, 180, 0, 450);
//        ArmorStandUtil.
    }
}
