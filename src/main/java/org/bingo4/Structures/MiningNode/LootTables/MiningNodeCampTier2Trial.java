package org.bingo4.Structures.MiningNode.LootTables;

import org.bingo4.Interfaces.CustomChestLootPool;
import org.bingo4.Interfaces.LootPoolItem;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Mobs.Bingo4Breeze;
import org.bingo4.Mobs.Bingo4Creeper;
import org.bingo4.Mobs.Skeletons.SkeletonSoldier;
import org.bingo4.Mobs.Skeletons.WitherRealmBog;
import org.bingo4.Mobs.Skeletons.WitherRealmSkeleton;
import org.bingo4.Mobs.Slimes.Bingo4Slime;
import org.bingo4.Mobs.Spiders.WitherRealmCaveSpider;
import org.bingo4.Mobs.Spiders.WitherRealmSpider;
import org.bingo4.Mobs.Zombies.WitherRealmHusk;
import org.bingo4.Mobs.Zombies.WitherRealmZombie;
import org.bingo4.Mobs.Zombies.WitherRealmZombieKnight;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.bingo4.MainMenuUI.Map.MapItem.MINING_CAMP_2;

public class MiningNodeCampTier2Trial extends MiningNodeCampTier2 {

    public MiningNodeCampTier2Trial() {
        super("2_trial", MINING_CAMP_2);
    }

    @Override
    protected Collection<ItemStack> addRareLoot(Random random) {
        CustomChestLootPool pool = new CustomChestLootPool();
        pool.generateItems(Material.BREEZE_ROD, 1, 2, 2, 4);
        pool.generateItems(Material.BRICKS, 1, 2, 3, 5);
        pool.generateItems(Material.HONEYCOMB, 1, 3, 1, 3);
        pool.generateItems(Material.GOLDEN_APPLE, 1, 5, 1, 3);
        pool.addLootPoolItem(new LootPoolItem(Material.AIR, 20),
                new LootPoolItem(Material.TRIAL_KEY, 8),
                new LootPoolItem(Material.OMINOUS_TRIAL_KEY, 5));
        return pool.getRandomItemFromList(random, 2);
    }

    @Override
    public List<List<RealmNMSMob>> getSpawnWaves(World world, Random random) {
        return List.of(
                List.of(new WitherRealmZombie(world, random),
                        new SkeletonSoldier(world, random),
                        new WitherRealmCaveSpider(world, random)),
                List.of(new Bingo4Creeper(world, random),
                        new WitherRealmSkeleton(world, random),
                        new WitherRealmHusk(world, random),
                        new WitherRealmBog(world, random)),
                List.of(new Bingo4Breeze(world, random),
                        new WitherRealmZombieKnight(world),
                        new Bingo4Slime(world, 2),
                        new Bingo4Breeze(world, random))
        );
    }

    @Override
    public Map<Material, Integer> getUnFloodables() {
        return Map.of(
                Material.OXIDIZED_COPPER_BULB, 5,
                Material.OXIDIZED_COPPER_GRATE, 5,
                Material.TUFF_BRICKS, 12,
                Material.CHISELED_TUFF_BRICKS, 8,
                Material.AIR, 0,
                Material.IRON_BLOCK, 0,
                Material.IRON_BARS, 0
        );
    }

    @Override
    public MiningNodeColorScheme getMaterialScheme() {
        return new MiningNodeColorScheme(Material.CUT_COPPER, Material.CUT_COPPER_STAIRS, Material.OXIDIZED_COPPER, Material.OXIDIZED_CUT_COPPER_STAIRS, Material.IRON_BLOCK);
    }


}
