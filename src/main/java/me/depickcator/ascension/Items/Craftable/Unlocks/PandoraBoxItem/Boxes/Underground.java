package me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.Boxes;

import me.depickcator.ascension.Interfaces.CustomChestLootPool;
import me.depickcator.ascension.Interfaces.LootPoolItem;
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
    protected CustomChestLootPool initSpecialItems() {
        List<Enchantment> enchants = new ArrayList<>(List.of(
                Enchantment.FORTUNE,
                Enchantment.EFFICIENCY,
                Enchantment.UNBREAKING
        ));

        return new CustomChestLootPool(
                new LootPoolItem(new ItemStack(Material.DIAMOND, rand.nextInt(3, 6)), 5),
                new LootPoolItem(new ItemStack(Material.EMERALD, rand.nextInt(2, 5)), 5),
                new LootPoolItem(Material.SPAWNER, 3),
                new LootPoolItem(randomlyEnchantedBook(enchants, rand), 3),
                new LootPoolItem(new ItemStack(Material.SPIDER_EYE, rand.nextInt(3, 6)), 3),
                new LootPoolItem(new ItemStack(Material.OBSIDIAN, rand.nextInt(3, 6)), 3)
        );
    }
}
