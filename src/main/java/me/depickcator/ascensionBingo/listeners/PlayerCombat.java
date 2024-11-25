package me.depickcator.ascensionBingo.listeners;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerCombat implements Listener {
    AscensionBingo plugin;
    private final String damageSourceKey = "lastDamageSource";
    private final String PLAYER_DAMAGE = "PLAYER_DAMAGE";
    public PlayerCombat(AscensionBingo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player victim = (Player) event.getEntity();
            if (!(event.getDamager() instanceof Player)) {
                victim.setMetadata(damageSourceKey, new FixedMetadataValue(plugin, event.getCause().toString()));
                return;
            }
            Player damager = (Player) event.getDamager();
            PlayerData damagerData = PlayerUtil.getPlayerData(damager);
            if (damagerData == null || damagerData.getPlayerTeam().getTeam().getOtherTeamMembers(damager).contains(victim)) {
                event.setCancelled(true);
                return;
            }
            victim.setMetadata(damageSourceKey, new FixedMetadataValue(plugin, PLAYER_DAMAGE));
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
            dropHead(victim);
            increaseKillCount(e);
        }

        plugin.getLogger().info(victim.getName() + " died due to: " + cause);

        // Remove the metadata after use
        victim.removeMetadata(damageSourceKey, plugin);
    }

    private void increaseKillCount(PlayerDeathEvent e) {
        Entity entity = e.getDamageSource().getCausingEntity();
        if (entity instanceof Player) {
            PlayerData killer = PlayerUtil.getPlayerData((Player) entity);
            killer.getPlayerStats().addKill();
        }
    }

    private void dropHead(Player victim) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwningPlayer(victim);
            skullMeta.setCustomModelData(0);
            skullMeta.setMaxStackSize(1);
        }
        skull.setItemMeta(skullMeta);
        victim.getWorld().dropItem(victim.getLocation(), skull);
    }
}

