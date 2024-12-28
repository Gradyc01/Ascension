package me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.Boxes;

import me.depickcator.ascension.Items.Craftable.Unlocks.Nectar;
import me.depickcator.ascension.Items.Craftable.Unlocks.Panacea;
import me.depickcator.ascension.Items.Craftable.Unlocks.Resurrection;
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
    protected List<ItemStack> initSpecialItems() {
        List<Enchantment> enchants = new ArrayList<>(List.of(
                Enchantment.SHARPNESS,
                Enchantment.INFINITY,
                Enchantment.POWER,
                Enchantment.FLAME,
                Enchantment.FIRE_ASPECT,
                Enchantment.UNBREAKING
        ));
        List<ItemStack> list = new ArrayList<>(List.of(
                new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1),
                new ItemStack(Material.GOLDEN_APPLE, rand.nextInt(1, 4)),
                new ItemStack(Material.ARROW, 24),
                new ItemStack(Material.CROSSBOW, 1),
                new ItemStack(Material.BOW, 1),
                randomlyEnchantedBook(enchants, rand),
                new ItemStack(Material.BOOK, rand.nextInt(1, 4)),
                Nectar.getInstance().getResult(),
                Panacea.getInstance().getResult(),
                Resurrection.getInstance().getResult()
        ));
        return list;
    }
}
