package me.depickcator.ascension.Items.ItemLists.ScavengerLists;

import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import me.depickcator.ascension.Items.Craftable.Unlocks.MasterCompass.MasterCompass;
import me.depickcator.ascension.Items.ItemLists.ItemLists;
import me.depickcator.ascension.Items.Uncraftable.NetherStar.NetherStar;
import me.depickcator.ascension.Items.Uncraftable.RejuvenationBook;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Items.Uncraftable.Skulls.CreeperHead;
import me.depickcator.ascension.Items.Uncraftable.Skulls.PlayerHead;
import me.depickcator.ascension.Items.Uncraftable.Skulls.SkeletonSkull;
import me.depickcator.ascension.Items.Uncraftable.Skulls.ZombieHead;
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
                Material.REDSTONE_LAMP
        ), 3));
        return items;
    }


}
