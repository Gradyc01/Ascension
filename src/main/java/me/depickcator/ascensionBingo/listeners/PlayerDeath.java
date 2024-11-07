package me.depickcator.ascensionBingo.listeners;

import me.depickcator.ascensionBingo.AscensionBingo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.loot.LootTable;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerDeath implements Listener {
    AscensionBingo plugin;
    private final String damageSourceKey = "lastDamageSource";
    private final String PLAYER_DAMAGE = "PLAYER_DAMAGE";
    public PlayerDeath(AscensionBingo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player victim = (Player) event.getEntity();
            if (event.getDamager() instanceof Player) {
                victim.setMetadata(damageSourceKey, new FixedMetadataValue(plugin, PLAYER_DAMAGE));
            } else {
                victim.setMetadata(damageSourceKey, new FixedMetadataValue(plugin, event.getCause().toString()));
            }

        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
//        p.getLocation().getWorld().strikeLightning(p.getLocation());
        Player victim = e.getEntity();
        String cause = "Unknown";


        // Check the cause of damage when the player dies
        if (victim.hasMetadata(damageSourceKey)) {
            cause = victim.getMetadata(damageSourceKey).getFirst().asString();
        }
        if (cause.equals(PLAYER_DAMAGE)) {
            // Optionally, drop the player's skull
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            if (skullMeta != null) {
                skullMeta.setOwningPlayer(victim);
                skull.setItemMeta(skullMeta);
            }
            victim.getWorld().dropItem(victim.getLocation(), skull);
        }

        // Log or handle the cause of death as needed
        plugin.getLogger().info(victim.getName() + " died due to: " + cause);




        // Remove the metadata after use
        victim.removeMetadata(damageSourceKey, plugin);
    }
}

