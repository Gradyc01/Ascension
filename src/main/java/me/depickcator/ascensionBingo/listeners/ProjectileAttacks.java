package me.depickcator.ascensionBingo.listeners;

import me.depickcator.ascensionBingo.Interfaces.ShootsProjectiles;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.potion.PotionEffectType;

public class ProjectileAttacks implements Listener {
    public ProjectileAttacks() {

    }

    @EventHandler
    public void onProjectileShot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        ShootsProjectiles shootsProjectiles = ShootsProjectiles.getProjectile(event.getBow());
        if (shootsProjectiles != null) {
            shootsProjectiles.applyKey(event);
            return;
        }
        if (event.getProjectile() instanceof Arrow) {
            setArrowDamage((Arrow) event.getProjectile(), player);
        }
    }

    private void setArrowDamage(Arrow arrow, Player player) {
        double arrowDamage = 4;
        if (player.hasPotionEffect(PotionEffectType.STRENGTH)) {
            arrowDamage *= 1.2;
        }
        if (player.hasPotionEffect(PotionEffectType.WEAKNESS)) {
            arrowDamage *= 0.8;
        }
        arrow.setDamage(arrowDamage);
    }
}
