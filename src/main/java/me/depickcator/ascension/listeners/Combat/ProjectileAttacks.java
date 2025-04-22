package me.depickcator.ascension.listeners.Combat;

import me.depickcator.ascension.Ascension;
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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
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
        Player p = (Player) event.getEntity();
        Entity e = event.getProjectile();
        p.hideEntity(plugin, e);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.showEntity(plugin, e);
            }

        }.runTaskLater(plugin, 1);
//        e.teleport(p.getEyeLocation().add(p.getEyeLocation().getDirection().multiply(0.75)));
//        e.setVelocity(e.getVelocity().multiply(1.02));

    }

}
