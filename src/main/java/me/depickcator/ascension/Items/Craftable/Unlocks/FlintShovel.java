package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

public class FlintShovel extends Craft implements LootTableChanger {
    private static FlintShovel instance;
    private FlintShovel() {
        super(1, 2, "Flint Shovel", "flint_shovel");
        registerItem();
    }


    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A", "B", "B");
        recipe.setIngredient('A', Material.FLINT);
        recipe.setIngredient('B', Material.STICK);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.AQUA));
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        meta.setEnchantmentGlintOverride(true);
        if (meta instanceof Repairable repairable) {
            repairable.setRepairCost(999);
        }
        item.setItemMeta(meta);
        LootTableChanger.addPersistentDataForItems(item, KEY);
        return item;
    }


    @Override
    public ItemStack getItem() {
        return result;
    }

    @Override
    public boolean uponEvent(Event event, Player p) {
        if (!isEventBreakBlockEvent(event)) {
            return false;
        }
        BlockBreakEvent e = (BlockBreakEvent) event;
        Block b = e.getBlock();
        if (b.getType() != Material.GRAVEL) {
            return false;
        }
        e.setDropItems(false);
        e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.FLINT));
        return true;
    }

    @Override
    public void registerItem() {
        addItem(getItem(), this);
    }

    public static FlintShovel getInstance() {
        if (instance == null) instance = new FlintShovel();
        return instance;
    }
}
