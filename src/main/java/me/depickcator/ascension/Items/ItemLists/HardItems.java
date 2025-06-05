package me.depickcator.ascension.Items.ItemLists;

import me.depickcator.ascension.Items.Craftable.Vanilla.Mace;
import me.depickcator.ascension.Items.Craftable.Vanilla.NetheriteAxe;
import me.depickcator.ascension.Items.Craftable.Vanilla.NetheriteSword;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HardItems extends ItemLists {

    @Override
    protected void initItemList() {
        addItems(parseMaterials(
                Material.ENCHANTED_GOLDEN_APPLE,
                Material.HONEY_BLOCK,
                Material.RESPAWN_ANCHOR,
                Material.END_CRYSTAL,
                Material.WITHER_SKELETON_SKULL,
                Material.LODESTONE,
                Material.GOAT_HORN,
                Material.WOLF_ARMOR,
                Material.HONEYCOMB_BLOCK,
                Material.SPAWNER
//                Material.SCULK_SENSOR
//                Material.NAUTILUS_SHELL
//                Material.POISONOUS_POTATO
//                Material.MUSIC_DISC_PIGSTEP
//                Material.CONDUIT
//                Material.TRIDENT
                ));
        //Mushroom Fields
        addItems(parseMaterials(Material.MYCELIUM), Biome.MUSHROOM_FIELDS);
        //Monument
        addItems(parseMaterials(Material.SPONGE, Material.SEA_LANTERN, Material.PRISMARINE_SHARD), Structure.MONUMENT);
        //Trials
        addItems(parseMaterials(Material.OMINOUS_TRIAL_KEY, Material.HEAVY_CORE), Structure.TRIAL_CHAMBERS);
        addItems(parseCustomItems(Mace.getInstance()), Structure.TRIAL_CHAMBERS);
        //Ancient City
        addItems(parseMaterials(
                Material.RECOVERY_COMPASS,
                Material.DISC_FRAGMENT_5,
                Material.ECHO_SHARD
        ), Structure.ANCIENT_CITY);
        //Lush Caves
        addItems(parseMaterials(
                Material.BIG_DRIPLEAF,
                Material.GLOW_BERRIES,
                Material.AXOLOTL_BUCKET
        ), Biome.LUSH_CAVES);
        //Desert Temple
        addItems(List.of(getRandomItemInList(getHorseArmor())), Structure.DESERT_PYRAMID);
        //Warm Ocean
        List<ItemStack> warmOceanItems = parseMaterials(Material.SEA_PICKLE);
        warmOceanItems.add(getRandomItemInList(getCoral()));
        addItems(warmOceanItems, Biome.WARM_OCEAN);

        addItems(List.of(getRandomItemInList(getWaxedCopperBlock())));
        addItems(getRandomItemInList(getNetherite(), 3));
    }

    private List<ItemStack> getWaxedCopperBlock() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.WAXED_COPPER_BLOCK));
        items.add(new ItemStack(Material.WAXED_CHISELED_COPPER));
        items.add(new ItemStack(Material.WAXED_COPPER_BULB));
        items.add(new ItemStack(Material.WAXED_COPPER_DOOR));
        items.add(new ItemStack(Material.WAXED_COPPER_GRATE));
        items.add(new ItemStack(Material.WAXED_COPPER_TRAPDOOR));
        items.add(new ItemStack(Material.WAXED_CUT_COPPER));
        items.add(new ItemStack(Material.WAXED_CUT_COPPER_SLAB));
        items.add(new ItemStack(Material.WAXED_CUT_COPPER_STAIRS));

        return items;
    }
    private List<ItemStack> getNetherite() {
        List<ItemStack> items = new ArrayList<>();
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

        return items;
    }
    private List<ItemStack> getCoral() {
        List<ItemStack> items = new ArrayList<>();
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

        return items;
    }
    private List<ItemStack> getHorseArmor() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.IRON_HORSE_ARMOR));
        items.add(new ItemStack(Material.GOLDEN_HORSE_ARMOR));
        items.add(new ItemStack(Material.DIAMOND_HORSE_ARMOR));


        return items;
    }




}
