package me.depickcator.ascension.Interfaces;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.HashMap;

public abstract class EntityInteraction {
    private static final HashMap<LivingEntity, EntityInteraction> entityInteractions = new HashMap<>();

    protected void addInteraction(LivingEntity entity, EntityInteraction interaction) {
        entityInteractions.put(entity, interaction);
    }

    protected void removeInteraction(LivingEntity entity) {
        entityInteractions.remove(entity);
    }

    public static EntityInteraction getEntityInteraction(LivingEntity entity) {
        return entityInteractions.get(entity);
    }

    public abstract boolean interact(PlayerInteractEntityEvent event);
}