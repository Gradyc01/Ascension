package me.depickcator.ascensionBingo.LootTables.Entities;

import me.depickcator.ascensionBingo.AscensionBingo;

public class EntityUtil {
    public static int BASIC_COMBAT = 6;

    public EntityUtil(AscensionBingo plugin) {
        new ZombieEntity(plugin);
        new SkeletonEntity(plugin);
        new SpiderEntity(plugin);
    }
}
