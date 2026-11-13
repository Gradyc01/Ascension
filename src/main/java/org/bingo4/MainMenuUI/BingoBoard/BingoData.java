package org.bingo4.MainMenuUI.BingoBoard;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bingo4.Bingo4;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Teams.Team;
import org.bingo4.Utility.ItemComparison;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;

import java.util.*;

public class BingoData {

    private final Bingo4 plugin;
    private List<ItemStack> itemList;
    private final List<BingoSlot> bingoSlots;
    private final Map<PlayerData, List<BingoSlot>> playerSlotMap;

    public BingoData(Bingo4 ab) {
        this.plugin = ab;
        itemList = new ArrayList<>();
        bingoSlots = new ArrayList<>();
        playerSlotMap = new HashMap<>();
        resetPlayers();
    }

    public void resetPlayers() {
        playerSlotMap.clear();
        for (BingoSlot slot : bingoSlots) {
            slot.setClaimer(null);
        }
    }

    public void resetPlayer(PlayerData playerData) {
        List<BingoSlot> slots = playerSlotMap.get(playerData);
        if (slots != null) {
            for (BingoSlot slot : slots) {
                slot.setClaimer(null);
            }
        }
    }


    public void claimItem(PlayerData pD) {
        List<BingoSlot> slots = getBingoSlots();
//        List<Boolean> hasItems = getItemsCompleted(p);

        if (slots.size() != 25) {
            TextUtil.errorMessage(pD.getPlayer(), "The board has not been initialized yet");
            return;
        }
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getClaimer().equals(pD)) {
                continue;
            }
            ItemStack item = slots.get(i).getItem();
            if (claimItem(pD, item, false)) return;
        }
        TextUtil.errorMessage(pD.getPlayer(), "There were no items to claim");
    }

    public boolean claimItem(PlayerData pD, ItemStack item, boolean displayErrorText) {
        return claimItem(pD, item, displayErrorText, true);
    }

    public boolean claimItem(PlayerData pD, ItemStack item, boolean displayErrorText, boolean removeItem) {
        Player p = pD.getPlayer();
        BingoSlot bingoSlot = getBingoSlotByItem(item);

        for (ItemStack j : p.getInventory().getContents()) {
            if (bingoSlot == null) break;
            if (j != null && ItemComparison.equalItems(j, item) && !playerTeamHasSlot(bingoSlot, pD)) {
//                if (removeItem) {
//                    j.setAmount(j.getAmount() - 1);
//                }
//                pD.getPlayerTeam().getTeam().getTeamStats().addGameScore(1);
//                pD.getPlayerTeam().getTeam().getTeamStats().addItemObtained();
//                pD.getPlayerStats().addItemsObtained();
//                sendItemObtainedMessage(pD, bingoSlot);
//
//                changeBoardItemAfterClaim(pD, bingoSlot);
//                checkForLineCompletion(pD); //Also adds an item obtained (idk where else to put it)
//                updateScoreboard();
//                if (openGui) new BingoBoardGUI(pD);
//                return true;
                completeClaimItemRewards(j, removeItem, true, pD, bingoSlot);
            }
        }
        if (displayErrorText) {
            TextUtil.errorMessage(p, "You can't claim this item");
        }
        return false;
    }

    public void completeClaimItemRewards(ItemStack invItemStack, boolean removeItem, boolean openGui, PlayerData pD, BingoSlot bingoSlot) {
        BingoSlot slot = bingoSlot == null ? getBingoSlotByItem(invItemStack) : bingoSlot;
        if (slot == null) {
            TextUtil.broadcastMessage(TextUtil.makeText("[ERROR]  BINGO SLOT NULL'ED"));
            return;
        }

        if (removeItem) {
            invItemStack.setAmount(invItemStack.getAmount() - 1);
        }
        pD.getPlayerTeam().getTeam().getTeamStats().addGameScore(1);
        pD.getPlayerTeam().getTeam().getTeamStats().addItemObtained();
        pD.getPlayerStats().addItemsObtained();
        sendItemObtainedMessage(pD, slot);

        changeBoardItemAfterClaim(pD, slot);
        checkForLineCompletion(pD); //Also adds an item obtained (idk where else to put it)
        updateScoreboard();
        if (openGui) new BingoBoardGUI(pD);
    }

    public boolean canUnlockItem(PlayerData pD, ItemStack item) {
        BingoSlot bingoSlot = getBingoSlotByItem(item);

        if (bingoSlot == null) return false;

        for (ItemStack j : pD.getPlayer().getInventory().getContents()) {
            if (j != null && ItemComparison.equalItems(j, item) && !playerTeamHasSlot(bingoSlot, pD)) {
                return true;
            }
        }
        return false;
    }

    public boolean itemOnBoard(ItemStack item) {
        return getBingoSlotByItem(item) != null;
    }

    private void sendItemObtainedMessage(PlayerData pD, BingoSlot slot) {
        Component obtainedItemTextItem = slot.getItem().displayName().color(TextUtil.GOLD);
        String claimedText = slot.isClaimed() ? "counter-claimed" : "claimed";

        Team team = pD.getPlayerTeam().getTeam();

        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player p : team.getTeamMembers()) {
            players.remove(p);
        }

        Audience self = Audience.audience(pD.getPlayer());
        Audience teamMembers = Audience.audience(team.getOtherTeamMembers(pD.getPlayer()));
        Audience everyone = Audience.audience(players);

        self.sendMessage(TextUtil.makeText("You have obtained an Item! ", TextUtil.GREEN).append(obtainedItemTextItem));
        teamMembers.sendMessage(TextUtil.makeText(pD.getPlayer().getName() + " obtained an Item", TextUtil.GREEN).append(obtainedItemTextItem));
        everyone.sendMessage(TextUtil.makeText("The enemy team has " + claimedText + " an item! ", TextUtil.RED).append(obtainedItemTextItem));
        SoundUtil.playHighPitchPling(pD.getPlayer());
        teamMembers.playSound(Sound.sound(org.bukkit.Sound.BLOCK_ENCHANTMENT_TABLE_USE, Sound.Source.MASTER, 100f, 0f));
        everyone.playSound(Sound.sound(org.bukkit.Sound.BLOCK_BEACON_DEACTIVATE, Sound.Source.MASTER, 100f, 1f));

    }

    private void checkForLineCompletion(PlayerData playerData) {
        if (playerData == null) return;
        List<Boolean> hasItems = getItemsCompleted(playerData);

        int newLines = calculateTotalLines(hasItems);
        Team team = playerData.getPlayerTeam().getTeam();
        team.getTeamStats().setLinesObtained(newLines);
    }

    private void updateScoreboard() {
        for (PlayerData playerData : PlayerUtil.getAllPlayingPlayers()) {
            playerData.getPlayerScoreboard().update();
        }

    }

    private int calculateTotalLines(List<Boolean> hasItems) {
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

    private int checkLine(int one, int two, int three, int four, int five,List<Boolean> hasItems) {
        if (hasItems.get(one) && hasItems.get(two) && hasItems.get(three) && hasItems.get(four) && hasItems.get(five)) {
            return 1;
        }
        return 0;
    }



//    private void giveRewards(Player p) {
//        PotionEffect rewardEffect = new PotionEffect(PotionEffectType.REGENERATION, 16 * 20, 0);
//        PlayerData pD = PlayerUtil.getPlayerData(p);
//        //Individual Rewards
//        soloRewards(rewardEffect, p, pD);
//        //Other Teammates rewards
//        teamRewards(rewardEffect, pD);
//    }
//
//    private void giveExp(PlayerData pD) {
////        pD.getPlayerSkills().getBoardEfficiency().addExp(2);
//        List<Player> otherTeamMembers = pD.getPlayerTeam().getTeam().getOtherTeamMembers(pD.getPlayer());
//        for (Player p : otherTeamMembers) {
////            PlayerUtil.getPlayerData(p).getPlayerSkills().getBoardEfficiency().addExp(1);
//        }
//    }
//
//    private void soloRewards(PotionEffect effect, Player p, PlayerData pD) {
//        p.giveExp(7);
////        pD.getPlayerUnlocks().addUnlockTokens(PlayerUnlocks.AMOUNT_RARE, true);
//        giveXPTome(pD);
//        p.addPotionEffect(effect);
//    }
//
//    private void teamRewards(PotionEffect effect, PlayerData pD) {
//        List<Player> otherTeamMembers = pD.getPlayerTeam().getTeam().getOtherTeamMembers(pD.getPlayer());
//        for (Player p : otherTeamMembers) {
////            PlayerUtil.getPlayerData(p).getPlayerUnlocks().addUnlockTokens(PlayerUnlocks.AMOUNT_UNCOMMON, true);
//            giveXPTome(PlayerUtil.getPlayerData(p));
//            p.addPotionEffect(effect);
//        }
//    }
//
//    private void giveLineRewards(Team team) {
//        team.getTeamStats().addGameScore(5);
//        Component text = TextUtil.topBorder(TextUtil.YELLOW)
//                .append(TextUtil.makeText("\nYour team has completed a line!\n", TextUtil.BLUE))
//                .append(TextUtil.bottomBorder(TextUtil.YELLOW));
//        Title title = TextUtil.makeTitle(TextUtil.makeText("LINE COMPLETED!", TextUtil.GOLD),
//                1, 2,0.5);
//        for (Player teamMember : team.getTeamMembers()) {
//            teamMember.sendMessage(text);
//            teamMember.showTitle(title);
//            teamMember.playSound(teamMember.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.0f);
//            PlayerUtil.giveItem(teamMember,
//                    MakeshiftSkull.getInstance().getResult(),
//                    NetherStar.getInstance().getResult(2));
//            PlayerData pD = PlayerUtil.getPlayerData(teamMember);
////            pD.getPlayerUnlocks().addUnlockTokens(PlayerUnlocks.AMOUNT_LEGENDARY * 2);
//        }
//    }

//    private void giveXPTome(PlayerData pD) {
//        Player p = pD.getPlayer();
//        if (pD.getPlayerStats().getSetting(PlayerStats.autoTome)) {
//            int exp = Integer.MAX_VALUE;
////            Skills lowestSkill = null;
////            for (Skills skill : List.of(skills.getCombat(), skills.getForaging(), skills.getMining())) {
////                if (skill.getExp() < exp) {
////                    exp = skill.getExp();
////                    lowestSkill = skill;
////                }
////            }
////            if (lowestSkill == null) return;
////            lowestSkill.addExp(70);
////            p.sendMessage(TextUtil.makeText("[Auto Tome] ", TextUtil.BLUE)
////                    .append(TextUtil.makeText("XP Tome from the Board has been auto claimed into ", TextUtil.DARK_GREEN))
////                    .append(TextUtil.makeText(lowestSkill.getSkillName(), TextUtil.GOLD)));
//        } else {
//            PlayerUtil.giveItem(p, XPTome.getInstance().getResult());
//        }
//    }


    private void changeBoardItemAfterClaim(PlayerData pD, BingoSlot slot) {
        PlayerData oldClaimer = slot.getClaimer();
        slot.setClaimer(pD);
        if (!itemList.isEmpty()) {
            slot.setItem(itemList.removeFirst());
        } else {
            slot.setItem(new ItemStack(Material.BEDROCK));
        }
        checkForLineCompletion(oldClaimer);

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
//    public void removeScore(int index, Player player) {
//        Score score = Objects.requireNonNull(bingoScoreboard.getObjective("bingo")).getScore(player.getName());
//        ArrayList<Boolean> bingoArray = setArray(score);
////        printArray(bingoArray, player);
//        if (doesHaveThisItem(index, bingoArray)) {
//            int ans = (int)Math.pow(2, index);
//            score.setScore(score.getScore() - ans);
//        }
//    }
//
//    public void addScore(int index, Player player) {
//        Score score = Objects.requireNonNull(bingoScoreboard.getObjective("bingo")).getScore(player.getName());
//        ArrayList<Boolean> bingoArray = setArray(score);
////        printArray(bingoArray, player);
//        if (!doesHaveThisItem(index, bingoArray)) {
//            int ans = score.getScore() + (int)Math.pow(2, index);
//            score.setScore(ans);
//            changeTeamScore(player, ans);
//        }
//    }
//
//    private void changeTeamScore(Player player, int newScore) {
//        PlayerData playerData = Bingo4.playerDataMap.get(player.getUniqueId());
//        if (playerData != null) {
//            for (Player teamMember : playerData.getPlayerTeam().getTeam().getTeamMembers()) {
//                Score score = Objects.requireNonNull(bingoScoreboard.getObjective("bingo")).getScore(teamMember.getName());
//                score.setScore(newScore);
//            }
//        }
//    }

//    private ArrayList<Boolean> setArray(Score score) {
//        int num = score.getScore();
//        int check = 16777216;
//        ArrayList<Boolean> arr = new ArrayList<>();
//        for (int i = 24; i >= 0; i--) {
//            Boolean isCompleted = setArrayChecker(num, check);
//            arr.add(isCompleted);
//            if (isCompleted) num -= check;
//            check /= 2;
//        }
//        return arr;
//    }
//
//    public void printArray(List<Boolean> arr, Player player) {
//        int index = 0;
//        for (int i = 0; i < 5; i++) {
//            String str = "";
//            for (int j = 0; j < 5; j++) {
//                str = str.concat(arr.get(index).toString() + " ");
//                index++;
//            }
//            player.sendMessage(str);
//        }
//    }

//    private Boolean setArrayChecker(int num, int check) {
//        return num / check == 1;
//    }
//
//    public Boolean doesHaveThisItem(int index, List<Boolean> arr) {
//        return arr.get(24 - index);
//    }

    private Boolean playerTeamHasSlot(BingoSlot slot, PlayerData pD) {
        Team team = pD.getPlayerTeam().getTeam();
        if (team == null) {
            return false;
        }
        List<PlayerData> playerData = team.getTeamMembersData();
        return slot.getClaimer() != null && playerData.contains(slot.getClaimer());
    }

    private BingoSlot getBingoSlotByItem(ItemStack item) {
        for (BingoSlot slot : bingoSlots) {
            if (ItemComparison.equalItems(item, slot.getItem())) {
                return slot;
            }
        }
        return null;
    }

    /**
     * Sets a list of items that dictate the sequence of which items are placed onto the Bingo Board
     * @param items
     */
    public void setItemList(List<ItemStack> items) {
        this.itemList = items;
        setInitialItems(items);
    }

    private void setInitialItems(List<ItemStack> items) {
        bingoSlots.clear();
        playerSlotMap.clear();
        if (items.size() < 25) {
            throw new RuntimeException("Item List is not long enough");
        }
        for (int i = 0; i < 25; i++) {
            ItemStack item = items.removeFirst();
            bingoSlots.add(new BingoSlot(item));
        }
    }

    public List<BingoSlot> getBingoSlots() {
        return bingoSlots;
    }

    public List<Boolean> getItemsCompleted(PlayerData pD) {
        List<Boolean> booleans = new ArrayList<>();
        for (BingoSlot slot : bingoSlots) {
            booleans.add(playerTeamHasSlot(slot, pD));
        }
        while (booleans.size() < 25) {
            booleans.add(false);
        }
        return booleans;
    }




}
