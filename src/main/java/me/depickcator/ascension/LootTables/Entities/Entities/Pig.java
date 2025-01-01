package me.depickcator.ascension.LootTables.Entities.Entities;

import me.depickcator.ascension.LootTables.Entities.EntityLootTable;
import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Skills.SkillExpAmount;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Pig implements LootTableChanger, EntityLootTable {
    public static String KEY = EntityType.PIG.translationKey();
    public Pig() {
        registerItem();
    }
    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public boolean uponEvent(Event event, Player p) {
        try {
            EntityDeathEvent e = getEntityDeathEvent(event);
            giveForagingExp(p, SkillExpAmount.FORAGING_UNCOMMON.getExp());
            if (!PlayerUtil.getPlayerData(p).getPlayerStats().isFoodDrops()) {
                e.getDrops().clear();
            }

        } catch (Exception ignored) {
            return false;
        }
        return true;
    }




    @Override
    public void registerItem() {
        addEntity(KEY,this);
    }


}
