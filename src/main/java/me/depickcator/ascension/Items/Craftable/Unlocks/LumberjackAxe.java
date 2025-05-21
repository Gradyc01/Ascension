package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Craftable.Vanilla.IronAxe;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.ForageBlocks;
import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Skills.SkillExpAmount;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class LumberjackAxe extends Craft implements LootTableChanger {
    private static LumberjackAxe instance;
    private LumberjackAxe() {
        super(UnlocksData.COST_100, 2, "Lumberjack Axe", "lumberjack_axe");
        registerItem();
    }


    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AAC", "AB ", " B ");
        recipe.setIngredient('A', new RecipeChoice.MaterialChoice(Tag.ITEMS_STONE_CRAFTING_MATERIALS));
        recipe.setIngredient('B', Material.STICK);
        recipe.setIngredient('C', Material.FLINT);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = IronAxe.getInstance().getResult();
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.AQUA));
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Drops all log blocks adjacent", TextUtil.DARK_PURPLE),
                TextUtil.makeText("to the one you strike and", TextUtil.DARK_PURPLE),
                TextUtil.makeText("the ones adjacent to them", TextUtil.DARK_PURPLE),
                TextUtil.makeText(""),
                TextUtil.makeText("Reduces experience gained", TextUtil.RED)
        ));
        meta.setEnchantmentGlintOverride(true);
        lore.addAll(meta.lore());
        meta.lore(lore);
        meta.setCustomModelDataComponent(generateUniqueModelNumber(meta.getCustomModelDataComponent()));
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
        List<Material> logs = new RecipeChoice.MaterialChoice(Tag.LOGS).getChoices();
        Block b = e.getBlock();
        if (logs.contains(b.getType())) {
            giveBreakReward(b, PlayerUtil.getPlayerData(p));
            breakLogs(getAllBlocksAround(b.getLocation()), PlayerUtil.getPlayerData(p));
        }
        return true;
    }

    private void breakLogs(List<Block> b, PlayerData pD) {
        ItemStack axe = pD.getPlayer().getInventory().getItemInMainHand();
        List<Material> logs = new RecipeChoice.MaterialChoice(Tag.LOGS).getChoices();
        new BukkitRunnable() {
            List<Block> blocks = new ArrayList<>(b);
            @Override
            public void run() {
                if (blocks.isEmpty()) {
                    cancel();
                    return;
                }
                while (!blocks.isEmpty()) {
                    Block b = blocks.removeLast();
                    if (logs.contains(b.getType())) {
                        giveBreakReward(b, pD, axe);
                        b.breakNaturally();
                        blocks = getAllBlocksAround(b.getLocation());
                        break;
                    }
                }
            }
        }.runTaskTimer(plugin, 3, 3);
    }

    private void giveBreakReward(Block b, PlayerData pD, ItemStack axe) {
        b.getLocation().getWorld().playSound(b.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
        if (axe != null && axe.getItemMeta() instanceof Damageable) {
            Damageable damageable = (Damageable) axe.getItemMeta();
            damageable.setDamage(damageable.getDamage() + 1);
            axe.setItemMeta(damageable);
        }
        if (!b.hasMetadata(ForageBlocks.PLACED_BY_PLAYER)) {
            pD.getPlayerSkills().getForaging().addExp(SkillExpAmount.FORAGING_COMMON.getExp()/3 * 2);
        }
    }

    private void giveBreakReward(Block b, PlayerData pD) {
        giveBreakReward(b, pD, null);
    }

    //Returns all blocks around location loc
    private List<Block> getAllBlocksAround(Location loc) {
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        List<Block> blocks = new ArrayList<>();
        for (int x_i = -1; x_i <= 1; x_i++ ) {
            for (int z_i = -1; z_i <= 1; z_i++ ) {
                for (int y_i = -1; y_i <= 1; y_i++ ) {
                    Block block = loc.getWorld().getBlockAt(x + x_i, y + y_i, z + z_i);
                    if (block.getType() != Material.AIR || (x_i == 0 && y_i == 0 && z_i == 0)) {
                        blocks.add(block);
                    }
                }
            }
        }
        return blocks;
    }

    @Override
    public void registerItem() {
        addItem(getItem(), this);
    }

    public static LumberjackAxe getInstance() {
        if (instance == null) instance = new LumberjackAxe();
        return instance;
    }
}
