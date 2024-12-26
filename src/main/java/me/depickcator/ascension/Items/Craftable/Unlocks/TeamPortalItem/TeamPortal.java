package me.depickcator.ascension.Items.Craftable.Unlocks.TeamPortalItem;

import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Cooldowns.TeleportCooldown;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TeamPortal extends Craft implements ItemClick {
    private static TeamPortal instance;
    private TeamPortal() {
        super(UnlocksData.COST_350, 2, "Team Portal", "team_portal");
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
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(" Travel to your teammates", TextUtil.DARK_PURPLE),
                TextUtil.makeText("and be there to assist", TextUtil.DARK_PURPLE)
        ));
        meta.lore(lore);
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
                && plugin.getGameState().canTeleport(p)
                && !CombatTimer.getInstance().isOnCooldown(p)) {
//            TeleportCooldown.getInstance().setCooldownTimer(p);
            new TeamPortalGUI(pD);
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(result, this);
    }
}
