package me.depickcator.ascensionBingo.LootTables.Entities;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.LootTables.Entities.Entities.*;
import me.depickcator.ascensionBingo.LootTables.Entities.Enums.NetherMobs;
import me.depickcator.ascensionBingo.LootTables.Entities.Enums.OverworldBosses;
import me.depickcator.ascensionBingo.LootTables.Entities.Enums.OverworldMobs;
import me.depickcator.ascensionBingo.LootTables.Entities.Enums.OverworldPassive;

public class EntityUtil {
    public static int COMBAT_COMMON = 8;
    public static int COMBAT_UNCOMMON = 12;
    public static int COMBAT_RARE = 16;
    public static int COMBAT_VERY_RARE = 64;
    public static int COMBAT_LEGENDARY = 128;


    public EntityUtil(AscensionBingo plugin) {
        new ZombieEntity(plugin);
        new SkeletonEntity(plugin);
        new SpiderEntity(plugin);
        new EndermanEntity(plugin);
        new CaveSpider(plugin);
        new CreeperEntity(plugin);
        new NetherMobs(plugin);
        new Chicken(plugin);
        new Cow(plugin);
        new Horse(plugin);
        new OverworldBosses(plugin);
        new OverworldMobs(plugin);
        new OverworldPassive(plugin);
        new Pig(plugin);
        new Sheep(plugin);
    }
}
