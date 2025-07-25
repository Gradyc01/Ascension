package me.depickcator.ascension.Items.ItemLists.ScavengerLists;

import me.depickcator.ascension.Items.Craftable.Unlocks.GoldenHead;
import me.depickcator.ascension.Items.Craftable.Unlocks.MakeshiftSkull;
import me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.PandoraBox;
import me.depickcator.ascension.Items.ItemLists.ItemLists;
import me.depickcator.ascension.Items.Uncraftable.HardenedSaddle;
import me.depickcator.ascension.Items.Uncraftable.NetherStar.NetherStar;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Items.Uncraftable.Skulls.CreeperHead;
import me.depickcator.ascension.Items.Uncraftable.Skulls.PlayerHead;
import me.depickcator.ascension.Items.Uncraftable.Skulls.SkeletonSkull;
import me.depickcator.ascension.Items.Uncraftable.Skulls.ZombieHead;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Input_Standard extends ItemLists {
    @Override
    protected void initItemList() {
        addItems(getPVEItems());
        addItems(getPVPItems());
    }

    private List<ItemStack> getPVPItems() {
        return getRandomItemInList(parseCustomItems(
                PlayerHead.getInstance(),
                MakeshiftSkull.getInstance(),
                GoldenHead.getInstance()
        ), 1);
    }

    private List<ItemStack> getPVEItems() {
        List<ItemStack> items = new ArrayList<>(getRandomItemInList(List.of(
                ZombieHead.getInstance().getResult(),
                SkeletonSkull.getInstance().getResult(),
                CreeperHead.getInstance().getResult(),
                NetherStar.getInstance().getResult()), 1));
        items.addAll(getRandomItemInList(parseMaterials(
                Material.NETHER_WART,
                Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                Material.NETHER_BRICKS,
                Material.SOUL_CAMPFIRE,
                Material.WARPED_FUNGUS_ON_A_STICK,
                Material.SOUL_LANTERN,
                Material.FIRE_CHARGE,
                Material.BREWING_STAND
        ), 1));
        List<ItemStack> normalItems = new ArrayList<>(parseMaterials(
                Material.NAME_TAG, Material.NETHERITE_SCRAP, Material.AMETHYST_SHARD, Material.HONEYCOMB,
                Material.GLISTERING_MELON_SLICE, Material.FERMENTED_SPIDER_EYE, Material.TROPICAL_FISH,
                Material.GHAST_TEAR, Material.ANCIENT_DEBRIS, Material.AMETHYST_BLOCK, Material.COBWEB,
                Material.SLIME_BLOCK, Material.DIAMOND_BLOCK, Material.EMERALD_BLOCK, Material.ENDER_EYE,
                Material.SPYGLASS, Material.OBSERVER, Material.MAGMA_CREAM, Material.BLAZE_POWDER, Material.HONEY_BOTTLE,
                Material.TINTED_GLASS, Material.STICKY_PISTON, Material.BEEHIVE, Material.MAGMA_BLOCK, Material.GLOW_ITEM_FRAME,
                Material.SPECTRAL_ARROW, Material.BLAZE_ROD, Material.OBSIDIAN, Material.RABBIT_FOOT, Material.CRAFTER));
        normalItems.add(HardenedSaddle.getInstance().getResult());
        items.addAll(getRandomItemInList(normalItems, 2));
        return items;
    }


}
