package me.depickcator.ascension.Items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MediumItems {
    private ArrayList<ItemStack> items;
    public MediumItems() {
        items = new ArrayList<>();
        setItems();
    }

    private void setItems() {
        items.add(new ItemStack(Material.COOKIE));
        items.add(new ItemStack(Material.NETHERITE_SCRAP));
        items.add(new ItemStack(Material.AMETHYST_SHARD));
        items.add(new ItemStack(Material.PUMPKIN_PIE));
        items.add(new ItemStack(Material.HONEYCOMB));
        items.add(new ItemStack(Material.SLIME_BALL));
        items.add(new ItemStack(Material.MOSSY_COBBLESTONE));
        items.add(new ItemStack(Material.GLISTERING_MELON_SLICE));
        items.add(new ItemStack(Material.FERMENTED_SPIDER_EYE));
//        items.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        items.add(new ItemStack(Material.TROPICAL_FISH));
        items.add(new ItemStack(Material.PRISMARINE_CRYSTALS));
        items.add(new ItemStack(Material.GHAST_TEAR));
        items.add(new ItemStack(Material.CAKE));
        items.add(new ItemStack(Material.RABBIT_HIDE));
        items.add(new ItemStack(Material.INK_SAC));
        items.add(new ItemStack(Material.ANCIENT_DEBRIS));
        items.add(new ItemStack(Material.AMETHYST_BLOCK));
        items.add(new ItemStack(Material.BLACK_BED));
        items.add(new ItemStack(Material.BAMBOO_BLOCK));
        items.add(new ItemStack(Material.COBWEB));
        items.add(new ItemStack(Material.NETHER_WART));
        items.add(new ItemStack(Material.SCAFFOLDING));
        items.add(new ItemStack(Material.SWEET_BERRIES));
        items.add(new ItemStack(Material.SLIME_BLOCK));
        items.add(new ItemStack(Material.RED_SANDSTONE));
        items.add(new ItemStack(Material.DIAMOND_BLOCK));
        items.add(new ItemStack(Material.EMERALD_BLOCK));
        items.add(new ItemStack(Material.VINE));
//        items.add(new ItemStack(Material.SPLASH_POTION));
        items.add(new ItemStack(Material.LILY_PAD));
        items.add(new ItemStack(Material.ENDER_CHEST));
        items.add(new ItemStack(Material.ENDER_EYE));
        items.add(new ItemStack(Material.SPYGLASS));
        items.add(new ItemStack(Material.OBSERVER));
        items.add(new ItemStack(Material.MAGMA_CREAM));
//        items.add(new ItemStack(Material.ELYTRA);
        items.add(new ItemStack(Material.JUKEBOX));
        items.add(new ItemStack(Material.BREWING_STAND));
        items.add(new ItemStack(Material.BEACON));
        items.add(new ItemStack(Material.ENCHANTING_TABLE));
        items.add(new ItemStack(Material.HEART_OF_THE_SEA));
        items.add(new ItemStack(Material.NETHER_BRICKS));
        items.add(new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE));
        items.add(new ItemStack(Material.HONEY_BOTTLE));
        items.add(new ItemStack(Material.MUSHROOM_STEW));
        items.add(new ItemStack(Material.RABBIT_STEW));
        items.add(new ItemStack(Material.FIRE_CHARGE));
        items.add(new ItemStack(Material.TINTED_GLASS));
        items.add(new ItemStack(Material.POINTED_DRIPSTONE));
        items.add(new ItemStack(Material.BREEZE_ROD));
        items.add(new ItemStack(Material.WIND_CHARGE));
        items.add(new ItemStack(Material.CREEPER_HEAD));
        items.add(new ItemStack(Material.ZOMBIE_HEAD));
        items.add(new ItemStack(Material.SKELETON_SKULL));
        items.add(new ItemStack(Material.TRIAL_KEY));
        items.add(new ItemStack(Material.SOUL_CAMPFIRE));
        items.add(new ItemStack(Material.SOUL_TORCH));
        items.add(new ItemStack(Material.STICKY_PISTON));
        items.add(new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK));
        items.add(new ItemStack(Material.BEETROOT_SOUP));
        items.add(new ItemStack(Material.CACTUS));
        items.add(new ItemStack(Material.BEEHIVE));
        items.add(new ItemStack(Material.SPAWNER));
        items.add(new ItemStack(Material.MAGMA_BLOCK));
        items.add(new ItemStack(Material.GLOW_ITEM_FRAME));
        items.add(new ItemStack(Material.TURTLE_HELMET));
        items.add(new ItemStack(Material.LEAD));

        items.add(new ItemStack(Material.BLAZE_ROD));
        items.add(new ItemStack(Material.SOUL_LANTERN));
        items.add(new ItemStack(Material.CRYING_OBSIDIAN));
        items.add(new ItemStack(Material.RABBIT_FOOT));
        items.add(getRandomCandle());
        items.add(getRandomHangingSign());
        items.add(getRandomOre());
    }

    private ItemStack getRandomCandle() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.WHITE_CANDLE));
        items.add(new ItemStack(Material.GRAY_CANDLE));
        items.add(new ItemStack(Material.LIGHT_GRAY_CANDLE));
        items.add(new ItemStack(Material.BLACK_CANDLE));
        items.add(new ItemStack(Material.BROWN_CANDLE));
        items.add(new ItemStack(Material.RED_CANDLE));
        items.add(new ItemStack(Material.ORANGE_CANDLE));
        items.add(new ItemStack(Material.YELLOW_CANDLE));
        items.add(new ItemStack(Material.LIME_CANDLE));
        items.add(new ItemStack(Material.GREEN_CANDLE));
        items.add(new ItemStack(Material.CYAN_CANDLE));
        items.add(new ItemStack(Material.LIGHT_BLUE_CANDLE));
        items.add(new ItemStack(Material.BLUE_CANDLE));
        items.add(new ItemStack(Material.PURPLE_CANDLE));
        items.add(new ItemStack(Material.MAGENTA_CANDLE));
        items.add(new ItemStack(Material.PINK_CANDLE));

        int pick = (int) Math.round(Math.random() * items.size());

        return items.get(pick);
    }

    private ItemStack getRandomHangingSign() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.ACACIA_HANGING_SIGN));
        items.add(new ItemStack(Material.BAMBOO_HANGING_SIGN));
        items.add(new ItemStack(Material.BIRCH_HANGING_SIGN));
        items.add(new ItemStack(Material.CHERRY_HANGING_SIGN));
        items.add(new ItemStack(Material.CRIMSON_HANGING_SIGN));
        items.add(new ItemStack(Material.DARK_OAK_HANGING_SIGN));
        items.add(new ItemStack(Material.MANGROVE_HANGING_SIGN));
        items.add(new ItemStack(Material.JUNGLE_HANGING_SIGN));
        items.add(new ItemStack(Material.SPRUCE_HANGING_SIGN));
        items.add(new ItemStack(Material.OAK_HANGING_SIGN));

        int pick = (int) Math.round(Math.random() * items.size());

        return items.get(pick);
    }

    private ItemStack getRandomOre() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.COPPER_ORE));
        items.add(new ItemStack(Material.COAL_ORE));
        items.add(new ItemStack(Material.IRON_ORE));
        items.add(new ItemStack(Material.GOLD_ORE));
        items.add(new ItemStack(Material.LAPIS_ORE));
        items.add(new ItemStack(Material.REDSTONE_ORE));
        items.add(new ItemStack(Material.DIAMOND_ORE));
        items.add(new ItemStack(Material.EMERALD_ORE));
        items.add(new ItemStack(Material.DEEPSLATE_COPPER_ORE));
        items.add(new ItemStack(Material.DEEPSLATE_COAL_ORE));
        items.add(new ItemStack(Material.DEEPSLATE_IRON_ORE));
        items.add(new ItemStack(Material.DEEPSLATE_GOLD_ORE));
        items.add(new ItemStack(Material.DEEPSLATE_LAPIS_ORE));
        items.add(new ItemStack(Material.DEEPSLATE_REDSTONE_ORE));
        items.add(new ItemStack(Material.DEEPSLATE_DIAMOND_ORE));
        items.add(new ItemStack(Material.DEEPSLATE_EMERALD_ORE));

        int pick = (int) Math.round(Math.random() * items.size());

        return items.get(pick);
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

}
