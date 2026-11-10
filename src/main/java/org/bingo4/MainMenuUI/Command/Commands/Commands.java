package org.bingo4.MainMenuUI.Command.Commands;

import org.bingo4.Player.Data.PlayerData;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface Commands {
    /*Returns true if CommandsGUI should be refreshed
    * False Otherwise*/
    boolean uponEvent(InventoryClickEvent event, PlayerData playerData);
    ItemStack getButton();

}
