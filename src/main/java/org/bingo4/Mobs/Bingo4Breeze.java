package org.bingo4.Mobs;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.breeze.Breeze;
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

public class Bingo4Breeze extends Breeze implements RealmNMSMob {
    private final Random random;
    private final Component name;

    public Bingo4Breeze(World world, Random random) {
        super(EntityTypes.BREEZE, ((CraftWorld) world).getHandle());
        name = getMobName();
        this.random = random;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        boolean bool = super.hurtServer(level, damageSource, amount);
        this.setCustomName(NMSMobUtil.generateHealthText(name, this));
        return bool;
    }

    @Override
    public Component getMobName() {
        return Component.literal("Breeze").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(TextUtil.GREEN.value())));
    }

    @Override
    public LivingEntity spawn(Location location) {
        this.setPosRaw(location.getX(), location.getY(), location.getZ());
        ((CraftWorld) location.getWorld()).getHandle().addFreshEntity(this);
        equipment.set(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.LEATHER_HELMET)));
        return this.getBukkitEntity();
    }
}
