package me.depickcator.ascensionBingo.Timeline.Events.CarePackage;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.BuildLobby;
import me.depickcator.ascensionBingo.General.SoundUtil;
import me.depickcator.ascensionBingo.General.TextUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Random;
import org.bukkit.util.Vector;

public class CarePackage {
    private final AscensionBingo plugin;
    private final int RADIUS = 1000;
    private Location spawnPoint;
    private ArmorStand armorStand;
    public CarePackage(AscensionBingo plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        Location spawn = AscensionBingo.getSpawn();
        Random r = new Random();
        int x = spawn.getBlockX() + (int) ((r.nextDouble() * (RADIUS - (- RADIUS))) + 1 - RADIUS);
        int z = spawn.getBlockZ() + (int) ((r.nextDouble() * (RADIUS - (- RADIUS))) + 1 - RADIUS);
        spawnPoint = new Location(plugin.getWorld(), x, 255, z);
        plugin.getServer().broadcast(TextUtil.makeText("A Care Package will drop at: (" + x + ", " + z + ") in 5 minutes", TextUtil.AQUA));
        SoundUtil.broadcastSound(Sound.EVENT_RAID_HORN, 100, 0);
        plugin.getWorld().setChunkForceLoaded((int) Math.floor((double) x /16), (int) Math.floor((double) z /16), true);
        makeCarePackageArmorStand();
        countDown();
    }

    private void makeCarePackageArmorStand() {
        armorStand = (ArmorStand) plugin.getWorld().spawnEntity(spawnPoint, EntityType.ARMOR_STAND);
        armorStand.setVisible(true);         // Hides the armor stand body
        armorStand.customName(TextUtil.makeText("Care Package Incoming", TextUtil.GOLD));
        armorStand.setInvisible(true);
        armorStand.setInvulnerable(true);
        armorStand.setSilent(true);
        armorStand.setCustomNameVisible(true);
    }

    private void countDown() {
        new BukkitRunnable() {
            int timer = 300;
            @Override
            public void run() {
                if (timer <= 0 || !plugin.getGameState().inGame()) {
                    unload();
                    cancel();
                    return;
                }
                if (timer == 4) {
                    fireballAnimation();
                }

                timer--;
//                plugin.getServer().broadcast(TextUtil.makeText("[Debug] Care Package Timer Ran", TextUtil.BLUE));
                plugin.getWorld().spawnParticle(Particle.FLAME, armorStand.getLocation().add(0, 0.5, 0), 50, 3, 3, 3, 0.1);
                plugin.getWorld().playSound(armorStand.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 3, 1);
            }
        }.runTaskTimer(plugin, 30, 20);
    }

    private void unload() {
        armorStand.remove();
        createStructure();
        plugin.getServer().broadcast(TextUtil.makeText("[Debug] Care Package Stopped", TextUtil.BLUE));
        plugin.getWorld().setChunkForceLoaded(
                (int) Math.floor((double) spawnPoint.getBlockX() /16),
                (int) Math.floor((double) spawnPoint.getBlockZ() /16),
                false);
    }


    private void createStructure() {
        BuildLobby.fillArea(-1, -1, -1, 1, 1, 1, "obsidian", armorStand);
        Block block = plugin.getWorld().getBlockAt(armorStand.getLocation());
        block.setType(Material.CHEST);
        BlockState state = block.getState();
        Chest chest = (Chest) state;
        Random random = new Random();
        CarePackageLoot carePackageLoot = new CarePackageLoot(plugin);
        Collection<ItemStack> items = carePackageLoot.populateLoot(chest.getBlockInventory(), random, 1);
    }

    private void fireballAnimation() {
        Fireball fireball = (Fireball) plugin.getWorld().spawnEntity(armorStand.getLocation().add(0, 100, 0), EntityType.FIREBALL);
        Vector v = new Vector(0, -1, 0);
        fireball.setDirection(v);
        fireball.setInvulnerable(true);

    }


}
