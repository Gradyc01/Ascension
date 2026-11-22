package org.bingo4.Mobs.Slimes;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.cubemob.Slime;
import net.minecraft.world.entity.monster.skeleton.Bogged;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.skeleton.Stray;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.entity.player.Player;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Utility.NMSMobUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;

public class Bingo4Slime extends Slime implements RealmNMSMob {
    private Component name;

    public Bingo4Slime(World world) {
        this(world, 3);
    }

    public Bingo4Slime(World world, int size) {
        super(EntityTypes.SLIME, ((CraftWorld) world).getHandle());
        this.setSize(size, true);

        name = getMobName();
        giveAttributes();
        super.targetSelector.removeAllGoals(goal -> true);
        super.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Skeleton.class, Stray.class, Bogged.class, Drowned.class)));
        super.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        boolean bool = super.hurtServer(level, damageSource, amount);
        this.setCustomName(NMSMobUtil.generateHealthText(name, this));
        return bool;
    }


    private void giveAttributes() {
        NMSMobUtil.removeAllEquipmentDropChance(this);
    }


    @Override
    public Component getMobName() {
        return Component.literal("Slime").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(TextUtil.WHITE.value())));
    }

    @Override
    public org.bukkit.entity.LivingEntity spawn(Location location) {
        this.setPosRaw(location.getX(), location.getY(), location.getZ());
        ((CraftWorld) location.getWorld()).getHandle().addFreshEntity(this);
        return this.getBukkitEntity();
    }
}
