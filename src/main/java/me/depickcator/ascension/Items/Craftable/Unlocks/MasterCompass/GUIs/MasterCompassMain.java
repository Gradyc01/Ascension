package me.depickcator.ascension.Items.Craftable.Unlocks.MasterCompass.GUIs;

import me.depickcator.ascension.Items.Craftable.Unlocks.MasterCompass.MasterCompass;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MasterCompassMain extends MasterCompassGUIs {
//    private final ItemStack addFuelButton;
    private final ItemStack trackSpecificOpponent;
    private final ItemStack trackNearestOpponent;
    public MasterCompassMain(PlayerData playerData, ItemStack compass) {
        super(playerData, (char) 3, TextUtil.makeText("Select An Option", TextUtil.AQUA), compass);
//        addFuelButton = initExplainer(Material.REDSTONE, 11,"Add Fuel");
        trackSpecificOpponent = initExplainer(Material.NAME_TAG, 12,"Choose a Target", trackSpecificPlayerCost);
        trackNearestOpponent = initExplainer(Material.RECOVERY_COMPASS, 14,"Nearest Target", trackNearestPlayerCost);
    }

    private ItemStack initExplainer(Material material, int index, String title, int cost) {
//        return initExplainerItem(Material.REDSTONE, new ArrayList<>(), TextUtil.makeText("Add Fuel", TextUtil.RED));
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Cost: ", TextUtil.DARK_PURPLE)
                        .append(TextUtil.makeText(cost +"", TextUtil.GOLD))
                        .append(TextUtil.makeText(" Souls", TextUtil.DARK_PURPLE))
        ));
        ItemStack item = initExplainerItem(material, lore, TextUtil.makeText(title, TextUtil.RED));
        inventory.setItem(index, item);
        return item;
    }



    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();

        if (isHolding(MasterCompass.getInstance().getResult())) {
            if (item.equals(trackSpecificOpponent)) {
                new MasterCompassTrackSpecificPlayer(playerData, player.getInventory().getItemInMainHand());
                return;
            } else if (item.equals(trackNearestOpponent)) {
                trackNearest(playerData);
            }
        }
        event.setCancelled(true);
        player.closeInventory();
    }
}
