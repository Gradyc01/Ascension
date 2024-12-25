package me.depickcator.ascension.Items.Craftable.Vanilla;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

public class NetheriteAxe extends Weapons implements Vanilla {
    private static NetheriteAxe instance;
    private NetheriteAxe() {
        super("Netherite Axe", "netherite_axe", 15, -3);
    }

    public static NetheriteAxe getInstance() {
        if (instance == null) instance = new NetheriteAxe();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        RecipeChoice base = new RecipeChoice.MaterialChoice(Material.DIAMOND_AXE);
        RecipeChoice addition = new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT);
        RecipeChoice template = new RecipeChoice.MaterialChoice(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
        SmithingTransformRecipe recipe = new SmithingTransformRecipe(key, result, template, base, addition);
        plugin.getServer().addRecipe(recipe);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.NETHERITE_AXE);
        item.setItemMeta(Vanilla.addModifiers(item.getItemMeta(), getAttackDamage(), getAttackSpeed(), KEY));
        return item;
    }

}