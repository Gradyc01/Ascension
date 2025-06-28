package me.depickcator.ascension.Timeline.Events.SoulShop.Algorithms;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Vanilla.DiamondAxe;
import me.depickcator.ascension.Items.Craftable.Vanilla.DiamondSword;
import me.depickcator.ascension.Items.Craftable.Vanilla.NetheriteAxe;
import me.depickcator.ascension.Items.Craftable.Vanilla.NetheriteSword;
import me.depickcator.ascension.Items.Uncraftable.Skulls.CreeperHead;
import me.depickcator.ascension.Items.Uncraftable.Skulls.SkeletonSkull;
import me.depickcator.ascension.Items.Uncraftable.Skulls.ZombieHead;
import me.depickcator.ascension.Utility.ItemComparison;
import me.depickcator.ascension.Timeline.Events.SoulShop.Shop;
import me.depickcator.ascension.Timeline.Events.SoulShop.SoulShopItem;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.*;

import java.util.*;

public abstract class SoulShopAlgorithm {
    private final List<SoulShopItem> items;
    protected final Ascension plugin;
    protected final Random r;
    protected final Set<Material> whiteListedItems;
    protected final Set<Material> commonSingleBlocks;
    protected final Set<String> expensiveList;
    protected final Set<String> cheapList;
    protected final Map<Material, ItemStack> manualOverrideList;
    public SoulShopAlgorithm() {
        plugin = Ascension.getInstance();
        items = new ArrayList<>();
        r = new Random();
        whiteListedItems = Set.of(Material.DIAMOND, Material.STICK, Material.PLAYER_HEAD,
                Material.GOLD_INGOT, Material.EMERALD, Material.IRON_INGOT, Material.GLASS_BOTTLE,
                Material.COMPASS, Material.WATER_BUCKET, Material.FISHING_ROD, Material.BOOK,
                Material.PAPER, Material.GLASS, Material.COBBLESTONE, Material.COBBLED_DEEPSLATE,
                Material.GRAVEL, Material.SAND, Material.GOLD_NUGGET, Material.IRON_NUGGET, Material.BOW);
        commonSingleBlocks = Set.of(Material.ANVIL, Material.HEAVY_CORE, Material.DRIED_GHAST,
                Material.ZOMBIE_HEAD, Material.SKELETON_SKULL, Material.CREEPER_HEAD, Material.LILY_PAD,
                Material.GOLD_BLOCK);
        manualOverrideList = Map.of(Material.DIAMOND_SWORD, DiamondSword.getInstance().getResult(),
                Material.DIAMOND_AXE, DiamondAxe.getInstance().getResult(),
                Material.NETHERITE_AXE, NetheriteAxe.getInstance().getResult(),
                Material.NETHERITE_SWORD, NetheriteSword.getInstance().getResult(),
                Material.CREEPER_HEAD, CreeperHead.getInstance().getResult(),
                Material.SKELETON_SKULL, SkeletonSkull.getInstance().getResult(),
                Material.ZOMBIE_HEAD, ZombieHead.getInstance().getResult());
        expensiveList = Set.of("sword", "axe", "ascension", "helmet", "chestplate", "leggings",
                "boots", "breeze_rod", "heavy_core", "blaze_rod", "golden_apple", "name_tag", "_skull", "_head");
        cheapList = Set.of("gold", "iron", "leather", "planks", "log", "stem", "slab", "redstone", "lapis_lazuli", "copper_ingot");
    }

    protected void buildSoulShopItemsFromItemStacks(Collection<ItemStack> items, List<Shop> shops) {
        for (ItemStack item : items) {
            String displayName = PlainTextComponentSerializer.plainText().serialize(item.displayName());
            displayName = displayName.substring(1, displayName.length() - 1);

            if (manualOverrideList.containsKey(item.getType())) item = manualOverrideList.get(item.getType()).clone();

            int price = getPrice(item);
            int quantity = containsString(item.getType().name(), expensiveList) ? 1 : r.nextInt(1, 6);
            for (Shop shop : shops) {
                addItem(new SoulShopItem(displayName, price, quantity, item, shop));
            }
        }
    }

