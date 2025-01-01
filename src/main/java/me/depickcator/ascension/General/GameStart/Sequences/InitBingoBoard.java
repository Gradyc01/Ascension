package me.depickcator.ascension.General.GameStart.Sequences;

import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.StartGame;
import me.depickcator.ascension.Items.ItemList;
import me.depickcator.ascension.MainMenuUI.BingoBoard.BingoData;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InitBingoBoard extends GameStartSequence {
    private final List<Integer> itemDistribution;
    public InitBingoBoard(List<Integer> itemDistribution) {
        this.itemDistribution = itemDistribution;
    }

    @Override
    public void run(StartGame game) {
        BingoData bingoData = plugin.getBingoData();
        bingoData.setItems(new ItemList().getItemsForBoard(itemDistribution));
        ArrayList<ItemStack> item = bingoData.getItems();
        for (ItemStack i : item) {
            TextUtil.debugText(i.toString());
        }
        game.callback();
    }
}
