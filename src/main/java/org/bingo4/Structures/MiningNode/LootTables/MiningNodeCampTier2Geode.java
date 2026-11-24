package org.bingo4.Structures.MiningNode.LootTables;

import org.bingo4.Interfaces.CustomChestLootPool;
import org.bingo4.Interfaces.LootPoolItem;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Mobs.Bingo4Creeper;
import org.bingo4.Mobs.Skeletons.WitherRealmBog;
import org.bingo4.Mobs.Skeletons.WitherRealmSkeleton;
import org.bingo4.Mobs.Spiders.WitherRealmCaveSpider;
import org.bingo4.Mobs.Spiders.WitherRealmSpider;
import org.bingo4.Mobs.Skeletons.SkeletonSoldier;
import org.bingo4.Mobs.Zombies.WitherRealmHusk;
import org.bingo4.Mobs.Zombies.WitherRealmZombie;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.bingo4.MainMenuUI.Map.MapItem.MINING_CAMP_2;

public class MiningNodeCampTier2Geode extends MiningNodeCampTier2 {

    public MiningNodeCampTier2Geode() {
        super("2_geode", MINING_CAMP_2);
    }

    @Override
    protected Collection<ItemStack> addRareLoot(Random random) {
        CustomChestLootPool pool = new CustomChestLootPool();
        pool.generateItems(Material.DIAMOND, 2, 2, 2, 4);
        pool.generateItems(Material.AMETHYST_SHARD, 2, 5, 1, 10);
        pool.addLootPoolItem(new LootPoolItem(Material.AIR, 20),
                new LootPoolItem(Material.RAW_COPPER_BLOCK, 1),
                new LootPoolItem(Material.RAW_IRON_BLOCK, 1));
        return pool.getRandomItemFromList(random, 1);
    }

    @Override
    public List<List<RealmNMSMob>> getSpawnWaves(World world, Random random) {
        return List.of(
                List.of(new WitherRealmZombie(world, random),
                        new SkeletonSoldier(world, random),
                        new WitherRealmCaveSpider(world, random)),
                List.of(new Bingo4Creeper(world, random),
                        new WitherRealmSkeleton(world, random),
                        new WitherRealmZombie(world, random),
                        new WitherRealmSpider(world, random)),
                List.of(new SkeletonSoldier(world, random),
                        new WitherRealmHusk(world, random),
                        new WitherRealmBog(world, random),
                        new SkeletonSoldier(world, random))
        );
    }

    @Override
    public Map<Material, Integer> getUnFloodables() {
        return Map.of(
                Material.AMETHYST_BLOCK, 10,
                Material.STONE, 10,
                Material.AMETHYST_CLUSTER, 1,
                Material.AIR, 0,
                Material.IRON_BLOCK, 0,
                Material.IRON_BARS, 0
        );
    }

    @Override
    public MiningNodeColorScheme getMaterialScheme() {
        return new MiningNodeColorScheme(Material.PURPLE_CONCRETE, Material.PURPLE_CONCRETE_STAIRS, Material.AMETHYST_BLOCK, Material.PINK_CONCRETE_STAIRS, Material.IRON_BLOCK, Color.ORANGE);
    }


}
