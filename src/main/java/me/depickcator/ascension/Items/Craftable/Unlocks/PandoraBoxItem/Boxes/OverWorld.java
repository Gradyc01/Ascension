package me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.Boxes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class OverWorld extends PandoraBoxes {
    public OverWorld() {
        super("OverWorld", Material.GRASS_BLOCK);
    }

    @Override
    protected List<ItemStack> initConstantItems() {
        List<ItemStack> list = new ArrayList<>(List.of(
                new ItemStack(Material.ENDER_PEARL, 1),
                new ItemStack(Material.BONE, rand.nextInt(3, 9)),
                new ItemStack(Material.GUNPOWDER, rand.nextInt(1, 4)),
                new ItemStack(Material.SUGAR_CANE, rand.nextInt(2, 8)),
                new ItemStack(Material.LEATHER, rand.nextInt(1, 5))
        ));
        return list;
    }

    @Override
    protected List<ItemStack> initSpecialItems() {
        List<Enchantment> enchants = new ArrayList<>(List.of(
                Enchantment.SILK_TOUCH,
                Enchantment.FORTUNE,
                Enchantment.LOOTING,
                Enchantment.MENDING,
                Enchantment.UNBREAKING
        ));
        List<ItemStack> list = new ArrayList<>(List.of(
                new ItemStack(Material.VINE, rand.nextInt(3, 8)),
                new ItemStack(Material.LILY_PAD, rand.nextInt(3, 8)),
                new ItemStack(Material.CACTUS, rand.nextInt(3, 8)),
                randomlyEnchantedBook(enchants, rand),
                new ItemStack(Material.MANGROVE_LOG, rand.nextInt(6, 9)),
                new ItemStack(Material.COCOA_BEANS, rand.nextInt(3, 9)),
                new ItemStack(Material.RED_MUSHROOM, rand.nextInt(3, 9)),
                new ItemStack(Material.BROWN_MUSHROOM, rand.nextInt(3, 9)),
                new ItemStack(Material.RABBIT_FOOT, 1),
                new ItemStack(Material.ENDER_PEARL, rand.nextInt(1, 4))
        ));
        return list;
    }
}
