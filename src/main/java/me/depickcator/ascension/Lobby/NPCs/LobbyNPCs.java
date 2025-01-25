package me.depickcator.ascension.Lobby.NPCs;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.EntityInteraction;

import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTables;
import org.bukkit.loot.Lootable;

public abstract class LobbyNPCs extends EntityInteraction {
    public LobbyNPCs(double x, double y, double z, Pair<Integer, Integer> rotation, EntityType type, Component name) {
        addInteraction(initEntity(new Location(Ascension.getInstance().getWorld(), x, y, z), type, name, rotation), this);
    }

    protected LivingEntity initEntity(Location loc, EntityType type, Component name, Pair<Integer, Integer> rotation) {
        LivingEntity entity = (LivingEntity) loc.getWorld().spawnEntity(loc, type);
        entity.setInvulnerable(true);
        entity.setSilent(true);
        entity.setAI(false);
        entity.addScoreboardTag("lobby");
        entity.setRemoveWhenFarAway(false);
        entity.setVisualFire(false);
        try {
            entity.getEquipment().setHelmet(new ItemStack(Material.STONE_BUTTON));
        } catch (Exception e) {
            //
        }
        if (entity instanceof Ageable) {
            Ageable ageable = (Ageable) entity;
            ageable.setAdult();
        }
        Lootable lootable = (Lootable) entity;
        lootable.setLootTable(LootTables.PLAYER.getLootTable());
        entity.setRotation(rotation.getLeft(), rotation.getRight());
        entity.setCustomNameVisible(true);
        entity.customName(name);
        return entity;
    }

    public abstract boolean interact(PlayerInteractEntityEvent event);
}
