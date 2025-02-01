package me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem;

import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PandoraBox extends Craft implements ItemClick {
    private static PandoraBox instance;
    private PandoraBox() {
        super(UnlocksData.COST_350, 4, "Pandora's Box", "pandora_box");
        registerItem();
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);

        recipe.shape("BDB", "CAC", "BCB");
        recipe.setIngredient('A', Material.NETHER_STAR);
        recipe.setIngredient('B', ShardOfTheFallen.getInstance().getResult());
        recipe.setIngredient('C', Material.CHEST);
        recipe.setIngredient('D', Material.PLAYER_HEAD);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.TRAPPED_CHEST, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setMaxStackSize(1);
        meta.setCustomModelData(plugin.generateModelNumber());
        meta.setEnchantmentGlintOverride(true);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(" Inside Pandora's Box awaits a treasure for you.", TextUtil.DARK_PURPLE)
        ));
        meta.lore(lore);
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.RED).append(TextUtil.rightClickText()));
        item.setItemMeta(meta);
        return item;
    }

    public static PandoraBox getInstance() {
        if (instance == null) instance = new PandoraBox();
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
                && !CombatTimer.getInstance().isOnCooldown(p)) {
            new PandoraBoxGUI(pD);
            p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 10f, 0f);
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(result, this);
    }
}
