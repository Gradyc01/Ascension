package me.depickcator.ascension.Items.ItemLists;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class EasyItems extends ItemLists {
    @Override
    protected void initItemList() {
        addItems(parseMaterials(
                Material.SADDLE,
                Material.LIGHTNING_ROD,
                Material.ANVIL,
                Material.LECTERN,
                Material.LAPIS_BLOCK,
                Material.REDSTONE_BLOCK,
                Material.GOLD_BLOCK,
                Material.JACK_O_LANTERN,
                Material.EGG,
                Material.TRIPWIRE_HOOK,
                Material.BUNDLE,
                Material.SNOWBALL,
                Material.GOLDEN_CARROT,
                Material.BAKED_POTATO,
                Material.TARGET,
                Material.BELL,
                Material.CARROT_ON_A_STICK,
                Material.BEETROOT,
                Material.MUD_BRICKS,
                Material.MILK_BUCKET,
                Material.TNT,
                Material.PUFFERFISH,
                Material.COD_BUCKET,
                Material.SALMON_BUCKET,
                Material.GLOWSTONE,
                Material.BRICK_SLAB,
                Material.EXPERIENCE_BOTTLE,
                Material.BONE_BLOCK,
                Material.DRIED_KELP_BLOCK,
                Material.FLOWER_POT,
                Material.ENDER_PEARL,
                Material.FIREWORK_STAR,
                Material.FIREWORK_ROCKET,
                Material.CHAIN,
                Material.CAMPFIRE,
                Material.CLOCK,
                Material.BRUSH,
                Material.ITEM_FRAME,
                Material.SUNFLOWER,
                Material.NOTE_BLOCK,
                Material.COMPOSTER,
                Material.CRAFTER,
                Material.LEAF_LITTER,
                Material.WILDFLOWERS,
                Material.REDSTONE_LAMP
        ));
        addItems(List.of(
                getRandomItemInList(getCraftTable()),
                getRandomItemInList(getCopperBlock()),
                getRandomItemInList(getGlass()),
                getRandomItemInList(getBanner()),
                getRandomItemInList(getConcrete()),
                getRandomItemInList(getWool()),
                getRandomItemInList(getHarness())
        ));
    }

    private List<ItemStack> getCraftTable() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.SMOKER));
        items.add(new ItemStack(Material.BLAST_FURNACE));
        items.add(new ItemStack(Material.GRINDSTONE));
        items.add(new ItemStack(Material.LOOM));
        items.add(new ItemStack(Material.SMITHING_TABLE));
        items.add(new ItemStack(Material.FLETCHING_TABLE));
        items.add(new ItemStack(Material.STONECUTTER));
        return items;
    }

    private List<ItemStack> getCopperBlock() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.COPPER_BLOCK));
        items.add(new ItemStack(Material.CHISELED_COPPER));
        items.add(new ItemStack(Material.COPPER_BULB));
        items.add(new ItemStack(Material.COPPER_DOOR));
        items.add(new ItemStack(Material.COPPER_GRATE));
        items.add(new ItemStack(Material.COPPER_TRAPDOOR));
        items.add(new ItemStack(Material.CUT_COPPER));
        items.add(new ItemStack(Material.CUT_COPPER_SLAB));
        items.add(new ItemStack(Material.CUT_COPPER_STAIRS));
        return items;
    }

    private List<ItemStack> getGlass() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.WHITE_STAINED_GLASS));
        items.add(new ItemStack(Material.GRAY_STAINED_GLASS));
        items.add(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS));
        items.add(new ItemStack(Material.BLACK_STAINED_GLASS));
        items.add(new ItemStack(Material.BROWN_STAINED_GLASS));
        items.add(new ItemStack(Material.RED_STAINED_GLASS));
        items.add(new ItemStack(Material.ORANGE_STAINED_GLASS));
        items.add(new ItemStack(Material.YELLOW_STAINED_GLASS));
        items.add(new ItemStack(Material.LIME_STAINED_GLASS));
        items.add(new ItemStack(Material.GREEN_STAINED_GLASS));
        items.add(new ItemStack(Material.CYAN_STAINED_GLASS));
        items.add(new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS));
        items.add(new ItemStack(Material.BLUE_STAINED_GLASS));
        items.add(new ItemStack(Material.PURPLE_STAINED_GLASS));
        items.add(new ItemStack(Material.MAGENTA_STAINED_GLASS));
        items.add(new ItemStack(Material.PINK_STAINED_GLASS));

        return items;
    }

    private List<ItemStack> getBanner() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.WHITE_BANNER));
        items.add(new ItemStack(Material.GRAY_BANNER));
        items.add(new ItemStack(Material.LIGHT_GRAY_BANNER));
        items.add(new ItemStack(Material.BLACK_BANNER));
        items.add(new ItemStack(Material.BROWN_BANNER));
        items.add(new ItemStack(Material.RED_BANNER));
        items.add(new ItemStack(Material.ORANGE_BANNER));
        items.add(new ItemStack(Material.YELLOW_BANNER));
        items.add(new ItemStack(Material.LIME_BANNER));
        items.add(new ItemStack(Material.GREEN_BANNER));
        items.add(new ItemStack(Material.CYAN_BANNER));
        items.add(new ItemStack(Material.LIGHT_BLUE_BANNER));
        items.add(new ItemStack(Material.BLUE_BANNER));
        items.add(new ItemStack(Material.PURPLE_BANNER));
        items.add(new ItemStack(Material.MAGENTA_BANNER));
        items.add(new ItemStack(Material.PINK_BANNER));
        return items;
    }

    private List<ItemStack> getConcrete() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.WHITE_CONCRETE));
        items.add(new ItemStack(Material.GRAY_CONCRETE));
        items.add(new ItemStack(Material.LIGHT_GRAY_CONCRETE));
        items.add(new ItemStack(Material.BLACK_CONCRETE));
        items.add(new ItemStack(Material.BROWN_CONCRETE));
        items.add(new ItemStack(Material.RED_CONCRETE));
        items.add(new ItemStack(Material.ORANGE_CONCRETE));
        items.add(new ItemStack(Material.YELLOW_CONCRETE));
        items.add(new ItemStack(Material.LIME_CONCRETE));
        items.add(new ItemStack(Material.GREEN_CONCRETE));
        items.add(new ItemStack(Material.CYAN_CONCRETE));
        items.add(new ItemStack(Material.LIGHT_BLUE_CONCRETE));
        items.add(new ItemStack(Material.BLUE_CONCRETE));
        items.add(new ItemStack(Material.PURPLE_CONCRETE));
        items.add(new ItemStack(Material.MAGENTA_CONCRETE));
        items.add(new ItemStack(Material.PINK_CONCRETE));
        return items;
    }

    private List<ItemStack> getWool() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.WHITE_WOOL));
        items.add(new ItemStack(Material.GRAY_WOOL));
        items.add(new ItemStack(Material.LIGHT_GRAY_WOOL));
        items.add(new ItemStack(Material.BLACK_WOOL));
        items.add(new ItemStack(Material.BROWN_WOOL));
        items.add(new ItemStack(Material.RED_WOOL));
        items.add(new ItemStack(Material.ORANGE_WOOL));
        items.add(new ItemStack(Material.YELLOW_WOOL));
        items.add(new ItemStack(Material.LIME_WOOL));
        items.add(new ItemStack(Material.GREEN_WOOL));
        items.add(new ItemStack(Material.CYAN_WOOL));
        items.add(new ItemStack(Material.LIGHT_BLUE_WOOL));
        items.add(new ItemStack(Material.BLUE_WOOL));
        items.add(new ItemStack(Material.PURPLE_WOOL));
        items.add(new ItemStack(Material.MAGENTA_WOOL));
        items.add(new ItemStack(Material.PINK_WOOL));

        return items;
    }

    private List<ItemStack> getHarness() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.WHITE_HARNESS));
        items.add(new ItemStack(Material.GRAY_HARNESS));
        items.add(new ItemStack(Material.LIGHT_GRAY_HARNESS));
        items.add(new ItemStack(Material.BLACK_HARNESS));
        items.add(new ItemStack(Material.BROWN_HARNESS));
        items.add(new ItemStack(Material.RED_HARNESS));
        items.add(new ItemStack(Material.ORANGE_HARNESS));
        items.add(new ItemStack(Material.YELLOW_HARNESS));
        items.add(new ItemStack(Material.LIME_HARNESS));
        items.add(new ItemStack(Material.GREEN_HARNESS));
        items.add(new ItemStack(Material.CYAN_HARNESS));
        items.add(new ItemStack(Material.LIGHT_BLUE_HARNESS));
        items.add(new ItemStack(Material.BLUE_HARNESS));
        items.add(new ItemStack(Material.PURPLE_HARNESS));
        items.add(new ItemStack(Material.MAGENTA_HARNESS));
        items.add(new ItemStack(Material.PINK_HARNESS));

        return items;
    }


}
