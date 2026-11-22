package org.bingo4.Mobs.Skeletons;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.skeleton.Bogged;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.skeleton.Stray;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.entity.player.Player;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Utility.NMSMobUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class SkeletonSoldier extends Skeleton implements RealmNMSMob {
    private final Component name;
    private final Random random;
    public SkeletonSoldier(World world, Random random) {
        super(EntityTypes.SKELETON, ((CraftWorld) world).getHandle());
        this.random = random;
        name = getMobName();
        giveAttributes();
    }

    private void giveAttributes() {
        this.equipment.set(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(new ItemStack(Material.IRON_SWORD)));
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0F);
        super.targetSelector.removeAllGoals(goal -> true);
        super.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Skeleton.class, Stray.class, Bogged.class, Drowned.class)));
        super.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.reassessWeaponGoal();
        this.setHealth(this.getMaxHealth());
        this.setShouldBurnInDay(false);
        this.setPersistenceRequired();
        NMSMobUtil.generateRandomArmor(this.equipment, random);
        NMSMobUtil.removeAllEquipmentDropChance(this);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        boolean bool = super.hurtServer(level, damageSource, amount);
        this.setCustomName(NMSMobUtil.generateHealthText(name, this));
        return bool;
    }


    @Override
    public Component getMobName() {
        return Component.literal("Skeleton Solider").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(TextUtil.YELLOW.value())));
    }

    @Override
    public LivingEntity spawn(Location location) {
        this.setPosRaw(location.getX(), location.getY(), location.getZ());
        ((CraftWorld) location.getWorld()).getHandle().addFreshEntity(this);
        return this.getBukkitEntity();
    }
}
