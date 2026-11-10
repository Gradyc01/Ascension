package org.bingo4.Mobs;

import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.player.Player;
import org.bingo4.Player.Data.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemDisplayDetection extends ItemDisplay {
    public ItemDisplayDetection(Location location, ItemStack itemStack) {
        this(location, itemStack, location.getWorld().getPlayers());
    }

    public ItemDisplayDetection(Location location, ItemStack itemStack, List<org.bukkit.entity.Player> players) {
        super(location, itemStack, players);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive()) {
            checkIfPlayerWalksIn();
        }
    }

    private void checkIfPlayerWalksIn() {
        try {
            Player player = this.level().findNearbyPlayer(this, 1.5, p -> playerUUIDs.contains(p.getUUID()));
            if (player == null) return;
            uponPlayerWalkIn((org.bukkit.entity.Player)  player.getBukkitEntity());
        } catch (Exception e) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    protected void uponPlayerWalkIn(org.bukkit.entity.Player player) {
        PlayerUtil.giveItem(player, CraftItemStack.asBukkitCopy(this.getItemStack()));
        this.remove(RemovalReason.KILLED);
    }
}
