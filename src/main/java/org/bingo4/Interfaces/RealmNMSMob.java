package org.bingo4.Interfaces;

import net.minecraft.network.chat.Component;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public interface RealmNMSMob {
    //    void giveAttributes();
    Component getMobName();
    LivingEntity spawn(Location location);
}
