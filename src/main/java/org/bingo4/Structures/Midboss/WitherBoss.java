
package org.bingo4.Structures.Midboss;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class WitherBoss extends net.minecraft.world.entity.boss.wither.WitherBoss implements RealmNMSMob {
    private static int REGEN_DELAY_SECONDS = 10;
    private static float REGEN_AMOUNT = 1.0F;
    private static int REGEN_INTERVAL_TICKS = 2;

    private static final double EXPLOSION_RADIUS = 10.0;
    private static final float EXPLOSION_DAMAGE = 2.0F;

    private static final double KNOCKBACK_HORIZONTAL = 1.5;
    private static final double KNOCKBACK_VERTICAL = 1.2;

    private final float phase2Damage;
    private final MidbossInstance midbossInstance;
    private boolean damageSequenceActive = false;
    private boolean hasPoweredExplosion = false;
    private int lastDamagedTick = 0;

    public WitherBoss(MidbossInstance midbossInstance, int phase2Health, int phase3Health) {
        super(EntityTypes.WITHER, ((CraftWorld) midbossInstance.getCenter().getWorld()).getHandle());

        this.setCustomName(getMobName());
        this.getBukkitEntity().setRotation(270, 0);

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(phase3Health * 2);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(100);

        this.setHealth(this.getMaxHealth());

        phase2Damage = (float) phase3Health / (float) phase2Health;
        this.midbossInstance = midbossInstance;

        this.targetSelector.removeAllGoals(goal -> true);

        super.targetSelector.addGoal(1, new HurtByTargetGoal(this, Vex.class));
        super.targetSelector.addGoal(2,
                new NearestAttackableTargetGoal<>(this, Player.class, false, false));

        lastDamagedTick = this.tickCount;
    }

    // ==============================
    // Location
    // ==============================

    public Location getLocation() {
        return this.getBukkitEntity().getLocation();
    }

    // ==============================
    // AI
    // ==============================

    @Override
    public void aiStep() {
        if (!this.isAlive()) return;

        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();

        super.aiStep();

        this.setDeltaMovement(Vec3.ZERO);
        this.setPos(x, y, z);
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {

        // Trigger explosion exactly once when becoming powered
        if (this.isPowered() && !hasPoweredExplosion) {
            triggerPoweredExplosion(level);
        }

        if (this.getInvulnerableTicks() > 0) {
            super.customServerAiStep(level);
        } else {

            for (int ix = 1; ix < 3; ++ix) {
                int alternativeTarget = this.getAlternativeTarget(ix);

                if (alternativeTarget > 0) {
                    LivingEntity livingEntity =
                            (LivingEntity) level.getEntity(alternativeTarget);

                    if (!shouldExcludeEntity(livingEntity)) {
                        super.customServerAiStep(level);
                    }
                }
            }

            if (this.getTarget() != null) {
                this.setAlternativeTarget(0, this.getTarget().getId());
            } else {
                this.setAlternativeTarget(0, 0);
            }

            // Health regeneration
            handleRegeneration();

            this.bossEvent.setProgress(
                    this.getHealth() / this.getMaxHealth()
            );
        }

        setBossBarProgress();
    }

    // ==============================
    // Powered Explosion
    // ==============================

    private void triggerPoweredExplosion(ServerLevel level) {

        hasPoweredExplosion = true;
        midbossInstance.poweredExplosion();
        // Visual explosion
        level.sendParticles(
                ParticleTypes.EXPLOSION_EMITTER,
                this.getX(),
                this.getY(),
                this.getZ(),
                1,
                0, 0, 0,
                0
        );

        // Explosion sound
        level.playSound(
                null,
                this.getX(),
                this.getY(),
                this.getZ(),
                SoundEvents.GENERIC_EXPLODE,
                SoundSource.HOSTILE,
                3.0F,
                0.8F
        );

        // Knock back nearby players
        for (Player player : level.players()) {

            double distance = player.distanceTo(this);

            if (distance > EXPLOSION_RADIUS || distance == 0) {
                continue;
            }

            // Small explosion damage
            player.hurtServer(
                    level,
                    this.damageSources().explosion(this, null),
                    EXPLOSION_DAMAGE
            );

            // Calculate horizontal direction away from the Wither
            double dx = player.getX() - this.getX();
            double dz = player.getZ() - this.getZ();

            double horizontalLength = Math.sqrt(dx * dx + dz * dz);

            if (horizontalLength == 0) {
                dx = 1;
                dz = 0;
                horizontalLength = 1;
            }

            dx /= horizontalLength;
            dz /= horizontalLength;

            // 45-degree upward knockback
            Vec3 knockback = new Vec3(
                    dx * KNOCKBACK_HORIZONTAL,
                    KNOCKBACK_VERTICAL,
                    dz * KNOCKBACK_HORIZONTAL
            );

            player.setDeltaMovement(knockback);
        }
    }

    // ==============================
    // Health Regeneration
    // ==============================

    private void handleRegeneration() {

        // Has the Wither been left alone long enough?
        int ticksSinceDamage = this.tickCount - lastDamagedTick;

        if (ticksSinceDamage < REGEN_DELAY_SECONDS * 20) {
            return;
        }

        // Only regenerate at the configured interval
        if (this.tickCount % REGEN_INTERVAL_TICKS != 0) {
            return;
        }

        // Don't regenerate if already at full health
        if (this.getHealth() >= this.getMaxHealth()) {
            return;
        }

        // Regenerate health
        this.heal(
                REGEN_AMOUNT,
                EntityRegainHealthEvent.RegainReason.CUSTOM
        );
        if (damageSequenceActive && this.getHealth() >= this.getMaxHealth()) {
            damageSequenceActive = false;
            midbossInstance.stop();
        }
    }

    // ==============================
    // Damage
    // ==============================

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {

        float newAmount = this.isPowered()
                ? amount
                : amount * phase2Damage;

        boolean damaged = super.hurtServer(level, damageSource, newAmount);

        // Reset regeneration timer only if damage was successfully applied
        if (damaged) {
            lastDamagedTick = this.tickCount;

            if (!damageSequenceActive) {
                damageSequenceActive = true;
                midbossInstance.start();
            }
        }

        return damaged;
    }

    @Override
    public void heal(
            float healAmount,
            EntityRegainHealthEvent.RegainReason regainReason,
            boolean isFastRegen
    ) {
        // Prevent vanilla regeneration
        if (regainReason == EntityRegainHealthEvent.RegainReason.REGEN) {
            return;
        }


        super.heal(healAmount, regainReason, isFastRegen);
    }

    // ==============================
    // Death
    // ==============================

    @Override
    protected void dropCustomDeathLoot(
            ServerLevel level,
            DamageSource damageSource,
            boolean recentlyHit
    ) {
//        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
//        World world = getLocation().getWorld();
//        for (Player player : level.getPlayers(serverPlayer -> serverPlayer.isRealPlayer)) {
//            new ItemDisplayDetection(getLocation(), WitherRealmKey.getInstance().getResult(),
//                    List.of((org.bukkit.entity.Player) player.getBukkitLivingEntity()));
//        }
    }

    private boolean shouldExcludeEntity(LivingEntity entity) {
        return entity instanceof Player;
    }

    @Override
    public void onRemoval(RemovalReason reason) {
        super.onRemoval(reason);
        if (reason.equals(RemovalReason.KILLED)) {
            midbossInstance.bossKilled();
        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity source) {
        return super.doHurtTarget(level, source);
    }

    // ==============================
    // Boss Name
    // ==============================

    @Override
    public Component getMobName() {
        return Component.literal("The Gatekeeper")
                .setStyle(
                        Style.EMPTY.withColor(
                                TextColor.fromRgb(TextUtil.DARK_PURPLE.value())
                        )
                );
    }

    // ==============================
    // Spawning
    // ==============================

    @Override
    public org.bukkit.entity.LivingEntity spawn(Location location) {

        this.setPosRaw(
                location.getX(),
                location.getY(),
                location.getZ()
        );

        ((CraftWorld) location.getWorld())
                .getHandle()
                .addFreshEntity(this);

        return this.getBukkitEntity();
    }

    @Override
    public boolean isPowered() {
        return super.isPowered();
    }

    // ==============================
    // Boss Bar
    // ==============================

    private void setBossBarProgress() {

        float phase3Progress = (float) (
                Float.min(
                        this.getHealth() / (this.getMaxHealth() / 2),
                        1
                ) * phase2Damage
        );

        float phase2Progress = (float) (
                Float.max(
                        (this.getHealth() - (this.getMaxHealth() / 2))
                                / (this.getMaxHealth() / 2),
                        0
                ) * (1 - phase2Damage)
        );

        bossEvent.setProgress(phase3Progress + phase2Progress);
    }
}
