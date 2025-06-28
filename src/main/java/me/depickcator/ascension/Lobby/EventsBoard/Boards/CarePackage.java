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

public class CarePackage extends Events {
    public CarePackage() {
        super("Care Package", Material.CHEST,  - 18.5,  + 104,  0);
    }
    @Override
    protected TextDisplay initTextDisplay() {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText("Care Package", TextUtil.GOLD, true, false),
                TextUtil.makeText("Its hard to keep all dimensions intact, once in a while the dimensions will collide with each other. " +
                        "The Care Package results from the Nether breaking open and falling into the Overworld.", TextUtil.AQUA),
                TextUtil.makeText("\n It brings", TextUtil.AQUA)
                        .append(TextUtil.makeText(" Nether items ", TextUtil.GREEN))
                        .append(TextUtil.makeText("including ", TextUtil.AQUA))
                        .append(TextUtil.makeText("Netherite", TextUtil.DARK_GRAY))
                        .append(TextUtil.makeText(" and ", TextUtil.AQUA))
                        .append(TextUtil.makeText("Nether Stars", TextUtil.DARK_PURPLE))
                        .append(TextUtil.makeText(" as well as some ", TextUtil.AQUA))
                        .append(TextUtil.makeText("Enlightenment Nuggets. ", TextUtil.WHITE)),
                TextUtil.makeText("\nThese items will be of great use in your journey towards Ascension", TextUtil.AQUA)
        ));
        Location loc = new Location(plugin.getSpawnWorld(), getX() ,getY() - 1.7, getZ());

        return DisplayUtil.makeTextDisplay(loc, text, 270, 0, 300);
    }

    @Override
    protected ItemDisplay initItemDisplay() {
        return
                DisplayUtil.makeItemDisplay(new Location(plugin.getSpawnWorld(), getX(), getY() - 2.5, getZ()),
                        new ItemStack(Material.CHEST), 0, 270, 1.5);
    }
}
