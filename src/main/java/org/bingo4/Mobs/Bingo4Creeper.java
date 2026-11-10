package org.bingo4.Mobs;

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
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.skeleton.Bogged;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.skeleton.Stray;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.entity.player.Player;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.Items.Uncraftable.Skulls.CreeperHead;
import org.bingo4.Utility.NMSMobUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bingo4.Utility.NMSMobUtil.removeAllEquipmentDropChance;

public class Bingo4Creeper extends Creeper implements RealmNMSMob {
    private final List<ItemStack> loot;
    private final Random random;
    private final Component name;
    public Bingo4Creeper(World world, Random random) {
        super(EntityTypes.CREEPER, ((CraftWorld) world).getHandle());
        this.random = random;
        name = getMobName();
        giveAttributes();
        loot = new ArrayList<>(List.of(new ItemStack(Material.BONE)));
        NMSMobUtil.generateRandomArmor(this.equipment, random);
        removeAllEquipmentDropChance(this);
    }

    private void giveAttributes() {
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
//        this.getAttribute(Attributes.ARMOR).setBaseValue(5.0F);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0F);
        this.setHealth(this.getMaxHealth());
        this.setPersistenceRequired();
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

    @Override
    protected void dropFromLootTable(ServerLevel level, DamageSource damageSource, boolean playerKill) {
        if (playerKill) {
            for (ItemStack stack : loot) {
                this.spawnAtLocation(level, CraftItemStack.asNMSCopy(stack));
            }
        }
        NMSMobUtil.attemptToDropItemStack(CreeperHead.getInstance().getResult(),  damageSource,this, 0.02);
    }


    @Override
    public Component getMobName() {
        return Component.literal("Creeper").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(TextUtil.WHITE.value())));
    }

    @Override
    public LivingEntity spawn(Location location) {
        this.setPosRaw(location.getX(), location.getY(), location.getZ());
        ((CraftWorld) location.getWorld()).getHandle().addFreshEntity(this);
        return this.getBukkitEntity();
    }
}
