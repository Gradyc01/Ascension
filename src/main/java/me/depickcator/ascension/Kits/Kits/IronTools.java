package me.depickcator.ascension.Kits.Kits;

import me.depickcator.ascension.Items.Uncraftable.ToolVoucher.ToolVoucher;
import me.depickcator.ascension.Utility.TextUtil;
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

public class IronTools extends Kit {
    public IronTools() {
        super("Iron Tools");
    }


    private ItemStack ironSword() {
        ItemStack sword = IronSword.getInstance().getResult().clone();
        Repairable meta = (Repairable) sword.getItemMeta();
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addEnchant(Enchantment.LOOTING, 1, true);
        meta.setRepairCost(999);
        sword.setItemMeta(meta);
        return sword;
    }

    @Override
    public ItemStack getMascot() {
        ItemStack item = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.AQUA));
        meta.setCustomModelData(10);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public List<ItemStack> initKitItems() {
        return new ArrayList<>(List.of(
                ironSword(),
                setToolMeta(new ItemStack(Material.IRON_PICKAXE)),
                setToolMeta(IronAxe.getInstance().getResult().clone()),
                setToolMeta(new ItemStack(Material.IRON_SHOVEL)),
                ToolVoucher.getInstance().getResult()
        ));
    }
}
