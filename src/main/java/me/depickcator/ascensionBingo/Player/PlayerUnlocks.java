package me.depickcator.ascensionBingo.Player;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.SoundUtil;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Items.UnlockUtil;
import me.depickcator.ascensionBingo.Items.Unlocks.Crafts;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerUnlocks {
    public static int AMOUNT_COMMON = 25;
    public static int AMOUNT_UNCOMMON = 50;
    public static int AMOUNT_RARE = 75;
    public static int AMOUNT_VERY_RARE = 125;
    public static int AMOUNT_LEGENDARY = 250;

    private final int AMOUNT_NEEDED_1 = 7;
    private final int AMOUNT_NEEDED_2 = 7;
    private final int AMOUNT_NEEDED_3 = 7;
    private final int AMOUNT_NEEDED_4 = 7;
    private AscensionBingo plugin;
    private Player player;
    private PlayerData playerData;
    private int tier1Unlocks;
    private int tier2Unlocks;
    private int tier3Unlocks;
    private int tier4Unlocks;
    private int tier5Unlocks;

    private int unlockTokens;
    private Map<String, Integer> UnlocksMap;
    public PlayerUnlocks(AscensionBingo plugin, PlayerData playerData) {
        this.plugin = plugin;
        this.player = playerData.getPlayer();
        this.playerData = playerData;
        this.tier1Unlocks = 0;
        this.tier2Unlocks = 0;
        this.tier3Unlocks = 0;
        this.tier4Unlocks = 0;
        this.tier5Unlocks = 0;
        this.unlockTokens = 0;
        UnlocksMap = new HashMap<>();
    }

    public boolean unlockUnlock(Crafts craft, Integer tier) {
        if (canBeUnlocked(craft, tier)) {
            Component unlockedText = TextUtil.makeText("You have unlocked ", TextUtil.AQUA);
            Component craftName = TextUtil.makeText(craft.getDisplayName(), TextUtil.GOLD);
            player.sendMessage(unlockedText.append(craftName));
            SoundUtil.playHighPitchPling(player);
            unlockTokens-=craft.getCraftCost();
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
        switch (tier) {
            case 1 -> {
                tier1Unlocks++;
            }
            case 2 -> {
                tier2Unlocks++;
            }
            case 3 -> {
                tier3Unlocks++;
            }
            case 4 -> {
                tier4Unlocks++;
            }
            case 5 -> {
                tier5Unlocks++;
            }
            default -> {
                player.sendMessage("ERROR: addToMap");
            }
        }
    }

    private boolean canBeUnlocked(Crafts craft, Integer tier) {
        String key = craft.getKey();
        if (UnlockUtil.isAUnlock(key) && unlockTokens >= craft.getCraftCost() && UnlocksMap.get(craft.getKey()) == null) {
            switch (tier) {
                case -1, 1 -> {
                    return true;
                }
                case 2 -> {
                    return canUnlockTier2();
                }
                case 3 -> {
                    return canUnlockTier3();
                }
                case 4 -> {
                    return canUnlockTier4();
                }
                case 5 -> {
                    return canUnlockTier5();
                }
                default -> {
                    player.sendMessage("ERROR: can be unlocked");
                    return false;
                }
            }
        }
        return false;
    }

    public boolean canUnlockTier2() {
        return tier1Unlocks >= AMOUNT_NEEDED_1;
    }

    public boolean canUnlockTier3() {
        return canUnlockTier2() && tier2Unlocks >= AMOUNT_NEEDED_2;
    }

    public boolean canUnlockTier4() {
        return canUnlockTier3() && tier3Unlocks >= AMOUNT_NEEDED_3;
    }

    public boolean canUnlockTier5() {
        return canUnlockTier4() && tier4Unlocks >= AMOUNT_NEEDED_4;
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
        playerData.getPlayerScoreboard().updateGameBoard();
    }
}
