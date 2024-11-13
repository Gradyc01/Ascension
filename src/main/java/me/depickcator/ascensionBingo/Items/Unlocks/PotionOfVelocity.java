package me.depickcator.ascensionBingo.Items.Unlocks;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Items.UnlockUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionOfVelocity implements Crafts{
    private final AscensionBingo plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 3;
    public static final String DISPLAY_NAME = "Potion of Velocity";
    public static final String KEY = "potion_of_velocity";
    public PotionOfVelocity(AscensionBingo plugin) {
        this.plugin = plugin;
        recipe();
    }

    @Override
    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = PotionOfVelocity.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("A", "B", "C");
        recipe.setIngredient('A', Material.COCOA_BEANS);
        recipe.setIngredient('B', Material.SUGAR);
        recipe.setIngredient('C', Material.GLASS_BOTTLE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    public static ItemStack result() {
        ItemStack item = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
        potionMeta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.YELLOW));
        potionMeta.setColor(Color.fromRGB(0x00, 0x00, 0x88));
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 20, 2), true);
        potionMeta.setCustomModelData(24);
        item.setItemMeta(potionMeta);
        return item;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return PotionOfVelocity.result();
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
        return COST;
    }


}
