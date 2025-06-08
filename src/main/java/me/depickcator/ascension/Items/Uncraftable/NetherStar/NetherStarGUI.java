package me.depickcator.ascension.Items.Uncraftable.NetherStar;

import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NetherStarGUI extends AscensionMenuGUI {
    private final List<ItemStack> items;
    private final int tier;
    public NetherStarGUI(PlayerData playerData, ItemStack item) {
        super(playerData, (char) 6, TextUtil.makeText("Nether Star", TextUtil.AQUA), true);
        this.items = new ArrayList<>();
        this.tier = item.getAmount();
        makeItems();
        inventory.setItem(49, getCloseButton());
        inventory.setItem(45, initExplainer());
        setSidePanels();
    }

    private void setSidePanels() {
        ItemStack red = initExplainerItem(Material.RED_STAINED_GLASS_PANE, new ArrayList<>(), TextUtil.makeText(""));
        ItemStack green = initExplainerItem(Material.GREEN_STAINED_GLASS_PANE, new ArrayList<>(), TextUtil.makeText(""));
        int[] arr = {9, 18, 27, 36};
        for (int i = 1; i <= arr.length; i++) {
            ItemStack item = i <= tier ? green : red;
            inventory.setItem(arr[i - 1], item);
            inventory.setItem(arr[i - 1] + 8, item);
        }
    }

    private void makeItems() {
        /*Tier 1*/
        buildItem(new ItemStack(Material.IRON_INGOT, 10), 10);
        buildItem(new ItemStack(Material.COPPER_INGOT, 16), 11);
        buildItem(new ItemStack(Material.GOLD_INGOT, 6), 12);
        buildItem(new ItemStack(Material.WHEAT, 10), 13);
        buildItem(new ItemStack(Material.POTATO, 3), 14);
        buildItem(new ItemStack(Material.ROTTEN_FLESH, 4), 15);
        buildItem(new ItemStack(Material.BONE, 3), 16);
        /*Tier 2*/
        buildItem(new ItemStack(Material.LAPIS_LAZULI, 12), 19);
        buildItem(new ItemStack(Material.REDSTONE, 16), 20);
        buildItem(new ItemStack(Material.LEATHER, 3), 21);
        buildItem(new ItemStack(Material.MELON_SLICE, 3), 22);
        buildItem(new ItemStack(Material.PUMPKIN, 1), 23);
        buildItem(new ItemStack(Material.INK_SAC, 3), 24);
        buildItem(new ItemStack(Material.GUNPOWDER, 2), 25);
        /*Tier 3*/
        buildItem(new ItemStack(Material.EMERALD, 2), 28);
        buildItem(new ItemStack(Material.DIAMOND, 5), 29);
        buildItem(new ItemStack(Material.FEATHER, 5), 30);
        buildItem(new ItemStack(Material.CARROT, 8), 31);
        buildItem(new ItemStack(Material.SUGAR_CANE, 8), 32);
        buildItem(new ItemStack(Material.SPIDER_EYE, 2), 33);
        buildItem(new ItemStack(Material.ENDER_PEARL, 1), 34);
        /*Tier 4*/
        buildItem(new ItemStack(Material.ANCIENT_DEBRIS, 3), 37);
        buildItem(new ItemStack(Material.BLAZE_ROD, 1), 38);
        buildItem(new ItemStack(Material.LILY_PAD, 3), 39);
        buildItem(new ItemStack(Material.BEETROOT, 4), 40);
        buildItem(new ItemStack(Material.BROWN_MUSHROOM, 2), 41);
        buildItem(ShardOfTheFallen.getInstance().getResult(3), 42);
        buildItem(new ItemStack(Material.SLIME_BALL, 3), 43);
    }

    private ItemStack initExplainer() {
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(" Trade nether stars for materials. The ", TextUtil.DARK_PURPLE),
                TextUtil.makeText("more nether stars used at once, the higher", TextUtil.DARK_PURPLE),
                TextUtil.makeText("the tier of materials you can access", TextUtil.DARK_PURPLE)
        ));
        return initExplainerItem(Material.REDSTONE_TORCH, lore, TextUtil.makeText("Information", TextUtil.RED));
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item.equals(getCloseButton())) {
            event.setCancelled(true);
            player.closeInventory();
        } else if (items.contains(item)) {
            int itemTier = event.getSlot()/9;
            if (itemTier <= tier && isHolding(NetherStar.getInstance().getResult())) {
                ItemStack mainHand = player.getInventory().getItemInMainHand();
                if (mainHand.getAmount() >= itemTier) {
                    event.setCancelled(true);
                    player.closeInventory();
                    mainHand.setAmount(mainHand.getAmount() - itemTier);
                    player.sendMessage(TextUtil.makeText("Trade Successful!", TextUtil.GREEN));
                    SoundUtil.playHighPitchPling(player);
                    PlayerUtil.giveItem(player, item);
                } else {
                    TextUtil.errorMessage(player, "Not enough Nether Stars to make the trade!");
                }
            } else {
                TextUtil.errorMessage(player, "You do not have the nether stars to make the trade!");
            }
        }
    }

    private void buildItem(ItemStack item, int slot) {
        inventory.setItem(slot, item);
        items.add(item);
    }
}
