package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Cornucopia extends Craft implements ItemClick {
    private static Cornucopia instance;
    private Cornucopia() {
        super(UnlocksData.COST_225, 1, "Cornucopia", "cornucopia");
        registerItem();
    }
    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("BBB", "BAB", "BBB");
        recipe.setIngredient('A', Material.GOLDEN_APPLE);
        recipe.setIngredient('B', Material.CARROT);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.GOLDEN_CARROT, 3);
        ItemMeta meta = item.getItemMeta();
        FoodComponent foodComponent = meta.getFood();
        foodComponent.setCanAlwaysEat(true);
        foodComponent.setNutrition(0);
        meta.setFood(foodComponent);
        List<Component> lore = new ArrayList<>(
                List.of(TextUtil.makeText("  Regeneration I (00:15)", TextUtil.DARK_PURPLE))
        );
        meta.lore(lore);
        meta.setEnchantmentGlintOverride(true);
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.YELLOW));
        item.setItemMeta(meta);
        generateUniqueModelNumber(item);
        addCooldownGroup(item, 2);
        return item;
    }


    @Override
    public ItemStack getItem() {
        return result;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        if (e.getAction().isRightClick() && !p.hasCooldown(item)) {
            item.setAmount(item.getAmount() - 1);
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 15 * 20, 0));
            p.setCooldown(item, 2 * 20);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f, 1f);
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(result, this);
    }

    public static Cornucopia getInstance() {
        if (instance == null) instance = new Cornucopia();
        return instance;
    }
}
