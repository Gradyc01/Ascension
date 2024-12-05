package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Repairable;


public class HermesBoots extends Craft {
    private static HermesBoots instance;
    private HermesBoots() {
        super(1, 2, "Hermes' Boots" ,"hermes_boots");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("BAB", "EDE", "C C");
        recipe.setIngredient('A', Material.PLAYER_HEAD);
        recipe.setIngredient('B', Material.DIAMOND);
        recipe.setIngredient('C', Material.FEATHER);
        recipe.setIngredient('D', Material.DIAMOND_BOOTS);
        recipe.setIngredient('E', Material.BLAZE_POWDER);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
        Repairable meta = (Repairable) item.getItemMeta();
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.YELLOW));
        AttributeModifier modifier = new AttributeModifier(
                new NamespacedKey(Ascension.getInstance(), KEY),
                0.25,
                AttributeModifier.Operation.MULTIPLY_SCALAR_1,
                EquipmentSlotGroup.FEET);
        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, modifier);
        meta.setRepairCost(999);
        meta.addEnchant(Enchantment.PROTECTION, 2, true);
        meta.addEnchant(Enchantment.FEATHER_FALLING, 1, true);
        meta.addEnchant(Enchantment.UNBREAKING, 2, true);
        item.setItemMeta(meta);
        return item;
    }

    public static HermesBoots getInstance() {
        if (instance == null) instance = new HermesBoots();
        return instance;
    }
}
