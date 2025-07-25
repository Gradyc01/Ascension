package me.depickcator.ascension.Timeline.Events.Ascension;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Uncraftable.NetherStar.NetherStar;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftItemStack;

public class GatekeeperWither extends WitherBoss {

    private final Ascension plugin;
    public GatekeeperWither(Location location) {
        super(EntityType.WITHER, ((CraftWorld) Ascension.getInstance().getWorld()).getHandle());
        this.setPosRaw(location.getX(), location.getY(), location.getZ());


        plugin = Ascension.getInstance();
        ((CraftWorld) plugin.getWorld()).getHandle().addFreshEntity(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(
                this,
                LivingEntity.class,
                0,
                false,
                false,
                (entity, level) -> shouldExcludeEntity(entity)
        ));
    }

    private boolean shouldExcludeEntity(LivingEntity entity) {
        return (entity instanceof Player) || entity.getType().is(EntityTypeTags.WITHER_FRIENDS);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
//        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
        ItemEntity itemEntity = this.spawnAtLocation(level, CraftItemStack.asNMSCopy(NetherStar.getInstance().getResult()), Vec3.ZERO, ItemEntity::setExtendedLifetime);
        if (itemEntity != null) {
            itemEntity.setExtendedLifetime();
        }
    }


    protected void customServerAiStep(ServerLevel level) {
        if (this.getInvulnerableTicks() > 0) {
            super.customServerAiStep(level);
        } else {
            for(int ix = 1; ix < 3; ++ix) {
                    int alternativeTarget = this.getAlternativeTarget(ix);
                    if (alternativeTarget > 0) {
                        LivingEntity livingEntity = (LivingEntity)level.getEntity(alternativeTarget);
                        if (!shouldExcludeEntity(livingEntity)) super.customServerAiStep(level);
                    }
            }

            if (this.getTarget() != null) {
                this.setAlternativeTarget(0, this.getTarget().getId());
            } else {
                this.setAlternativeTarget(0, 0);
            }

//            if (super.tickCount % 20 == 0) {
//                this.heal(1.0F, EntityRegainHealthEvent.RegainReason.REGEN);
//            }

            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        }

    }



    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (!(target instanceof Player)) {
            super.performRangedAttack(target, distanceFactor);
        }
    }

    @Override
    public void aiStep() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.aiStep();
        this.setDeltaMovement(Vec3.ZERO);       // Reset velocity every tick
        this.setPos(x, y, z);
    }

//    // Convert the NMS WitherBoss (or any NMS Entity) to a Bukkit Entity
//    public Entity toBukkitEntity() {
//        return CraftEntity.getEntity((CraftServer) plugin.getServer(), this);
//    }
}
