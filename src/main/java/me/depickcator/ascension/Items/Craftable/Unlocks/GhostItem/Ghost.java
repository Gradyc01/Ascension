package me.depickcator.ascension.Items.Craftable.Unlocks.GhostItem;

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
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Ghost extends Craft implements ItemClick {
    private static Ghost instance;
    private Ghost() {
        super(600, 1, "Ghost", "ghost");
        registerItem();
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);


        recipe.shape("ADA", "ABA", "ACA");
        recipe.setIngredient('A', Material.GOLD_INGOT);
        recipe.setIngredient('B', Material.PLAYER_HEAD);
        recipe.setIngredient('C', Material.EMERALD);
        recipe.setIngredient('D', Material.FERMENTED_SPIDER_EYE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.GOLDEN_HOE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(plugin.generateModelNumber());
        meta.setEnchantmentGlintOverride(true);
        meta.setUnbreakable(true);
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(" Hunt through the void", TextUtil.DARK_PURPLE)
        ));
        meta.lore(lore);
        meta.setMaxStackSize(1);
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.AQUA).append(TextUtil.rightClickText()));
        item.setItemMeta(meta);
        return item;
    }

    public static Ghost getInstance() {
        if (instance == null) instance = new Ghost();
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
                && plugin.getGameState().inGame()
                && !CombatTimer.getInstance().isOnCooldown(p)) {
            new GhostEffect(pD);
            p.getInventory().getItemInMainHand().setAmount(0);
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(result, this);
    }
}
