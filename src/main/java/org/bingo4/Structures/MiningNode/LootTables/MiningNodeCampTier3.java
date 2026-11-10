package org.bingo4.Structures.MiningNode.LootTables;

import org.bingo4.Interfaces.CustomChestLootPool;
import org.bingo4.Interfaces.LootPoolItem;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Mobs.Bingo4Creeper;
import org.bingo4.Mobs.Skeletons.WitherRealmBog;
import org.bingo4.Mobs.Skeletons.WitherRealmSkeleton;
import org.bingo4.Mobs.Skeletons.WitherRealmSkeletonKnight;
import org.bingo4.Mobs.Spiders.WitherRealmCaveSpider;
import org.bingo4.Mobs.Zombies.WitherRealmDrowned;
import org.bingo4.Mobs.Zombies.WitherRealmHusk;
import org.bingo4.Mobs.Zombies.WitherRealmZombie;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.bingo4.MainMenuUI.Map.MapItem.MINING_CAMP_3;

public class MiningNodeCampTier3 extends MiningNodeCampLoot {

    public MiningNodeCampTier3() {
        super("3", MINING_CAMP_3);
    }

    @Override
    protected void addLoot(Random r) {
        loot.addAll(generateItems(r, Material.DIAMOND, 3, 4, 1));
        loot.addAll(generateItems(r, Material.EMERALD, 1, 2, 1));
        loot.addAll(generateItems(r, Material.IRON_INGOT, 10, 4, 2));
        loot.addAll(generateItems(r, Material.GOLD_INGOT, 10, 4, 2));
    }

    @Override
    protected Collection<ItemStack> addRareLoot(Random random) {
        CustomChestLootPool pool = new CustomChestLootPool();
        pool.generateItems(Material.OBSIDIAN, 2, 2, 2, 2);
        pool.generateItems(Material.DISC_FRAGMENT_5, 2, 2, 2, 5);
        pool.addLootPoolItem(new LootPoolItem(Material.AIR, 25),
                new LootPoolItem(Material.ECHO_SHARD, 10));
        return pool.getRandomItemFromList(random, 1);
    }

    @Override
    public List<List<RealmNMSMob>> getSpawnWaves(World world, Random random) {
        return List.of(
                List.of(new WitherRealmZombie(world, random),
                        new WitherRealmHusk(world, random),
                        new Bingo4Creeper(world, random)),
                List.of(new WitherRealmBog(world, random),
                        new WitherRealmCaveSpider(world, random),
                        new WitherRealmSkeleton(world, random),
                        new WitherRealmHusk(world, random)),
                List.of(new WitherRealmDrowned(world),
                        new WitherRealmSkeletonKnight(world))
        );
    }

    @Override
    public Map<Material, Integer> getUnFloodables() {
        return Map.of(
                Material.SCULK, 10,
                Material.DEEPSLATE, 20,
                Material.COBBLED_DEEPSLATE, 5,
                Material.WATER, 5,
                Material.LAVA, 5,
                Material.AIR, 0,
                Material.IRON_BLOCK, 0,
                Material.IRON_BARS, 0
        );
    }


}
