package me.depickcator.ascension.Skills;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkillRewards {
    private List<ItemStack> items;
    private int unlockTokens;
    public SkillRewards() {
        items = new ArrayList<>();
        unlockTokens = 0;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
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
        pD.getPlayerUnlocks().addUnlockTokens(unlockTokens, true);
    }

    private void giveItems(PlayerData pD) {
        Player p = pD.getPlayer();
        for (ItemStack i : items) {
            PlayerUtil.giveItem(p, i);
        }

    }

}
