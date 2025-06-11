package me.depickcator.ascension.MainMenuUI.Command.Commands;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerStats;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NotifyCrafts implements Commands {
    private final ItemStack item;
    public NotifyCrafts() {
        item = makeButton();
    }
    @Override
    public void uponEvent(InventoryClickEvent event, PlayerData playerData) {
        Player p = playerData.getPlayer();
        Component bool;
        if (!playerData.getPlayerStats().getSetting(PlayerStats.craftNotifications)) {
            bool = TextUtil.makeText("Enabled", TextUtil.GREEN);
        } else {
            bool = TextUtil.makeText("Disabled", TextUtil.RED);
        }
        playerData.getPlayerStats().booleanSwitch(PlayerStats.craftNotifications);
        p.sendMessage(TextUtil.makeText("Craft Notifications is now ", TextUtil.AQUA).append(bool));
        SoundUtil.playHighPitchPling(p);
    }

    @Override
    public ItemStack getButton() {
        return item;
    }

    private ItemStack makeButton() {
        ItemStack item = new ItemStack(Material.REDSTONE_LAMP);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(TextUtil.makeText("Craft Notifications", TextUtil.AQUA));
        item.setItemMeta(itemMeta);
        return item;
    }
}
