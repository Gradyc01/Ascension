package me.depickcator.ascensionBingo.Items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class EasyItems {
    private ArrayList<ItemStack> items;
    public EasyItems() {
        items = new ArrayList<>();
        setItems();
    }

    private void setItems() {
        addItem(new ItemStack(Material.LIGHTNING_ROD));
        addItem(new ItemStack(Material.ANVIL));
        addItem(new ItemStack(Material.LECTERN));
        addItem(new ItemStack(Material.LAPIS_BLOCK));
//        addItem(new ItemStack(Material.ENCHANTED_BOOK));
        addItem(new ItemStack(Material.REDSTONE_BLOCK));
        addItem(new ItemStack(Material.GOLD_BLOCK));
        addItem(new ItemStack(Material.JACK_O_LANTERN));
        addItem(new ItemStack(Material.EGG));
        addItem(new ItemStack(Material.ORANGE_STAINED_GLASS));
        addItem(new ItemStack(Material.SNOWBALL));
        addItem(new ItemStack(Material.GOLDEN_CARROT));
        addItem(new ItemStack(Material.BAKED_POTATO));
        addItem(new ItemStack(Material.TARGET));
        addItem(new ItemStack(Material.BELL));
        addItem(new ItemStack(Material.CARROT_ON_A_STICK));
        addItem(new ItemStack(Material.BEETROOT));
        addItem(new ItemStack(Material.MUD_BRICKS));
        addItem(new ItemStack(Material.CUT_COPPER));
        addItem(new ItemStack(Material.MILK_BUCKET));
        addItem(new ItemStack(Material.TNT));
        addItem(new ItemStack(Material.PUFFERFISH));
        addItem(new ItemStack(Material.COD_BUCKET));
        addItem(new ItemStack(Material.SALMON_BUCKET));
        addItem(new ItemStack(Material.DAYLIGHT_DETECTOR));
        addItem(new ItemStack(Material.GLOWSTONE));
        addItem(new ItemStack(Material.BRICK_SLAB));
        addItem(new ItemStack(Material.EXPERIENCE_BOTTLE));
        addItem(new ItemStack(Material.BONE_BLOCK));
        addItem(new ItemStack(Material.DRIED_KELP_BLOCK));
        addItem(new ItemStack(Material.FLOWER_POT));
        addItem(new ItemStack(Material.ENDER_PEARL));
        addItem(new ItemStack(Material.FIREWORK_STAR));
        addItem(new ItemStack(Material.FIREWORK_ROCKET));
        addItem(new ItemStack(Material.CHAIN));
        addItem(new ItemStack(Material.CAMPFIRE));
        addItem(new ItemStack(Material.CLOCK));
        addItem(new ItemStack(Material.BROWN_BANNER));
        addItem(new ItemStack(Material.BRUSH));
        addItem(getRandomGlass());
        addItem(getRandomBanner());
    }

    private void addItem(ItemStack item) {
        items.add(item);
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

    private ItemStack getRandomGlass() {
        ArrayList<ItemStack> items = new ArrayList<>();
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

        int pick = (int) Math.round(Math.random() * items.size());

        return items.get(pick);
    }

    private ItemStack getRandomBanner() {
        ArrayList<ItemStack> items = new ArrayList<>();
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

        int pick = (int) Math.round(Math.random() * items.size());

        return items.get(pick);
    }


}
