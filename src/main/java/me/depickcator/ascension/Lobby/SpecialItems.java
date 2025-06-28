package me.depickcator.ascension.Lobby;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Uncraftable.EnlightenedNugget;
import me.depickcator.ascension.Items.Uncraftable.HadesBook.HadesBook;
import me.depickcator.ascension.Items.Uncraftable.XPTome.XPTome;
import me.depickcator.ascension.Utility.DisplayUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.TextDisplay;

import java.util.ArrayList;
import java.util.List;

public class SpecialItems extends Boards {
    public SpecialItems() {
        initOtherDisplays();
    }
    @Override
    protected TextDisplay initTextDisplay() {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText("SPECIAL ITEMS", TextUtil.YELLOW, true, false)
//                TextUtil.makeText("\n Items that can only be earned through different objectives", TextUtil.AQUA)
        ));
        Location loc = new Location(plugin.getSpawnWorld(), spawn.getX() , spawn.getY() + 105.4, spawn.getZ() - 18.45);

        return DisplayUtil.makeTextDisplay(loc, text, 0, 0, 450);
    }

    private void initOtherDisplays() {
        double x = spawn.getX();
        double y = spawn.getY() + 102.5;
        double z = spawn.getZ() - 18.45;
        DisplayUtil.makeTextDisplay(new Location(plugin.getSpawnWorld(), x, y + 0.7, z), initHadesBook(), 0, 0, 140);
        DisplayUtil.makeTextDisplay(new Location(plugin.getSpawnWorld(), x + 3.5, y + 0.7, z), initEnlightenmentNugget(), 0, 0, 140);
        DisplayUtil.makeTextDisplay(new Location(plugin.getSpawnWorld(), x - 3.5, y + 0.7, z), initXPTome(), 0, 0, 140);
        DisplayUtil.makeItemDisplay(new Location(plugin.getSpawnWorld(), x - 3.5, y - 0.3, z), XPTome.getInstance().getItem(), 0, 0, 1);
        DisplayUtil.makeItemDisplay(new Location(plugin.getSpawnWorld(), x, y - 0.3, z), HadesBook.getInstance().getItem(), 0, 0, 1);
        DisplayUtil.makeItemDisplay(new Location(plugin.getSpawnWorld(), x + 3.5, y - 0.3, z), EnlightenedNugget.getInstance().getItem(), 0, 0, 1);
    }

    private List<Component> initHadesBook() {
        return new ArrayList<>(List.of(
                TextUtil.makeText("Hades' Book", TextUtil.GOLD, true, false),
                TextUtil.makeText("\n A Book from the God of the Dead. " +
                        "This remnant of the gods allows whoever to grant " +
                        "themselves any item that they so wish from the board.", TextUtil.AQUA)
        ));
    }

    private List<Component> initXPTome() {
        return new ArrayList<>(List.of(
                TextUtil.makeText("XP Tome", TextUtil.GOLD, true, false),
                TextUtil.makeText("\n The XP Tome is often found in the hands of artisans. " +
                        "The XP Tome allows any player to gain 70 Skill Experience in " +
                        "any Skill of their choice", TextUtil.DARK_GREEN)
        ));
    }

    private List<Component> initEnlightenmentNugget() {
        return new ArrayList<>(List.of(
                TextUtil.makeText("Enlightenment Nugget", TextUtil.GOLD, true, false),
                TextUtil.makeText("\n It is said to have been crafted by a " +
                        "deity of light. The Enlightenment nugget grants " +
                        "extra enlightenment for those that can bear its power", TextUtil.DARK_GREEN)
        ));
    }
}
