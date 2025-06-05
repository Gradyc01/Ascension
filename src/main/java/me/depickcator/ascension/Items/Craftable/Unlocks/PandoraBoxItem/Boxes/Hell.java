package me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.Boxes;

import me.depickcator.ascension.Interfaces.CustomChestLootPool;
import me.depickcator.ascension.Interfaces.LootPoolItem;
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
    protected CustomChestLootPool initSpecialItems() {
        List<Enchantment> enchants = new ArrayList<>(List.of(
                Enchantment.QUICK_CHARGE,
                Enchantment.SOUL_SPEED
        ));
        CustomChestLootPool pool = new CustomChestLootPool(
                new LootPoolItem(randomlyEnchantedBook(enchants, rand)),
                new LootPoolItem(Material.BLAZE_ROD, 2),
                new LootPoolItem(Material.NETHERITE_INGOT),
                new LootPoolItem(Material.GHAST_TEAR, 2),
                new LootPoolItem(new ItemStack(Material.OBSIDIAN, 8)),
                new LootPoolItem(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                new LootPoolItem(Material.WITHER_SKELETON_SKULL),
                new LootPoolItem(Material.MAGMA_CREAM, 2),
                new LootPoolItem(new ItemStack(Material.SOUL_SAND, 8))
        );
        return pool;
    }
}
