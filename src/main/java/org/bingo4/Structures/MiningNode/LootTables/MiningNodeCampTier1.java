package org.bingo4.Structures.MiningNode.LootTables;

import org.bingo4.Interfaces.CustomChestLootPool;
import org.bingo4.Interfaces.LootPoolItem;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Mobs.Bingo4Creeper;
import org.bingo4.Mobs.Skeletons.WitherRealmSkeleton;
import org.bingo4.Mobs.Spiders.WitherRealmSpider;
import org.bingo4.Mobs.Zombies.WitherRealmZombie;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.bingo4.MainMenuUI.Map.MapItem.MINING_CAMP_1;

public class MiningNodeCampTier1 extends MiningNodeCampLoot {

    public MiningNodeCampTier1() {
        super("1", MINING_CAMP_1);
    }

    @Override
    protected void addLoot(Random r) {
        loot.addAll(generateItems(r, Material.IRON_INGOT, 6, 4, 2));
        loot.addAll(generateItems(r, Material.COAL, 8, 4, 4));
        loot.addAll(generateItems(r, Material.COPPER_INGOT, 8, 4, 4));
    }

    @Override
    protected Collection<ItemStack> addRareLoot(Random r) {
        CustomChestLootPool pool = new CustomChestLootPool();
        pool.generateItems(Material.GOLD_INGOT, 2, 2, 2, 4);
        pool.addLootPoolItem(new LootPoolItem(Material.AIR, 40),
                new LootPoolItem(Material.NAME_TAG, 1),
                new LootPoolItem(Material.SADDLE, 1));
        return pool.getRandomItemFromList(r, 1);
    }

    @Override
    public List<List<RealmNMSMob>> getSpawnWaves(World world, Random random) {
        return List.of(
                List.of(new WitherRealmZombie(world, random), new WitherRealmZombie(world, random), new WitherRealmSpider(world, random)),
                List.of(new Bingo4Creeper(world, random), new WitherRealmSkeleton(world, random)),
                List.of(new WitherRealmZombie(world, random), new WitherRealmSkeleton(world, random), new WitherRealmSpider(world, random), new WitherRealmZombie(world, random))
        );
    }

    @Override
    public Map<Material, Integer> getUnFloodables() {
        return Map.of(
                Material.COBBLESTONE, 10,
                Material.STONE, 10,
                Material.ANDESITE, 5,
                Material.DIORITE, 5,
                Material.AIR, 0,
                Material.IRON_BLOCK, 0,
                Material.IRON_BARS, 0
        );
    }


}
