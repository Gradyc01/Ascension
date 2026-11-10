package org.bingo4.Player.Data;

import org.bingo4.Bingo4;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.bingo4.Interfaces.ItemClick.compareItems;

public class PlayerUtil {
    public static void assignNewPlayerData() {
        Bingo4.playerDataMap.clear();
        ArrayList<Player> onlinePlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player p : onlinePlayerList) {
            Bingo4.playerDataMap.put(p.getUniqueId(), new PlayerData(p));
        }
    }

    public static PlayerData assignNewPlayerData(Player p) {
        PlayerData playerData = new PlayerData(p);
        Bingo4.playerDataMap.put(p.getUniqueId(), playerData);
        return playerData;
    }

    public static void clearPlayerDataMap() {
        Bingo4.playerDataMap.clear();
    }

    public static PlayerData getPlayerData(Player p) {
        if (Bingo4.playerDataMap.containsKey(p.getUniqueId())) {
            return Bingo4.playerDataMap.get(p.getUniqueId());
        }
        return null;
    }

    public static boolean removePlayerData(Player p) {
        if (Bingo4.playerDataMap.containsKey(p.getUniqueId())) {
            Bingo4.playerDataMap.remove(p.getUniqueId());
            return true;
        }
        return false;
    }

    //Changes the visibility of the player on tab lists and also in game for all players
    public static void changePlayerVisibility(PlayerData playerData) {
        for (PlayerData pD : PlayerUtil.getAllPlayingPlayers()) {
            Player p = pD.getPlayer();
            if (playerData.checkState(PlayerData.STATE_ALIVE)) {
                p.showPlayer(Bingo4.getInstance(), playerData.getPlayer());
                TextUtil.debugText(playerData.getPlayer().getName() + " is now shown to " + p.getName());
            } else {
                p.hidePlayer(Bingo4.getInstance(), playerData.getPlayer());
                TextUtil.debugText(playerData.getPlayer().getName() + " is no longer now shown to " + p.getName());
            }
        }
    }

    public static void giveItem(Player p, ItemStack... items) {
        giveItem(p, new ArrayList<>(List.of(items)));
    }

    public static void giveItem(Player p, List<ItemStack> items) {
        PlayerInventory inv = p.getInventory();
        PlayerData pD = getPlayerData(p);
//        UnlockRecommender recommender = UnlockRecommender.getInstance();
        PlayerInventoryTracker tracker = pD.getPlayerInventoryTracker();
        List<ItemStack> itemsLeft = new ArrayList<>(); // Items Left to give
        for (ItemStack item : items) {
            itemsLeft.add(item.clone());
        }
        List<Integer> emptySlots = new ArrayList<>(); // All empty slots
        for (int i = 0; i < 35; i++) {
            ItemStack invItem = inv.getItem(i);
            if (invItem == null) {
                emptySlots.add(i);
            } else {
                for (ItemStack item : new ArrayList<>(itemsLeft)) {;
                    if (compareItems(item, invItem)) {
                        int maxSize = invItem.getMaxStackSize();
                        int itemAmount = item.getAmount();
                        int invItemAmount = invItem.getAmount();
                        p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5f, 2);
                        if (maxSize >= invItemAmount + itemAmount) {
                            invItem.setAmount(invItemAmount + itemAmount);
                            itemsLeft.remove(item);
                            //Adds to tracker and recommender
                            tracker.addItems(invItem, itemAmount);
//                            recommender.checkMaterial(new ItemStack(invItem.getType(), itemAmount), p);
                        } else {
                            invItem.setAmount(maxSize);
                            item.setAmount(itemAmount - (maxSize - invItemAmount));
                            //Adds to tracker and recommender
                            if (maxSize - invItemAmount > 0) {
                                tracker.addItems(invItem, (maxSize - invItemAmount));
//                                recommender.checkMaterial(new ItemStack(invItem.getType(), (maxSize - invItemAmount)), p);
                            }
                        }
                    }
                }
            }
        }
        boolean dropped = false;
        for (ItemStack item : itemsLeft) {
            try {
                int slot = emptySlots.getFirst();
                TextUtil.debugText("Empty slot found in " + p.getName() + "'s inventory at: " + slot);
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5f, 2);
                inv.setItem(slot, item);
                emptySlots.removeFirst();
                //Adds to tracker and recommender
                tracker.addItems(item);
//                recommender.checkMaterial(item, p);

            } catch (NoSuchElementException ignored) {
                dropped = true;
                p.getWorld().dropItem(p.getLocation(), item);
            }
        }
        if (dropped) {
            p.sendMessage(TextUtil.makeText("Some items were dropped onto the floor make sure you pick those up", TextUtil.RED));
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
        if (pD.getPlayerStats().getSetting(PlayerStats.nightVisionKey)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, -1, 0, false, false));
        }
    }

}
