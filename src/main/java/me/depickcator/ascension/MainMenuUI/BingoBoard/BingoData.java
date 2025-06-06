package me.depickcator.ascension.MainMenuUI.BingoBoard;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.MakeshiftSkull;
import me.depickcator.ascension.Items.Uncraftable.NetherStar.NetherStar;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.ItemComparison;
import me.depickcator.ascension.Items.Uncraftable.XPTome.XPTome;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Teams.Team;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class BingoData extends ItemComparison {
    private ScoreboardManager scoreboardManager;
    private Scoreboard bingoScoreboard;
    @SuppressWarnings("unused")
    private Objective bingodata;
    // private Objective bingoLines;
    // private Objective bingoItemsObtained;
    private ArrayList<ItemStack> items;
//    private ItemList itemList;
    private final Ascension plugin;

    public BingoData(Ascension ab) {
        scoreboardManager = Bukkit.getServer().getScoreboardManager();
        bingoScoreboard = scoreboardManager.getMainScoreboard();
        try {
            bingodata = bingoScoreboard.registerNewObjective("bingo", Criteria.DUMMY , Component.text("Ascension"), RenderType.INTEGER);
        } catch (Exception e) {
            bingodata = bingoScoreboard.getObjective("bingo");
        }

        this.plugin = ab;
        items = new ArrayList<>();
        resetPlayers();
    }

    public void resetPlayers() {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        bingoScoreboard.resetScores("bingo");
        for (Player player : players) {
            resetPlayer(player);
        }
    }

    public void resetPlayer(Player player) {
        Score score = Objects.requireNonNull(bingoScoreboard.getObjective("bingo")).getScore(player.getName());
        score.setScore(0);
    }


    public void claimItem(Player p) {
        ArrayList<ItemStack> items = getItems();
        ArrayList<Boolean> hasItems = getItemsCompleted(p);

        if (items.size() != 25) {
            TextUtil.errorMessage(p, "The board has not been initialized yet");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            if (hasItems.get(i)) {
                continue;
            }
            ItemStack item = items.get(i);
            if (claimItem(p, item, false)) return;
        }
        TextUtil.errorMessage(p, "There were no items to claim");
    }

    public boolean claimItem(Player p, ItemStack item, boolean displayErrorText) {
        return claimItem(p, item, displayErrorText, true);
    }

    public boolean claimItem(Player p, ItemStack item, boolean displayErrorText, boolean removeItem) {
        PlayerData pD = PlayerUtil.getPlayerData(p);
        for (ItemStack j : p.getInventory().getContents()) {
            if (j != null && equalItems(j, item)) {
                if (removeItem) {
                    j.setAmount(j.getAmount() - 1);
                    giveRewards(p);
                }
                pD.getPlayerTeam().getTeam().getTeamStats().addGameScore(1);
                pD.getPlayerStats().addItemsObtained();
                sendItemObtainedMessage(p, item);
                addScore(24 - items.indexOf(item), p);
                checkForLineCompletion(p); //Also adds an item obtained (idk where else to put it)
                updateScoreboard(p);
                new BingoBoardGUI(pD);
                return true;
            }
        }
        if (displayErrorText) {
            TextUtil.errorMessage(p, "You can't claim this item");
        }
        return false;
    }

    public boolean canUnlockItem(Player p, ItemStack item) {
        for (ItemStack j : p.getInventory().getContents()) {
            if (j != null && equalItems(j, item)) {
                return true;
            }
        }
        return false;
    }

    private void sendItemObtainedMessage(Player p, ItemStack item) {
        Component obtainedItemTextSolo = TextUtil.makeText("You have obtained an Item! ", TextUtil.GREEN);
        Component obtainedItemTextItem = item.displayName().color(TextUtil.GOLD);
        Component obtainedItemTextTeam = TextUtil.makeText(p.getName() + " obtained an Item! ", TextUtil.GREEN);
        p.sendMessage(obtainedItemTextSolo.append(obtainedItemTextItem));
        PlayerData pD = PlayerUtil.getPlayerData(p);
        List<Player> teamMembers =pD.getPlayerTeam().getTeam().getOtherTeamMembers(p);
        TextUtil.broadcastMessage(obtainedItemTextTeam.append(obtainedItemTextItem) , teamMembers);
        SoundUtil.playHighPitchPling(p);
        SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2.0, teamMembers);
    }

    private void checkForLineCompletion(Player p) {
        PlayerData playerData = PlayerUtil.getPlayerData(p);
        playerData.getPlayerTeam().getTeam().getTeamStats().addItemObtained();
        ArrayList<Boolean> hasItems = getItemsCompleted(playerData.getPlayer());

        int oldLines = playerData.getPlayerTeam().getTeam().getTeamStats().getLinesObtained();
        int newLines = calculateTotalLines(hasItems);
        if (newLines > oldLines) {
            Team team = playerData.getPlayerTeam().getTeam();
            for (int i = 0; i < newLines - oldLines; i++) {
                playerData.getPlayerTeam().getTeam().getTeamStats().addGameScore(5);
                List<Player> teamMembers = team.getTeamMembers();
                for (Player teamMember : teamMembers) {
                    teamMember.sendMessage(TextUtil.topBorder(TextUtil.YELLOW));
                    teamMember.sendMessage(TextUtil.makeText("Your team has completed a line!", TextUtil.BLUE));
                    teamMember.sendMessage(TextUtil.bottomBorder(TextUtil.YELLOW));
                    teamMember.playSound(teamMember.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.0f);
                    PlayerUtil.giveItem(teamMember, MakeshiftSkull.getInstance().getResult());
                    PlayerUtil.giveItem(teamMember, NetherStar.getInstance().getResult(2));
                }
            }
            team.getTeamStats().setLinesObtained(newLines);
        }

    }

    private void updateScoreboard(Player p) {
        PlayerData playerData = PlayerUtil.getPlayerData(p);
        for (Player player : playerData.getPlayerTeam().getTeam().getTeamMembers()) {
            PlayerUtil.getPlayerData(player).getPlayerScoreboard().update();
        }
    }

    private int calculateTotalLines(ArrayList<Boolean> hasItems) {
        int lines = 0;
        lines += checkLine(0, 1, 2, 3, 4, hasItems);
        lines += checkLine(5, 6, 7, 8, 9, hasItems);
        lines += checkLine(10, 11, 12, 13, 14, hasItems);
        lines += checkLine(15, 16, 17, 18, 19, hasItems);
        lines += checkLine(20, 21, 22, 23, 24, hasItems);
        lines += checkLine(0, 5, 10, 15, 20, hasItems);
        lines += checkLine(1, 6, 11, 16, 21, hasItems);
        lines += checkLine(2, 7, 12, 17, 22, hasItems);
        lines += checkLine(3, 8, 13, 18, 23, hasItems);
        lines += checkLine(4, 9, 14, 19, 24, hasItems);
        lines += checkLine(0, 6, 12, 18, 24, hasItems);
        lines += checkLine(4, 8, 12, 16, 20, hasItems);
        return lines;
    }

    private int checkLine(int one, int two, int three, int four, int five,ArrayList<Boolean> hasItems) {
        if (hasItems.get(one) && hasItems.get(two) && hasItems.get(three) && hasItems.get(four) && hasItems.get(five)) {
            return 1;
        }
        return 0;
    }



    private void giveRewards(Player p) {

        PotionEffect rewardEffect = new PotionEffect(PotionEffectType.REGENERATION, 16 * 20, 0);
        PlayerData pD = PlayerUtil.getPlayerData(p);
        //Individual Rewards
        soloRewards(rewardEffect, p, pD);
        //Other Teammates rewards
        teamRewards(rewardEffect, pD);
    }

    private void soloRewards(PotionEffect effect, Player p, PlayerData pD) {
        p.giveExp(7);
        pD.getPlayerUnlocks().addUnlockTokens(PlayerUnlocks.AMOUNT_RARE, true);
        PlayerUtil.giveItem(p, XPTome.getInstance().getItem());
        p.addPotionEffect(effect);
    }

    private void teamRewards(PotionEffect effect, PlayerData pD) {
        List<Player> otherTeamMembers = pD.getPlayerTeam().getTeam().getOtherTeamMembers(pD.getPlayer());
        for (Player p : otherTeamMembers) {
            PlayerUtil.giveItem(p, XPTome.getInstance().getItem());
            PlayerUtil.getPlayerData(p).getPlayerUnlocks().addUnlockTokens(PlayerUnlocks.AMOUNT_UNCOMMON, true);
            p.addPotionEffect(effect);
        }
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
//        printArray(bingoArray, player);
        if (doesHaveThisItem(index, bingoArray)) {
            int ans = (int)Math.pow(2, index);
            score.setScore(score.getScore() - ans);
        }
    }

    public void addScore(int index, Player player) {
        Score score = Objects.requireNonNull(bingoScoreboard.getObjective("bingo")).getScore(player.getName());
        ArrayList<Boolean> bingoArray = setArray(score);
//        printArray(bingoArray, player);
        if (!doesHaveThisItem(index, bingoArray)) {
            int ans = score.getScore() + (int)Math.pow(2, index);
            score.setScore(ans);
            changeTeamScore(player, ans);
        }
    }

    private void changeTeamScore(Player player, int newScore) {
        PlayerData playerData = Ascension.playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            for (Player teamMember : playerData.getPlayerTeam().getTeam().getTeamMembers()) {
                Score score = Objects.requireNonNull(bingoScoreboard.getObjective("bingo")).getScore(teamMember.getName());
                score.setScore(newScore);
            }
        }
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

//    public ItemList getItemList() {
//        return itemList;
//    }

    public ArrayList<Boolean> getItemsCompleted(Player player) {
        return setArray(plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective("bingo").getScore(player.getName()));
    }




}
