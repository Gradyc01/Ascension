package me.depickcator.ascension.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerConsumeItem implements Listener {
    public PlayerConsumeItem() {

    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType().equals(Material.GOLDEN_APPLE)) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 13 * 20, 1, true, true));
            return;
        }
    }
}
