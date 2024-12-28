package me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.Boxes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Underground extends PandoraBoxes {
    public Underground() {
        super("Underground", Material.COBBLESTONE);
    }

    @Override
    protected List<ItemStack> initConstantItems() {
        List<ItemStack> list = new ArrayList<>(List.of(
                new ItemStack(Material.DIAMOND, rand.nextInt(1, 10)),
                new ItemStack(Material.GOLD_INGOT, rand.nextInt(5, 15)),
                new ItemStack(Material.IRON_INGOT, rand.nextInt(8, 19)),
                new ItemStack(Material.LAPIS_LAZULI, rand.nextInt(14, 28)),
                new ItemStack(Material.COPPER_INGOT, rand.nextInt(8, 20)),
                new ItemStack(Material.REDSTONE, rand.nextInt(14, 28))
        ));
        return list;
    }

    @Override
    protected List<ItemStack> initSpecialItems() {
        List<Enchantment> enchants = new ArrayList<>(List.of(
                Enchantment.FORTUNE,
                Enchantment.EFFICIENCY,
                Enchantment.UNBREAKING
        ));
        List<ItemStack> list = new ArrayList<>(List.of(
                new ItemStack(Material.DIAMOND, rand.nextInt(3, 6)),
                new ItemStack(Material.EMERALD, rand.nextInt(1, 4)),
                new ItemStack(Material.SPAWNER, 1),
                randomlyEnchantedBook(enchants, rand),
                new ItemStack(Material.SPIDER_EYE, rand.nextInt(1, 6)),
                new ItemStack(Material.OBSIDIAN, rand.nextInt(3, 6))
        ));
        return list;
    }
}
