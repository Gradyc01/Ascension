package me.depickcator.ascension.listeners.Combat;

import me.depickcator.ascension.Items.Craftable.Unlocks.Exodus;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class onDamage extends PlayerCombat{
    public onDamage() {

    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!plugin.getGameState().inGame() || !(event.getEntity() instanceof LivingEntity)) return;

        double damageDealt = calculateDamage(event);
        event.setDamage(damageDealt);

        entityDamagedEffects(event, (LivingEntity) event.getEntity());

        if (!(event.getEntity() instanceof Player)) return;
        Player victim = (Player) event.getEntity();
        playerDamagedEffects(event, victim);
        Pair<Player, Boolean> damageData = setDamageMetadata(event, victim);
        if (damageData == null) return;
        Player attacker = damageData.getLeft();

        if (canNotDamage(attacker, victim)) {
            event.setCancelled(true);
            return;
        }

        //TODO: Remove later too lazy to make it a class
        if (equalItems(attacker.getInventory().getItem(EquipmentSlot.HEAD), Exodus.getInstance().getResult())) {
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,  5 * 20, 0));
        }


        calculateKnockback(event, attacker, victim, damageData.getRight());
        addFinalAscensionTimer(PlayerUtil.getPlayerData(attacker), (int) event.getFinalDamage() * 2 + 1 );
    }

    //====================================================================================

    private double calculateDamage(EntityDamageByEntityEvent event) {
        double damageDealt;
//        PlayerData attackerData = PlayerUtil.getPlayerData(attacker);/

        if (isPlayerMeleeAttack(event)) {
            Player attacker = (Player) event.getDamager();

            ItemStack weapon = attacker.getInventory().getItemInMainHand();

//            if ()) {
            double baseDamage = getDamageValue(weapon.getType());
            double cooldownMultiplier = attacker.getCooledAttackStrength(0);
            double criticalMultiplier = event.isCritical() ? (isAnAxe(weapon) ? 2.0 : 1.5) : 1;
            double effectMultiplier = 1 + 0.2 * effectMultiplier(attacker);
            double sharpnessMultiplier = meleeEnchantMultiplier(event, weapon);
            damageDealt = (((baseDamage + sharpnessMultiplier) * effectMultiplier) * criticalMultiplier) * cooldownMultiplier;
            TextUtil.debugText("(baseDamage(" + baseDamage +
                    ") + enchantDamage(" + sharpnessMultiplier +
                    ")) * effectMultiplier(" + effectMultiplier +
                    ") * criticalMultiplier(" + criticalMultiplier +
                    ") * cooldownMultiplier (" + cooldownMultiplier +
                    ")");
//            } else {
//                damageDealt = event.getDamage();
//            }
            TextUtil.debugText("Total Damage: " + damageDealt);
        } else if (isPlayerProjectileAttack(event)) {
            damageDealt = event.getDamage();
        } else {
            damageDealt = event.getDamage();
        }

        //Victim is "marked"
        if (event.getEntity().isGlowing()) {
            damageDealt *= 1.3;
        }

        return damageDealt;
    }

    private double meleeEnchantMultiplier(EntityDamageEvent event, ItemStack weapon) {
        double multiplier;
        if (getUndead().contains(event.getEntityType())) {
            multiplier = 1.75 * (weapon.containsEnchantment(Enchantment.SMITE) ? weapon.getEnchantmentLevel(Enchantment.SMITE) : 0);
        } else if (getArthropods().contains(event.getEntityType())) {
            multiplier = 1.75 * (weapon.containsEnchantment(Enchantment.BANE_OF_ARTHROPODS) ? weapon.getEnchantmentLevel(Enchantment.BANE_OF_ARTHROPODS) : 0);
        } else {
            multiplier = 1.25 * (weapon.containsEnchantment(Enchantment.SHARPNESS) ? weapon.getEnchantmentLevel(Enchantment.SHARPNESS) : 0);
        }
        return multiplier;
    }

    private int effectMultiplier(Player attacker) {
        int strengthLevel = attacker.hasPotionEffect(PotionEffectType.STRENGTH) ? attacker.getPotionEffect(PotionEffectType.STRENGTH).getAmplifier() + 1 : 0;
        int weaknessLevel = attacker.hasPotionEffect(PotionEffectType.WEAKNESS) ? attacker.getPotionEffect(PotionEffectType.WEAKNESS).getAmplifier() + 1 : 0;
        return strengthLevel + weaknessLevel;
    }

    private boolean isAnAxe(ItemStack item) {
        Material type = item.getType();
        return type == Material.WOODEN_AXE ||
                type == Material.STONE_AXE ||
                type == Material.IRON_AXE ||
                type == Material.GOLDEN_AXE ||
                type == Material.DIAMOND_AXE ||
                type == Material.NETHERITE_AXE;
    }

    private boolean isPlayerMeleeAttack(EntityDamageByEntityEvent event) {
        return  (event.getDamager() instanceof Player);
    }

    private boolean isPlayerProjectileAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow) {
            return ((Arrow) event.getDamager()).getShooter() instanceof Player;
        }
        return false;
    }

    //====================================================================================
    private void entityDamagedEffects(EntityDamageByEntityEvent event, LivingEntity victim) {

        setSpecialArrowIfNecessary(event);
    }


    private void playerDamagedEffects(EntityDamageByEntityEvent event, Player victim) {
        CombatTimer.getInstance().setCooldownTimer(victim);
        setShieldCooldown(event);
        if (victim.isGliding()) {
            victim.setGliding(false);
            TextUtil.debugText("Set Gliding false");
        }
        if (isPlayerProjectileAttack(event)) {
//            setSpecialArrowIfNecessary(event);
            sendArrowDamageMessage(victim, (Player) ((Arrow) event.getDamager()).getShooter(), event);
        }
    }


    private void setShieldCooldown(EntityDamageByEntityEvent event) {

        Player victim = (Player) event.getEntity();
//        TextUtil.debugText(victim.getShieldBlockingDelay() + "");
        if (victim.isBlocking() && event.getFinalDamage() == 0) {
            int shieldCooldown = Math.min(20, Math.max((int) event.getDamage(), 3));
            TextUtil.debugText(victim.getName() + " Shield Cooldown = " + shieldCooldown + "sec");
            victim.setCooldown(Material.SHIELD, shieldCooldown * 20);
//            victim.setShieldBlockingDelay(4);
//            new BukkitRunnable() {
//                public void run() {
//                    ItemStack item = victim.getInventory().getItemInOffHand();
//                    victim.getInventory().setItemInOffHand(item);
//                    victim.updateInventory();
//                }
//            }.runTaskLater(plugin, 1);
        }
    }

    //====================================================================================

    private Pair<Player, Boolean> setDamageMetadata(EntityDamageByEntityEvent event, Player victim) {
        if (isPlayerMeleeAttack(event)) {
            victim.setMetadata(getDamageSourceKey(), new FixedMetadataValue(plugin, getPLAYER_DAMAGE()));
            return Pair.of((Player) event.getDamager(), true);
        } else if (isPlayerProjectileAttack(event)) {
            victim.setMetadata(getDamageSourceKey(), new FixedMetadataValue(plugin, getPLAYER_DAMAGE()));
            Player attacker = (Player) ((Arrow) event.getDamager()).getShooter();
            return Pair.of(attacker, false);
        } else {
            victim.setMetadata(getDamageSourceKey(), new FixedMetadataValue(plugin, event.getCause().toString()));
            return null;
        }
    }

    private void setSpecialArrowIfNecessary(EntityDamageByEntityEvent event) {
        try {
            MetadataValue key = event.getDamager().getMetadata(ShootsProjectiles.METADATA).getFirst();
            ShootsProjectiles shootsProjectiles = ShootsProjectiles.getProjectile(key);
            if (shootsProjectiles != null && event.getEntity() instanceof LivingEntity) {
                shootsProjectiles.setProjectileComponent(event, (LivingEntity) event.getEntity());
            }
        } catch (Exception ignored) {

        }
    }

    private void sendArrowDamageMessage(Player victim, Player damager, EntityDamageByEntityEvent event) {
        double health = victim.getHealth()/* + victim.getAbsorptionAmount() */- event.getFinalDamage();
        if (health <= 0) return;
        Component name = TextUtil.makeText(victim.getName(), TextUtil.RED);
        Component isAt = TextUtil.makeText(" is at ", TextUtil.YELLOW);
//        BigDecimal.valueOf(health).setScale(1, RoundingMode.HALF_UP)
        Component num = TextUtil.makeText(  (double) ((int) (health * 10))/10 + "", TextUtil.RED);
        Component hp = TextUtil.makeText(" HP!", TextUtil.YELLOW);
        damager.sendMessage(name.append(isAt).append(num).append(hp));
    }

    //=====================================================================================================================

    private boolean canNotDamage(Player attacker, Player victim) {
        PlayerData attackerData = PlayerUtil.getPlayerData(attacker);
        return (attackerData == null ||
                attackerData.getPlayerTeam().getTeam().getOtherTeamMembers(attacker).contains(victim) ||
                plugin.getGameState().canNotPVP());
    }

    private void calculateKnockback(EntityDamageByEntityEvent event, Player attacker, Player victim, boolean isMelee) {
//        TextUtil.debugText(victim.getVelocity() + "");
//        victim.setVelocity(victim.getVelocity().multiply(2));
//        victim.setVelocity(damager.getLocation().getDirection().multiply(10));
        //Need To Added Punch and Knockback manually to make this work
        double enchantMultiplier = 0.6 * (isMelee ? attacker.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.KNOCKBACK) :
                attacker.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.PUNCH)); //Not the way to do it should cache punch level on arrow but too lazy

        double criticalMultiplier = isMelee ? isAnAxe(attacker.getInventory().getItemInMainHand()) ? event.isCritical() ? 2 : 1 : 1 : 1;
        double knockbackValue = Math.max(0.4, (event.getFinalDamage()/10 + enchantMultiplier) * criticalMultiplier);
        victim.setVelocity(victim.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize().multiply(knockbackValue));
        TextUtil.debugText("Knockback value: " + knockbackValue);
    }


}
