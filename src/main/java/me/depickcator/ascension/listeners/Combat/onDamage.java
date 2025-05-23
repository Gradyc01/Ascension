package me.depickcator.ascension.listeners.Combat;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Items.Craftable.Unlocks.Exodus;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import net.kyori.adventure.sound.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractArrow;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class onDamage extends PlayerCombat{
    public onDamage() {
    }

//    public void register() {
//        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
//        protocolManager.addPacketListener(new PacketAdapter(this.plugin,
//                PacketType.Play.Server.NAMED_SOUND_EFFECT) {
//
//            @Override
//            public void onPacketSending(PacketEvent event) {
//                PacketContainer packet = event.getPacket();
//
//                Sound sound;
//                try {
//                    sound = packet.getSoundEffects().read(0);
//                } catch (Exception e) {
//                    plugin.getLogger().warning("Could not read Sound enum from NAMED_SOUND_EFFECT packet. Sound blocking might not work.");
//                    return;
//                }
//
//                if (Sound.ENTITY_PLAYER_ATTACK_NODAMAGE.equals(sound)) {
//                    event.setCancelled(true);
//                }
//            }
//        });
//    }

//    private static void stopSound(Player p) {
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                TextUtil.debugText("Stop Sound Again for " + p.getName());
//
//                net.kyori.adventure.sound.Sound sound = Sound.sound(Key.key("entity.player.attack.nodamage"), Sound.Source.PLAYER, 0.7f, 1.0f);
//                p.stopSound(sound);
//            }
//        }.runTaskLater(Ascension.getInstance(), 1);
//    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (plugin.getGameState().checkState(GameStates.GAME_PAUSED)) {
            event.setCancelled(true);
            if (!(event.getDamager() instanceof Player)) event.getDamager().remove();
        }
        if (!plugin.getGameState().inGame() || !(event.getEntity() instanceof LivingEntity)) return;
        if (event.getEntity().isInvulnerable() || ((LivingEntity) event.getEntity()).getNoDamageTicks() > 0) {
            event.setCancelled(true);

//            TextUtil.debugText("Stop Sound for " + event.getDamager().getName());
//            net.kyori.adventure.sound.Sound sound = Sound.sound(Key.key("entity.player.attack.nodamage"), Sound.Source.PLAYER, 0.7f, 1.0f);
//            event.getDamager().stopSound(sound);
//            stopSound((Player) event.getDamager());
//            Player p = (Player) event.getEntity();
//            p.playSound(sound);
//            p.stopSound(sound);
            return;
        }

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

        if (isAnAxe(attacker.getInventory().getItemInMainHand())) {
            calculateKnockback(event, attacker, victim, damageData.getRight());
        }
        addFinalAscensionTimer(PlayerUtil.getPlayerData(attacker), (int) event.getFinalDamage() * 2 + 1 );
    }

    //====================================================================================

    private double calculateDamage(EntityDamageByEntityEvent event) {
        double damageDealt;
//        PlayerData attackerData = PlayerUtil.getPlayerData(attacker);/

        if (isPlayerMeleeAttack(event)) {
            damageDealt = calculatePlayerMeleeAttack(event);
        } else if (isPlayerProjectileAttack(event)) {
            damageDealt = calculatePlayerArrowAttack(event);
        } else {
            damageDealt = event.getDamage();
        }

        //Victim is "marked"
        if (event.getEntity().isGlowing()) {
            damageDealt *= 1.3;
        }
        TextUtil.debugText("Total Damage: " + damageDealt);
        return damageDealt;
    }

    private double calculatePlayerArrowAttack(EntityDamageByEntityEvent event) {
        double damageDealt;
        AbstractArrow arrow = (AbstractArrow) event.getDamager();
        ItemStack weapon = arrow.getWeapon();

        //Calculate Vanilla minecraft damage
        int powerLevel = (weapon.containsEnchantment(Enchantment.POWER)) ? weapon.getEnchantmentLevel(Enchantment.POWER) : 0;
        double damageTag = 2;
        if (powerLevel != 0) damageTag += 0.5 + (0.5 * powerLevel);
        double speed = event.getDamage() / damageTag;
        TextUtil.debugText("Old Damage Tag: " + damageTag + "Speed: " + speed);
        //Custom Damage
        damageTag = 2.75;
        if (powerLevel != 0) damageTag += 0.75 + (0.5 * powerLevel);
        TextUtil.debugText("New Damage Tag: " + damageTag + "Speed: " + speed);

        //Set Damage
        damageDealt = damageTag * speed;
        damageDealt *= 1 + 0.1 * effectMultiplier((Player) ((AbstractArrow) event.getDamager()).getShooter());
        ShootsProjectiles customWeapon = ShootsProjectiles.getProjectile(weapon);
        if (customWeapon != null) {
            double newDamage = customWeapon.setProjectileComponent(event, (LivingEntity) event.getEntity());
            if (newDamage != -1) damageDealt = newDamage;
        }
        return damageDealt;
    }

    private double calculatePlayerMeleeAttack(EntityDamageByEntityEvent event) {
        double damageDealt;
        Player attacker = (Player) event.getDamager();

        ItemStack weapon = attacker.getInventory().getItemInMainHand();

//            if ()) {
        double baseDamage = /*getDamageValue(weapon.getType());*/attacker.getAttribute(Attribute.ATTACK_DAMAGE).getValue();
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
        if (event.getDamager() instanceof AbstractArrow) {
            return ((AbstractArrow) event.getDamager()).getShooter() instanceof Player;
        }
        return false;
    }

    //====================================================================================
    private void entityDamagedEffects(EntityDamageByEntityEvent event, LivingEntity victim) {
//        victim.setNoDamageTicks(7);
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
            sendArrowDamageMessage(victim, (Player) ((AbstractArrow) event.getDamager()).getShooter(), event);
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
            Player attacker = (Player) ((AbstractArrow) event.getDamager()).getShooter();
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
