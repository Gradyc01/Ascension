package org.bingo4.MainMenuUI.BingoBoard;

import org.bingo4.Player.Data.PlayerData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BingoSlot {
    private ItemStack item;
    private PlayerData claimer;
    private boolean locked;

    public BingoSlot(ItemStack item) {
        this.item = item;
        this.claimer = null;
        locked = false;
    }
    
    public ItemStack getItem() {
        return item;
    }

    public PlayerData getClaimer() {
        return claimer;
    }

    public void setClaimer(PlayerData claimer) {
        this.claimer = claimer;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void setLocked() {
        locked = true;
        setItem(new ItemStack(Material.BEDROCK));
    }

    public boolean isClaimed() {
        return claimer != null;
    }

    public boolean isLocked() {
        return locked;
    }
}
