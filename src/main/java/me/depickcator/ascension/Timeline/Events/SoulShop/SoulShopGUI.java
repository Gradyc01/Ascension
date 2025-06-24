package me.depickcator.ascension.Timeline.Events.SoulShop;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.MainMenuUI.MainMenuGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SoulShopGUI extends AscensionMenuGUI {
    private final Shop shop;
    private final int GUIState;
    private final Map<ItemStack, UUID> shopIcons;
    private final static int[] displayBorder= {47, 46, 37, 28, 19, 10, 11, 12, 13, 14, 15, 16, 25, 34, 43, 52, 51};
    public SoulShopGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Soul Shop", TextUtil.AQUA), true);
        shop = plugin.getSettingsUI().getSettings().getTimeline().getSoulShops().getPlayerShop(playerData);
        GUIState = shop == null ? -1 : 1;
        shopIcons = new HashMap<>();
        playerHeadButton(49);
        inventory.setItem(48, goBackItem());
        setDisplayBorder(Material.BLUE_STAINED_GLASS_PANE);
        createShopIcons();
    }

    private void setDisplayBorder(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(""));
        item.setItemMeta(meta);
        for (int i : displayBorder) {
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


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (item.equals(goBackItem())) {
            new MainMenuGUI(playerData);
        } else if (GUIState == 1 && shopIcons.containsKey(item)) {
            UUID uuid = shopIcons.get(item);
            if (shop.purchaseItem(uuid)) {
                new SoulShopGUI(playerData);
            }
        }
    }
}
