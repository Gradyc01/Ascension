package me.depickcator.ascension.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class PlayerExpGain implements Listener {
    public PlayerExpGain() {

    }
    @EventHandler
    public void onPlayerGainExperience(PlayerExpChangeEvent event) {
        event.setAmount((int) (event.getAmount() * 1.75));
    }

    @EventHandler
    public void onPlayerShearSheep(PlayerShearEntityEvent event) {
        Entity e = event.getEntity();
        if (e instanceof Sheep) {
            List<ItemStack> items = new ArrayList<>(event.getDrops());
            items.add(new ItemStack(Material.STRING));
            event.setDrops(items);
        }
    }
}
