package org.bingo4.listeners;

import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;

public class FastSmelt implements Listener {
    @EventHandler
    public void onFurnaceSmelt(FurnaceStartSmeltEvent event) {
        Furnace furnace = (Furnace) event.getBlock().getState();

        if (furnace.getInventory().getFuel() != null && furnace.getInventory().getFuel().getType() == Material.COAL) {
            event.setTotalCookTime(event.getTotalCookTime() / 40);
        }
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        Furnace furnace = (Furnace) event.getBlock().getState();

        if (furnace.getInventory().getFuel() != null && furnace.getInventory().getFuel().getType() == Material.COAL) {
            event.setBurnTime(event.getBurnTime() / 40);
        }
    }
}
