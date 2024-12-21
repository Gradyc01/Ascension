package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.ShootsProjectiles;
import me.depickcator.ascension.Player.Data.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Data.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
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
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class PlayerCombat implements Listener {
    private final Ascension plugin;
    private final String damageSourceKey = "lastDamageSource";
    private final String PLAYER_DAMAGE = "PLAYER_DAMAGE";
    public PlayerCombat() {
        this.plugin = Ascension.getInstance();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!plugin.getGameState().inGame()) return;
        if (event.getEntity() instanceof Player) {
            Player victim = (Player) event.getEntity();
            CombatTimer.getInstance().setCooldownTimer(victim);
            setShieldCooldown(event);
            if (victim.isGliding()) {
                victim.setGliding(false);
                TextUtil.debugText("Set Gliding false");
            }

            if (event.getDamager() instanceof Player) {
                attackedByPlayer(victim, (Player) event.getDamager(), event);
                return;
            }
            if (event.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    Player damager = (Player) arrow.getShooter();
                    attackedByPlayer(victim, damager, event);
                    setSpecialArrowIfNecessary(event);
                    sendArrowDamageMessage(victim, damager, event);
                }
                return;
            }
            victim.setMetadata(damageSourceKey, new FixedMetadataValue(plugin, event.getCause().toString()));
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        PlayerData victimData = PlayerUtil.getPlayerData(victim);
        String cause = "Unknown";

        PlayerDeath.getInstance().playerDied(victimData);
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

    @EventHandler
    public void onPlayerElytra(EntityToggleGlideEvent event) {
        if (event.getEntity() instanceof Player /*&& plugin.getGameState().inGame()*/) {
            Player p = (Player) event.getEntity();
//            PlayerData pD = PlayerUtil.getPlayerData(p);
            if (CombatTimer.getInstance().isOnCooldown(p)) {
                TextUtil.debugText("Set Gliding false and cancelled");
                p.setGliding(false);
                event.setCancelled(true);
            }
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

    private void attackedByPlayer(Player victim, Player damager, EntityDamageByEntityEvent event) {
        PlayerData damagerData = PlayerUtil.getPlayerData(damager);
        if (damagerData == null ||
                damagerData.getPlayerTeam().getTeam().getOtherTeamMembers(damager).contains(victim) ||
                plugin.getGameState().canNotPVP()) {
            event.setCancelled(true);
            return;
        }
        TextUtil.debugText(victim.getVelocity() + "");
//        victim.setVelocity(victim.getVelocity().multiply(2));
//        victim.setVelocity(damager.getLocation().getDirection().multiply(10));
        //Need To Added Punch and Knockback manually to make this word
        victim.setVelocity(victim.getLocation().toVector().subtract(damager.getLocation().toVector()).normalize().multiply(2));
        TextUtil.debugText(victim.getVelocity() + "");

        addFinalAscensionTimer(damagerData, (int) event.getFinalDamage() * 2 + 1);
        victim.setMetadata(damageSourceKey, new FixedMetadataValue(plugin, PLAYER_DAMAGE));
    }

    private void addFinalAscensionTimer(PlayerData damager, int time) {
        if (plugin.getGameState().checkState(GameStates.GAME_FINAL_ASCENSION)) {
            damager.getPlayerTeam().getTeam().getTeamStats().addFinalAscensionTimer(time);
        }
    }

    private void increaseKillCount(PlayerDeathEvent e) {
        Entity entity = e.getDamageSource().getCausingEntity();
        if (entity instanceof Player) {
            PlayerData killer = PlayerUtil.getPlayerData((Player) entity);
            killer.getPlayerStats().addKill();
            addFinalAscensionTimer(killer, 60);
        }
    }

    private void dropHead(Player victim) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwningPlayer(victim);
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

