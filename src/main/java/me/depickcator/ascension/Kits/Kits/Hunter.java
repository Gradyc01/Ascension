package me.depickcator.ascension.Kits.Kits;

import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.Craftable.Vanilla.IronAxe;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Hunter implements Kit{
    private final static String DISPLAY_NAME = "Hunter";
    private final List<ItemStack> kitItems;
    public Hunter() {
        kitItems = ironTools();
        registerKit(this);
    }

    private List<ItemStack> ironTools() {
        ItemStack shovel = new ItemStack(Material.IRON_SHOVEL);
        shovel.setItemMeta(setMeta(shovel));

        return new ArrayList<>(Set.of(
                shovel,
                hunterAxe(),
                hunterBook(),
                new ItemStack(Material.STRING, 6),
                new ItemStack(Material.FEATHER, 9),
                new ItemStack(Material.FLINT, 6),
                new ItemStack(Material.CHAINMAIL_HELMET),
                new ItemStack(Material.CHAINMAIL_CHESTPLATE),
                new ItemStack(Material.CHAINMAIL_LEGGINGS),
                new ItemStack(Material.CHAINMAIL_BOOTS)
        ));
    }

    private ItemStack hunterAxe() {
        ItemStack item = IronAxe.getInstance().getResult().clone();
        Repairable meta = (Repairable) item.getItemMeta();
        meta.displayName(TextUtil.makeText("Hunter's Axe", TextUtil.AQUA));
        meta.addEnchant(Enchantment.SHARPNESS, 2, true);
        meta.addEnchant(Enchantment.LOOTING, 3, true);
        meta.addEnchant(Enchantment.BANE_OF_ARTHROPODS, 2, true);
        meta.addEnchant(Enchantment.SMITE, 2, true);
        meta.addEnchant(Enchantment.EFFICIENCY, 3, true);
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack hunterBook() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        meta.addStoredEnchant(Enchantment.POWER, 3, true);
        meta.addStoredEnchant(Enchantment.PROJECTILE_PROTECTION, 4, true);
        item.setItemMeta(meta);
        return item;
    }

    private Repairable setMeta(ItemStack item) {
        Repairable meta = (Repairable) item.getItemMeta();
        meta.setRepairCost(999);
        meta.addEnchant(Enchantment.EFFICIENCY, 3, true);
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        return meta;
    }

    @Override
    public List<ItemStack> getKitItems() {
        return kitItems;
    }

    @Override
    public ItemStack getMascot() {
        ItemStack item = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.AQUA));
        meta.setCustomModelData(10);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }
}
