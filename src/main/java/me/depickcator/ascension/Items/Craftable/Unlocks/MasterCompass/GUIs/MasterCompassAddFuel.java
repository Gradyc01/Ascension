//package me.depickcator.ascension.Items.Craftable.Unlocks.MasterCompass.GUIs;
//
//import me.depickcator.ascension.Player.Data.PlayerData;
//import me.depickcator.ascension.Utility.TextUtil;
//import net.kyori.adventure.text.Component;
//import org.bukkit.Material;
//import org.bukkit.event.inventory.InventoryClickEvent;
//import org.bukkit.inventory.ItemStack;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MasterCompassAddFuel extends MasterCompassGUIs {
//    private final ItemStack add1Fuel;
//    private final ItemStack add16Fuel;
//    private final ItemStack addAllFuel;
//    public MasterCompassAddFuel(PlayerData playerData, ItemStack compass) {
//        super(playerData, (char) 2, TextUtil.makeText("Add Fuel", TextUtil.AQUA), compass);
//        add1Fuel = initAddFuelButtons(1, 2);
//        add16Fuel = initAddFuelButtons(16, 4);
//        addAllFuel = initAddFuelButtons(-1, 6);
//    }
//
//    private ItemStack initAddFuelButtons(int amount, int index) {
//        String textAmount = amount == -1 ? "Everything" : "" + amount;
//        List<Component> lore = new ArrayList<>(List.of(
//                TextUtil.makeText("Adds " + textAmount + " to Compass's Fuel Supply", TextUtil.DARK_PURPLE)
//        ));
//        ItemStack item = initExplainerItem(Material.REDSTONE_TORCH, lore, TextUtil.makeText("Add " + textAmount, TextUtil.AQUA));
//        inventory.setItem(index, item);
//        return item;
//    }
//
//    @Override
//    public void interactWithGUIButtons(InventoryClickEvent event) {
//        ItemStack item = event.getCurrentItem();
//        if (item.equals(add1Fuel)) {
//
//        } else if (item.equals(add16Fuel)) {
//
//        } else if (item.equals(addAllFuel)) {
//
//        }
//    }
//}
