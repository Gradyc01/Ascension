package me.depickcator.ascension.mainMenu.Command.Commands;

import me.depickcator.ascension.Player.PlayerData;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface Commands {
    void uponEvent(InventoryClickEvent event, PlayerData playerData);
    ItemStack getButton();

}
