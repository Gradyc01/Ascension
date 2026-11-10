package org.bingo4.LootTables.Entities;

import org.bingo4.LootTables.Entities.Entities.*;
import org.bingo4.LootTables.Entities.Enums.NetherMobs;
import org.bingo4.LootTables.Entities.Enums.OverworldBosses;
import org.bingo4.LootTables.Entities.Enums.OverworldMobs;
import org.bingo4.LootTables.Entities.Enums.OverworldPassive;

public class EntityUtil {
//    public static int COMBAT_COMMON = 8;
//    public static int COMBAT_UNCOMMON = 12;
//    public static int COMBAT_RARE = 16;
//    public static int COMBAT_VERY_RARE = 64;
//    public static int COMBAT_LEGENDARY = 128;


    public EntityUtil() {
        new ZombieEntity();
        new SkeletonEntity();
        new SpiderEntity();
        new EndermanEntity();
        new CaveSpider();
        new CreeperEntity();
        new NetherMobs();
        new Chicken();
        new Cow();
        new Horse();
        new OverworldBosses();
        new OverworldMobs();
        new OverworldPassive();
        new Pig();
        new Sheep();
        new Turtle();
        new Bee();
    }
}
