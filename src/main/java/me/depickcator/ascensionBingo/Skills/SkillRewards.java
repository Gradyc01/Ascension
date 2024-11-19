package me.depickcator.ascensionBingo.Skills;

import me.depickcator.ascensionBingo.Player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;

public class SkillRewards {
    private ArrayList<ItemStack> items;
    private int unlockTokens;
    public SkillRewards() {
        items = new ArrayList<>();
        unlockTokens = 0;
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemStack> items) {
        this.items = items;
    }

    public int getUnlockTokens() {
        return unlockTokens;
    }

    public void setUnlockTokens(int unlockTokens) {
        this.unlockTokens = unlockTokens;
    }

    public void giveRewards(PlayerData pD) {
        giveUnlockTokens(pD);
        giveItems(pD);
    }

    private void giveUnlockTokens(PlayerData pD) {
        pD.getPlayerUnlocks().addUnlockTokens(unlockTokens);
    }

    private void giveItems(PlayerData pD) {
        Player p = pD.getPlayer();
        PlayerInventory inv = p.getInventory();
        for (ItemStack i : items) {
            HashMap<Integer, ItemStack> remain = inv.addItem(i);
            if (!remain.isEmpty()) {
                for (int left: remain.keySet()) {
                    ItemStack remainingItem = remain.get(left);
                    remainingItem.setAmount(left);
                    p.getWorld().dropItem(p.getLocation(), remainingItem);
                }
            }
        }
    }

}
