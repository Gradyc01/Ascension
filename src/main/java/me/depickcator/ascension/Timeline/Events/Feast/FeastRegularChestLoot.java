package me.depickcator.ascension.Timeline.Events.Feast;

import me.depickcator.ascension.Interfaces.CustomChestLoot;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class FeastRegularChestLoot extends CustomChestLoot {
    private static FeastRegularChestLoot instance;
    private List<ItemStack> combatItems;
    private List<ItemStack> enchantedBooks;
    private FeastRegularChestLoot() {
        initCombatItems();
        initEnchantedBooks();
    }
    @Override
    public Collection<ItemStack> populateLoot(Inventory inv, Random r, double luck) {
        ArrayList<ItemStack> items = new ArrayList<>();

        items.addAll(getRandomItemFromList(combatItems, r, 2));
        items.addAll(getRandomItemFromList(enchantedBooks, r, 1));

        return placeInInventory(inv, r, items);
    }

    private void initCombatItems() {
//        ItemStack shard = ShardOfTheFallen.result().clone();
        combatItems = new ArrayList<>(List.of(
                ShardOfTheFallen.getInstance().getResult(),
                ShardOfTheFallen.getInstance().getResult(),
                new ItemStack(Material.ARROW, 8),
                new ItemStack(Material.ARROW, 16),
                new ItemStack(Material.ARROW, 24),
                new ItemStack(Material.ARROW, 32),
                new ItemStack(Material.GOLDEN_APPLE, 1),
                new ItemStack(Material.GOLDEN_APPLE, 1),
                new ItemStack(Material.GOLDEN_APPLE, 1),
                new ItemStack(Material.GOLDEN_APPLE, 2)
        ));
    }

    private void initEnchantedBooks() {
        enchantedBooks = new ArrayList<>(List.of(
                makeEnchantedBook(Enchantment.EFFICIENCY, 1),
                makeEnchantedBook(Enchantment.EFFICIENCY, 2),
                makeEnchantedBook(Enchantment.EFFICIENCY, 3),
                makeEnchantedBook(Enchantment.SHARPNESS, 1),
                makeEnchantedBook(Enchantment.SHARPNESS, 2),
                makeEnchantedBook(Enchantment.SHARPNESS, 3),
                makeEnchantedBook(Enchantment.POWER, 1),
                makeEnchantedBook(Enchantment.POWER, 2),
                makeEnchantedBook(Enchantment.POWER, 3),
                makeEnchantedBook(Enchantment.FORTUNE, 1),
                makeEnchantedBook(Enchantment.FORTUNE, 2),
                makeEnchantedBook(Enchantment.FORTUNE, 3),
                makeEnchantedBook(Enchantment.UNBREAKING, 1),
                makeEnchantedBook(Enchantment.UNBREAKING, 2),
                makeEnchantedBook(Enchantment.UNBREAKING, 3),
                makeEnchantedBook(Enchantment.PROTECTION, 1),
                makeEnchantedBook(Enchantment.PROTECTION, 2),
                makeEnchantedBook(Enchantment.PROTECTION, 3),
                makeEnchantedBook(Enchantment.PROJECTILE_PROTECTION, 1),
                makeEnchantedBook(Enchantment.PROJECTILE_PROTECTION, 2),
                makeEnchantedBook(Enchantment.PROJECTILE_PROTECTION, 3)
        ));
    }

    private ItemStack makeEnchantedBook(Enchantment enchantment, int level) {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        meta.addStoredEnchant(enchantment, level, true);
        item.setItemMeta(meta);
        return item;
    }

    public static FeastRegularChestLoot getInstance() {
        if (instance == null) instance = new FeastRegularChestLoot();
        return instance;
    }
}
