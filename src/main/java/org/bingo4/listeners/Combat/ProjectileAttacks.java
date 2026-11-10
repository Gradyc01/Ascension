package org.bingo4.listeners.Combat;

import org.bingo4.Bingo4;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

public class ProjectileAttacks implements Listener {
    private final Bingo4 plugin;
    public ProjectileAttacks() {
        plugin = Bingo4.getInstance();
    }

    @EventHandler
    public void onProjectileShot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        ShootsProjectiles item = ShootsProjectiles.getProjectile(event.getBow());
        if (item != null) item.applyKey(event);
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        ProjectileSource source = projectile.getShooter();
        if (source instanceof Player) {
            Player p = (Player) source;
            p.hideEntity(plugin, projectile);
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.showEntity(plugin, projectile);
                }

            }.runTaskLater(plugin, 1);
        }
    }

}
