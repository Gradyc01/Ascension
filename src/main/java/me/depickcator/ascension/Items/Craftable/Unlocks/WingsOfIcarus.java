package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Utility.AttributeUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.Repairable;

public class WingsOfIcarus extends Craft {
    private static WingsOfIcarus instance;
    private WingsOfIcarus() {
        super(UnlocksData.COST_500, 1, "Wings of Icarus", "wings_of_icarus");
    }

    public static WingsOfIcarus getInstance() {
        if (instance == null) instance = new WingsOfIcarus();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("CBC", "A A", "A A");
        recipe.setIngredient('A', Material.FEATHER);
        recipe.setIngredient('B', Material.ENDER_PEARL);
        recipe.setIngredient('C', Material.PLAYER_HEAD);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.ELYTRA);
        Damageable itemMeta = (Damageable) item.getItemMeta();
        itemMeta.setMaxDamage(125);
        itemMeta.addEnchant(Enchantment.MENDING, 1, true);
        itemMeta.addAttributeModifier(Attribute.ARMOR, AttributeUtil.makeModifier(new NamespacedKey(plugin, KEY),
                -0.5,
                AttributeModifier.Operation.MULTIPLY_SCALAR_1,
                EquipmentSlotGroup.ARMOR));
        itemMeta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        itemMeta.setCustomModelData(plugin.generateModelNumber());
        itemMeta.displayName(TextUtil.makeText(this.getDisplayName(), TextUtil.PINK));
        item.setItemMeta(itemMeta);
        Repairable itemMetaRepairable = (Repairable) item.getItemMeta();
        itemMetaRepairable.setRepairCost(999);
        item.setItemMeta(itemMetaRepairable);
        return item;
    }
}
