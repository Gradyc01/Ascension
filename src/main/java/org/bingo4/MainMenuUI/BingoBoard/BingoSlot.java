package org.bingo4.MainMenuUI.BingoBoard;

import org.bingo4.Player.Data.PlayerData;
import org.bukkit.inventory.ItemStack;

public class BingoSlot {
    private ItemStack item;
    private PlayerData claimer;

    public BingoSlot(ItemStack item) {
        this.item = item;
        this.claimer = null;
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

    public boolean isClaimed() {
        return claimer != null;
    }
}
