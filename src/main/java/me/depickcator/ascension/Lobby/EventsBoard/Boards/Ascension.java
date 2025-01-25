package me.depickcator.ascension.Lobby.EventsBoard.Boards;

import me.depickcator.ascension.Utility.ArmorStandUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Ascension extends Events {
    public Ascension() {
        super("Ascension", Material.END_PORTAL_FRAME,  - 18.5,  + 104,  0);
    }
    @Override
    protected TextDisplay initTextDisplay() {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText(getKey(), TextUtil.GOLD, true, false),
                TextUtil.makeText("In order to Ascend you must be given passage by ", TextUtil.AQUA)
                        .append(TextUtil.makeText("The GateKeeper", TextUtil.GRAY)),
                TextUtil.makeText("\nOnce you have a full enlightenment bar you will be ", TextUtil.AQUA)
                        .append(TextUtil.makeText("Ascension Ready", TextUtil.WHITE))
                        .append(TextUtil.makeText(" Bring the", TextUtil.AQUA))
                        .append(TextUtil.makeText(" Ascension key ", TextUtil.DARK_PURPLE))
                        .append(TextUtil.makeText("and ", TextUtil.AQUA))
                        .append(TextUtil.makeText("[Right Click]", TextUtil.DARK_GREEN))
                        .append(TextUtil.makeText(" the Gatekeeper for him to begin the ritual.", TextUtil.AQUA)),
                TextUtil.makeText("\nOnce Ascension begins, protect the Gatekeeper until the ritual is complete", TextUtil.AQUA)
        ));
        Location loc = new Location(plugin.getWorld(), getX() ,getY() - 1.5, getZ()/* + 1.5*/);

        return
                ArmorStandUtil.makeTextDisplay(loc, text, 270, 0, 300);
    }

    @Override
    protected ItemDisplay initItemDisplay() {
        return
                ArmorStandUtil.makeItemDisplay(new Location(plugin.getWorld(), getX() + 0.5, getY() - 2.5, getZ() /*- 4*/),
                        new ItemStack(getMaterial()), 0, 270, 1.5);
    }
}
