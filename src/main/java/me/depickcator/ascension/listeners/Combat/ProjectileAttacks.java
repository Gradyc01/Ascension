package me.depickcator.ascension.listeners.Combat;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.testingCommands.Debugger;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class ProjectileAttacks implements Listener {
    private final Ascension plugin;
    public ProjectileAttacks() {
        plugin = Ascension.getInstance();
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
