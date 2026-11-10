package org.bingo4.listeners.ChestGeneration;

import org.bingo4.Items.Uncraftable.ShardOfTheFallen;
import org.bingo4.Player.Data.PlayerData;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public abstract class ChestLootTable {
    private final String lootTableName;
    public ChestLootTable(String lootTableName) {
        this.lootTableName = lootTableName;
    }

    public abstract void addLootToTable(PlayerData pD, List<ItemStack> lootList);

    public String getLootTableName() {
        return lootTableName;
    }

    protected void givePlayerForagingExp(PlayerData pD, int amount) {
//        pD.getPlayerSkills().getForaging().addExp(amount);
    }

    protected void addShardsOfTheFallen(int min, int max, List<ItemStack> lootList) {
        Random r = new Random();
        addShardsOfTheFallen(r.nextInt(min, max + 1), lootList);
    }

    protected void addShardsOfTheFallen(int amount, List<ItemStack> lootList) {
        ItemStack reward = ShardOfTheFallen.getInstance().getResult(amount);
//        reward.setAmount(amount);
        lootList.add(reward);
    }
}
