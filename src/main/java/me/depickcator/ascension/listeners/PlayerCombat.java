package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.ShootsProjectiles;
import me.depickcator.ascension.Items.Craftable.Vanilla.*;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PlayerCombat implements Listener {
    private final Ascension plugin;
    private final String damageSourceKey = "lastDamageSource";
    private final String PLAYER_DAMAGE = "PLAYER_DAMAGE";
    private HashMap<Material, Double> weaponDamageValues;
    private Set<EntityType> undead;
    private Set<EntityType> arthropods;
    public PlayerCombat() {
        this.plugin = Ascension.getInstance();
        initDamageValues();
        initMobSets();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!plugin.getGameState().inGame()) return;

        double damageDealt = calculateDamage(event);
        event.setDamage(damageDealt);

        if (!(event.getEntity() instanceof Player)) return;
        Player victim = (Player) event.getEntity();
        victimDamagedEffects(event, victim);
        Pair<Player, Boolean> damageData = setDamageMetadata(event, victim);
        if (damageData == null) return;
        Player attacker = damageData.getLeft();
        if (canNotDamage(attacker, victim)) {
            event.setCancelled(true);
            return;
        }

        calculateKnockback(event, attacker, victim, damageData.getRight());
        addFinalAscensionTimer(PlayerUtil.getPlayerData(attacker), (int) event.getFinalDamage() * 2 + 1 );

    }

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

    private double calculateDamage(EntityDamageByEntityEvent event) {
        double damageDealt;
//        PlayerData attackerData = PlayerUtil.getPlayerData(attacker);/

        if (isPlayerMeleeAttack(event)) {
            Player attacker = (Player) event.getDamager();

            ItemStack weapon = attacker.getInventory().getItemInMainHand();
            if (weaponDamageValues.containsKey(weapon.getType())) {
                double baseDamage = weaponDamageValues.get(weapon.getType());
                double cooldownMultiplier = attacker.getCooledAttackStrength(0);
                double criticalMultiplier = event.isCritical() ? (isAnAxe(weapon) ? 2.5 : 1.5) : 1;
                double effectMultiplier = 1 + 0.2 * effectMultiplier(attacker);
                double sharpnessMultiplier = meleeEnchantMultiplier(event, weapon);
                damageDealt = (((baseDamage + sharpnessMultiplier) * effectMultiplier) * criticalMultiplier) * cooldownMultiplier;
                TextUtil.debugText("(baseDamage(" + baseDamage +
                        ") + enchantDamage(" + sharpnessMultiplier +
                        ")) * effectMultiplier(" + effectMultiplier +
                        ") * criticalMultiplier(" + criticalMultiplier +
                        ") * cooldownMultiplier (" + cooldownMultiplier +
                        ")");
            } else {
                damageDealt = event.getDamage();
            }
             TextUtil.debugText("Total Damage: " + damageDealt);
        } else if (isPlayerProjectileAttack(event)) {
            damageDealt = event.getDamage();
//            ((Arrow) event.getDamager()).
        } else {
            damageDealt = event.getDamage();
        }
        return damageDealt;
    }

    private double meleeEnchantMultiplier(EntityDamageEvent event, ItemStack weapon) {
        double multiplier;
        if (undead.contains(event.getEntityType())) {
            multiplier = 1.75 * (weapon.containsEnchantment(Enchantment.SMITE) ? weapon.getEnchantmentLevel(Enchantment.SMITE) : 0);
        } else if (arthropods.contains(event.getEntityType())) {
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

    private Pair<Player, Boolean> setDamageMetadata(EntityDamageByEntityEvent event, Player victim) {
        if (isPlayerMeleeAttack(event)) {
            victim.setMetadata(damageSourceKey, new FixedMetadataValue(plugin, PLAYER_DAMAGE));
            return Pair.of((Player) event.getDamager(), true);
        } else if (isPlayerProjectileAttack(event)) {
            victim.setMetadata(damageSourceKey, new FixedMetadataValue(plugin, PLAYER_DAMAGE));
            Player attacker = (Player) ((Arrow) event.getDamager()).getShooter();
            setSpecialArrowIfNecessary(event);
            sendArrowDamageMessage(victim, attacker, event); //For now doesn't matter as we aren't changing the damage dealt
            return Pair.of(attacker, false);
        } else {
            victim.setMetadata(damageSourceKey, new FixedMetadataValue(plugin, event.getCause().toString()));
            return null;
        }
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

    private void victimDamagedEffects(EntityDamageByEntityEvent event, Player victim) {
        CombatTimer.getInstance().setCooldownTimer(victim);
        setShieldCooldown(event);
        if (victim.isGliding()) {
            victim.setGliding(false);
            TextUtil.debugText("Set Gliding false");
        }
    }

    private void initDamageValues() {
        weaponDamageValues = new HashMap<>();
        weaponDamageValues.put(Material.WOODEN_AXE, WoodenAxe.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.STONE_AXE, StoneAxe.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.IRON_AXE, IronAxe.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.DIAMOND_AXE, DiamondAxe.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.NETHERITE_AXE, NetheriteAxe.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.GOLDEN_AXE, (double) 9);
        weaponDamageValues.put(Material.WOODEN_SWORD, WoodenSword.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.STONE_SWORD, StoneSword.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.IRON_SWORD, IronSword.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.DIAMOND_SWORD, DiamondSword.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.NETHERITE_SWORD, NetheriteSword.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.GOLDEN_SWORD, (double) 4);
    }

    private void initMobSets() {
        undead = new HashSet<>(Set.of(
                EntityType.ZOMBIE,
                EntityType.ZOMBIE_VILLAGER,
                EntityType.DROWNED,
                EntityType.SKELETON,
                EntityType.WITHER_SKELETON,
                EntityType.STRAY,
                EntityType.HUSK,
                EntityType.ZOMBIFIED_PIGLIN,
                EntityType.PHANTOM,
                EntityType.WITHER
        ));
        arthropods = new HashSet<>(Set.of(
                EntityType.SPIDER,
                EntityType.CAVE_SPIDER
        ));
    }

    //=================================================================================================
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
            ItemStack shards = ShardOfTheFallen.result().clone();
            shards.setAmount(12);
            victim.getWorld().dropItem(victim.getLocation(), shards);
            increaseKillCount(e);
        }

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


//    private void strikeLightning(Location loc) {
//        loc.getWorld().strikeLightningEffect(loc);
//        loc.getWorld().playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0F, 1.0F);
//        loc.getWorld().playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.0F, 0.0F);
//    }

    private void sendArrowDamageMessage(Player victim, Player damager, EntityDamageByEntityEvent event) {
        double health = victim.getHealth()/* + victim.getAbsorptionAmount() */- event.getFinalDamage();
        if (health <= 0) return;
        Component name = TextUtil.makeText(victim.getName(), TextUtil.RED);
        Component isAt = TextUtil.makeText(" is at ", TextUtil.YELLOW);
        Component num = TextUtil.makeText( BigDecimal.valueOf(health).setScale(1, RoundingMode.UNNECESSARY) + "", TextUtil.RED);
        Component hp = TextUtil.makeText(" HP!", TextUtil.YELLOW);
        damager.sendMessage(name.append(isAt).append(num).append(hp));
    }

    private void setShieldCooldown(EntityDamageByEntityEvent event) {

        Player victim = (Player) event.getEntity();
//        TextUtil.debugText(victim.getShieldBlockingDelay() + "");
        if (victim.isBlocking() && event.getFinalDamage() == 0) {
            int shieldCooldown = Math.min(20, Math.max((int) event.getDamage(), 4));
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

