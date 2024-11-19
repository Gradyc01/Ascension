package me.depickcator.ascensionBingo.LootTables.Entities;

import me.depickcator.ascensionBingo.AscensionBingo;

public class EntityUtil {
    public static int COMBAT_COMMON = 8;
    public static int COMBAT_UNCOMMON = 12;
    public static int COMBAT_RARE = 16;
    public static int COMBAT_VERY_RARE = 48;
    public static int COMBAT_LEGENDARY = 96;


    public EntityUtil(AscensionBingo plugin) {
        new ZombieEntity(plugin);
        new SkeletonEntity(plugin);
        new SpiderEntity(plugin);
        new EndermanEntity(plugin);
        new CaveSpider(plugin);
        new CreeperEntity(plugin);
        new NetherMobs(plugin);
    }
}
