package me.depickcator.ascension.Items.Craftable.Unlocks;

import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.UseCooldown;
import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Effects.NatureWrath;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.listeners.Combat.ShootsProjectiles;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.components.UseCooldownComponent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Poseidon extends Craft implements ShootsProjectiles {
    private static Poseidon instance;
    private final NamespacedKey key;
    private Poseidon() {
        super(850, 1, "Poseidon", "poseidon");
        key = new NamespacedKey(plugin, KEY);
        addProjectile(getKey(), this);
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" CB", " AC", "A  ");
        recipe.setIngredient('A', Material.STICK);
        recipe.setIngredient('B', Material.HEART_OF_THE_SEA);
        recipe.setIngredient('C', Material.PLAYER_HEAD);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.TRIDENT);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.setMaxDamage(32);
        meta.setEnchantmentGlintOverride(true);
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.YELLOW));
        meta.addEnchant(Enchantment.LOYALTY, 1, true);
        meta.getPersistentDataContainer().set(ShootsProjectiles.key, PersistentDataType.STRING, getKey());
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Nature's Wrath I", TextUtil.GRAY),
                TextUtil.makeText(""),
                TextUtil.makeText("Smites down the enemy", TextUtil.DARK_PURPLE)
//                TextUtil.makeText("[32 Uses]", TextUtil.GOLD)
        ));
        meta.lore(lore);
        item.setItemMeta(meta);
        Repairable meta2 = (Repairable) item.getItemMeta();
        meta2.setRepairCost(999);
        item.setItemMeta(meta2);
        addCooldownGroup(item, 10f);
        generateUniqueModelNumber(item);
        return item;
    }

    public static Poseidon getInstance() {
        if (instance == null) instance = new Poseidon();
        return instance;
    }

    @Override
    public void applyKey(EntityShootBowEvent event) {

    }

    @Override
    public double setProjectileComponent(EntityDamageByEntityEvent event, LivingEntity victim) {
        if (victim instanceof Player) {
            Player v = (Player) victim;
            Player a = (Player) ((Trident) event.getDamager()).getShooter();
            PlayerData aD = PlayerUtil.getPlayerData(a);
            if (aD.getPlayerTeam().getTeam().getTeamMembers().contains(v)) {
                event.setCancelled(true);
            }
            double distance = v.getLocation().distance(a.getLocation());
            new NatureWrath(PlayerUtil.getPlayerData(v), PlayerUtil.getPlayerData(a),
                    Integer.min(8, Integer.max(2, (int) distance/7)));
        }
        return -1;
    }
}
