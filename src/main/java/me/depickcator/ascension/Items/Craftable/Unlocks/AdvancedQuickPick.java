package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdvancedQuickPick extends Craft implements ItemClick {
    private static AdvancedQuickPick instance;
    private final Set<Material> ores;
    private AdvancedQuickPick() {
        super(UnlocksData.COST_250, 1, "Advanced Quick Pick", "advanced_quick_pick");
        ores = initOres();
        registerItem();
    }

    public static AdvancedQuickPick getInstance() {
        if (instance == null) instance = new AdvancedQuickPick();
        return instance;
    }

    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("ACA", "BDB", " D ");
        recipe.setIngredient('A', Material.DIAMOND);
        recipe.setIngredient('B', ShardOfTheFallen.getInstance().getResult());
        recipe.setIngredient('C', makeRecipeChoice());
        recipe.setIngredient('D', Material.STICK);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.AQUA));
        meta.addEnchant(Enchantment.EFFICIENCY, 4, true);
        meta.addEnchant(Enchantment.UNBREAKING, 2, true);
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Gives haste when mining", TextUtil.DARK_PURPLE)
        ));
        meta.lore(lore);
        item.setItemMeta(meta);
        generateUniqueModelNumber(item);
        return item;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (!e.getAction().isLeftClick()) return false;
        Player p = pD.getPlayer();
        if (e.getClickedBlock() != null && ores.contains(e.getClickedBlock().getType())) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 8 * 20, 2, false, false));
            return true;
        }
        return false;
    }

    private Set<Material> initOres() {
        return new HashSet<>(Set.of(
                Material.COAL_ORE,
                Material.COPPER_ORE,
                Material.IRON_ORE,
                Material.GOLD_ORE,
                Material.LAPIS_ORE,
                Material.REDSTONE_ORE,
                Material.DIAMOND_ORE,
                Material.EMERALD_ORE,
                Material.DEEPSLATE_COAL_ORE,
                Material.DEEPSLATE_COPPER_ORE,
                Material.DEEPSLATE_IRON_ORE,
                Material.DEEPSLATE_GOLD_ORE,
                Material.DEEPSLATE_LAPIS_ORE,
                Material.DEEPSLATE_REDSTONE_ORE,
                Material.DEEPSLATE_DIAMOND_ORE,
                Material.DEEPSLATE_EMERALD_ORE,
                Material.NETHER_QUARTZ_ORE,
                Material.NETHER_GOLD_ORE,
                Material.OBSIDIAN
        ));
    }

    private RecipeChoice makeRecipeChoice() {
        List<ItemStack> itemStacks = new ArrayList<>();
        ItemStack item = QuickPick.getInstance().getResult();

        for (int i = 0; i < 250; i++) {
            ItemStack pick = item.clone();
            Damageable meta = (Damageable) pick.getItemMeta();
            meta.setDamage(i);
            pick.setItemMeta(meta);
            itemStacks.add(pick);
        }
        return new RecipeChoice.ExactChoice(itemStacks);
    }


    @Override
    public ItemStack getItem() {
        return result;
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }
}
