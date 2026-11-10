package org.bingo4.Structures.MiningNode.LootTables;

import org.bingo4.Interfaces.CustomChestLoot;
import org.bingo4.Interfaces.CustomChestLootPool;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Utility.FloodBuild.FloodBlocks;
import org.bingo4.Utility.FloodBuild.Floodable;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class MiningNodeCampLoot extends CustomChestLoot implements Floodable {

    protected List<ItemStack> loot = new ArrayList<>();
    private final String id;
    private final int mapId;

    public MiningNodeCampLoot(String id, int mapId) {
        this.id = "mining_camp_" + id;
        this.mapId = mapId;
    }

    @Override
    public Collection<ItemStack> populateLoot(Inventory inv, Random r, double luck) {
        loot.clear();
        addLoot(r);
        for (ItemStack item : addRareLoot(r)) {
            if (!item.getType().equals(Material.AIR)) loot.add(item);
        }
        if (inv != null) placeInInventory(inv, r, loot);
        return loot;
    }

    protected abstract void addLoot(Random r);
    protected abstract Collection<ItemStack> addRareLoot(Random random);

    public abstract List<List<RealmNMSMob>> getSpawnWaves(World world, Random random);



    protected Collection<ItemStack> generateItems(Random r, Material material, int base, int num, int increment) {
        CustomChestLootPool items = new CustomChestLootPool();
        items.generateItems(material, base, num, increment);
        return items.getRandomItemFromList(r, 1, true);
    }

    @Override
    public Block changeBlock(Block block, Random r, FloodBlocks floodBlocks) {
        block.setType(floodBlocks.chooseNextMaterial(r));
        return block;
    }

    @Override
    public boolean isUnFloodable(Block b) {
        return getUnFloodables().containsKey(b.getType());
    }

    @Override
    public double getNewSuccessRate(double oldSuccessRate, Random r) {
        return oldSuccessRate - r.nextDouble(0.1, 0.20);
    }

    public String getId() {
        return id;
    }

    public int getMapId() {
        return mapId;
    }
}
