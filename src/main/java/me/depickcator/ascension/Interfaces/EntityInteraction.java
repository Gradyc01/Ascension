package me.depickcator.ascension.Interfaces;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.HashMap;
import java.util.UUID;

public abstract class EntityInteraction {
    private static final HashMap<UUID, EntityInteraction> entityInteractions = new HashMap<>();

    protected void addInteraction(LivingEntity entity, EntityInteraction interaction) {
//        entityInteractions.put(entity, interaction);
        UUID uuid = entity.getUniqueId();
        entityInteractions.put(uuid, interaction);
    }

    protected void removeInteraction(LivingEntity entity) {
        entityInteractions.remove(entity.getUniqueId());
    }

    public static EntityInteraction getEntityInteraction(LivingEntity entity) {
        return entityInteractions.get(entity.getUniqueId());
    }

    public static void clearInteractions() {
        entityInteractions.clear();
    }

    /* Triggers when a user interacts with an entity has an EntityInteraction attached */
    public abstract boolean interact(PlayerInteractEntityEvent event);
}
