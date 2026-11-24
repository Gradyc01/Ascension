package org.bingo4.Structures.MiningNode.LootTables;

import org.bingo4.Interfaces.CustomChestLootPool;
import org.bingo4.Interfaces.LootPoolItem;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Mobs.Bingo4Creeper;
import org.bingo4.Mobs.Skeletons.SkeletonSoldier;
import org.bingo4.Mobs.Skeletons.WitherRealmBog;
import org.bingo4.Mobs.Skeletons.WitherRealmRangerSkeleton;
import org.bingo4.Mobs.Spiders.WitherRealmCaveSpider;
import org.bingo4.Mobs.Zombies.WitherRealmHusk;
import org.bingo4.Mobs.Zombies.WitherRealmZombie;
import org.bingo4.Mobs.Zombies.WitherRealmZombieKnight;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.bingo4.MainMenuUI.Map.MapItem.MINING_CAMP_3;

public class MiningNodeCampTier3Lush extends MiningNodeCampTier3 {

    public MiningNodeCampTier3Lush() {
        super("3_lush", MINING_CAMP_3);
    }


    @Override
    protected Collection<ItemStack> addRareLoot(Random random) {
        CustomChestLootPool pool = new CustomChestLootPool();
        pool.generateItems(Material.BIG_DRIPLEAF, 2, 2, 2, 5);
        pool.generateItems(Material.GLOW_INK_SAC, 2, 2, 2, 5);
        pool.generateItems(Material.GLOW_BERRIES, 2, 2, 2, 5);
        pool.addLootPoolItem(new LootPoolItem(Material.AIR, 10),
                new LootPoolItem(Material.AXOLOTL_BUCKET, 10));
        return pool.getRandomItemFromList(random, 2, true);
    }

    @Override
    public List<List<RealmNMSMob>> getSpawnWaves(World world, Random random) {
        return List.of(
                List.of(new WitherRealmBog(world, random),
                        new WitherRealmHusk(world, random),
                        new Bingo4Creeper(world, random)),
                List.of(new WitherRealmZombie(world, random),
                        new WitherRealmCaveSpider(world, random),
                        new WitherRealmZombieKnight(world)),
                List.of(new WitherRealmRangerSkeleton(world),
                        new SkeletonSoldier(world, random),
                        new SkeletonSoldier(world, random))
        );
    }

    @Override
    public Map<Material, Integer> getUnFloodables() {
        return Map.of(
                Material.MOSS_BLOCK, 10,
                Material.ROOTED_DIRT, 20,
                Material.DEEPSLATE, 10,
                Material.CLAY, 10,
                Material.WATER, 5,
                Material.AIR, 0,
                Material.IRON_BLOCK, 0,
                Material.IRON_BARS, 0
        );
    }

    @Override
    public MiningNodeColorScheme getMaterialScheme() {
        return new MiningNodeColorScheme(Material.GREEN_CONCRETE, Material.GREEN_CONCRETE_STAIRS, Material.LIME_CONCRETE, Material.LIME_CONCRETE_STAIRS, Material.GOLD_BLOCK, Color.YELLOW);
    }

}
