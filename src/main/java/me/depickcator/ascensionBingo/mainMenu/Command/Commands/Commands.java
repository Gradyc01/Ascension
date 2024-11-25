package me.depickcator.ascensionBingo.mainMenu.Command.Commands;

import me.depickcator.ascensionBingo.Player.PlayerData;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface Commands {
    void uponEvent(InventoryClickEvent event, PlayerData playerData);
    ItemStack getButton();

}
