package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Interfaces.ShootsProjectiles;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class ProjectileAttacks implements Listener {
    // private final Ascension plugin;
    public ProjectileAttacks() {
        // plugin = Ascension.getInstance();
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
            setArrowDamage((Arrow) event.getProjectile(), player, event.getBow());
//            setArrowNBT((Arrow) event.getProjectile(), );
        }
    }

    private void setArrowDamage(Arrow arrow, Player player, ItemStack bow) {
        double arrowDamage = 4;
        if (player.hasPotionEffect(PotionEffectType.STRENGTH)) {
            arrowDamage *= 1.2;
        }
        if (player.hasPotionEffect(PotionEffectType.WEAKNESS)) {
            arrowDamage *= 0.8;
        }
        if (bow != null) {
            arrowDamage += (0.1 * bow.getEnchantmentLevel(Enchantment.POWER));
        }
        arrow.setDamage(arrowDamage);
    }

//    private void setArrowNBT(Arrow arrow, ItemStack bow) {
//        int powerLevel = bow.getEnchantmentLevel(Enchantment.POWER);
//        int punchLevel = bow.getEnchantmentLevel(Enchantment.PUNCH);
//        arrow.getPersistentDataContainer().set(new NamespacedKey(plugin, "power"), PersistentDataType.INTEGER, powerLevel);
//        arrow.getPersistentDataContainer().set(new NamespacedKey(plugin, "punch"), PersistentDataType.INTEGER, punchLevel);
//    }
}
