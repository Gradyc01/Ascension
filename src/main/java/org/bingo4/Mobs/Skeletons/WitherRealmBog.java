package org.bingo4.Mobs.Skeletons;


import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Utility.NMSMobUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class WitherRealmBog extends Bogged implements RealmNMSMob {
    private final Random random;
    private final Component name;
    public WitherRealmBog(World world, Random random) {
        super(EntityTypes.BOGGED, ((CraftWorld) world).getHandle());
        this.random = random;
        name = getMobName();
        giveAttributes();
        NMSMobUtil.generateRandomArmor(this.equipment, random);
    }

    private void giveAttributes() {
        this.equipment.set(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(initBow()));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.getAttribute(Attributes.ARMOR).setBaseValue(3.5F);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0F);
        this.setHealth(this.getMaxHealth());
        this.setShouldBurnInDay(false);
        this.setPersistenceRequired();
        super.targetSelector.removeAllGoals(goal -> true);
        super.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Skeleton.class, Stray.class, Bogged.class, Drowned.class)));
        super.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.reassessWeaponGoal();
//        this.equipment(EquipmentSlot.Mob.getEquipmentForSlot(EquipmentSlot.HEAD, random.nextInt(4));
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        boolean bool = super.hurtServer(level, damageSource, amount);
        this.setCustomName(NMSMobUtil.generateHealthText(name, this));
        return bool;
    }


    private ItemStack initBow() {
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta meta = bow.getItemMeta();
        meta.addEnchant(Enchantment.POWER, 1, true);
        if (random.nextDouble() < 0.10) meta.addEnchant(Enchantment.PUNCH, 4, true);
        meta.setEnchantmentGlintOverride(false);
        bow.setItemMeta(meta);
        ItemMeta meta1 = bow.getItemMeta();
        if (meta1.hasEnchant(Enchantment.FLAME) || meta1.hasEnchant(Enchantment.PUNCH)) meta1.setEnchantmentGlintOverride(true);
        bow.setItemMeta(meta1);
        return bow;
    }


    @Override
    public Component getMobName() {
        return Component.literal("Dungeon Skeleton").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(TextUtil.WHITE.value())));
    }

    @Override
    public LivingEntity spawn(Location location) {
        this.setPosRaw(location.getX(), location.getY(), location.getZ());
        ((CraftWorld) location.getWorld()).getHandle().addFreshEntity(this);
        equipment.set(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.LEATHER_HELMET)));
        return this.getBukkitEntity();
    }

    @Override
    protected @NotNull AbstractArrow getArrow(net.minecraft.world.item.ItemStack arrow, float velocity, @Nullable net.minecraft.world.item.ItemStack weapon) {
        AbstractArrow abstractArrow = super.getArrow(arrow, velocity, weapon);
        if (abstractArrow instanceof Arrow arrow1) {
            arrow1.addEffect(new MobEffectInstance(MobEffects.POISON, 6 * 20, 0));
        }

        return abstractArrow;
    }
}
