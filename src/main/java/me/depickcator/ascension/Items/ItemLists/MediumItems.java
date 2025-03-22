package me.depickcator.ascension.Items.ItemLists;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.LocationChecker.LocationCheck;
import me.depickcator.ascension.Items.Uncraftable.Skulls.CreeperHead;
import me.depickcator.ascension.Items.Uncraftable.Skulls.SkeletonSkull;
import me.depickcator.ascension.Items.Uncraftable.Skulls.ZombieHead;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class MediumItems {
    private ArrayList<ItemStack> items;
    private Random rand;
    private final LocationCheck locationCheck;
    public MediumItems() {
        items = new ArrayList<>();
        rand = new Random();
        locationCheck = Ascension.getInstance().getLocationCheck();
        setItems();

    }

    private void setItems() {
        //Jungle
        if (locationCheck.isALocation(Biome.BAMBOO_JUNGLE)) {
            items.add(new ItemStack(Material.BAMBOO_BLOCK));
            items.add(new ItemStack(Material.SCAFFOLDING));
            items.add(new ItemStack(Material.COOKIE));
        }
        //Swamp
        if (locationCheck.isALocation(Biome.SWAMP, Biome.JUNGLE, Biome.BAMBOO_JUNGLE)) items.add(new ItemStack(Material.VINE));
        if (locationCheck.isALocation(Biome.SWAMP, Biome.MANGROVE_SWAMP)) items.add(new ItemStack(Material.LILY_PAD));

        //Trial
        if (locationCheck.isALocation(Structure.TRIAL_CHAMBERS)) {
            items.add(new ItemStack(Material.BREEZE_ROD));
            items.add(new ItemStack(Material.WIND_CHARGE));
            items.add(new ItemStack(Material.TRIAL_KEY));
        }

        //Desert
        if (locationCheck.isALocation(Biome.DESERT)) {
            items.add(new ItemStack(Material.CACTUS));
            items.add(new ItemStack(Material.RED_SANDSTONE));
        }

        //Pale Garden
        if (locationCheck.isALocation(Biome.PALE_GARDEN)) {
            items.add(getResinMaterial());
        }

        //Cherry Grove
        if (locationCheck.isALocation(Biome.CHERRY_GROVE)) {
            items.add(new ItemStack(Material.PINK_PETALS));
        }


        items.add(new ItemStack(Material.SADDLE)); //TODO: Change to Medium?
        items.add(new ItemStack(Material.NAME_TAG)); //TODO: Change to Medium?

        items.add(new ItemStack(Material.NETHERITE_SCRAP));
        items.add(new ItemStack(Material.AMETHYST_SHARD));
        items.add(new ItemStack(Material.PUMPKIN_PIE));
        items.add(new ItemStack(Material.HONEYCOMB));
        items.add(new ItemStack(Material.SLIME_BALL));
        items.add(new ItemStack(Material.MOSSY_COBBLESTONE));
        items.add(new ItemStack(Material.GLISTERING_MELON_SLICE));
        items.add(new ItemStack(Material.FERMENTED_SPIDER_EYE));
        items.add(new ItemStack(Material.TROPICAL_FISH));
        items.add(new ItemStack(Material.PRISMARINE_CRYSTALS));
        items.add(new ItemStack(Material.GHAST_TEAR));
        items.add(new ItemStack(Material.CAKE));
        items.add(new ItemStack(Material.RABBIT_HIDE));
        items.add(new ItemStack(Material.INK_SAC));
        items.add(new ItemStack(Material.ANCIENT_DEBRIS));
        items.add(new ItemStack(Material.AMETHYST_BLOCK));
        items.add(new ItemStack(Material.BLACK_BED));
        items.add(new ItemStack(Material.COBWEB));
        items.add(new ItemStack(Material.NETHER_WART));

        items.add(new ItemStack(Material.SWEET_BERRIES));
        items.add(new ItemStack(Material.SLIME_BLOCK));
        items.add(new ItemStack(Material.DIAMOND_BLOCK));
        items.add(new ItemStack(Material.EMERALD_BLOCK));

        items.add(new ItemStack(Material.ENDER_CHEST));
        items.add(new ItemStack(Material.ENDER_EYE));
        items.add(new ItemStack(Material.SPYGLASS));
        items.add(new ItemStack(Material.OBSERVER));
        items.add(new ItemStack(Material.MAGMA_CREAM));
        items.add(new ItemStack(Material.JUKEBOX));
        items.add(new ItemStack(Material.BREWING_STAND));
        items.add(new ItemStack(Material.BEACON));
        items.add(new ItemStack(Material.ENCHANTING_TABLE));
        if (locationCheck.isALocation(Structure.SHIPWRECK)) items.add(new ItemStack(Material.HEART_OF_THE_SEA));
        items.add(new ItemStack(Material.NETHER_BRICKS));
        items.add(new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE));
        items.add(new ItemStack(Material.HONEY_BOTTLE));
        items.add(new ItemStack(Material.MUSHROOM_STEW));
        items.add(new ItemStack(Material.RABBIT_STEW));
        items.add(new ItemStack(Material.FIRE_CHARGE));
        items.add(new ItemStack(Material.TINTED_GLASS));
        items.add(new ItemStack(Material.POINTED_DRIPSTONE));

        items.add(new ItemStack(CreeperHead.getInstance().getResult()));
        items.add(new ItemStack(ZombieHead.getInstance().getResult()));
        items.add(new ItemStack(SkeletonSkull.getInstance().getResult()));
        items.add(new ItemStack(Material.SOUL_CAMPFIRE));
        items.add(new ItemStack(Material.SOUL_TORCH));
        items.add(new ItemStack(Material.STICKY_PISTON));
        items.add(new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK));
        items.add(new ItemStack(Material.BEETROOT_SOUP));
        items.add(new ItemStack(Material.BEEHIVE));
        items.add(new ItemStack(Material.SPAWNER));
        items.add(new ItemStack(Material.MAGMA_BLOCK));
        items.add(new ItemStack(Material.GLOW_ITEM_FRAME));
        items.add(new ItemStack(Material.TURTLE_HELMET));
        items.add(new ItemStack(Material.LEAD));
        items.add(new ItemStack(Material.SPECTRAL_ARROW));

        items.add(new ItemStack(Material.BLAZE_ROD));
        items.add(new ItemStack(Material.SOUL_LANTERN));
        items.add(new ItemStack(Material.CRYING_OBSIDIAN));
        items.add(new ItemStack(Material.OBSIDIAN));
        items.add(new ItemStack(Material.RABBIT_FOOT));

        items.add(new ItemStack(Material.CRAFTER));

        items.add(getRandomCandle());
        items.add(getRandomHangingSign());
        items.add(getRandomOre());
    }

    private ItemStack getResinMaterial() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.RESIN_CLUMP));
        items.add(new ItemStack(Material.RESIN_BRICK));
        items.add(new ItemStack(Material.RESIN_BLOCK));
        items.add(new ItemStack(Material.RESIN_BRICK_SLAB));
        items.add(new ItemStack(Material.RESIN_BRICK_STAIRS));
        items.add(new ItemStack(Material.RESIN_BRICK_WALL));

        int pick = rand.nextInt(items.size());

        return items.get(pick);
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

        int pick = rand.nextInt(items.size());

        return items.get(pick);
    }

    private ItemStack getRandomHangingSign() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.ACACIA_HANGING_SIGN));
        if (locationCheck.isALocation(Biome.BAMBOO_JUNGLE)) items.add(new ItemStack(Material.BAMBOO_HANGING_SIGN));
        items.add(new ItemStack(Material.BIRCH_HANGING_SIGN));
        if (locationCheck.isALocation(Biome.CHERRY_GROVE)) items.add(new ItemStack(Material.CHERRY_HANGING_SIGN));
        items.add(new ItemStack(Material.CRIMSON_HANGING_SIGN));
        items.add(new ItemStack(Material.DARK_OAK_HANGING_SIGN));
        if (locationCheck.isALocation(Biome.MANGROVE_SWAMP)) items.add(new ItemStack(Material.MANGROVE_HANGING_SIGN));
        if (locationCheck.isALocation(Biome.JUNGLE)) items.add(new ItemStack(Material.JUNGLE_HANGING_SIGN));
        if (locationCheck.isALocation(Biome.PALE_GARDEN)) items.add(new ItemStack(Material.PALE_OAK_HANGING_SIGN));
        items.add(new ItemStack(Material.SPRUCE_HANGING_SIGN));
        items.add(new ItemStack(Material.OAK_HANGING_SIGN));

        int pick = rand.nextInt(items.size());

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

        int pick = rand.nextInt(items.size());

        return items.get(pick);
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

}
