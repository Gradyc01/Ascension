package me.depickcator.ascension.Items.ItemLists;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.LocationChecker.LocationCheck;
import me.depickcator.ascension.Items.Uncraftable.HardenedSaddle;
import me.depickcator.ascension.Items.Uncraftable.Skulls.CreeperHead;
import me.depickcator.ascension.Items.Uncraftable.Skulls.SkeletonSkull;
import me.depickcator.ascension.Items.Uncraftable.Skulls.ZombieHead;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MediumItems extends ItemLists {

    @Override
    protected void initItemList() {
        //Jungle
        addItems(parseMaterials(Material.BAMBOO_BLOCK, Material.SCAFFOLDING, Material.COOKIE), Biome.BAMBOO_JUNGLE);
        //Swamp
        addItems(parseMaterials(Material.VINE), Biome.BAMBOO_JUNGLE, Biome.JUNGLE, Biome.SWAMP);
        addItems(parseMaterials(Material.LILY_PAD), Biome.MANGROVE_SWAMP, Biome.SWAMP);
        //Trial
        addItems(parseMaterials(
                Material.BREEZE_ROD,
                Material.WIND_CHARGE,
                Material.TRIAL_KEY), Structure.TRIAL_CHAMBERS);

        //Desert
        addItems(parseMaterials(
                Material.CACTUS,
                Material.RED_SANDSTONE,
                Material.CACTUS_FLOWER
        ), Biome.DESERT);
        //Pale Garden
        addItems(getRandomItemInList(getResinMaterial(), 1), Biome.PALE_GARDEN);

        //Cherry Grove
        addItems(parseMaterials(Material.PINK_PETALS), Biome.CHERRY_GROVE);

        //Shipwreck
        addItems(parseMaterials(Material.HEART_OF_THE_SEA), Structure.SHIPWRECK, Structure.SHIPWRECK_BEACHED);
        addItems(parseMaterials(
                        Material.NAME_TAG,
                        Material.NETHERITE_SCRAP,
                        Material.AMETHYST_SHARD,
                        Material.PUMPKIN_PIE,
                        Material.HONEYCOMB,
                        Material.SLIME_BALL,
                        Material.MOSSY_COBBLESTONE,
                        Material.GLISTERING_MELON_SLICE,
                        Material.FERMENTED_SPIDER_EYE,
                        Material.TROPICAL_FISH,
                        Material.GHAST_TEAR,
                        Material.CAKE,
                        Material.RABBIT_HIDE,
                        Material.INK_SAC,
                        Material.ANCIENT_DEBRIS,
                        Material.AMETHYST_BLOCK,
                        Material.BLACK_BED,
                        Material.COBWEB,
                        Material.NETHER_WART,
                        Material.SWEET_BERRIES,
                        Material.SLIME_BLOCK,
                        Material.DIAMOND_BLOCK,
                        Material.EMERALD_BLOCK,
                        Material.ENDER_CHEST,
                        Material.ENDER_EYE,
                        Material.SPYGLASS,
                        Material.OBSERVER,
                        Material.MAGMA_CREAM,
                        Material.JUKEBOX,
                        Material.BREWING_STAND,
                        Material.BEACON,
                        Material.ENCHANTING_TABLE,
                        Material.NETHER_BRICKS,
                        Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Material.HONEY_BOTTLE,
                        Material.MUSHROOM_STEW,
                        Material.RABBIT_STEW,
                        Material.FIRE_CHARGE,
                        Material.TINTED_GLASS,
                        Material.POINTED_DRIPSTONE,
                        Material.SOUL_CAMPFIRE,
                        Material.SOUL_TORCH,
                        Material.STICKY_PISTON,
                        Material.WARPED_FUNGUS_ON_A_STICK,
                        Material.BEETROOT_SOUP,
                        Material.BEEHIVE,
                        Material.MAGMA_BLOCK,
                        Material.GLOW_ITEM_FRAME,
                        Material.TURTLE_HELMET,
                        Material.SPECTRAL_ARROW,
                        Material.BLAZE_ROD,
                        Material.SOUL_LANTERN,
                        Material.CRYING_OBSIDIAN,
                        Material.OBSIDIAN,
                        Material.RABBIT_FOOT,
                        Material.DAYLIGHT_DETECTOR,
                        Material.FIREFLY_BUSH,
                        Material.BUSH,
                        Material.DRIED_GHAST
                ));
        addItems(List.of(
                ZombieHead.getInstance().getResult(),
                SkeletonSkull.getInstance().getResult(),
                CreeperHead.getInstance().getResult(),
                HardenedSaddle.getInstance().getResult(),
                getRandomItemInList(getCandle()),
                getRandomItemInList(getHangingSign()),
                getRandomItemInList(getOre())));
    }

    private List<ItemStack> getResinMaterial() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.RESIN_CLUMP));
        items.add(new ItemStack(Material.RESIN_BRICK));
        items.add(new ItemStack(Material.RESIN_BLOCK));
        items.add(new ItemStack(Material.RESIN_BRICK_SLAB));
        items.add(new ItemStack(Material.RESIN_BRICK_STAIRS));
        items.add(new ItemStack(Material.RESIN_BRICK_WALL));

        return items;
    }
    private List<ItemStack> getCandle() {
        List<ItemStack> items = new ArrayList<>();
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
        return items;
    }
    private List<ItemStack> getHangingSign() {
        List<ItemStack> items = new ArrayList<>();
        LocationCheck locationCheck = Ascension.getInstance().getLocationCheck();
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
        return items;
    }
    private List<ItemStack> getOre() {
        List<ItemStack> items = new ArrayList<>();
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
        return items;
    }

}
