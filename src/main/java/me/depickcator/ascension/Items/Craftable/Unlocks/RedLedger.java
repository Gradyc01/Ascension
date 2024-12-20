package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.General.ItemClick;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Craftable.Unlocks.GUIs.RedLedgerGUI;
import me.depickcator.ascension.Items.Craftable.Unlocks.GUIs.TeamPortalGUI;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Cooldowns.TeleportCooldown;
import me.depickcator.ascension.Player.PlayerData;
import me.depickcator.ascension.Player.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class RedLedger extends Craft implements ItemClick {
    private static RedLedger instance;
    private RedLedger() {
        super(1, 999, "Red Ledger", "red_ledger");
        registerItem();
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);

        RecipeChoice.MaterialChoice skulls = new RecipeChoice.MaterialChoice(
                Material.ZOMBIE_HEAD,
                Material.SKELETON_SKULL,
                Material.CREEPER_HEAD,
                Material.PLAYER_HEAD);

        recipe.shape("AAA", "BCD", "AAA");
        recipe.setIngredient('A', Material.DIAMOND);
        recipe.setIngredient('B', Material.IRON_SWORD);
        recipe.setIngredient('C', Material.BOOK);
        recipe.setIngredient('D', skulls);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(plugin.generateModelNumber());
        meta.setEnchantmentGlintOverride(true);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setMaxStackSize(1);
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.RED).append(TextUtil.rightClickText()));
        item.setItemMeta(meta);
        return item;
    }

    public static RedLedger getInstance() {
        if (instance == null) instance = new RedLedger();
        return instance;
    }


    @Override
    public ItemStack getItem() {
        return getResult();
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        Player p = pD.getPlayer();
        e.setCancelled(true);
        if (isMainHandRightClick(e)
                && plugin.getGameState().canTeleport()
                && !CombatTimer.getInstance().isOnCooldown(p)) {
            new RedLedgerGUI(pD);
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(result, this);
    }
}
