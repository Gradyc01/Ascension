package me.depickcator.ascension.Items.Craftable.Vanilla;


import me.depickcator.ascension.Items.Craftable.Craft;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;


public class NetheriteSword extends Craft implements Vanilla {
    private static NetheriteSword instance;
    private NetheriteSword() {
        super("Iron Sword", "iron_sword");
    }

    public static NetheriteSword getInstance() {
        if (instance == null) instance = new NetheriteSword();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        RecipeChoice base = new RecipeChoice.MaterialChoice(Material.DIAMOND_SWORD);
        RecipeChoice template = new RecipeChoice.MaterialChoice(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
        RecipeChoice addition = new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT);
        SmithingTransformRecipe recipe = new SmithingTransformRecipe(key, result, template, base, addition);
        plugin.getServer().addRecipe(recipe);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        double attackDamage = 16; double attackSpeed = -2.4;
        item.setItemMeta(Vanilla.addModifiers(item.getItemMeta(), attackDamage, attackSpeed, KEY));
        return item;
    }

}
