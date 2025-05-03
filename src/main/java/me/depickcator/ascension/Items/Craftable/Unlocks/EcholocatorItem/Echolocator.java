package me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Echolocator extends Craft implements ItemClick {
    private static Echolocator instance;
    private Echolocator() {
        super(UnlocksData.COST_325, 2, "Echolocator", "echolocator");
        registerItem();
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("CA ", "AB ", "  D");
        recipe.setIngredient('A', Material.EMERALD);
        recipe.setIngredient('B', Material.PLAYER_HEAD);
        recipe.setIngredient('C', Material.MAP);
        recipe.setIngredient('D', Material.GOLD_INGOT);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.CLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setEnchantmentGlintOverride(true);
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.YELLOW).append(TextUtil.rightClickText()));
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("   Fires off a signal to", TextUtil.DARK_PURPLE),
                TextUtil.makeText("any region of the world", TextUtil.DARK_PURPLE)
        ));
        meta.lore(lore);
        meta.setMaxStackSize(1);
        item.setItemMeta(meta);
        return item;
    }

    public static Echolocator getInstance() {
        if (instance == null) instance = new Echolocator();
        return instance;
    }


    @Override
    public ItemStack getItem() {
        return getResult();
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (isMainHandRightClick(e)) {
            new EcholocatorGUI(pD);
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }
}
