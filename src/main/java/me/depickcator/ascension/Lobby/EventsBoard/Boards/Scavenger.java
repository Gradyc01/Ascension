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

public class Scavenger extends Events {
    public Scavenger() {
        super("Scavenger", Material.FEATHER,  - 18.5,  + 104,  0);
    }
    @Override
    protected TextDisplay initTextDisplay() {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText(getKey(), TextUtil.GOLD, true, false),
                TextUtil.makeText("In a dimension like the OverWorld there are beings that have given up and taken this as their way of life." +
                        " Constantly wandering the world and picking up scraps left behind. " +
                        "Sometimes its a rare item, others are some gear from a fallen also trying to reach Ascension." +
                        " These items mean no use to them, but they are willing to trade it for something in return.", TextUtil.AQUA),
                TextUtil.makeText("\nThese scavengers offer you ", TextUtil.AQUA)
                        .append(TextUtil.makeText("Five Trades", TextUtil.GRAY))
                        .append(TextUtil.makeText(" that last for the ", TextUtil.AQUA))
                        .append(TextUtil.makeText("Whole Game", TextUtil.GRAY))
                        .append(TextUtil.makeText(" and each trade has a ", TextUtil.AQUA))
                        .append(TextUtil.makeText("Specific Reward", TextUtil.GRAY))
                        .append(TextUtil.makeText(" tied to it. Additionally if your team ", TextUtil.AQUA))
                        .append(TextUtil.makeText("Completes Three out of the Five Trades", TextUtil.GRAY))
                        .append(TextUtil.makeText(" the God of the dead rewards you with his ", TextUtil.AQUA))
                        .append(TextUtil.makeText(" the Scavenger thanks you by giving you his ", TextUtil.AQUA))
                        .append(TextUtil.makeText("Prized Book", TextUtil.DARK_PURPLE))
                        .append(TextUtil.makeText(" as a treat for doing so much business with him", TextUtil.AQUA))

        ));
        Location loc = new Location(plugin.getWorld(), getX() ,getY() - 1.5, getZ());

        return DisplayUtil.makeTextDisplay(loc, text, 270, 0, 440);
    }

    @Override
    protected ItemDisplay initItemDisplay() {
        return DisplayUtil.makeItemDisplay(new Location(plugin.getWorld(), getX(), getY() - 2.5, getZ() ),
                        new ItemStack(getMaterial()), 0, 270, 1);
    }
}
