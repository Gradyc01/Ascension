package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.listeners.Combat.ShootsProjectiles;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ApolloGlare extends Craft implements ShootsProjectiles  {
    private static ApolloGlare instance;
    private ApolloGlare() {
        super(750, 1, "Apollo's Glare", "apollo_glare");

        addProjectile(KEY, this);
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A", "B", "C");
        recipe.setIngredient('A', Material.INK_SAC);
        recipe.setIngredient('B', Material.CROSSBOW);
        recipe.setIngredient('C', Material.PLAYER_HEAD);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.CROSSBOW);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.setMaxDamage(24);
        meta.setEnchantmentGlintOverride(true);
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.YELLOW));
        meta.getPersistentDataContainer().set(ShootsProjectiles.key, PersistentDataType.STRING, KEY);
        ArrayList<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Nearsights players that are hit", TextUtil.DARK_PURPLE),
                TextUtil.makeText("[24 Uses]", TextUtil.GOLD)
        ));
        meta.lore(lore);
        item.setItemMeta(meta);
        Repairable meta2 = (Repairable) item.getItemMeta();
        meta2.setRepairCost(999);
        item.setItemMeta(meta2);
        generateUniqueModelNumber(item);
        addCooldownGroup(item);
        return item;
    }

    public static ApolloGlare getInstance() {
        if (instance == null) instance = new ApolloGlare();
        return instance;
    }


    @Override
    public void applyKey(EntityShootBowEvent event) {
        Player player = (Player) event.getEntity();
        ItemStack bow = event.getBow();
        player.setCooldown(bow, 7 * 20);
        PlayerInventory inv = player.getInventory();
        if (inv.contains(Material.ARROW)) {
            ItemStack item = inv.getItem(inv.first(Material.ARROW));
            item.setAmount(item.getAmount() - 1);
            CrossbowMeta bowMeta = (CrossbowMeta) bow.getItemMeta();
            bowMeta.addChargedProjectile(item);
            bow.setItemMeta(bowMeta);
        }
    }

    @Override
    public double setProjectileComponent(EntityDamageByEntityEvent event, LivingEntity victim) {
        victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 4 * 20, 0, true, true));

        if (victim instanceof Player) {
            Player p = (Player) victim;
            p.sendMessage(TextUtil.makeText("BLINDED", TextUtil.DARK_GRAY, true, false));
            p.playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1.0f, 2.0f);
        }
        return 0.1;
    }
}
