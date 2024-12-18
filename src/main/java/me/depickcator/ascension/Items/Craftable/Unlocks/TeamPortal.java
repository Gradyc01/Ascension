package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.General.ItemClick;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
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
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamPortal extends Craft implements ItemClick {
    private static TeamPortal instance;
    private TeamPortal() {
        super(1, 999, "Team Portal", "team_portal");
        registerItem();
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);


        recipe.shape(" D ", " B ", "ACA");
        recipe.setIngredient('A', Material.GOLDEN_APPLE);
        recipe.setIngredient('B', Material.NETHER_STAR);
        recipe.setIngredient('C', Material.SHIELD);
        recipe.setIngredient('D', Material.EMERALD);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.PHANTOM_MEMBRANE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(plugin.generateModelNumber());
        meta.setEnchantmentGlintOverride(true);
        meta.addEnchant(Enchantment.PROTECTION, 10, true);
        meta.setMaxStackSize(1);
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.AQUA).append(TextUtil.rightClickText()));
        item.setItemMeta(meta);
        return item;
    }

    public static TeamPortal getInstance() {
        if (instance == null) instance = new TeamPortal();
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
                && !TeleportCooldown.getInstance().isOnCooldown(p)
                && plugin.getGameState().canTeleport()
                && !CombatTimer.getInstance().isOnCooldown(p)) {
//            TeleportCooldown.getInstance().setCooldownTimer(p);
            new TeamPortalGUI(PlayerUtil.getPlayerData(p));
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(result, this);
    }
}
