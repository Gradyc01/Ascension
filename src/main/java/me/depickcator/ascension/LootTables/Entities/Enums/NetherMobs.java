package me.depickcator.ascension.LootTables.Entities.Enums;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.LootTables.Entities.EntityLootTable;
import me.depickcator.ascension.LootTables.Entities.EntityUtil;
import me.depickcator.ascension.LootTables.LootTableChanger;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class NetherMobs implements LootTableChanger, EntityLootTable {
    private final Ascension plugin;
    public NetherMobs(Ascension plugin) {
        this.plugin = plugin;
        registerItem();
    }
    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public boolean uponEvent(Event event, Player p) {
        try {
            giveCombatExp(p, EntityUtil.COMBAT_RARE);

        } catch (Exception ignored) {
            return false;
        }
        return true;
    }


    @Override
    public void registerItem() {
        addEntity(EntityType.BLAZE.translationKey(),this);
        addEntity(EntityType.MAGMA_CUBE.translationKey(),this);
        addEntity(EntityType.PIGLIN.translationKey(),this);
        addEntity(EntityType.ZOMBIFIED_PIGLIN.translationKey(),this);
        addEntity(EntityType.PIGLIN_BRUTE.translationKey(),this);
        addEntity(EntityType.HOGLIN.translationKey(),this);
        addEntity(EntityType.GHAST.translationKey(),this);
    }
}
