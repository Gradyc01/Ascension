package org.bingo4.Lobby.BingoBoard;

import org.bingo4.Bingo4;
import org.bingo4.Lobby.Boards;
import org.bingo4.Lobby.NPCs.Bingo.BingoNPC;
import org.bingo4.MainMenuUI.BingoBoard.BingoSlot;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.DisplayUtil;
import org.bingo4.Utility.TextUtil;
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
                List<BingoSlot> bingoSlots = plugin.getBingoData().getBingoSlots();
                if (bingoSlots.size() != 25) return;
                for (int i = 1; i <= 25; i++) {
                    Location loc = new Location(plugin.getSpawnWorld(), itemX, itemY, itemZ);
                    ItemStack item = bingoSlots.get(i - 1).getItem();
                    double size = item.getType().isBlock() ? 0.7 : 0.45;
//            double size = item.getType().isItem() ? 0.45 : 0.9;
                    ItemDisplay itemDisplay = DisplayUtil.makeItemDisplay(loc, bingoSlots.get(i - 1).getItem(), 0, 90, size);
                    itemDisplay.addScoreboardTag("bingo_items");
                    if (i % 5 == 0) {
                        itemY -= 0.9;
                        itemZ = startingZ;
                    } else {
                        itemZ +=0.9;
                    }
                }
            }
        }.runTaskLater(Bingo4.getInstance(), 20);

    }

    public void displayBoard(Player viewer, Player target) {
        List<Boolean> completed = plugin.getBingoData().getItemsCompleted(PlayerUtil.getPlayerData(target));
        //TODO: This no longer works b/c bingo data is not kept after the fact
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
