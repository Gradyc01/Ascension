package me.depickcator.ascensionBingo.listeners;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Interfaces.ShootsProjectiles;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

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
            setShieldCooldown(event);
            Player victim = (Player) event.getEntity();
            if (event.getDamager() instanceof Player) {
                setDamagerMetadataPlayer(victim, (Player) event.getDamager(), event);
                return;
            }
            if (event.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    setDamagerMetadataPlayer(victim, (Player) arrow.getShooter(), event);
                    setSpecialArrowIfNecessary(event);
                }
                return;
            }

            victim.setMetadata(damageSourceKey, new FixedMetadataValue(plugin, event.getCause().toString()));

        }
    }

    private void setShieldCooldown(EntityDamageByEntityEvent event) {
        Player victim = (Player) event.getEntity();
        if (victim.isBlocking() && event.getFinalDamage() == 0) {
            victim.setCooldown(Material.SHIELD, 7 * 25);
        }
    }

    private void setDamagerMetadataPlayer(Player victim, Player damager, EntityDamageByEntityEvent event) {
        PlayerData damagerData = PlayerUtil.getPlayerData(damager);
        if (damagerData == null || damagerData.getPlayerTeam().getTeam().getOtherTeamMembers(damager).contains(victim)) {
            event.setCancelled(true);
            return;
        }

        victim.setMetadata(damageSourceKey, new FixedMetadataValue(plugin, PLAYER_DAMAGE));
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

    private void setSpecialArrowIfNecessary(EntityDamageByEntityEvent event) {
        try {
            MetadataValue key = event.getDamager().getMetadata(ShootsProjectiles.METADATA).getFirst();
            ShootsProjectiles shootsProjectiles = ShootsProjectiles.getProjectile(key);
            if (shootsProjectiles != null) {
                shootsProjectiles.setProjectileComponent(event);
            }
        } catch (Exception ignored) {

        }
    }
}

