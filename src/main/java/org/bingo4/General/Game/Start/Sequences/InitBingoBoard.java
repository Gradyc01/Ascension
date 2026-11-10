package org.bingo4.General.Game.Start.Sequences;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.General.Game.Start.StartGame;
import org.bingo4.Items.ItemList;
import org.bingo4.MainMenuUI.BingoBoard.BingoData;
import org.bingo4.MainMenuUI.BingoBoard.BingoSlot;
import org.bingo4.Utility.DisplayUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InitBingoBoard extends GameSequences {
    private final List<Integer> itemDistribution;
    public InitBingoBoard(List<Integer> itemDistribution) {
        this.itemDistribution = itemDistribution;
    }

    @Override
    public void run(GameLauncher game) {
        BingoData bingoData = plugin.getBingoData();
        bingoData.setItemList(new ItemList().getItemsForBoard(itemDistribution));
        if (game instanceof StartGame) {
            StartGame startGame = (StartGame) game;
            startGame.setCageItemDisplay(displayBoardForEveryone(startGame.getSpawnCages(), Bingo4.getInstance().getWorld()));
        }
        plugin.getSettingsUI().getSettings().getTimeline().generateLocationsForCamps();
        game.callback();
    }

    private List<Entity> displayBoardForEveryone(List<Location> locations, World world) {
        List<BingoSlot> slots = plugin.getBingoData().getBingoSlots();
        List<Entity> itemDisplays = new ArrayList<>();
        for (Location loc : locations) {
            double x = loc.getX() + 2.2;
            double y = loc.getY() + 99;
            double z = loc.getZ() + 4.9;

//            double startingZ = z;
            for (int i = 1; i <= 25; i++) {

                Location l = new Location(world, x, y, z);
                ItemStack item = slots.get(i - 1).getItem();
                double size = item.getType().isBlock() ? 0.7 : 0.45;
                ItemDisplay itemDisplay = DisplayUtil.makeItemDisplay(l, slots.get(i - 1).getItem(), -90, 270, size);
                itemDisplays.add(itemDisplay);
                if (i % 5 == 0) {
                    x += 0.7;
                    z = loc.getZ() + 4.9;
                } else {
                    z -=0.7;
                }
            }
        }
        return itemDisplays;
    }
}
