package me.depickcator.ascension.Timeline.Events.SoulShop;

import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.MainMenuUI.MainMenuGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class SoulShopGUI extends AscensionMenuGUI {
    private final Shop shop;
    private final int GUIState;
    private final Map<ItemStack, UUID> shopIcons;
    private final static int[] innerBorder = {47, 46, 37, 28, 19, 10, 11, 12, 13, 14, 15, 16, 25, 34, 43, 52, 51};
    private final static int[] outerBorder = {45, 36, 27, 18, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 26, 35, 44, 53};
    public SoulShopGUI(PlayerData playerData, boolean successfulPurchase) {
        super(playerData, (char) 6, TextUtil.makeText("Soul Shop", TextUtil.AQUA), true);
        shop = plugin.getSettingsUI().getSettings().getTimeline().getSoulShops().getPlayerShop(playerData);
        GUIState = shop == null ? -1 : 1;
        shopIcons = new HashMap<>();
        playerHeadButton(49);
        inventory.setItem(48, goBackItem());
        inventory.setItem(50, initExplainerItem());
        setDisplayBorder(Material.BLUE_STAINED_GLASS_PANE, innerBorder);
        createShopIcons();
        if (successfulPurchase) {
            purchaseAnimation();
        }
    }

    public SoulShopGUI(PlayerData playerData) {
        this(playerData, false);
    }

    private void setDisplayBorder(Material material, int[] border) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(""));
        item.setItemMeta(meta);
        for (int i : border) {
            inventory.setItem(i, item);
        }
    }

    private void createShopIcons() {
        if (GUIState != 1) return;
        shopIcons.clear();
        int index = 20;
        for (Map.Entry<UUID, ItemStack> entry : shop.getIconMappings().entrySet()) {
            inventory.setItem(index++, entry.getValue());
            shopIcons.put(entry.getValue(), entry.getKey());
            if (index % 9 == 7) index += 4;
        }
    }

    private ItemStack initExplainerItem() {
        List<Component> lore = List.of(
                TextUtil.makeText("May be replenished with resources that are ", TextUtil.DARK_PURPLE),
                TextUtil.makeText("incredibly valuable to the player", TextUtil.DARK_PURPLE),
                TextUtil.makeText("Each shop is unique to the player", TextUtil.RED)
        );
        return initExplainerItem(Material.REDSTONE_TORCH, lore, TextUtil.makeText("Info", TextUtil.GREEN));
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null || !event.isLeftClick()) return;
        if (item.equals(goBackItem())) {
            new MainMenuGUI(playerData);
        } else if (GUIState == 1 && shopIcons.containsKey(item)) {
            UUID uuid = shopIcons.get(item);
            if (shop.purchaseItem(uuid)) {
                new SoulShopGUI(playerData, true);
            }
        }
    }

    private void purchaseAnimation() {
        new BukkitRunnable() {
            int i = 0;
            float pitch = 0.5f;
            ItemStack item = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
            ItemStack item2 = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            @Override
            public void run() {
                if (i >= outerBorder.length && i >= innerBorder.length) {
                    cancel();
                    setDisplayBorder(Material.GRAY_STAINED_GLASS_PANE, outerBorder);
                    setDisplayBorder(Material.BLUE_STAINED_GLASS_PANE, innerBorder);
                    return;
                }
                if (i < outerBorder.length) {
                    int index = outerBorder[i];
                    int index2 = outerBorder[outerBorder.length - i - 1];
                    inventory.setItem(index, item);
                    inventory.setItem(index2, item);
                }
                if (i < innerBorder.length) {
                    int index = innerBorder[i];
                    int index2 = innerBorder[innerBorder.length - i - 1];
                    inventory.setItem(index, item2);
                    inventory.setItem(index2, item2);
                }
                i++;

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, pitch);
                pitch += 0.1f;

            }
        }.runTaskTimer(plugin, 1L, 2L);
    }
}
