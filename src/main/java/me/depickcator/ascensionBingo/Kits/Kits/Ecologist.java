package me.depickcator.ascensionBingo.Kits.Kits;

import me.depickcator.ascensionBingo.General.TextUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Ecologist implements Kit{
    private final static String DISPLAY_NAME = "Ecologist";
    private final List<ItemStack> kitItems;
    public Ecologist() {
        kitItems = kitItems();
        registerKit(this);
    }

    private List<ItemStack> kitItems() {
        ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE);
        pickaxe.setItemMeta(setMeta(pickaxe));
        return new ArrayList<>(Set.of(
                pickaxe,
                new ItemStack(Material.LILY_PAD, 64),
                new ItemStack(Material.SUGAR_CANE, 12),
                new ItemStack(Material.VINE, 32),
                new ItemStack(Material.COW_SPAWN_EGG, 3)
        ));
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
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public ItemStack getMascot() {
        ItemStack item = new ItemStack(Material.OAK_LOG);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.AQUA));
        meta.setCustomModelData(10);
        item.setItemMeta(meta);
        return item;
    }
}
