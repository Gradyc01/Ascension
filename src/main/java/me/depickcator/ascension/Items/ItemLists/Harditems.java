package me.depickcator.ascension.Items.ItemLists;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.LocationChecker.LocationCheck;
import me.depickcator.ascension.Items.Craftable.Vanilla.NetheriteAxe;
import me.depickcator.ascension.Items.Craftable.Vanilla.NetheriteSword;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Harditems {
    private ArrayList<ItemStack> items;
    private final Random rand;
    private final LocationCheck locationCheck;
    public Harditems() {
        items = new ArrayList<>();
        rand = new Random();
        locationCheck = Ascension.getInstance().getLocationCheck();
        setItems();
    }

    private void setItems() {
        items.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        if (locationCheck.isALocation(Biome.MUSHROOM_FIELDS)) items.add(new ItemStack(Material.MYCELIUM));

        //Ocean Temple
        if (locationCheck.isALocation(Structure.MONUMENT)) {
            items.add(new ItemStack(Material.SPONGE));
            items.add(new ItemStack(Material.SEA_LANTERN));
        }

        //Trials
        if (locationCheck.isALocation(Structure.TRIAL_CHAMBERS)) {
            items.add(new ItemStack(Material.OMINOUS_TRIAL_KEY));
//            items.add(new ItemStack(Material.MACE));//TODO: Maybe Change Later to Custom
            items.add(new ItemStack(Material.HEAVY_CORE));
        }

        items.add(new ItemStack(Material.HONEY_BLOCK));
        items.add(new ItemStack(Material.RESPAWN_ANCHOR));

        items.add(new ItemStack(Material.END_CRYSTAL));
        items.add(new ItemStack(Material.WITHER_SKELETON_SKULL));

//        items.add(new ItemStack(Material.POISONOUS_POTATO));

        items.add(new ItemStack(Material.LODESTONE));
//        items.add(new ItemStack(Material.SCULK_SENSOR));

//        items.add(new ItemStack(Material.NAUTILUS_SHELL));
        items.add(new ItemStack(Material.GOAT_HORN));

        items.add(new ItemStack(Material.WOLF_ARMOR));

        //Ancient City
        if (locationCheck.isALocation(Structure.ANCIENT_CITY)) {
            items.add(new ItemStack(Material.RECOVERY_COMPASS));
            items.add(new ItemStack(Material.DISC_FRAGMENT_5));
            items.add(new ItemStack(Material.ECHO_SHARD));
        }

        //Lush Caves
        if (locationCheck.isALocation(Biome.LUSH_CAVES)) {
            items.add(new ItemStack(Material.BIG_DRIPLEAF));
            items.add(new ItemStack(Material.GLOW_BERRIES));
            items.add(new ItemStack(Material.AXOLOTL_BUCKET));
        }


//        items.add(new ItemStack(Material.MUSIC_DISC_PIGSTEP));

//        items.add(new ItemStack(Material.CONDUIT));
        items.add(new ItemStack(Material.HONEYCOMB_BLOCK));
//        items.add(new ItemStack(Material.TRIDENT)); //TODO: Maybe Change Later to Custom


        if (locationCheck.isALocation(Structure.DESERT_PYRAMID)) items.add(getRandomHorseArmor());

        //Warm Ocean
        if (locationCheck.isALocation(Biome.WARM_OCEAN)) {
            items.add(getRandomCoral());
            items.add(new ItemStack(Material.SEA_PICKLE));
        }
        items.add(getRandomWaxedCopperBlock());
        items.addAll(getRandomNetherite());

    }

    private ItemStack getRandomWaxedCopperBlock() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.WAXED_COPPER_BLOCK));
        items.add(new ItemStack(Material.WAXED_CHISELED_COPPER));
        items.add(new ItemStack(Material.WAXED_COPPER_BULB));
        items.add(new ItemStack(Material.WAXED_COPPER_DOOR));
        items.add(new ItemStack(Material.WAXED_COPPER_GRATE));
        items.add(new ItemStack(Material.WAXED_COPPER_TRAPDOOR));
        items.add(new ItemStack(Material.WAXED_CUT_COPPER));
        items.add(new ItemStack(Material.WAXED_CUT_COPPER_SLAB));
        items.add(new ItemStack(Material.WAXED_CUT_COPPER_STAIRS));

        int pick = rand.nextInt(items.size());
        return items.get(pick);
    }

    private List<ItemStack> getRandomNetherite() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.NETHERITE_HELMET));
        items.add(new ItemStack(Material.NETHERITE_CHESTPLATE));
        items.add(new ItemStack(Material.NETHERITE_LEGGINGS));
        items.add(new ItemStack(Material.NETHERITE_BOOTS));
        items.add(NetheriteAxe.getInstance().getResult());
        items.add(NetheriteSword.getInstance().getResult());
        items.add(new ItemStack(Material.NETHERITE_PICKAXE));
        items.add(new ItemStack(Material.NETHERITE_SHOVEL));
        items.add(new ItemStack(Material.NETHERITE_HOE));
        items.add(new ItemStack(Material.NETHERITE_INGOT));

        Collections.shuffle(items);
        return new ArrayList<>(List.of(
                items.getFirst(),
                items.getLast(),
                items.get(2)
        ));
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
