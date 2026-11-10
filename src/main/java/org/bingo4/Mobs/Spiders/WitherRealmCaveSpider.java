package org.bingo4.Mobs.Spiders;


import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.skeleton.Bogged;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.skeleton.Stray;
import net.minecraft.world.entity.monster.spider.CaveSpider;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.entity.player.Player;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Utility.NMSMobUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.event.entity.EntityPotionEffectEvent;

import java.util.Random;

public class WitherRealmCaveSpider extends CaveSpider implements RealmNMSMob {
    private final Random random;
    private final Component name;
    public WitherRealmCaveSpider(World world, Random random) {
        super(EntityTypes.CAVE_SPIDER, ((CraftWorld) world).getHandle());
        this.random = random;
        name = getMobName();
        giveEffects();
        super.targetSelector.removeAllGoals(goal -> true);
        super.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Skeleton.class, Stray.class, Bogged.class, Drowned.class)));
        super.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    private void giveEffects() {
        double num = random.nextDouble();
        Holder<MobEffect> effect = null;
        if (num <= 0.02) effect = MobEffects.INVISIBILITY;
        else if (num <= 0.15) effect = MobEffects.STRENGTH;
        else if (num <= 0.35) effect = MobEffects.FIRE_RESISTANCE;
        else if (num <= 0.60) effect = MobEffects.SPEED;
        if (effect != null) this.addEffect(new MobEffectInstance(effect, -1, 0, false, true));
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        boolean bool = super.hurtServer(level, damageSource, amount);
        this.setCustomName(NMSMobUtil.generateHealthText(name, this));
        return bool;
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity source) {
        if (super.doHurtTarget(level, source)) {
            if (source instanceof LivingEntity) {
                ((LivingEntity)source).addEffect(new MobEffectInstance(MobEffects.POISON, 7 * 20, 0), this, EntityPotionEffectEvent.Cause.ATTACK);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
    }


    @Override
    public Component getMobName() {
        return Component.literal("Dungeon Cave Spider").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(TextUtil.GREEN.value())));
    }

    @Override
    public org.bukkit.entity.LivingEntity spawn(Location location) {
        this.setPosRaw(location.getX(), location.getY(), location.getZ());
        ((CraftWorld) location.getWorld()).getHandle().addFreshEntity(this);
        return this.getBukkitEntity();
    }
}
