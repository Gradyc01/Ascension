package org.bingo4.Items.ItemLists.ScavengerLists;

import org.bingo4.Items.Craftable.Unlocks.*;
import org.bingo4.Items.Craftable.Unlocks.MasterCompass.MasterCompass;
import org.bingo4.Items.ItemLists.ItemLists;
import org.bingo4.Items.Uncraftable.NetherStar.NetherStar;
import org.bingo4.Items.Uncraftable.RejuvenationBook;
import org.bingo4.Items.Uncraftable.ShardOfTheFallen;
import org.bingo4.Items.Uncraftable.Skulls.PlayerHead;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Input_Quickplay extends ItemLists {
    @Override
    protected void initItemList() {
        addItems(getPVEItems());
        addItems(getPVPItems());
    }

    private List<ItemStack> getPVPItems() {
        List<ItemStack> items = new ArrayList<>();
        items.addAll(getRandomItemInList(parseCustomItems(
                PlayerHead.getInstance(),
                MakeshiftSkull.getInstance(),
                GoldenHead.getInstance(),
                NetherStar.getInstance(),
                ShardOfTheFallen.getInstance()
        ), 1));
        items.addAll(getRandomItemInList(parseCustomItems(
                PhilosopherPickaxe.getInstance(),
                VorpalSword.getInstance(),
                KingsRod.getInstance(),
                ApprenticeHelmet.getInstance(),
                MasterCompass.getInstance(),
                Tarnhelm.getInstance(),
                RejuvenationBook.getInstance()
        ), 1));
        return items;
    }

    private List<ItemStack> getPVEItems() {
        List<ItemStack> items = new ArrayList<>();
        items.addAll(getRandomItemInList(parseMaterials(
                Material.CRAFTER,
                Material.ANVIL,
                Material.LECTERN,
                Material.GOLD_BLOCK,
                Material.JACK_O_LANTERN,
                Material.DIAMOND,
//                Material.BUNDLE,
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
                Material.DAYLIGHT_DETECTOR,
                Material.GLOWSTONE,
//                Material.BRICK_SLAB, //Todo: Maybe
                Material.EXPERIENCE_BOTTLE,
                Material.DRIED_KELP_BLOCK,
                Material.ENDER_PEARL,
                Material.FIREWORK_STAR,
                Material.FIREWORK_ROCKET,
                Material.CLOCK,
                Material.SUNFLOWER,
                Material.WILDFLOWERS,
                Material.REDSTONE_LAMP,
                Material.LEAD
        ), 3));
        return items;
    }


}
