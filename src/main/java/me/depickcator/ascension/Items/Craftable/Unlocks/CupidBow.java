package me.depickcator.ascension.Items.Craftable.Unlocks;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.UseCooldown;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.listeners.Combat.ShootsProjectiles;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.components.UseCooldownComponent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;

public class CupidBow extends Craft implements ShootsProjectiles {
    private static CupidBow instance;
    private CupidBow() {
        super(850, 1, "Cupid's Bow", "cupid_bow");
        addProjectile(KEY, this);
    }
    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" C ", "ABA", " D ");
        recipe.setIngredient('A', Material.PLAYER_HEAD);
        recipe.setIngredient('B', Material.BOW);
        recipe.setIngredient('C', Material.BLAZE_ROD);
        recipe.setIngredient('D', Material.LAVA_BUCKET);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.CROSSBOW);
        Repairable meta = (Repairable) item.getItemMeta();
        meta.setRepairCost(999);
        meta.addEnchant(Enchantment.POWER, 2, true);
        meta.addEnchant(Enchantment.FLAME, 1, true);
        meta.setCustomModelData(plugin.generateModelNumber());
        meta.setMaxStackSize(1);
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.DARK_RED));
//        meta.getUseCooldown().setCooldownGroup(key);
        meta.getPersistentDataContainer().set(ShootsProjectiles.key, PersistentDataType.STRING, KEY);
        item.setItemMeta(meta);
        Damageable meta1 = (Damageable) item.getItemMeta();
        meta1.setMaxDamage(32);
        item.setItemMeta(meta1);
//        UseCooldownComponent component = UseCooldown.useCooldown(1).build().;
//        /
        item.setData(DataComponentTypes.USE_COOLDOWN,
                UseCooldown.useCooldown(0.01f)
                .cooldownGroup(Key.key(plugin.getName().toLowerCase()+ ":" + getKey())));
        return item;
    }

    public static CupidBow getInstance() {
        if (instance == null) instance = new CupidBow();
        return instance;
    }

    @Override
    public void applyKey(EntityShootBowEvent event) {
        /*Empty on Purpose*/
        Player player = (Player) event.getEntity();
        ItemStack bow = event.getBow();
        player.setCooldown(bow, 8 * 20);
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
        //Empty on purpose
        return -1;
    }
}
