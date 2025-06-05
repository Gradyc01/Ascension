package me.depickcator.ascension.Timeline.Events.Feast;

import me.depickcator.ascension.Interfaces.CustomChestLoot;
import me.depickcator.ascension.Interfaces.CustomChestLootPool;
import me.depickcator.ascension.Interfaces.LootPoolItem;
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
    private CustomChestLootPool combatItems;
    private CustomChestLootPool enchantedBooks;
    private FeastRegularChestLoot() {
        initCombatItems();
        initEnchantedBooks();
    }
    @Override
    public Collection<ItemStack> populateLoot(Inventory inv, Random r, double luck) {
        ArrayList<ItemStack> items = new ArrayList<>();

        items.addAll(combatItems.getRandomItemFromList(r, 2));
        items.addAll(enchantedBooks.getRandomItemFromList(r, 1));

        return placeInInventory(inv, r, items);
    }

    private void initCombatItems() {
        combatItems = new CustomChestLootPool(
                new LootPoolItem(ShardOfTheFallen.getInstance().getResult(1), 3),
                new LootPoolItem(ShardOfTheFallen.getInstance().getResult(2), 1),
                new LootPoolItem(Material.GOLDEN_APPLE, 4),
                new LootPoolItem(Material.GOLDEN_APPLE, 2)
        );
        combatItems.generateItems(Material.ARROW, 8, 4, 8, 2);
    }

    private void initEnchantedBooks() {
        enchantedBooks = new CustomChestLootPool();
        enchantedBooks.addLootPoolItem(makeEnchantedBooks(Enchantment.EFFICIENCY, 1, 1, 3));
        enchantedBooks.addLootPoolItem(makeEnchantedBooks(Enchantment.SHARPNESS, 2, 2, 3));
        enchantedBooks.addLootPoolItem(makeEnchantedBooks(Enchantment.POWER, 2, 2, 3));
        enchantedBooks.addLootPoolItem(makeEnchantedBooks(Enchantment.FORTUNE, 1, 1, 3));
        enchantedBooks.addLootPoolItem(makeEnchantedBooks(Enchantment.UNBREAKING, 1, 1, 3));
        enchantedBooks.addLootPoolItem(makeEnchantedBooks(Enchantment.PROTECTION, 1, 2, 4));
        enchantedBooks.addLootPoolItem(makeEnchantedBooks(Enchantment.PROJECTILE_PROTECTION, 1, 2, 4));
    }

    private List<LootPoolItem> makeEnchantedBooks(Enchantment enchantment, int baseWeight, int incrementWeight, int maxLevel) {
        List<LootPoolItem> lootPoolItems = new ArrayList<>();
        int increment = 0;
        for (int i = maxLevel; i > 0; i--) {
            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
            meta.addStoredEnchant(enchantment, i, true);
            item.setItemMeta(meta);

            lootPoolItems.add(new LootPoolItem(item, baseWeight + increment * incrementWeight));
            increment++;
        }
        return lootPoolItems;
    }

    public static FeastRegularChestLoot getInstance() {
        if (instance == null) instance = new FeastRegularChestLoot();
        return instance;
    }
}
