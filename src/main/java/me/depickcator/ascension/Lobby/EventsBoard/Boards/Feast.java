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

public class Feast extends Events {
    public Feast() {
        super("Feast", Material.CAMPFIRE,  - 18.5,  + 104,  0);
    }
    @Override
    protected TextDisplay initTextDisplay() {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText("Feast", TextUtil.GOLD, true, false),
                TextUtil.makeText("Everyone in the world has been forced to gather around the run down Ascension ritual as it begins to erupt.", TextUtil.AQUA),
                TextUtil.makeText("\nAs Lightning starts striking down on to the world with Fireballs falling from the sky there also comes these", TextUtil.AQUA)
                        .append(TextUtil.makeText(" Chests.", TextUtil.GREEN)),
                TextUtil.makeText("\nThe loot from these includes many", TextUtil.AQUA)
                        .append(TextUtil.makeText(" Materials ", TextUtil.DARK_GRAY))
                        .append(TextUtil.makeText("along with the ", TextUtil.AQUA))
                        .append(TextUtil.makeText("Combat Essentials", TextUtil.DARK_GRAY))
                        .append(TextUtil.makeText(" and ", TextUtil.AQUA))
                        .append(TextUtil.makeText("First Aid.", TextUtil.DARK_GRAY))
        ));
        Location loc = new Location(plugin.getWorld(), getX() ,getY() - 1.5, getZ());

        return DisplayUtil.makeTextDisplay(loc, text, 270, 0, 280);
    }

    @Override
    protected ItemDisplay initItemDisplay() {
        return DisplayUtil.makeItemDisplay(new Location(plugin.getWorld(), getX(), getY() - 2.3, getZ()), new ItemStack(Material.CAMPFIRE), 0, 270, 1);
    }
}
