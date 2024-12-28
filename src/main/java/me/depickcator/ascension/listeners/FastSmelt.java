package me.depickcator.ascension.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;

public class FastSmelt implements Listener {
    @EventHandler
    public void onFurnaceSmelt(FurnaceStartSmeltEvent event) {
        event.setTotalCookTime(event.getTotalCookTime() / 20);
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        event.setBurnTime(event.getBurnTime() / 20);
    }
}
