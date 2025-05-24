package me.depickcator.ascension.General.Game.Start.Sequences;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Start.StartGame;
import me.depickcator.ascension.Items.ItemList;
import me.depickcator.ascension.MainMenuUI.BingoBoard.BingoData;
import me.depickcator.ascension.Utility.DisplayUtil;
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
        bingoData.setItems(new ItemList().getItemsForBoard(itemDistribution));
//        ArrayList<ItemStack> item = bingoData.getItems();
//        for (ItemStack i : item) {
//            TextUtil.debugText(i.toString());
//        }
        if (game instanceof StartGame) {
            StartGame startGame = (StartGame) game;
            startGame.setCageItemDisplay(displayBoardForEveryone(startGame.getSpawnCages(), Ascension.getInstance().getWorld()));
        }
//        game.setCageItemDisplay(displayBoardForEveryone(game.getSpawnCages(), Ascension.getInstance().getWorld()));
        game.callback();
    }

    private List<Entity> displayBoardForEveryone(List<Location> locations, World world) {
        List<ItemStack> items = plugin.getBingoData().getItems();
        List<Entity> itemDisplays = new ArrayList<>();
        for (Location loc : locations) {
            double x = loc.getX() + 2.2;
            double y = loc.getY() + 99;
            double z = loc.getZ() + 4.9;

//            double startingZ = z;
            for (int i = 1; i <= 25; i++) {

                Location l = new Location(world, x, y, z);
                ItemStack item = items.get(i - 1);
                double size = item.getType().isBlock() ? 0.7 : 0.45;
                ItemDisplay itemDisplay = DisplayUtil.makeItemDisplay(l, items.get(i - 1), -90, 270, size);
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
