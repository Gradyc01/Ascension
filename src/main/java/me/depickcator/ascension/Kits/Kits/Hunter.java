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

public class Hunter extends Kit {
    public Hunter() {
        super("Hunter");
    }

    private ItemStack hunterAxe() {
        ItemStack item = IronAxe.getInstance().getResult().clone();
        Repairable meta = (Repairable) item.getItemMeta();
        meta.displayName(TextUtil.makeText("Hunter's Axe", TextUtil.AQUA));
        meta.addEnchant(Enchantment.SHARPNESS, 2, true);
        meta.addEnchant(Enchantment.LOOTING, 3, true);
        meta.addEnchant(Enchantment.BANE_OF_ARTHROPODS, 2, true);
        meta.addEnchant(Enchantment.SMITE, 3, true);
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

    @Override
    public ItemStack getMascot() {
        ItemStack item = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.AQUA));
        meta.setCustomModelData(10);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public List<ItemStack> initKitItems() {
        return new ArrayList<>(List.of(
                hunterAxe(),
                new ItemStack(Material.STRING, 6),
                new ItemStack(Material.FEATHER, 9),
                new ItemStack(Material.FLINT, 6),
                setToolMeta(new ItemStack(Material.IRON_SHOVEL)),
                hunterBook(),
                new ItemStack(Material.CHAINMAIL_HELMET),
                new ItemStack(Material.CHAINMAIL_CHESTPLATE),
                new ItemStack(Material.CHAINMAIL_LEGGINGS),
                new ItemStack(Material.CHAINMAIL_BOOTS)
        ));
    }
}
