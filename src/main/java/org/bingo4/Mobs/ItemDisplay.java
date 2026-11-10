package org.bingo4.Mobs;

import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.Player;
import org.bingo4.Bingo4;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ItemDisplay extends Display.ItemDisplay {
    protected final Set<UUID> playerUUIDs;
    public ItemDisplay(Location location, ItemStack itemStack) {
        this(location, itemStack, location.getWorld().getPlayers());
    }

    public ItemDisplay(Location location, ItemStack itemStack, List<org.bukkit.entity.Player> players) {
        super(EntityTypes.ITEM_DISPLAY, ((CraftWorld) location.getWorld()).getHandle());
        this.setPosRaw(location.getX(), location.getY(), location.getZ());
        ((CraftWorld) location.getWorld()).getHandle().addFreshEntity(this);
        setItemStack(CraftItemStack.asNMSCopy(itemStack));
        setCustomName(CraftItemStack.asNMSCopy(itemStack).getDisplayName());
        setCustomNameVisible(true);
        //TODO: switch this line out
//        location.getWorld().getPlayers().forEach(player ->
//                {if (!player.isOp()) player.hideEntity(TrablesAdditions.getInstance(), this.getBukkitEntity());});
        location.getWorld().getPlayers().forEach(player ->
        {player.hideEntity(Bingo4.getInstance(), this.getBukkitEntity());});
        playerUUIDs = new HashSet<>();
        for (org.bukkit.entity.Player player : players) {
            playerUUIDs.add(player.getUniqueId());
            player.showEntity(Bingo4.getInstance(), this.getBukkitEntity());
        }
        this.setViewRange(196);
    }
}
