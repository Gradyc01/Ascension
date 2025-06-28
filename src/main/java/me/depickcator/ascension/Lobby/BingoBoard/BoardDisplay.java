package me.depickcator.ascension.Lobby.BingoBoard;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Lobby.Boards;
import me.depickcator.ascension.Lobby.NPCs.Bingo.BingoNPC;
import me.depickcator.ascension.Utility.DisplayUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BoardDisplay extends Boards {
    private final double x;
    private final double y;
    private final double z;
    private final List<DisplayBox> displayBoxes;
    public BoardDisplay() {
        x = spawn.getX() + 18.4;
        y = spawn.getY() + 105.3;
        z = spawn.getZ();
//        glassDisplay = new ArrayList<>();
//        greenDisplay = new ArrayList<>();
        displayBoxes = new ArrayList<>();
        initBackground();
        new BingoNPC(spawn.getX() + 17, spawn.getY() + 101, spawn.getZ() - 7, new ImmutablePair<>(45, 0));
    }

    @Override
    protected TextDisplay initTextDisplay() {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText("Board ", TextUtil.YELLOW, true, false)
        ));
        Location loc = new Location(plugin.getWorld(), spawn.getX() + 18.4 , spawn.getY() + 105.5, spawn.getZ());
        return DisplayUtil.makeTextDisplay(loc, text, 90, 0, 450);
    }

    private void initBackground() {
        double itemX = x - 0.4;
        double startingZ = z - 1.8;
        double itemY = y - 0.3;
        double itemZ = startingZ;
        for (int i = 1; i <= 25; i++) {
            Location loc = new Location(plugin.getSpawnWorld(), itemX, itemY, itemZ);
            DisplayBox box = new DisplayBox(loc);
            displayBoxes.add(box);
//            Player p = Bukkit.getPlayer("Depickcator");
//            box.showCompleted(p);
            if (i % 5 == 0) {
                itemY -= 0.9;
                itemZ = startingZ;
            } else {
                itemZ +=0.9;
            }
        }
    }


    public void initBoardItemsDisplay() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[tag=bingo_items]");
        new BukkitRunnable() {
            @Override
            public void run() {
                double itemX = x - 0.4;
                double startingZ = z - 1.8;
                double itemY = y - 0.3;
                double itemZ = startingZ;
                List<ItemStack> items = plugin.getBingoData().getItems();
                if (items.size() != 25) return;
                for (int i = 1; i <= 25; i++) {
                    Location loc = new Location(plugin.getSpawnWorld(), itemX, itemY, itemZ);
                    ItemStack item = items.get(i - 1);
                    double size = item.getType().isBlock() ? 0.7 : 0.45;
//            double size = item.getType().isItem() ? 0.45 : 0.9;
                    ItemDisplay itemDisplay = DisplayUtil.makeItemDisplay(loc, items.get(i - 1), 0, 90, size);
                    itemDisplay.addScoreboardTag("bingo_items");
                    if (i % 5 == 0) {
                        itemY -= 0.9;
                        itemZ = startingZ;
                    } else {
                        itemZ +=0.9;
                    }
                }
            }
        }.runTaskLater(Ascension.getInstance(), 20);

    }

    public void displayBoard(Player viewer, Player target) {
        List<Boolean> completed = plugin.getBingoData().getItemsCompleted(target);
        for (int i = 0; i < displayBoxes.size(); i++) {
            DisplayBox box = displayBoxes.get(i);
//            box.teleport(viewer)
            if (completed.get(i)) {
                box.showCompleted(viewer);
            } else {
                box.showNotCompleted(viewer);
            }
        }
    }


}
