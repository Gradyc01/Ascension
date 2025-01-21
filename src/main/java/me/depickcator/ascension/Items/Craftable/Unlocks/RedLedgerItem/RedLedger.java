package me.depickcator.ascension.Items.Craftable.Unlocks.RedLedgerItem;

import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
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

import java.util.ArrayList;
import java.util.List;

public class RedLedger extends Craft implements ItemClick {
    private static RedLedger instance;
    private RedLedger() {
        super(UnlocksData.COST_400, 4, "Red Ledger", "red_ledger");
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

        recipe.shape(" A ", "BCD", " A ");
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
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(" Hunt down those that ", TextUtil.DARK_PURPLE),
                TextUtil.makeText("are in your way", TextUtil.DARK_PURPLE)
        ));
        meta.lore(lore);
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
                && plugin.getGameState().canTeleport(p)
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
