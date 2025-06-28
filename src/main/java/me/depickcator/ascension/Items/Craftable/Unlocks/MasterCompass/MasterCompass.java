package me.depickcator.ascension.Items.Craftable.Unlocks.MasterCompass;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Craftable.Unlocks.MasterCompass.GUIs.MasterCompassMain;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.ArrayList;
import java.util.List;

public class MasterCompass extends Craft implements ItemClick {

    private static MasterCompass instance;
    private MasterCompass() {
        super(UnlocksData.COST_425, 1, "Master's Compass", "master_compass");
        registerItem();
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("BAB", "BCB", "BBB");
        recipe.setIngredient('A', Material.REDSTONE_TORCH);
        recipe.setIngredient('B', Material.REDSTONE);
        recipe.setIngredient('C', Material.COMPASS);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.COMPASS);
        CompassMeta meta = (CompassMeta) item.getItemMeta();
        meta.setEnchantmentGlintOverride(true);
        meta.setCustomModelData(plugin.generateModelNumber());
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.RED)
                .append(TextUtil.clickText()));
        meta.setLodestoneTracked(false);
//        meta.setLodestone(Ascension.getSpawn());
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Points in the direction of your chosen target", TextUtil.DARK_PURPLE),
                TextUtil.makeText("Costs Redstone to power", TextUtil.DARK_PURPLE)
        ));
        meta.lore(lore);


        item.setItemMeta(meta);
//        meta.getPersistentDataContainer().set(tracked_player, PersistentDataType.STRING, "None");
        return item;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        ItemStack item = e.getItem();
        if (e.getAction().isRightClick()) {
            new CompassAbilities(item).track(pD.getPlayer());
        } else if (e.getAction().isLeftClick()) {
            new MasterCompassMain(pD, item);
        }
        return true;
    }

    public static MasterCompass getInstance() {
        if (instance == null) instance = new MasterCompass();
        return instance;
    }

    @Override
    public ItemStack getItem() {
        return getResult();
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }
}
