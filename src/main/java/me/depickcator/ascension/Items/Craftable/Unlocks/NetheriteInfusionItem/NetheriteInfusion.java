package me.depickcator.ascension.Items.Craftable.Unlocks.NetheriteInfusionItem;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Craftable.Unlocks.DragonSword;
import me.depickcator.ascension.Items.Craftable.Unlocks.HermesBoots;
import me.depickcator.ascension.Items.Craftable.Unlocks.MinerBlessing;
import me.depickcator.ascension.Items.Craftable.Vanilla.NetheriteAxe;
import me.depickcator.ascension.Items.Craftable.Vanilla.NetheriteSword;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class NetheriteInfusion extends Craft{
    private static NetheriteInfusion instance;
    private final Map<Float, CustomInfusion> customInfusions;

    private NetheriteInfusion() {
        super(UnlocksData.COST_350, 1, "Netherite Infusion", "netherite_infusion");
        customInfusions = initCustomModelNumbers();
    }

    public static NetheriteInfusion getInstance() {
        if (instance == null) instance = new NetheriteInfusion();
        return instance;
    }

    @Override
    public boolean uponCrafted(CraftItemEvent e, Player p) {
        CraftingInventory inv = e.getInventory();
        if (!isMatchingDiamondPieceType(inv.getMatrix())) {
            TextUtil.errorMessage(p, "The Diamond and Netherite pieces do not match!");
            return false;
        }
        Pair<ItemStack, ItemStack> itemStackPair = findMatchingMaterial(inv.getMatrix());
        ItemStack originalItemStack = itemStackPair.getLeft();
        ItemStack newItemStack= itemStackPair.getRight();
        Damageable originalMeta = (Damageable) originalItemStack.getItemMeta();
        newItemStack.setItemMeta(originalMeta);

        applyCustomInfusionsIfNecessary(p, newItemStack);

        e.setCurrentItem(newItemStack);
        return true;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapelessRecipe recipe = new ShapelessRecipe(key, initResult());
        recipe.addIngredient(initDiamondPieces());
        recipe.addIngredient(initNetheritePieces());
        recipe.addIngredient(new RecipeChoice.MaterialChoice(
                Material.PLAYER_HEAD,
                Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE));
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.GOLD));
        List<Component> lore = List.of(
                TextUtil.makeText("Infuses a Diamond Piece into a ", TextUtil.DARK_PURPLE),
                TextUtil.makeText("Netherite Piece by providing ", TextUtil.DARK_PURPLE),
                TextUtil.makeText("either an equal piece of", TextUtil.DARK_PURPLE),
                TextUtil.makeText("netherite or an ingot", TextUtil.DARK_PURPLE)
        );
        meta.lore(lore);
        meta.setEnchantmentGlintOverride(true);
        item.setItemMeta(meta);
        return item;
    }

    /*Determines the type of diamond piece it is and returns the matching netherite material
    * However if the netherite material does not match the diamond piece then it returns null*/
    private boolean isMatchingDiamondPieceType(ItemStack[] matrix) {
        String diamondPiece = "";
        String netheritePiece = "";
        for (ItemStack item : matrix) {
            if (item == null) continue;
            if (initDiamondPieces().getChoices().contains(item.getType())) diamondPiece = item.getType().name();
            if (initNetheritePieces().getChoices().contains(item.getType())) netheritePiece = item.getType().name();
        }
        String diamondSubstring = diamondPiece.substring(diamondPiece.indexOf("_"));
        String netheriteSubstring = netheritePiece.substring(netheritePiece.indexOf("_"));
        return diamondSubstring.equalsIgnoreCase(netheriteSubstring)
                || netheriteSubstring.equalsIgnoreCase("_Ingot");
    }

    /*Finds the matching netherite piece to the diamond piece: material
    * Returns a Pair of ItemStacks with the first being the original ItemStack and the second being the matched ItemStack
    * Returns null if material is not matched*/
    private Pair<ItemStack, ItemStack> findMatchingMaterial(ItemStack[] material) {
        for (ItemStack item : material) {
            ItemStack ans = switch (item.getType()) {
                case Material.DIAMOND_AXE -> NetheriteAxe.getInstance().getResult();
                case Material.DIAMOND_SWORD -> NetheriteSword.getInstance().getResult();
                case Material.DIAMOND_SHOVEL -> new ItemStack(Material.NETHERITE_SHOVEL);
                case Material.DIAMOND_PICKAXE -> new ItemStack(Material.NETHERITE_PICKAXE);
                case Material.DIAMOND_HOE -> new ItemStack(Material.NETHERITE_HOE);
                case Material.DIAMOND_HELMET -> new ItemStack(Material.NETHERITE_HELMET);
                case Material.DIAMOND_CHESTPLATE -> new ItemStack(Material.NETHERITE_CHESTPLATE);
                case Material.DIAMOND_LEGGINGS -> new ItemStack(Material.NETHERITE_LEGGINGS);
                case Material.DIAMOND_BOOTS -> new ItemStack(Material.NETHERITE_BOOTS);
                default -> null;
            };
            if (ans != null) return new ImmutablePair<>(item, ans);
        }
        return null;
    }

    /*Applies custom infusions to custom items if necessary*/
    private void applyCustomInfusionsIfNecessary(Player p, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<Float> floats = meta.getCustomModelDataComponent().getFloats();
        if (!floats.isEmpty()) {
            CustomInfusion customInfusion = customInfusions.get(floats.getFirst());
            if (customInfusion != null) {
                customInfusion.customInfusion(item, p);
            }
        }
    }

    private Map<Float, CustomInfusion> initCustomModelNumbers() {
        List<Craft> list = List.of(
                DragonSword.getInstance(),
                HermesBoots.getInstance(),
                MinerBlessing.getInstance()
        );
        Map<Float, CustomInfusion> map = new HashMap<>();
        for (Craft c : list) {
//            floats.add();
            map.put(c.getResult().getItemMeta().getCustomModelDataComponent().getFloats().getFirst(), (CustomInfusion) c);
        }
        return map;
    }


    private RecipeChoice.MaterialChoice initDiamondPieces() {
        return new RecipeChoice.MaterialChoice(
                Material.DIAMOND_AXE,
                Material.DIAMOND_SWORD,
                Material.DIAMOND_SHOVEL,
                Material.DIAMOND_PICKAXE,
                Material.DIAMOND_HOE,
                Material.DIAMOND_HELMET,
                Material.DIAMOND_CHESTPLATE,
                Material.DIAMOND_LEGGINGS,
                Material.DIAMOND_BOOTS
        );
    }

    private RecipeChoice.MaterialChoice initNetheritePieces() {
        return new RecipeChoice.MaterialChoice(
                Material.NETHERITE_AXE,
                Material.NETHERITE_SWORD,
                Material.NETHERITE_SHOVEL,
                Material.NETHERITE_PICKAXE,
                Material.NETHERITE_HOE,
                Material.NETHERITE_HELMET,
                Material.NETHERITE_CHESTPLATE,
                Material.NETHERITE_LEGGINGS,
                Material.NETHERITE_BOOTS,
                Material.NETHERITE_INGOT
        );
    }

}
