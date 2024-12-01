package me.depickcator.ascensionBingo.listeners;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Interfaces.ShootsProjectiles;
import me.depickcator.ascensionBingo.Player.Cooldowns.CombatTimer;
import me.depickcator.ascensionBingo.Player.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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

import java.awt.*;

public class PlayerCombat implements Listener {
    AscensionBingo plugin;
    private final String damageSourceKey = "lastDamageSource";
    private final String PLAYER_DAMAGE = "PLAYER_DAMAGE";
    public PlayerCombat(AscensionBingo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!plugin.getGameState().inGame()) return;
        if (event.getEntity() instanceof Player) {
            Player victim = (Player) event.getEntity();
            CombatTimer.getInstance().setCooldownTimer(victim);
            setShieldCooldown(event);

            if (event.getDamager() instanceof Player) {
                setDamagerMetadataPlayer(victim, (Player) event.getDamager(), event);
                return;
            }
            if (event.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    Player damager = (Player) arrow.getShooter();
                    setDamagerMetadataPlayer(victim, damager, event);
                    setSpecialArrowIfNecessary(event);
                    sendArrowDamageMessage(victim, damager, event);
                }
                return;
            }
            victim.setMetadata(damageSourceKey, new FixedMetadataValue(plugin, event.getCause().toString()));
        }
    }

    private void strikeLightning(Location loc) {
        loc.getWorld().strikeLightningEffect(loc);
        loc.getWorld().playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0F, 1.0F);
        loc.getWorld().playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.0F, 0.0F);
    }

    private void sendArrowDamageMessage(Player victim, Player damager, EntityDamageByEntityEvent event) {
        double health = victim.getHealth() - event.getFinalDamage();
        if (health <= 0) return;
        Component name = TextUtil.makeText(victim.getName(), TextUtil.RED);
        Component isAt = TextUtil.makeText(" is at ", TextUtil.YELLOW);
        Component num = TextUtil.makeText(health + "", TextUtil.RED);
        Component hp = TextUtil.makeText(" HP!", TextUtil.YELLOW);
        damager.sendMessage(name.append(isAt).append(num).append(hp));
    }

    private void setShieldCooldown(EntityDamageByEntityEvent event) {
        Player victim = (Player) event.getEntity();
        if (victim.isBlocking() && event.getFinalDamage() == 0) {
            victim.setCooldown(Material.SHIELD, 7 * 25);
        }
    }

    private void setDamagerMetadataPlayer(Player victim, Player damager, EntityDamageByEntityEvent event) {
        PlayerData damagerData = PlayerUtil.getPlayerData(damager);
        if (damagerData == null ||
                damagerData.getPlayerTeam().getTeam().getOtherTeamMembers(damager).contains(victim) ||
                plugin.getGameState().canNotPVP()) {
            event.setCancelled(true);
            return;
        }

        victim.setMetadata(damageSourceKey, new FixedMetadataValue(plugin, PLAYER_DAMAGE));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        PlayerData victimData = PlayerUtil.getPlayerData(victim);
        String cause = "Unknown";

        PlayerDeath.getInstance(plugin).playerDied(victimData);
        victimData.getPlayerStats().addDeaths(1);

        // Check the cause of damage when the player dies
        if (victim.hasMetadata(damageSourceKey)) {
            cause = victim.getMetadata(damageSourceKey).getFirst().asString();
        }
        if (cause.equals(PLAYER_DAMAGE)) {
            dropHead(victim);
            increaseKillCount(e);
        }

        strikeLightning(victim.getLocation());
        plugin.getServer().broadcast(e.deathMessage().color(TextUtil.DARK_RED));


        plugin.getLogger().info(victim.getName() + " died due to: " + cause);

        // Remove the metadata after use

        victim.removeMetadata(damageSourceKey, plugin);
        e.setCancelled(true);
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

