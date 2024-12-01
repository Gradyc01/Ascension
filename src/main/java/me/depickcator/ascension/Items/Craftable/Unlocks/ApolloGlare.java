package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.ShootsProjectiles;
import me.depickcator.ascension.Items.Craftable.Crafts;
import me.depickcator.ascension.Items.UnlockUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ApolloGlare implements Crafts, ShootsProjectiles {
    private final Ascension plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 1;
    public static final String DISPLAY_NAME = "Apollo's Glare";
    public static final String KEY = "apollo_glare";
    private final ItemStack result;
    private static final int modelNumber = Ascension.generateModelNumber();
    public ApolloGlare(Ascension plugin) {
        this.plugin = plugin;
        result = makeItem();
        recipe();
        addProjectile(KEY, this);
    }

    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = result;

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("A", "B", "C");
        recipe.setIngredient('A', Material.INK_SAC);
        recipe.setIngredient('B', Material.CROSSBOW);
        recipe.setIngredient('C', Material.NETHER_STAR);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return result;
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

    private ItemStack makeItem() {
        ItemStack item = new ItemStack(Material.CROSSBOW);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.setMaxDamage(24);
        meta.setCustomModelData(modelNumber);
        meta.setEnchantmentGlintOverride(true);
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.YELLOW));
        meta.getPersistentDataContainer().set(ShootsProjectiles.key, PersistentDataType.STRING, KEY);
        ArrayList<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(""),
                TextUtil.makeText(""),
                TextUtil.makeText("Nearsights players that are hit", TextUtil.DARK_PURPLE),
                TextUtil.makeText("[24 Uses]", TextUtil.GOLD)
        ));
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }


    @Override
    public void applyKey(EntityShootBowEvent event) {
        if (!(event.getProjectile() instanceof Arrow)) return;
//        ItemStack bow = event.getBow();
//        assert bow != null;
        Arrow arrow = (Arrow) event.getProjectile();
        arrow.setMetadata(ShootsProjectiles.METADATA, new FixedMetadataValue(plugin, KEY));
        arrow.setDamage(0.5);
//        PersistentDataContainer container = arrow.getPersistentDataContainer();
//        NamespacedKey key = new NamespacedKey(plugin, "arrow");
//        container.set(key, PersistentDataType.STRING, KEY);
    }

    @Override
    public void setProjectileComponent(EntityDamageByEntityEvent event) {
        Player victim = (Player) event.getEntity();
        victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 4 * 20, 0, true, true));
        victim.sendMessage(TextUtil.makeText("BLINDED", TextUtil.DARK_GRAY, true, false));
        victim.playSound(victim.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1.0f, 2.0f);
    }
}
