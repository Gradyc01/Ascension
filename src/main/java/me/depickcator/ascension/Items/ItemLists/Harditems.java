package me.depickcator.ascension.Items.ItemLists;

import me.depickcator.ascension.Items.Craftable.Vanilla.NetheriteAxe;
import me.depickcator.ascension.Items.Craftable.Vanilla.NetheriteSword;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class Harditems {
    private ArrayList<ItemStack> items;
    private final Random rand;
    public Harditems() {
        items = new ArrayList<>();
        rand = new Random();
        setItems();
    }

    private void setItems() {
        items.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        items.add(new ItemStack(Material.MYCELIUM));
        items.add(new ItemStack(Material.SEA_LANTERN));
        items.add(new ItemStack(Material.HONEY_BLOCK));
        items.add(new ItemStack(Material.SPONGE));
        items.add(new ItemStack(Material.RESPAWN_ANCHOR));
        items.add(new ItemStack(Material.GLOW_BERRIES));
        items.add(new ItemStack(Material.END_CRYSTAL));
        items.add(new ItemStack(Material.WITHER_SKELETON_SKULL));
        items.add(new ItemStack(Material.BIG_DRIPLEAF));
        items.add(new ItemStack(Material.POISONOUS_POTATO));
        items.add(new ItemStack(Material.NAME_TAG)); //TODO: Change to Medium?
        items.add(new ItemStack(Material.LODESTONE));
        items.add(new ItemStack(Material.SCULK_SENSOR));
        items.add(new ItemStack(Material.AXOLOTL_BUCKET));
        items.add(new ItemStack(Material.NAUTILUS_SHELL));
        items.add(new ItemStack(Material.GOAT_HORN));
        items.add(new ItemStack(Material.SADDLE)); //TODO: Change to Medium?
        items.add(new ItemStack(Material.WOLF_ARMOR));
        items.add(new ItemStack(Material.SEA_PICKLE));
        items.add(new ItemStack(Material.RECOVERY_COMPASS));
        items.add(new ItemStack(Material.MUSIC_DISC_PIGSTEP));
        items.add(new ItemStack(Material.HEAVY_CORE));
        items.add(new ItemStack(Material.OMINOUS_TRIAL_KEY));
        items.add(new ItemStack(Material.WAXED_COPPER_GRATE)); //Change to a list later
        items.add(new ItemStack(Material.CONDUIT));
        items.add(new ItemStack(Material.HONEYCOMB_BLOCK));
        items.add(new ItemStack(Material.MACE));//TODO: Maybe Change Later to Custom
        items.add(new ItemStack(Material.TRIDENT)); //TODO: Maybe Change Later to Custom


        items.add(getRandomHorseArmor());
        items.add(getRandomCoral());
        items.add(getRandomNetherite());
        items.add(getRandomNetherite());
        items.add(getRandomNetherite());

    }

    private ItemStack getRandomNetherite() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.NETHERITE_HELMET));
        items.add(new ItemStack(Material.NETHERITE_CHESTPLATE));
        items.add(new ItemStack(Material.NETHERITE_LEGGINGS));
        items.add(new ItemStack(Material.NETHERITE_BOOTS));
//        items.add(new ItemStack(Material.NETHERITE_SWORD));
//        items.add(new ItemStack(Material.NETHERITE_AXE));
        items.add(NetheriteAxe.getInstance().getResult());
        items.add(NetheriteSword.getInstance().getResult());
        items.add(new ItemStack(Material.NETHERITE_PICKAXE));
        items.add(new ItemStack(Material.NETHERITE_SHOVEL));
        items.add(new ItemStack(Material.NETHERITE_HOE));
        items.add(new ItemStack(Material.NETHERITE_INGOT));

        int pick = rand.nextInt(items.size());

        return items.get(pick);
    }

    private ItemStack getRandomCoral() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.DEAD_BRAIN_CORAL));
        items.add(new ItemStack(Material.DEAD_BRAIN_CORAL_FAN));
        items.add(new ItemStack(Material.DEAD_BRAIN_CORAL_BLOCK));

        items.add(new ItemStack(Material.DEAD_BUBBLE_CORAL));
        items.add(new ItemStack(Material.DEAD_BUBBLE_CORAL_FAN));
        items.add(new ItemStack(Material.DEAD_BUBBLE_CORAL_BLOCK));

        items.add(new ItemStack(Material.DEAD_FIRE_CORAL));
        items.add(new ItemStack(Material.DEAD_FIRE_CORAL_FAN));
        items.add(new ItemStack(Material.DEAD_FIRE_CORAL_BLOCK));

        items.add(new ItemStack(Material.DEAD_HORN_CORAL));
        items.add(new ItemStack(Material.DEAD_HORN_CORAL_FAN));
        items.add(new ItemStack(Material.DEAD_HORN_CORAL_BLOCK));

        items.add(new ItemStack(Material.DEAD_TUBE_CORAL));
        items.add(new ItemStack(Material.DEAD_TUBE_CORAL_FAN));
        items.add(new ItemStack(Material.DEAD_TUBE_CORAL_BLOCK));

        int pick = rand.nextInt(items.size());

        return items.get(pick);
    }
    private ItemStack getRandomHorseArmor() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.IRON_HORSE_ARMOR));
        items.add(new ItemStack(Material.GOLDEN_HORSE_ARMOR));
        items.add(new ItemStack(Material.DIAMOND_HORSE_ARMOR));

        int pick = rand.nextInt(items.size());

        return items.get(pick);
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }



}
