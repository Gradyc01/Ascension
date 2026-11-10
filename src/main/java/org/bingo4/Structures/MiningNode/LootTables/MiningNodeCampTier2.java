package org.bingo4.Structures.MiningNode.LootTables;

import org.bingo4.Interfaces.CustomChestLootPool;
import org.bingo4.Interfaces.LootPoolItem;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Mobs.Bingo4Creeper;
import org.bingo4.Mobs.Skeletons.WitherRealmSkeleton;
import org.bingo4.Mobs.Spiders.WitherRealmSpider;
import org.bingo4.Mobs.Zombies.WitherRealmHusk;
import org.bingo4.Mobs.Zombies.WitherRealmZombie;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.bingo4.MainMenuUI.Map.MapItem.MINING_CAMP_1;
import static org.bingo4.MainMenuUI.Map.MapItem.MINING_CAMP_2;

public class MiningNodeCampTier2 extends MiningNodeCampLoot {

    public MiningNodeCampTier2() {
        super("2", MINING_CAMP_2);
    }

    @Override
    protected void addLoot(Random r) {
        loot.addAll(generateItems(r, Material.IRON_INGOT, 8, 4, 2));
        loot.addAll(generateItems(r, Material.GOLD_INGOT, 4, 5, 2));
        loot.addAll(generateItems(r, Material.REDSTONE, 8, 4, 4));
        loot.addAll(generateItems(r, Material.LAPIS_LAZULI, 8, 4, 4));
    }

    @Override
    protected Collection<ItemStack> addRareLoot(Random random) {
        CustomChestLootPool pool = new CustomChestLootPool();
        pool.generateItems(Material.DIAMOND, 2, 2, 2, 4);
        pool.addLootPoolItem(new LootPoolItem(Material.AIR, 30),
                new LootPoolItem(Material.POINTED_DRIPSTONE, 10),
                new LootPoolItem(Material.RAW_COPPER_BLOCK, 1),
                new LootPoolItem(Material.RAW_IRON_BLOCK, 1));
        return pool.getRandomItemFromList(random, 1);
    }

    @Override
    public List<List<RealmNMSMob>> getSpawnWaves(World world, Random random) {
        return List.of(
                List.of(new WitherRealmZombie(world, random),
                        new WitherRealmHusk(world, random),
                        new Bingo4Creeper(world, random)),
                List.of(new WitherRealmSkeleton(world, random),
                        new WitherRealmSkeleton(world, random),
                        new WitherRealmSpider(world, random),
                        new WitherRealmSpider(world, random)),
                List.of(new WitherRealmHusk(world, random),
                        new WitherRealmHusk(world, random),
                        new WitherRealmSkeleton(world, random),
                        new WitherRealmZombie(world, random),
                        new WitherRealmSpider(world, random))
        );
    }

    @Override
    public Map<Material, Integer> getUnFloodables() {
        return Map.of(
                Material.DRIPSTONE_BLOCK, 10,
                Material.STONE, 20,
                Material.GRANITE, 5,
                Material.CLAY, 5,
                Material.GRAVEL, 5,
                Material.WATER, 2,
                Material.AIR, 0,
                Material.IRON_BLOCK, 0,
                Material.IRON_BARS, 0
        );
    }


}
