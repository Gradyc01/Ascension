package org.bingo4.Items.Craftable.Vanilla;


import org.bingo4.Items.Craftable.Unlocks.MakeshiftMace;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.List;


public class Mace extends Weapons implements Vanilla {
    private static Mace instance;
    private Mace() {
        super("Mace", "mace", 11, -3.4);
    }

    public static Mace getInstance() {
        if (instance == null) instance = new Mace();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A", "B");
        recipe.setIngredient('A', Material.HEAVY_CORE);
        recipe.setIngredient('B', Material.BREEZE_ROD);
        plugin.getServer().addRecipe(recipe);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.MACE);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.setMaxDamage(65);
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.YELLOW).append(TextUtil.rightClickText()));
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        List<Float> floats = MakeshiftMace.getInstance().getResult().getItemMeta().getCustomModelDataComponent().getFloats();
        component.setFloats(floats);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);
        addCooldownGroup(item);
        item.setItemMeta(Vanilla.addModifiers(item.getItemMeta(), getAttackDamage(), getAttackSpeed(), KEY));
        return item;
    }

}
