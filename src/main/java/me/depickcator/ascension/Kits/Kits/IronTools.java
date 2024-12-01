package me.depickcator.ascension.Kits.Kits;

import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Vanilla.IronAxe;
import me.depickcator.ascension.Items.Craftable.Vanilla.IronSword;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IronTools implements Kit{
    private final static String DISPLAY_NAME = "Iron Tools";
    private final List<ItemStack> kitItems;
    public IronTools() {
        kitItems = ironTools();
        registerKit(this);
    }

    private List<ItemStack> ironTools() {
        ItemStack pickaxe = new ItemStack(Material.IRON_PICKAXE);
        pickaxe.setItemMeta(setMeta(pickaxe));
        ItemStack axe = IronAxe.item().clone();
        axe.setItemMeta(setMeta(axe));
        ItemStack shovel = new ItemStack(Material.IRON_SHOVEL);
        shovel.setItemMeta(setMeta(shovel));

        return new ArrayList<>(Set.of(
                pickaxe,
                axe,
                shovel,
                ironSword(),
                silkTouchBook()
        ));
    }

    private Repairable setMeta(ItemStack item) {
        Repairable meta = (Repairable) item.getItemMeta();
        meta.setRepairCost(999);
        meta.addEnchant(Enchantment.EFFICIENCY, 3, true);
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        return meta;
    }
    private ItemStack ironSword() {
        ItemStack sword = IronSword.item().clone();
        Repairable meta = (Repairable) sword.getItemMeta();
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addEnchant(Enchantment.LOOTING, 1, true);
        meta.setRepairCost(999);
        sword.setItemMeta(meta);
        return sword;
    }
    private ItemStack silkTouchBook() {
        ItemStack silkTouchBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) silkTouchBook.getItemMeta();
        storageMeta.addStoredEnchant(Enchantment.SILK_TOUCH, 1, true);
        silkTouchBook.setItemMeta(storageMeta);
        return silkTouchBook;
    }

    @Override
    public List<ItemStack> getKitItems() {
        return kitItems;
    }

    @Override
    public ItemStack getMascot() {
        ItemStack item = new ItemStack(Material.IRON_PICKAXE);
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
