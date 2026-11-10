package org.bingo4.Items.ItemLists.ScavengerLists;

import org.bingo4.Bingo4;
import org.bingo4.Items.Craftable.Unlocks.*;
import org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.Echolocator;
import org.bingo4.Items.Craftable.Unlocks.GhostItem.Ghost;
import org.bingo4.Items.Craftable.Unlocks.RedLedgerItem.RedLedger;
import org.bingo4.Items.Craftable.Unlocks.TeamPortalItem.TeamPortal;
import org.bingo4.Items.ItemLists.HardItems;
import org.bingo4.Items.ItemLists.ItemLists;
import org.bingo4.Items.Uncraftable.ToolVoucher.ToolVoucher;
import org.bingo4.MainMenuUI.BingoBoard.BingoSlot;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Output_Quickplay extends ItemLists {
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
                WingsOfIcarus.getInstance()
        ), 1));
        items.addAll(getRandomItemInList(parseCustomItems(
                ApolloGlare.getInstance(),
                HeliosCurse.getInstance(),
                CupidBow.getInstance(),
                MinerBlessing.getInstance(),
                Poseidon.getInstance(),
                ToolVoucher.getInstance(),
                MakeshiftMace.getInstance()
        ), 1));
        items.addAll(getRandomItemInList(List.of(
                Nectar.getInstance().getResult(),
                Cornucopia.getInstance().getResult(),
                Panacea.getInstance().getResult(),
                Resurrection.getInstance().getResult(),
                new ItemStack(Material.GOLDEN_APPLE, 12),
                new ItemStack(Material.GOLDEN_APPLE, 8)
        ), 1));
        return items;
    }

    private List<ItemStack> getPVEItems() {
        List<ItemStack> items = new ArrayList<>();
        List<ItemStack> hardItems = new HardItems().getItems();
        for (BingoSlot slot : Bingo4.getInstance().getBingoData().getBingoSlots()) {
            if (hardItems.contains(slot.getItem())) {
                    items.add(slot.getItem());
            }
        }
        while (items.isEmpty()) {
            items.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        }

        return getRandomItemInList(items, 1);
    }


}
