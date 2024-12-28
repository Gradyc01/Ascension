package me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem;

import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.Boxes.*;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PandoraBoxGUI extends AscensionGUI {
    private final HashMap<ItemStack, PandoraBoxes> boxes;
    private final ItemStack claimBox;
    private final PandoraBoxes displayBox;
    private final boolean displayConstant;
    private final int[] displaySlots = {20, 21, 22, 23, 24, 29, 30, 31, 32, 33};
    private final int[] displayBorder = {10, 11, 12, 13, 14, 15, 16, 19, 25, 28, 34, 37, 38, 39, 40, 41, 42, 43};
    private final ItemStack switchDisplay;
    private final int displayIndicator;
    public PandoraBoxGUI(PlayerData playerData) {
        this(playerData, new Village(), true, 4);
    }

    public PandoraBoxGUI(PlayerData playerData, PandoraBoxes displayBox, boolean displayConstant, int displayIndicator) {
        super(playerData, (char) 6, TextUtil.makeText("Pandora's Box", TextUtil.AQUA), true);
        boxes = initBoxes();
        claimBox = initClaimItem();
        inventory.setItem(49, claimBox);

        this.displayIndicator = displayIndicator;

        this.displayBox = displayBox;
        this.displayConstant = displayConstant;
        initContentDisplay();
        initDisplayBorder();
        inventory.setItem(45, initExplainerItem());
        switchDisplay = initSwitchDisplayItem();
        inventory.setItem(26, switchDisplay);
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        if (!isHolding(PandoraBox.getInstance().getResult())) {
            event.setCancelled(true);
            player.closeInventory();
            return;
        }

        if (boxes.containsKey(item)) {
            new PandoraBoxGUI(playerData, boxes.get(item), displayConstant, event.getSlot());
        } else if (item.equals(claimBox)) {
            Block b = player.getWorld().getBlockAt(player.getLocation());
            b.setType(Material.CHEST);
            player.teleport(player.getLocation().add(0, 2, 0));
            player.getInventory().getItemInMainHand().setAmount(0);
            SoundUtil.playHighPitchPling(player);
//            BlockState state = block.getState();
//            Chest chest = (Chest) state;
            displayBox.populateLoot(((Chest) b.getState()).getBlockInventory(), new Random(), 1);
        } else if (item.equals(switchDisplay)) {
            new PandoraBoxGUI(playerData, displayBox, !displayConstant, displayIndicator);
        }
    }

    private HashMap<ItemStack, PandoraBoxes> initBoxes() {
        HashMap<ItemStack, PandoraBoxes> boxes = new HashMap<>();
        initBox(new OverWorld(), 2, boxes);
        initBox(new Underground(), 3, boxes);
        initBox(new Village(), 4, boxes);
        initBox(new Rejuvenation(), 5, boxes);
        initBox(new Hell(), 6, boxes);

        return boxes;
    }

    private ItemStack initClaimItem() {
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Claim this Pandora's Box", TextUtil.DARK_PURPLE)
        ));
        Component name = TextUtil.makeText("Claim Box", TextUtil.GREEN);
        return initExplainerItem(Material.LIME_WOOL, lore, name);
    }

    private ItemStack initSwitchDisplayItem() {
        String contentType = !displayConstant ? "Constant" : "Special";
        Material contentMaterial = !displayConstant ? Material.LIME_STAINED_GLASS_PANE : Material.BLUE_STAINED_GLASS_PANE;
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Switch to displaying " + contentType + " loot", TextUtil.DARK_PURPLE)
        ));
        Component name = TextUtil.makeText("Display " + contentType + " loot", TextUtil.GREEN);
        return initExplainerItem(contentMaterial, lore, name);
    }

    private void initContentDisplay() {
        List<ItemStack> items = displayConstant ? displayBox.getConstantItems() : displayBox.getSpecialItems();
        for (int i : displaySlots) {
            ItemStack item = new ItemStack(Material.AIR);
            inventory.setItem(i, item);
        }
        for (int i = 0; i < displaySlots.length && i < items.size(); i++) {
//            ItemStack item = new ItemStack(items.get(i).getType());
//            inventory.setItem(displaySlots[i], item);
            ItemStack item = items.get(i).clone();

            if (item.getItemMeta() instanceof EnchantmentStorageMeta) {
                item = new ItemStack(Material.ENCHANTED_BOOK);
            }
            item.setAmount(1);
            inventory.setItem(displaySlots[i], item);
        }
    }

    private void initDisplayBorder() {
        ItemStack item = initExplainerItem(Material.BLACK_STAINED_GLASS_PANE, new ArrayList<>(), TextUtil.makeText(""));
        for (int i : displayBorder) {
            if (i - 9 == displayIndicator) {
                inventory.setItem(i,
                        initExplainerItem(Material.LIME_STAINED_GLASS_PANE, new ArrayList<>(), TextUtil.makeText("")));
            } else {
                inventory.setItem(i, item);
            }
        }
    }

    private ItemStack initExplainerItem() {
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("  Claiming this box would grant", TextUtil.DARK_PURPLE),
                TextUtil.makeText("all constant items and potentially", TextUtil.DARK_PURPLE),
                TextUtil.makeText("give some special items too", TextUtil.DARK_PURPLE)
        ));
        Component name = TextUtil.makeText("Information", TextUtil.RED);
        return initExplainerItem(Material.REDSTONE_TORCH, lore, name);
    }


    private void initBox(PandoraBoxes box, int index, HashMap<ItemStack, PandoraBoxes> boxes) {
        ItemStack item = new ItemStack(box.getIcon());
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(0xFFF000);
        meta.setMaxStackSize(1);
        meta.displayName(TextUtil.makeText(box.getDisplayName(), TextUtil.AQUA));
        item.setItemMeta(meta);

        boxes.put(item, box);
        inventory.setItem(index, item);
    }


}
