package me.depickcator.ascension.Items.ItemLists.ScavengerLists;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Echolocator;
import me.depickcator.ascension.Items.Craftable.Unlocks.GhostItem.Ghost;
import me.depickcator.ascension.Items.Craftable.Unlocks.RedLedgerItem.RedLedger;
import me.depickcator.ascension.Items.Craftable.Unlocks.TeamPortalItem.TeamPortal;
import me.depickcator.ascension.Items.ItemLists.HardItems;
import me.depickcator.ascension.Items.ItemLists.ItemLists;
import me.depickcator.ascension.Items.Uncraftable.ToolVoucher.ToolVoucher;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Output_Standard extends ItemLists {
    @Override
    protected void initItemList() {
        addItems(getPVEItems());
        addItems(getPVPItems());
    }

    private List<ItemStack> getPVPItems() {
        List<ItemStack> items = new ArrayList<>();
        items.addAll(getRandomItemInList(parseCustomItems(
                TeamPortal.getInstance(),
                RedLedger.getInstance(),
                Echolocator.getInstance(),
                Ghost.getInstance(),
                AscensionKey.getInstance(),
                CubeConverter.getInstance(),
                TabletsOfDestiny.getInstance()
        ), 1));
        items.addAll(getRandomItemInList(parseCustomItems(
                DragonArmor.getInstance(),
                HideOfLeviathan.getInstance(),
                SevenLeagueBoots.getInstance(),
                Exodus.getInstance(),
                WingsOfIcarus.getInstance(),
                ApolloGlare.getInstance(),
                HeliosCurse.getInstance(),
                CupidBow.getInstance(),
                MinerBlessing.getInstance(),
                ToolVoucher.getInstance()
        ), 1));
        items.addAll(getRandomItemInList(List.of(
                Nectar.getInstance().getResult(),
                Cornucopia.getInstance().getResult(),
                Panacea.getInstance().getResult(),
                Resurrection.getInstance().getResult(),
                new ItemStack(Material.GOLDEN_APPLE, 12),
                new ItemStack(Material.GOLDEN_APPLE, 8),
                new ItemStack(Material.ENCHANTED_GOLDEN_APPLE)
        ), 1));
        return items;
    }

    private List<ItemStack> getPVEItems() {
        List<ItemStack> items = new ArrayList<>();
        List<ItemStack> hardItems = new HardItems().getItems();
        Random r = new Random();
        for (ItemStack item : Ascension.getInstance().getBingoData().getItems()) {
            if (hardItems.contains(item)) {
                    items.add(item);
            }
        }
        while (items.size() < 2) {
            items.add(hardItems.get(r.nextInt(hardItems.size())));
        }

        return getRandomItemInList(items, 2);
    }


}
