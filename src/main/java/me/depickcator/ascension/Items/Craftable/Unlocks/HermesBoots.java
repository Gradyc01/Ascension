package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.NetheriteInfusionItem.CustomInfusion;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;


public class HermesBoots extends Craft implements CustomInfusion {
    private static HermesBoots instance;
    private HermesBoots() {
        super(700, 1, "Hermes' Boots" ,"hermes_boots");
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
        AttributeModifier speed = new AttributeModifier(new NamespacedKey(KEY, "speed"), 0.25, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlotGroup.FEET);
        AttributeModifier armor = new AttributeModifier(new NamespacedKey(KEY, "armor"), 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.FEET);
        AttributeModifier toughness = new AttributeModifier(new NamespacedKey(KEY, "toughness"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.FEET);
        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speed);
        meta.addAttributeModifier(Attribute.ARMOR, armor);
        meta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS, toughness);
        meta.setRepairCost(999);
        meta.addEnchant(Enchantment.PROTECTION, 2, true);
        meta.addEnchant(Enchantment.FEATHER_FALLING, 1, true);
        meta.addEnchant(Enchantment.UNBREAKING, 2, true);
        item.setItemMeta(meta);
        ArmorMeta armorMeta = (ArmorMeta) item.getItemMeta();
        ArmorTrim armorTrim = new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.SNOUT);
        armorMeta.setTrim(armorTrim);
        item.setItemMeta(armorMeta);
        return item;
    }

    public static HermesBoots getInstance() {
        if (instance == null) instance = new HermesBoots();
        return instance;
    }

    @Override
    public void customInfusion(ItemStack item, Player p) {
        ItemMeta meta = item.getItemMeta();
        meta.removeAttributeModifier(Attribute.ARMOR_TOUGHNESS);
        AttributeModifier toughness = new AttributeModifier(
                new NamespacedKey(KEY, "toughness"),
                3,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.FEET);
        meta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS, toughness);
        item.setItemMeta(meta);
    }
}