    /*Gets the price for ItemStack item*/
    private int getPrice(ItemStack item) {
        String type =  item.getType().name();
        if (containsString(type, expensiveList)) {
            return choosePrice(1000, 1500);
        } else if (containsString(type, cheapList)) {
            return choosePrice(125, 400);
        } else {
            return choosePrice(500, 900);
        }
    }

    /*Chooses a price between min and max that is divisible by 25
    * min and max also need to be divisible by 25*/
    private int choosePrice(int min, int max) {
        return (r.nextInt(min, max + 1) / 25) * 25;
    }

    protected boolean containsString(String text, Set<String> strings) {
        for (String word : strings) {
            if (text.toLowerCase().contains(word.toLowerCase())) return true;
        }
        return false;
    }

    /*Picks num items out of List items with a couple stipulations
    * Can't be the same Item
    * Must not have the same material
    * Must not be in the whitelist*/
    protected List<ItemStack> pickItemsFromList(List<ItemStack> items, int num) {
        List<ItemStack> pickedItems = new ArrayList<>();
        Set<Material> materials = new HashSet<>();
        int numOfPicks = Integer.min(items.size(), num);
        int maxIterations = 100;
        while (numOfPicks > 0 && maxIterations > 0) {
            ItemStack item = items.get(r.nextInt(items.size()));
            if (ItemComparison.getItemModelNumber(item) == 0 && !materials.contains(item.getType()) && !whiteListedItems.contains(item.getType())) {
                materials.add(item.getType());
                pickedItems.add(item);
                numOfPicks--;
            }
            maxIterations--;
        }
        return pickedItems;
    }




    /*Gets all the ItemStacks from Recipe recipe*/
    protected List<ItemStack> getItemsFromRecipe(Recipe recipe) {
        List<ItemStack> items = new ArrayList<>();
        if (recipe instanceof ShapedRecipe shapedRecipe) {
            for (Map.Entry<Character, RecipeChoice> entry : shapedRecipe.getChoiceMap().entrySet()) {
                List<ItemStack> list = getItemsFromChoiceMap(entry.getValue(), getChoiceCount(shapedRecipe.getShape(), entry.getKey()));
                if (list.isEmpty()) continue;
                items.add(list.get(r.nextInt(list.size())));
            }
        } else if (recipe instanceof ShapelessRecipe shapelessRecipe) {
            for (RecipeChoice recipeChoice : shapelessRecipe.getChoiceList()) {
                List<ItemStack> list = getItemsFromChoiceMap(recipeChoice, 1);
                if (list.isEmpty()) continue;
                items.add(list.get(r.nextInt(list.size())));
            }
        }
        return items;
    }


    /*Gets every single Itemstack from a recipe choice, quantity sets the itemstack's amount*/
    private List<ItemStack> getItemsFromChoiceMap(RecipeChoice recipeChoice, int quantity) {
        List<ItemStack> items = new ArrayList<>();
        if (recipeChoice instanceof RecipeChoice.ExactChoice exactChoice) {
            for (ItemStack item : exactChoice.getChoices()) {
                buildItem(item.clone(), quantity, items);
            }
        } else if (recipeChoice instanceof RecipeChoice.MaterialChoice materialChoice) {
            for (Material mat : materialChoice.getChoices()) {
                buildItem(new ItemStack(mat), quantity, items);
            }
        }
        return items;
    }

    private void buildItem(ItemStack item, int quantity, List<ItemStack> items) {
        item.setAmount(r.nextInt(1, 6));
        if (commonSingleBlocks.contains(item.getType())) {
            item.setAmount(1);
        } else if (item.getType().isBlock()) {
            if (item.getType().name().contains("planks") || quantity != 1) {
                item.setAmount(r.nextInt(1, 6) * 4);
            }
        } else if (quantity == 1) {
            item.setAmount(1);
        }
        items.add(item);
    }

    /*Gets how many times character target is in input*/
    private int getChoiceCount(String[] input, char target) {
        String combined = String.join("", input);
        long count = combined.chars().filter(ch -> ch == target).count();
        return (int) count;
    }

    protected void addItem(SoulShopItem item) {
        items.add(item);
    }

    public List<SoulShopItem> retrieveResult() {
        return items;
    }
}
