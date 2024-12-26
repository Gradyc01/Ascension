package me.depickcator.ascension.MainMenu.Map;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.MainMenu.MainMenuGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MapGUI extends AscensionGUI {
    private final MapItems mapItems;
    public MapGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Map", TextUtil.AQUA), true);
//        airLines(2);
//        airLines(3);
        mapItems = Ascension.getInstance().getTimeline().getMapItems();
        mapMapItems();
        inventory.setItem(48, goBackItem());
        playerHeadButton(49);
    }

//    private void airLines(int row) {
//        for (int i = row * 9; i < row * 9 + 9; i++) {
//            inventory.setItem(row, new ItemStack(Material.AIR));
//        }
//    }
    private void mapMapItems() {
        int carePackageIndex = 0;
        int scavengerIndex = 26;
        for (MapItem mapItem : mapItems.getMapItems()) {
            switch (mapItem.getType()) {
                case MapItem.SPAWN -> {
                    inventory.setItem(22, makeSquare(mapItem, Material.GREEN_STAINED_GLASS_PANE));
                }
                case MapItem.CARE_PACKAGE -> {
                    inventory.setItem(carePackageIndex, makeSquare(mapItem, Material.CHEST));
                    carePackageIndex+=9;
                }
                case MapItem.ASCENSION -> {
                    int index;
                    Pair<Integer, Integer> coords = mapItem.getCoords();
                    index = coords.getLeft() > Ascension.getSpawn().getBlockX() ? 2 : 6;
                    index = coords.getRight() > Ascension.getSpawn().getBlockZ() ? index : index + 36;
                    inventory.setItem(index, makeSquare(mapItem, Material.REINFORCED_DEEPSLATE));
                }
                case MapItem.SCAVENGER -> {
                    inventory.setItem(scavengerIndex, makeSquare(mapItem, Material.FEATHER));
                    scavengerIndex+=9;
                }
            }
        }
    }

    private ItemStack makeSquare(MapItem mapItem, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(mapItem.getName(), TextUtil.DARK_PURPLE));
        Pair<Integer, Integer> coords = mapItem.getCoords();
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Coordinates: ", TextUtil.DARK_PURPLE),
                TextUtil.makeText("   (" + coords.getLeft() + ", " + coords.getRight() + ") ", TextUtil.GOLD)
        ));
        meta.lore(lore);
        meta.setEnchantmentGlintOverride(true);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (item.equals(goBackItem())) {
            new MainMenuGUI(playerData);
        }
    }
}
