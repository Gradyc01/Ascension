package me.depickcator.ascensionBingo.LootTables.Entities;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Interfaces.LootTableChanger;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ZombieEntity implements LootTableChanger, EntityLootTable {
    private final AscensionBingo plugin;
    public static String KEY = EntityType.ZOMBIE.translationKey();
    public ZombieEntity(AscensionBingo plugin) {
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
            EntityDeathEvent e = getEntityDeathEvent(event);
            giveCombatExp(p);
            e.getDrops().clear();

            Random r = new Random();
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());
            if (e.getEntityType() == EntityType.ZOMBIE) {
                lootPoolConstant(e, lootingLevel, r);
                lootPoolBonus(e, lootingLevel, r);
            }
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }

    private void lootPoolConstant(EntityDeathEvent e, int lootingLevel, Random r) {
        int min = 1, max = 2;
        int count = (int) (min + (max - min) * r.nextDouble());

        // Apply looting bonus
        count += (int) (r.nextDouble() * lootingLevel);

        if (count > 0) {
            ItemStack item = new ItemStack(Material.ROTTEN_FLESH, count);
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), item);
        }
    }

    private void lootPoolBonus(EntityDeathEvent e, int lootingLevel, Random r) {
        if (r.nextDouble() < calculateLootChance(lootingLevel, 0.025, 0.035, 0.01)) {
            Material[] drops = {Material.IRON_INGOT, Material.CARROT, Material.POTATO};
            Material drop = drops[r.nextInt(drops.length)];
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(drop));
        }

        if (r.nextDouble() <= calculateLootChance(lootingLevel, 0.020, 0.030, 0.01)) {
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.ZOMBIE_HEAD));
        }
    }


    @Override
    public void registerItem() {
        plugin.getServer().broadcast(TextUtil.makeText(KEY, TextUtil.WHITE));
        addEntity(KEY,this);
    }

    private int getLootingLevel(Player killer) {
        if (killer != null) {
            // killer.getInventory().getItemInMainHand().getEnchantments() != null
            return killer.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOTING);
        }
        return 0;
    }

    private double calculateLootChance(int lootingLevel, double baseChance, double enchantedBase, double perLevel) {
        if (lootingLevel > 0) {
            return enchantedBase + perLevel * (lootingLevel - 1);
        }
        return baseChance;
    }
}