package me.depickcator.ascension.Player.Data;

import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerUnlocks implements PlayerDataObservers {
    public static int AMOUNT_COMMON = 75;
    public static int AMOUNT_UNCOMMON = 125;
    public static int AMOUNT_RARE = 250;
    public static int AMOUNT_VERY_RARE = 500;
    public static int AMOUNT_LEGENDARY = 750;

    private Player player;
    private final PlayerData playerData;
    private final List<Integer> amountNeeded = new ArrayList<>(List.of(
            0, 0, 0, 0, 0
));
    private final List<Integer> unlockTiers;
    private final List<Boolean> canUnlockTiers;
    private int unlockTokens;
    private final Map<String, Integer> UnlocksMap;
    public PlayerUnlocks(PlayerData playerData) {
        this.player = playerData.getPlayer();
        this.playerData = playerData;

        this.unlockTokens = 0;
        UnlocksMap = new HashMap<>();
        unlockTiers = new ArrayList<>(List.of(
                0, 0, 0, 0, 0
        ));
        canUnlockTiers = new ArrayList<>(List.of(
                false, false, false, false, false
        ));
    }

    public boolean unlockUnlock(Craft craft, Integer tier) {
        if (canBeUnlocked(craft, tier)) {
            Component unlockedText = TextUtil.makeText("You have unlocked ", TextUtil.AQUA);
            Component craftName = TextUtil.makeText(craft.getDisplayName(), TextUtil.GOLD);
            player.sendMessage(unlockedText.append(craftName));
            SoundUtil.playHighPitchPling(player);
            addUnlockTokens(-craft.getCraftCost());
            addToMap(craft.getKey(), tier);
            return true;
        }
        return false;
    }

    public boolean hasUnlock(String name) {
        if (UnlockUtil.isAUnlock(name)) {
            return UnlocksMap.containsKey(name);
        }
        return false;
    }

    public int getUnlockCount(String name) {
        if (UnlockUtil.isAUnlock(name) && hasUnlock(name)) {
            return UnlocksMap.get(name);
        }
        return -1;
    }

    public boolean addUnlockCount(String name, int amount) {
        if (UnlockUtil.isAUnlock(name) && hasUnlock(name)) {
            int newAmount = amount + UnlocksMap.get(name);
            if (isUnderLimit(name, newAmount)) {
                UnlocksMap.put(name, UnlocksMap.get(name) + amount);
                return true;
            }
        }
        return false;
    }

    private boolean isUnderLimit(String name, int amount) {
        int maxCrafts = UnlockUtil.getMaxCrafts(name);
        return amount <= maxCrafts;
    }

    private void addToMap(String name, Integer tier) {
        UnlocksMap.put(name, 0);
        unlockTiers.set(tier - 1, unlockTiers.get(tier - 1) + 1);
    }

    public boolean canBeUnlocked(Craft craft, Integer tier) {
        String key = craft.getKey();
        if (UnlockUtil.isAUnlock(key) && unlockTokens >= craft.getCraftCost() && UnlocksMap.get(craft.getKey()) == null) {
            if (tier == -1) return true;
            return canUnlockTier(tier);
        }
        return false;
    }

    private boolean canUnlockTier(int tier) {
        initCanUnlockTiers();
        return canUnlockTiers.get(tier - 1);
    }

    /*Returns a double between 0 - 1 representing the percentage of the tier that has been unlocked*/
    public double unlockTierPercentage(int tier) {
        if (tier > unlockTiers.size()) return -1;
        if (canUnlockTier(tier)) return 1;
        return ((double) unlockTiers.get(tier - 2) / amountNeeded.get(tier - 1));
    }

    private void initCanUnlockTiers() {
        canUnlockTiers.clear();
        canUnlockTiers.add(true);
        for (int i = 0; i < 4; i++) {
            boolean hasAmountNeeded = unlockTiers.get(i) >= amountNeeded.get(i + 1);
            boolean unlockedPreviousTier = canUnlockTiers.get(i);
            canUnlockTiers.add(hasAmountNeeded && unlockedPreviousTier);
        }
    }

    public int getUnlockTokens() {
        return unlockTokens;
    }

    public void setUnlockTokens(int unlockTokens) {
        this.unlockTokens = unlockTokens;
    }

    public void addUnlockTokens(int unlockTokens) {
        addUnlockTokens(unlockTokens, false);

    }

    public void addUnlockTokens(int unlockTokens, boolean msg) {
        if (msg) {
            player.sendMessage(TextUtil.makeText("You have earned " + unlockTokens + " Souls", TextUtil.AQUA));
        }
        this.unlockTokens += unlockTokens;
        playerData.getPlayerScoreboard().update();
    }

    @Override
    public void updatePlayer() {
        player = playerData.getPlayer();
    }
}
