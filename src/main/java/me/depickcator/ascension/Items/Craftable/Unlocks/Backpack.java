package me.depickcator.ascension.Items.Craftable.Unlocks;

// import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
// import me.depickcator.ascension.Player.Data.PlayerData;
// import me.depickcator.ascension.Utility.TextUtil;
// import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
// import org.bukkit.Sound;
// import org.bukkit.block.Block;
// import org.bukkit.block.BlockState;
// import org.bukkit.block.ShulkerBox;
// import org.bukkit.entity.Player;
// import org.bukkit.event.inventory.CraftItemEvent;
// import org.bukkit.event.player.PlayerInteractEvent;
// import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
// import org.bukkit.inventory.meta.BlockStateMeta;
// import org.bukkit.inventory.meta.ItemMeta;

// import java.util.ArrayList;
// import java.util.List;

public class Backpack extends Craft {
    private static Backpack instance;
    private Backpack() {
        super(UnlocksData.COST_125, 2, "Backpack", "backpack");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("ABA", "ACA", "ABA");
        recipe.setIngredient('A', Material.STICK);
        recipe.setIngredient('B', Material.LEATHER);
        recipe.setIngredient('C', Material.CHEST);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.SHULKER_BOX, 1);
    }

    public static Backpack getInstance() {
        if (instance == null) instance = new Backpack();
        return instance;
    }

//    @Override
//    public ItemStack getItem() {
//        return getResult();
//    }

//    @Override
//    public boolean uponCrafted(CraftItemEvent e, Player p) {
////        ItemStack item = e.getCurrentItem();
////        ItemMeta meta = item.getItemMeta();
////        List<Component> lore = new ArrayList<>(List.of(
////                TextUtil.makeText("Crafted by ", TextUtil.YELLOW)
////                        .append(TextUtil.makeText(p.getName(), TextUtil.AQUA, false, true))
////        ));
////
////        meta.lore(lore);
////        item.setItemMeta(meta);
//        return true;
//    }

//    @Override
//    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
//        ItemStack item = e.getItem();
//        BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
//        ShulkerBox blockState = (ShulkerBox) meta.getBlockState();
//        Inventory inv = blockState.getInventory();
//        Player p = pD.getPlayer();
//        p.openInventory(inv);
//        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 1.0f, 1.0f);
//        e.setCancelled(true);
//        return true;
//    }

//    @Override
//    public void registerItem() {
//        addItem(getResult(), this);
//    }
}
