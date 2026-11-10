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
import org.bukkit.inventory.meta.Damageable;

public class WitherRealmSkeletonKnight extends Skeleton implements RealmNMSMob {
    private final Component name;
    public WitherRealmSkeletonKnight(World world) {
        super(EntityTypes.SKELETON, ((CraftWorld) world).getHandle());
        name = getMobName();
        giveAttributes();
    }

    private void giveAttributes() {
        this.equipment.set(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(initSword()));
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0F);
        this.equipment.set(EquipmentSlot.HEAD, initArmor(Material.GOLDEN_HELMET, "Helmet"));
        this.equipment.set(EquipmentSlot.CHEST, initArmor(Material.GOLDEN_CHESTPLATE, "Chestplate"));
        this.equipment.set(EquipmentSlot.LEGS, initArmor(Material.GOLDEN_LEGGINGS, "Leggings"));
        this.equipment.set(EquipmentSlot.FEET, initArmor(Material.GOLDEN_BOOTS, "Boots"));
        super.targetSelector.removeAllGoals(goal -> true);
        super.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Skeleton.class, Stray.class, Bogged.class, Drowned.class)));
        super.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.reassessWeaponGoal();
        this.setHealth(this.getMaxHealth());
        this.setShouldBurnInDay(false);
        this.setPersistenceRequired();
        NMSMobUtil.removeAllEquipmentDropChance(this);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        boolean bool = super.hurtServer(level, damageSource, amount);
        this.setCustomName(NMSMobUtil.generateHealthText(name, this));
        return bool;
    }


    private ItemStack initSword() {
        ItemStack item = new ItemStack(Material.GOLDEN_SWORD);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.customName(TextUtil.makeText("Skeleton Knight's Sword", TextUtil.YELLOW));
        meta.addEnchant(Enchantment.SHARPNESS, 3, true);
        meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        meta.setMaxDamage(255);
        item.setItemMeta(meta);
        return item;
    }

    private net.minecraft.world.item.ItemStack initArmor(Material material, String name) {
        ItemStack item = new ItemStack(material);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.customName(TextUtil.makeText("Skeleton Knight " + name, TextUtil.YELLOW));
        meta.addEnchant(Enchantment.PROTECTION, 3, true);
        meta.addEnchant(Enchantment.UNBREAKING, 3, true);
        meta.addEnchant(Enchantment.FIRE_PROTECTION, 1, true);
        meta.setMaxDamage(255);
        item.setItemMeta(meta);
        return CraftItemStack.asNMSCopy(item);
    }


    @Override
    public Component getMobName() {
        return Component.literal("Skeleton Knight").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(TextUtil.YELLOW.value())));
    }

    @Override
    public LivingEntity spawn(Location location) {
        this.setPosRaw(location.getX(), location.getY(), location.getZ());
        ((CraftWorld) location.getWorld()).getHandle().addFreshEntity(this);
        return this.getBukkitEntity();
    }
}
