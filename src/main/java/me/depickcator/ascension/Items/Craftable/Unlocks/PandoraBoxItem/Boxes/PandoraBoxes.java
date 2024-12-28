package me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.Boxes;

import me.depickcator.ascension.Interfaces.CustomChestLoot;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public abstract class PandoraBoxes extends CustomChestLoot {
    private final Material icon;
    private final String displayName;
    private final List<ItemStack> constantItems;
    private final List<ItemStack> specialItems;
    protected final Random rand;
    public PandoraBoxes(String displayName, Material icon) {
        rand = new Random();
        this.icon = icon;
        this.displayName = displayName;
        specialItems = initSpecialItems();
        constantItems = initConstantItems();
    }

    protected ItemStack randomlyEnchantedBook(List<Enchantment> enchantments, Random r) {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        Enchantment enchantment = enchantments.get(r.nextInt(0, enchantments.size()));
        int maxLevel = enchantment.getMaxLevel() + 1;
        meta.addStoredEnchant(enchantment, r.nextInt(1, maxLevel), true);
        item.setItemMeta(meta);
        return item;
    }

    public Collection<ItemStack> populateLoot(Inventory inv, Random r, double luck) {
        List<ItemStack> items = new ArrayList<>();
        items.addAll(getRandomItemFromList(constantItems, r, constantItems.size()));
        items.addAll(getRandomItemFromList(specialItems, r, 1));
        return placeInInventory(inv, r, items);
    }
    protected abstract List<ItemStack> initConstantItems();
    protected abstract List<ItemStack> initSpecialItems();

    public Material getIcon() {
        return icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<ItemStack> getConstantItems() {
        return constantItems;
    }

    public List<ItemStack> getSpecialItems() {
        return specialItems;
    }
}
