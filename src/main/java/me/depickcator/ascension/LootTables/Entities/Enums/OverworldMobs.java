package me.depickcator.ascension.LootTables.Entities.Enums;

import me.depickcator.ascension.LootTables.Entities.EntityLootTable;
import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.Skills.SkillExpAmount;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class OverworldMobs implements LootTableChanger, EntityLootTable {
    public OverworldMobs() {
        registerItem();
    }
    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public boolean uponEvent(Event event, Player p) {
        try {
            giveCombatExp(p, SkillExpAmount.COMBAT_COMMON.getExp());

        } catch (Exception ignored) {
            return false;
        }
        return true;
    }


    @Override
    public void registerItem() {
        addEntity(EntityType.HUSK.translationKey(),this);
        addEntity(EntityType.DROWNED.translationKey(),this);
        addEntity(EntityType.GUARDIAN.translationKey(),this);
        addEntity(EntityType.PILLAGER.translationKey(),this);
        addEntity(EntityType.SLIME.translationKey(),this);
        addEntity(EntityType.STRAY.translationKey(),this);
        addEntity(EntityType.WITCH.translationKey(),this);
        addEntity(EntityType.ZOMBIE_VILLAGER.translationKey(),this);
    }
}
