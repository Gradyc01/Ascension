package org.bingo4.Mobs.Skeletons;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
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
import org.bingo4.Items.Uncraftable.Skulls.CreeperHead;
import org.bingo4.Items.Uncraftable.Skulls.SkeletonSkull;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bingo4.Utility.NMSMobUtil.removeAllEquipmentDropChance;

public class WitherRealmSkeleton extends Skeleton implements RealmNMSMob {
    private final List<ItemStack> loot;
    private final Random random;
    private final Component name;
    public WitherRealmSkeleton(World world, Random random) {
        super(EntityTypes.SKELETON, ((CraftWorld) world).getHandle());
        this.random = random;
        name = getMobName();
        giveAttributes();
        loot = new ArrayList<>(List.of(new ItemStack(Material.BONE)));
        NMSMobUtil.generateRandomArmor(this.equipment, random);
        removeAllEquipmentDropChance(this);
    }

    private void giveAttributes() {
        this.equipment.set(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(initBow()));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
//        this.getAttribute(Attributes.ARMOR).setBaseValue(5.0F);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0F);
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
//        meta.addEnchant(Enchantment.POWER, 1, true);
        if (random.nextDouble() < 0.05) meta.addEnchant(Enchantment.FLAME, 1, true);
        if (random.nextDouble() < 0.10) meta.addEnchant(Enchantment.PUNCH, 2, true);
        meta.setEnchantmentGlintOverride(false);
        bow.setItemMeta(meta);
        ItemMeta meta2 = bow.getItemMeta();
        if (meta2.hasEnchant(Enchantment.FLAME) || meta2.hasEnchant(Enchantment.PUNCH)) meta2.setEnchantmentGlintOverride(true);
        bow.setItemMeta(meta2);
        return bow;
    }

    @Override
    protected void dropFromLootTable(ServerLevel level, DamageSource damageSource, boolean playerKill) {
        if (playerKill) {
            for (ItemStack stack : loot) {
                this.spawnAtLocation(level, CraftItemStack.asNMSCopy(stack));
            }
        }
        NMSMobUtil.attemptToDropItemStack(SkeletonSkull.getInstance().getResult(),  damageSource,this, 0.02);
    }


    @Override
    public Component getMobName() {
        return Component.literal("Skeleton").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(TextUtil.WHITE.value())));
    }

    @Override
    public LivingEntity spawn(Location location) {
        this.setPosRaw(location.getX(), location.getY(), location.getZ());
        ((CraftWorld) location.getWorld()).getHandle().addFreshEntity(this);
        equipment.set(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.LEATHER_HELMET)));
        return this.getBukkitEntity();
    }
}
