package me.depickcator.ascension.listeners;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.LootTables.Entities.Superable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import me.depickcator.ascension.Ascension;

public class MobSpawning implements Listener {
    private final Ascension plugin;
    private final ArrayList<EntityType> superMobs;
    private final Set<EntityType> hostileMobs = Set.of(
            EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER, EntityType.SPIDER, EntityType.ENDERMAN,
            EntityType.PILLAGER, EntityType.BLAZE, EntityType.GHAST, EntityType.MAGMA_CUBE, EntityType.WITHER_SKELETON,
            EntityType.VINDICATOR, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE,
            EntityType.SLIME, EntityType.HUSK, EntityType.DROWNED, EntityType.STRAY
    );
    public MobSpawning(Ascension plugin) {
        superMobs = new ArrayList<>();
        superMobs.add(EntityType.ZOMBIE);
        superMobs.add(EntityType.SKELETON);
        superMobs.add(EntityType.CREEPER);
        superMobs.add(EntityType.SPIDER);
        this.plugin = plugin;
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        if (hostileMobs.contains(event.getEntityType())) {
            event.getEntity().getAttribute(Attribute.ARMOR).setBaseValue(15);
        } else {
            return;
        }
        Random r = new Random();
        if (superMobs.contains(event.getEntityType()) && r.nextDouble() <= 0.025) {
            Superable entity = (Superable) LootTableChanger.findEntity(event.getEntity());
            entity.superEntity(event.getEntity());
        }
    }
}



