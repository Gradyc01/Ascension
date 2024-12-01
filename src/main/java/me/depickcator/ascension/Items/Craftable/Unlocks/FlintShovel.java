package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Crafts;
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

public class FlintShovel implements Crafts, LootTableChanger {
    private final Ascension plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 2;
    public static final String DISPLAY_NAME = "Flint Shovel";
    public static final String KEY = "flint_shovel";
    private static final int modelNumber = Ascension.generateModelNumber();
    public FlintShovel(Ascension plugin) {
        this.plugin = plugin;
        recipe();
        registerItem();
    }

    @Override
    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = FlintShovel.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("A", "B", "B");
        recipe.setIngredient('A', Material.FLINT);
        recipe.setIngredient('B', Material.STICK);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    public static ItemStack result() {
        ItemStack item = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.AQUA));
        meta.setCustomModelData(modelNumber);
        meta.setEnchantmentGlintOverride(true);
        if (meta instanceof Repairable repairable) {
            repairable.setRepairCost(999);
        }
        item.setItemMeta(meta);
        LootTableChanger.addPersistentDataForItems(item, KEY);
        return item;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return FlintShovel.result();
    }

    @Override
    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public int getCraftCost() {
        return COST;
    }


    @Override
    public ItemStack getItem() {
        return FlintShovel.result();
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
}
