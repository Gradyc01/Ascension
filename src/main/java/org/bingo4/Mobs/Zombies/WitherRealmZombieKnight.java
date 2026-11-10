package org.bingo4.Mobs.Zombies;


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
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import org.bingo4.Bingo4;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Utility.NMSMobUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class WitherRealmZombieKnight extends Zombie implements RealmNMSMob {
    private final Component name;
    public WitherRealmZombieKnight(World world) {
        super(EntityTypes.ZOMBIE, ((CraftWorld) world).getHandle());
        name = getMobName();
        giveAttributes();
    }

    private void giveAttributes() {
        this.equipment.set(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(initSword()));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        this.setDropChance(EquipmentSlot.LEGS, 0.0F);
        this.setDropChance(EquipmentSlot.FEET, 0.0F);

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0F);
        this.equipment.set(EquipmentSlot.HEAD, initArmor(Material.LEATHER_HELMET, "Helmet", 3.0));
        this.equipment.set(EquipmentSlot.CHEST, initArmor(Material.LEATHER_CHESTPLATE, "Chestplate", 7.5));
        this.equipment.set(EquipmentSlot.LEGS, initArmor(Material.LEATHER_LEGGINGS, "Leggings", 5.25));
        this.equipment.set(EquipmentSlot.FEET, initArmor(Material.LEATHER_BOOTS, "Boots", 3.25));
        super.targetSelector.removeAllGoals(goal -> true);
        super.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Skeleton.class, Stray.class, Bogged.class, Drowned.class)));
        super.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.setHealth(this.getMaxHealth());
        this.setShouldBurnInDay(false);
        this.setPersistenceRequired();
//        this.equipment(EquipmentSlot.Mob.getEquipmentForSlot(EquipmentSlot.HEAD, random.nextInt(4));
    }

    @Override
    protected void dropFromLootTable(ServerLevel level, DamageSource damageSource, boolean playerKill) {
//        NMSMobUtil.attemptToDropItemStack(ZombieHeart.getInstance().getResult(), damageSource, this, 0.05);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        boolean bool = super.hurtServer(level, damageSource, amount);
        this.setCustomName(NMSMobUtil.generateHealthText(name, this));
        return bool;
    }


    private ItemStack initSword() {
        ItemStack item = new ItemStack(Material.GOLDEN_SHOVEL);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.addEnchant(Enchantment.SHARPNESS, 3, true);
        meta.addAttributeModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(
                new NamespacedKey(Bingo4.getInstance(), "zombie_knight_sword")
                , 1, AttributeModifier.Operation.ADD_NUMBER));
        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, new AttributeModifier(
                new NamespacedKey(Bingo4.getInstance(), "zombie_knight_sword_speed")
                , 0.1, AttributeModifier.Operation.ADD_NUMBER));
        meta.setMaxDamage(255);
        item.setItemMeta(meta);
        return item;
    }

    private net.minecraft.world.item.ItemStack initArmor(Material material, String name, double amount) {
        ItemStack item = new ItemStack(material);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.customName(TextUtil.makeText("Zombie Knight " + name, TextUtil.YELLOW));
        meta.addAttributeModifier(Attribute.ARMOR, new AttributeModifier(
                new NamespacedKey(Bingo4.getInstance(), "zombie_knight_" + name),
                amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ARMOR));
        meta.addAttributeModifier(Attribute.MAX_HEALTH, new AttributeModifier(
                new NamespacedKey(Bingo4.getInstance(), "zombie_knight_" + name + "_health"),
                2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ARMOR));
        meta.setMaxDamage(600);
        meta.setEnchantmentGlintOverride(true);
        item.setItemMeta(meta);
        LeatherArmorMeta meta1 = (LeatherArmorMeta) item.getItemMeta();
        meta1.setColor(Color.fromRGB(255, 105, 180));
        item.setItemMeta(meta1);
        return CraftItemStack.asNMSCopy(item);
    }


    @Override
    public Component getMobName() {
        return Component.literal("Zombie Knight").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(TextUtil.YELLOW.value())));
    }

    @Override
    public org.bukkit.entity.LivingEntity spawn(Location location) {
        this.setPosRaw(location.getX(), location.getY(), location.getZ());
        ((CraftWorld) location.getWorld()).getHandle().addFreshEntity(this);
        return this.getBukkitEntity();
    }
}
