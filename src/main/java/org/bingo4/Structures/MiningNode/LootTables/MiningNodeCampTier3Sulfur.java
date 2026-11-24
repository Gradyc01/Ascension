package org.bingo4.Structures.MiningNode.LootTables;

import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Mobs.Bingo4Creeper;
import org.bingo4.Mobs.Skeletons.WitherRealmSkeleton;
import org.bingo4.Mobs.Slimes.Bingo4Slime;
import org.bingo4.Mobs.Slimes.Bingo4SulfurCube;
import org.bingo4.Mobs.Zombies.*;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.bingo4.MainMenuUI.Map.MapItem.MINING_CAMP_3;

public class MiningNodeCampTier3Sulfur extends MiningNodeCampTier3 {

    public MiningNodeCampTier3Sulfur() {
        super("3_sulfur", MINING_CAMP_3);
    }


//    @Override
//    protected Collection<ItemStack> addRareLoot(Random random) {
//        CustomChestLootPool pool = new CustomChestLootPool();
//        pool.generateItems(Material.BIG_DRIPLEAF, 2, 2, 2, 5);
//        pool.generateItems(Material.GLOW_INK_SAC, 2, 2, 2, 5);
//        pool.generateItems(Material.GLOW_BERRIES, 2, 2, 2, 5);
//        pool.addLootPoolItem(new LootPoolItem(Material.AIR, 10),
//                new LootPoolItem(Material.AXOLOTL_BUCKET, 10));
//        return pool.getRandomItemFromList(random, 2, true);
//    }

    @Override
    public List<List<RealmNMSMob>> getSpawnWaves(World world, Random random) {
        return List.of(
                List.of(new WitherRealmZombie(world, random),
                        new WitherRealmHusk(world, random),
                        new Bingo4Creeper(world, random)),
                List.of(new WitherRealmZombieKnight(world),
                        new WitherRealmSkeleton(world, random),
                        new WitherRealmHusk(world, random)),
                List.of(new Bingo4SulfurCube(world),
                        new Bingo4Slime(world))
        );
    }

    @Override
    public Map<Material, Integer> getUnFloodables() {
        return Map.of(
                Material.SULFUR, 10,
                Material.POLISHED_SULFUR, 10,
                Material.CINNABAR, 10,
                Material.POTENT_SULFUR, 10,
                Material.WATER, 2,
                Material.AIR, 0,
                Material.IRON_BLOCK, 0,
                Material.IRON_BARS, 0
        );
    }

    @Override
    public MiningNodeColorScheme getMaterialScheme() {
        return new MiningNodeColorScheme(Material.YELLOW_CONCRETE, Material.YELLOW_CONCRETE_STAIRS, Material.CINNABAR, Material.CINNABAR_STAIRS, Material.GOLD_BLOCK, Color.YELLOW);
    }

}
