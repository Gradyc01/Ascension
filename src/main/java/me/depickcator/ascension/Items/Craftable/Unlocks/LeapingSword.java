package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Craftable.Unlocks.NetheriteInfusionItem.CustomInfusion;
import me.depickcator.ascension.Items.Craftable.Vanilla.Vanilla;
import me.depickcator.ascension.Items.Craftable.Vanilla.Weapons;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class LeapingSword extends Weapons implements ItemClick {
    private static LeapingSword instance;
    private LeapingSword() {
        super(UnlocksData.COST_325, 1, "Leaping Sword", "leaping_sword", 14, -2.4);
        registerItem();
    }

    public static LeapingSword getInstance() {
        if (instance == null) instance = new LeapingSword();
        return instance;
    }

    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A", "B", "C");
        recipe.setIngredient('A', Material.FERMENTED_SPIDER_EYE);
        recipe.setIngredient('B', Material.PLAYER_HEAD);
        recipe.setIngredient('C', Material.STICK);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.GOLDEN_SWORD);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.YELLOW).append(TextUtil.rightClickText()));
        meta.setMaxDamage(1561);
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        AttributeModifier modifier = new AttributeModifier(key, -0.5, AttributeModifier.Operation.ADD_SCALAR);
        meta.addAttributeModifier(Attribute.FALL_DAMAGE_MULTIPLIER, modifier);
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Ya yeet", TextUtil.DARK_PURPLE)
        ));
        meta.lore(lore);
        item.setItemMeta(Vanilla.addModifiers(meta, getAttackDamage(), getAttackSpeed(), getKey()));
        addCooldownGroup(item);
        generateUniqueModelNumber(item);
        return item;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (!e.getAction().isRightClick()) return false;
        ItemStack item = e.getItem();
        Damageable meta = (Damageable) item.getItemMeta();
        Player p = pD.getPlayer();
        if (checkCooldown(p, item)) {
            p.setVelocity(p.getEyeLocation().getDirection().multiply(2.0));
        }
        return false;
    }

    /*Returns true if not on cooldown and sets the cooldown, False otherwise*/
    private boolean checkCooldown(Player p, ItemStack item) {
        if (!p.hasCooldown(item) && plugin.getGameState().inGame()) {
            p.setCooldown(item, 2 * 20);
            return true;
        }
        return false;
    }


    @Override
    public ItemStack getItem() {
        return result;
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }

}
