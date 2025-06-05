package me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.Boxes;

import me.depickcator.ascension.Interfaces.CustomChestLootPool;
import me.depickcator.ascension.Interfaces.LootPoolItem;
import me.depickcator.ascension.Items.Craftable.Unlocks.GoldenHead;
import me.depickcator.ascension.Items.Craftable.Unlocks.Nectar;
import me.depickcator.ascension.Items.Craftable.Unlocks.Panacea;
import me.depickcator.ascension.Items.Craftable.Unlocks.Resurrection;
import me.depickcator.ascension.Items.Uncraftable.Skulls.ZombieHead;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Rejuvenation extends PandoraBoxes {
    public Rejuvenation() {
        super("Rejuvenation", Material.APPLE);
    }

    @Override
    protected List<ItemStack> initConstantItems() {
        List<ItemStack> list = new ArrayList<>(List.of(
                new ItemStack(Material.GOLDEN_APPLE, rand.nextInt(1, 4)),
                new ItemStack(Material.ARROW, rand.nextInt(16, 33)),
                new ItemStack(Material.ANVIL, 1),
                new ItemStack(Material.BOOK, rand.nextInt(1, 4)),
                new ItemStack(Material.ENCHANTING_TABLE, 1)
        ));
        return list;
    }

    @Override
    protected CustomChestLootPool initSpecialItems() {
        List<Enchantment> enchants = new ArrayList<>(List.of(
                Enchantment.SHARPNESS,
                Enchantment.SHARPNESS,
                Enchantment.SHARPNESS,
                Enchantment.INFINITY,
                Enchantment.INFINITY,
                Enchantment.INFINITY,
                Enchantment.POWER,
                Enchantment.POWER,
                Enchantment.POWER,
                Enchantment.UNBREAKING,
                Enchantment.UNBREAKING,
                Enchantment.UNBREAKING,
                Enchantment.SMITE,
                Enchantment.DENSITY,
                Enchantment.FLAME,
                Enchantment.FIRE_ASPECT,
                Enchantment.BREACH
        ));
        return new CustomChestLootPool(
                new LootPoolItem(Material.ENCHANTED_GOLDEN_APPLE),
                new LootPoolItem(new ItemStack(Material.GOLDEN_APPLE, rand.nextInt(1, 4)), 15),
                new LootPoolItem(new ItemStack(Material.ARROW, 24), 12),
                new LootPoolItem(new ItemStack(Material.BOOK, rand.nextInt(1, 4)), 10),
                new LootPoolItem(Nectar.getInstance().getResult()),
                new LootPoolItem(Panacea.getInstance().getResult()),
                new LootPoolItem(Resurrection.getInstance().getResult()),
                new LootPoolItem(ZombieHead.getInstance().getResult()),
                new LootPoolItem(GoldenHead.getInstance().getResult()),
                new LootPoolItem(randomlyEnchantedBook(enchants, rand), 13)
        );
    }
}
