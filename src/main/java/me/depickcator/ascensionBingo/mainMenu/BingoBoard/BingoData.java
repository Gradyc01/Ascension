package me.depickcator.ascensionBingo.mainMenu.BingoBoard;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.ItemList;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class BingoData {
    private ScoreboardManager scoreboardManager;
    private Scoreboard bingoScoreboard;
    private Objective bingodata;
    private Objective bingoLines;
    private Objective bingoItemsObtained;
    private ArrayList<ItemStack> items;
    private ItemList itemList;
    private AscensionBingo ab;

    public BingoData(AscensionBingo ab) {
        scoreboardManager = Bukkit.getServer().getScoreboardManager();
        bingoScoreboard = scoreboardManager.getMainScoreboard();
        bingodata = bingoScoreboard.registerNewObjective("bingo", Criteria.DUMMY , Component.text("Ascension Bingo"), RenderType.INTEGER);
        this.ab = ab;
        items = new ArrayList<>();
        itemList = new ItemList(ab);
        resetPlayers();
    }

    public void resetPlayers() {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        bingoScoreboard.resetScores("bingo");
        for (Player player : players) {
            Score score = Objects.requireNonNull(bingoScoreboard.getObjective("bingo")).getScore(player.getName());
            score.setScore(0);

//            player.setScoreboard(bingoScoreboard);
            Objects.requireNonNull(bingoScoreboard.getObjective("bingo")).setDisplaySlot(DisplaySlot.SIDEBAR);
        }
//        logger.info("Reset Players in BingoData");
    }
    /*
    Bingo Board Index Array
     00 01 02 03 04
     05 06 07 08 09
     10 11 12 13 14
     15 16 17 18 19
     20 21 22 23 24


     1 2 4 8 16
     32 64 128 256 512
     1024 2048 4096 8192 16384
     32768 65536 131072 262144 524288
     1048576 2097152 4194304 8388608 16777216
     */
    public void removeScore(int index, Player player) {
        Score score = Objects.requireNonNull(bingoScoreboard.getObjective("bingo")).getScore(player.getName());
        ArrayList<Boolean> bingoArray = setArray(score);
        printArray(bingoArray, player);
        if (doesHaveThisItem(index, bingoArray)) {
            int ans = (int)Math.pow(2, index);
//            logger.info("Successfully Removed Score in BingoData");
//            player.sendMessage("Successfully Removed Score");
            score.setScore(score.getScore() - ans);
        }
//        logger.info("Did not Remove Score in BingoData");
    }

    public void addScore(int index, Player player) {
        Score score = Objects.requireNonNull(bingoScoreboard.getObjective("bingo")).getScore(player.getName());
        ArrayList<Boolean> bingoArray = setArray(score);
        printArray(bingoArray, player);
        if (!doesHaveThisItem(index, bingoArray)) {
            int ans = (int)Math.pow(2, index);
//            logger.info("Successfully Added Score in BingoData");
            score.setScore(score.getScore() + ans);
        }
//        logger.info("Did not Added Score in BingoData");
    }

    private ArrayList<Boolean> setArray(Score score) {
        int num = score.getScore();
        int check = 16777216;
        ArrayList<Boolean> arr = new ArrayList<>();
        for (int i = 24; i >= 0; i--) {
            Boolean isCompleted = setArrayChecker(num, check);
            arr.add(isCompleted);
            if (isCompleted) num -= check;
            check /= 2;
        }
        return arr;
    }

    public void printArray(ArrayList<Boolean> arr, Player player) {
        int index = 0;
        for (int i = 0; i < 5; i++) {
            String str = "";
            for (int j = 0; j < 5; j++) {
                str = str.concat(arr.get(index).toString() + " ");
                index++;
            }
            player.sendMessage(str);
        }
    }

    private Boolean setArrayChecker(int num, int check) {
        return num / check == 1;
    }

    public Boolean doesHaveThisItem(int index, ArrayList<Boolean> arr) {
        return arr.get(24 - index);
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemStack> items) {
        this.items = items;
    }

    public ItemList getItemList() {
        return itemList;
    }

    public ArrayList<Boolean> getItemsCompleted(Player player) {
        return setArray(Objects.requireNonNull(player.getScoreboard().getObjective("bingo")).getScore(player.getName()));
    }




}
