package me.depickcator.ascensionBingo.Items.Craftable.Vanilla;


import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.Craftable.Crafts;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;


public class NetheriteSword implements Crafts, Vanilla {
    private Recipe recipe;
    private final AscensionBingo plugin;
    public static final String DISPLAY_NAME = "Netherite Sword";
    public static final String KEY = "netherite_sword_smithing";
    private static final ItemStack result = NetheriteSword.makeResult();
    public NetheriteSword(AscensionBingo plugin) {
        this.plugin = plugin;
        removeVanillaRecipe();
        recipe();
    }

    @Override
    public void recipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        ItemStack item = getResult();
        RecipeChoice base = new RecipeChoice.MaterialChoice(Material.DIAMOND_SWORD);
        RecipeChoice template = new RecipeChoice.MaterialChoice(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
        RecipeChoice addition = new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT);
        SmithingTransformRecipe recipe = new SmithingTransformRecipe(key, item, template, base, addition);

        this.recipe = recipe;
        plugin.getServer().addRecipe(recipe);
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return result;
    }

    public static ItemStack item() {
        return result;
    }

    private static ItemStack makeResult() {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        double attackDamage = 16; double attackSpeed = -2.4;
        item.setItemMeta(Vanilla.addModifiers(item.getItemMeta(), attackDamage, attackSpeed, KEY));
        return item;
    }

    @Override
    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public int getCraftCost() {
        return 0;
    }

    @Override
    public void removeVanillaRecipe() {
        plugin.getServer().removeRecipe(NamespacedKey.minecraft(KEY));
    }
}
