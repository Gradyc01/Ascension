package me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.Boxes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Hell extends PandoraBoxes {
    public Hell() {
        super("Hell", Material.NETHERRACK);
    }

    @Override
    protected List<ItemStack> initConstantItems() {
        List<ItemStack> list = new ArrayList<>(List.of(
                new ItemStack(Material.BLAZE_POWDER, rand.nextInt(1, 3)),
                new ItemStack(Material.MAGMA_CREAM, rand.nextInt(1, 4)),
//                new ItemStack(Material.GHAST_TEAR, rand.nextInt(1)),
                new ItemStack(Material.GOLD_INGOT, rand.nextInt(2, 10)),
                new ItemStack(Material.GLOWSTONE_DUST, rand.nextInt(7, 20))
        ));
        return list;
    }

    @Override
    protected List<ItemStack> initSpecialItems() {
        List<Enchantment> enchants = new ArrayList<>(List.of(
                Enchantment.QUICK_CHARGE,
                Enchantment.SOUL_SPEED
        ));
        List<ItemStack> list = new ArrayList<>(List.of(
                new ItemStack(Material.BLAZE_ROD, 1),
                new ItemStack(Material.NETHERITE_INGOT, 1),
                new ItemStack(Material.GHAST_TEAR, 1),
                new ItemStack(Material.OBSIDIAN, rand.nextInt(2, 8)),
                randomlyEnchantedBook(enchants, rand),
                new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1),
                new ItemStack(Material.WITHER_SKELETON_SKULL, 1),
                new ItemStack(Material.MAGMA_CREAM, rand.nextInt(1, 4)),
                new ItemStack(Material.SOUL_SAND, rand.nextInt(2, 8))
        ));
        return list;
    }
}
