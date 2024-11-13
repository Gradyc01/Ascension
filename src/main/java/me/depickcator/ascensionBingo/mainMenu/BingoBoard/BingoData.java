package me.depickcator.ascensionBingo.mainMenu.BingoBoard;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.SoundUtil;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Items.ItemList;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import me.depickcator.ascensionBingo.Teams.TeamUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class BingoData {
    private ScoreboardManager scoreboardManager;
    private Scoreboard bingoScoreboard;
    @SuppressWarnings("unused")
    private Objective bingodata;
    // private Objective bingoLines;
    // private Objective bingoItemsObtained;
    private ArrayList<ItemStack> items;
    private ItemList itemList;
    private final AscensionBingo plugin;

    public BingoData(AscensionBingo ab) {


        scoreboardManager = Bukkit.getServer().getScoreboardManager();
        bingoScoreboard = scoreboardManager.getMainScoreboard();
        try {
            bingodata = bingoScoreboard.registerNewObjective("bingo", Criteria.DUMMY , Component.text("Ascension Bingo"), RenderType.INTEGER);
        } catch (Exception e) {
            bingodata = bingoScoreboard.getObjective("bingo");
        }

        this.plugin = ab;
        items = new ArrayList<>();
        itemList = new ItemList(ab);
        resetPlayers();
    }

//    private void resetScoreboards() {
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives remove bingo");
//    }

    private void resetTeams() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            try {
                TeamUtil.disbandTeam(p);
            } catch (Exception ignored) {
                continue;
            }
        }
    }

    public void resetPlayers() {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        bingoScoreboard.resetScores("bingo");
        resetTeams();
        for (Player player : players) {
            Score score = Objects.requireNonNull(bingoScoreboard.getObjective("bingo")).getScore(player.getName());
            score.setScore(0);

//            player.setScoreboard(bingoScoreboard);
            Objects.requireNonNull(bingoScoreboard.getObjective("bingo")).setDisplaySlot(DisplaySlot.SIDEBAR);
        }
//        logger.info("Reset Players in BingoData");
    }

    private boolean containsItem(ItemStack item1, ItemStack item2) {
        ItemMeta item1Meta = item1.getItemMeta();
        ItemMeta item2Meta = item2.getItemMeta();
        boolean modelData;
        try {
            modelData = item1Meta.getCustomModelData() == item2Meta.getCustomModelData();
        } catch (Exception e) {
            modelData =true;
        }
        return (item1.getType().equals(item2.getType()) &&
                Objects.equals(item1Meta.lore(), item2Meta.lore()) &&
                Objects.equals(item1Meta.getItemFlags(), item2Meta.getItemFlags()) &&
                modelData &&
                Objects.equals(item1Meta.getEnchants(), item2Meta.getEnchants()) &&
                Objects.equals(item1Meta.getFood(), item2Meta.getFood()));
    }


    public void claimItem(Player p) {
        Inventory inv = p.getInventory();
        ArrayList<ItemStack> items = getItems();
        ArrayList<Boolean> hasItems = getItemsCompleted(p);

        if (items.size() != 25) {
            p.sendMessage(Component.text("The board has not been initialized yet").color(TextColor.color(255,0,0)));
//            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1.0f, 0f);
            SoundUtil.playErrorSoundEffect(p);
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            if (hasItems.get(i)) {
                continue;
            }
            ItemStack item = items.get(i);
            for (ItemStack j : inv.getContents()) {
                if (j != null && containsItem(j, item)) {
                    j.setAmount(j.getAmount() - 1);
                    addScore(24 - i, p);
                    giveRewards(p, item);
                    new BingoBoardGUI(plugin, p);
                    return;
                }
            }
        }
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1.0f, 0f);
        p.sendMessage(Component.text("There were no items to claim").color(TextColor.color(255,0,0)));
    }

    private void giveRewards(Player p, ItemStack item) {
        Component obtainedItemTextSolo = TextUtil.makeText("You have obtained an Item! ", TextUtil.GREEN);
        Component obtainedItemTextItem = item.displayName().color(TextUtil.GOLD);
        Component obtainedItemTextTeam = TextUtil.makeText(p.getName() + " obtained an Item! ", TextUtil.GREEN);
        PotionEffect rewardEffect = new PotionEffect(PotionEffectType.REGENERATION, 16 * 20, 0);
        PlayerData pD = PlayerUtil.getPlayerData(p);
        if (pD == null) {
            plugin.getServer().broadcast(TextUtil.makeText("UNABLE TO FIND PLAYER DATA Bingo data::giveReward", TextUtil.RED));
            return;
        }
        //Individual Rewards
        soloRewards(obtainedItemTextSolo.append(obtainedItemTextItem), rewardEffect, p, pD);
        //Other Teammates rewards
        teamRewards(obtainedItemTextTeam.append(obtainedItemTextItem), rewardEffect, pD);
    }

    private void soloRewards(Component text, PotionEffect effect, Player p, PlayerData pD) {
        p.sendMessage(text);
        p.giveExp(7);
        SoundUtil.playHighPitchPling(p);
        pD.getPlayerUnlocks().addUnlockTokens(1);
        p.addPotionEffect(effect);
    }

    private void teamRewards(Component text, PotionEffect effect, PlayerData pD) {
        ArrayList<Player> otherTeamMembers = pD.getTeam().getOtherTeamMembers(pD.getPlayer());
        for (Player p : otherTeamMembers) {
            p.sendMessage(text);
            SoundUtil.playHighPitchPling(p);
            Objects.requireNonNull(PlayerUtil.getPlayerData(p)).getPlayerUnlocks().addUnlockTokens(1);
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
        PlayerData playerData = AscensionBingo.playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            for (Player teamMember : playerData.getTeam().getTeamMembers()) {
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

    public ItemList getItemList() {
        return itemList;
    }

    public ArrayList<Boolean> getItemsCompleted(Player player) {
        return setArray(Objects.requireNonNull(player.getScoreboard().getObjective("bingo")).getScore(player.getName()));
    }




}
