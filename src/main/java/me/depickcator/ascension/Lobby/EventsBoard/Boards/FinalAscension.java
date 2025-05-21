package me.depickcator.ascension.Lobby.EventsBoard.Boards;

import me.depickcator.ascension.Utility.DisplayUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FinalAscension extends Events {
    public FinalAscension() {
        super("Final Ascension", Material.IRON_SWORD,  - 18.5,  + 104,  0);
    }
    @Override
    protected TextDisplay initTextDisplay() {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText(getKey(), TextUtil.GOLD, true, false),
                TextUtil.makeText("You start feeling the pressure of being in the Overworld. " +
                        "It's starting to collapse you from within. " +
                        "This is your final chance to make a stand and fight past the weight", TextUtil.AQUA),
                TextUtil.makeText("\nThere is now a ", TextUtil.AQUA)
                        .append(TextUtil.makeText("Timer ", TextUtil.DARK_PURPLE))
                        .append(TextUtil.makeText("before you and the rest of your team gets ", TextUtil.AQUA))
                        .append(TextUtil.makeText("Vaporized", TextUtil.DARK_PURPLE))
                        .append(TextUtil.makeText(" by the Overworld. Be the ", TextUtil.AQUA))
                        .append(TextUtil.makeText("Last Team Standing", TextUtil.DARK_PURPLE))
                        .append(TextUtil.makeText(" and Ascension will be yours", TextUtil.AQUA))
        ));
        Location loc = new Location(plugin.getWorld(), getX() ,getY() - 1.5, getZ());

        return DisplayUtil.makeTextDisplay(loc, text, 270, 0, 250);
    }

    @Override
    protected ItemDisplay initItemDisplay() {
        return DisplayUtil.makeItemDisplay(new Location(plugin.getWorld(), getX(), getY() - 2.5, getZ() ),
                        new ItemStack(getMaterial()), 0, 270, 1);
    }
}
