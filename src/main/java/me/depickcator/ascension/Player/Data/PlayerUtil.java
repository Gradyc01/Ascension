package me.depickcator.ascension.Player.Data;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.ItemComparison;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static me.depickcator.ascension.Interfaces.ItemClick.compareItems;

public class PlayerUtil {
    public static void assignNewPlayerData() {
        Ascension.playerDataMap.clear();
        ArrayList<Player> onlinePlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player p : onlinePlayerList) {
            Ascension.playerDataMap.put(p.getUniqueId(), new PlayerData(p));
        }
    }

    public static PlayerData assignNewPlayerData(Player p) {
        PlayerData playerData = new PlayerData(p);
        Ascension.playerDataMap.put(p.getUniqueId(), playerData);
        return playerData;
    }

    public static void clearPlayerDataMap() {
        Ascension.playerDataMap.clear();
    }

    public static PlayerData getPlayerData(Player p) {
        if (Ascension.playerDataMap.containsKey(p.getUniqueId())) {
            return Ascension.playerDataMap.get(p.getUniqueId());
        }
        return null;
    }

    public static boolean removePlayerData(Player p) {
        if (Ascension.playerDataMap.containsKey(p.getUniqueId())) {
            Ascension.playerDataMap.remove(p.getUniqueId());
            return true;
        }
        return false;
    }

    //Changes the visibility of the player on tab lists and also in game for all players
    public static void changePlayerVisibility(PlayerData playerData) {
        for (PlayerData pD : PlayerUtil.getAllPlayingPlayers()) {
            Player p = pD.getPlayer();
            if (playerData.checkState(PlayerData.STATE_ALIVE)) {
                p.showPlayer(Ascension.getInstance(), playerData.getPlayer());
                TextUtil.debugText(playerData.getPlayer().getName() + " is now shown to " + p.getName());
            } else {
                p.hidePlayer(Ascension.getInstance(), playerData.getPlayer());
                TextUtil.debugText(playerData.getPlayer().getName() + " is no longer now shown to " + p.getName());
            }
        }
    }

    public static void giveItem(Player p, ItemStack... items) {
        giveItem(p, new ArrayList<>(List.of(items)));
    }

    public static void giveItem(Player p, List<ItemStack> items) {
        PlayerInventory inv = p.getInventory();
//        for (ItemStack item : items) {
//            int emptySlot = inv.firstEmpty();
//            if (emptySlot != -1) {
//                TextUtil.debugText("Empty slot found in " + p.getName() + "'s inventory at: " + emptySlot);
//                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 2);
//                inv.setItem(emptySlot, item);
//                continue;
//            }
//            p.getWorld().dropItem(p.getLocation(), item);
//        }
        List<ItemStack> itemsLeft = new ArrayList<>(); // Items Left to give
        for (ItemStack item : items) {
            itemsLeft.add(item.clone());
        }
        List<Integer> emptySlots = new ArrayList<>(); // All empty slots
        for (int i = 0; i < 35; i++) {
            ItemStack invItem = inv.getItem(i);
            if (invItem == null) {
                TextUtil.debugText("Empty Slot found at index " + i);
                emptySlots.add(i);
            } else {
                TextUtil.debugText("Empty Slot not found at index " + i);
                for (ItemStack item : new ArrayList<>(itemsLeft)) {
                    TextUtil.debugText("Comparing Item " + invItem.getType() + " to " + item.getType());
                    if (compareItems(item, invItem)) {
                        TextUtil.debugText("Same Item");
                        int maxSize = invItem.getMaxStackSize();
                        int itemAmount = item.getAmount();
                        int invItemAmount = invItem.getAmount();
                        if (maxSize >= invItemAmount + itemAmount) {
                            TextUtil.debugText("Not full");
                            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5f, 2);
                            invItem.setAmount(invItemAmount + itemAmount);
                            itemsLeft.remove(item);
                        } else {
                            TextUtil.debugText("Full");
                            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5f, 2);
                            invItem.setAmount(maxSize);
                            item.setAmount(itemAmount - (maxSize - invItemAmount));
                        }
                    }
                }
            }
        }
        for (ItemStack item : itemsLeft) {
            try {
                int slot = emptySlots.getFirst();
                TextUtil.debugText("Empty slot found in " + p.getName() + "'s inventory at: " + slot);
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5f, 2);
                inv.setItem(slot, item);
                emptySlots.removeFirst();

            } catch (NoSuchElementException ignored) {
                p.getWorld().dropItem(p.getLocation(), item);
            }
        }
    }


    //Get & returns all players who are in the game
    public static List<PlayerData> getAllPlayingPlayers() {
        ArrayList<Player> onlinePlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        ArrayList<PlayerData> playingPlayers = new ArrayList<>();
        for (Player p : onlinePlayerList) {
            PlayerData pD = getPlayerData(p);
            if (pD == null || pD.checkState(PlayerData.STATE_SPECTATING)) continue;
            playingPlayers.add(pD);
        }
        return playingPlayers;
    }

    public static void updateTabList() {
        ArrayList<Player> onlinePlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player p : onlinePlayerList) {
            PlayerData pD = getPlayerData(p);
            pD.getPlayerScoreboard().updateTabList();
        }
    }

    public static void clearEffects(PlayerData pD) {
        Player p = pD.getPlayer();
        p.clearActivePotionEffects();
        if (pD.getPlayerStats().isNightVision()) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, -1, 0, false, false));
        }
    }

}
