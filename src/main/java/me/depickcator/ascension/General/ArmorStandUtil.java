package me.depickcator.ascension.General;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import net.kyori.adventure.text.Component;

public class ArmorStandUtil {

    //Making a Armor Stand for the purpose of marking a specific location
    public static ArmorStand makeMarkerArmorStand(Location loc, World world, String name) {
        ArmorStand armorStand = (ArmorStand) world.spawnEntity(loc, EntityType.ARMOR_STAND);
        armorStand.setGravity(false);         // Prevents the armor stand from falling
        armorStand.setVisible(false);         // Hides the armor stand body
        armorStand.customName(Component.text(name));
        armorStand.setInvisible(true);
        armorStand.setMarker(true);
        armorStand.setInvulnerable(true);
        armorStand.setSilent(true);
        armorStand.setCustomNameVisible(true); //TODO Remove After Completion
        return armorStand;
    }
}
