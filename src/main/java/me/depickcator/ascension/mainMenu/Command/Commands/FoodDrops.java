package me.depickcator.ascension.MainMenu.Command.Commands;

import me.depickcator.ascension.General.SoundUtil;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FoodDrops implements Commands {
    public final static String NAME = "food_drops";
    private final ItemStack item;
    public FoodDrops() {
        item = makeButton();
    }
    @Override
    public void uponEvent(InventoryClickEvent event, PlayerData playerData) {
        Player p = playerData.getPlayer();
        Component bool;
        if (!playerData.getPlayerStats().isFoodDrops()) {
//            p.getPersistentDataContainer().set(namespaceKey, PersistentDataType.BOOLEAN, true);
            bool = TextUtil.makeText("Enabled", TextUtil.GREEN);
            playerData.getPlayerStats().foodDropsSwitch();
        } else {
//            p.getPersistentDataContainer().set(namespaceKey, PersistentDataType.BOOLEAN, false);
            bool = TextUtil.makeText("Disabled", TextUtil.RED);
            playerData.getPlayerStats().foodDropsSwitch();
        }
        p.sendMessage(TextUtil.makeText("Food Drops is now ", TextUtil.AQUA).append(bool));
        SoundUtil.playHighPitchPling(p);
    }

    @Override
    public ItemStack getButton() {
        return item;
    }

    private ItemStack makeButton() {
        ItemStack item = new ItemStack(Material.COOKED_BEEF);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(TextUtil.makeText("Food Drops", TextUtil.AQUA));
        item.setItemMeta(itemMeta);
        return item;
    }
}
