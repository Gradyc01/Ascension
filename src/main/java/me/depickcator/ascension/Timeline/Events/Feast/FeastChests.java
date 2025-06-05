package me.depickcator.ascension.Timeline.Events.Feast;


import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.CustomChestLoot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class FeastChests {
    private final Location location;
    private final CustomChestLoot loot;
    private final Ascension plugin;
    private int delay;
    private final int x;
    private final int z;
    private final int y;
    private final boolean launchFireball;
    private final Random random;
    public FeastChests(Location location, CustomChestLoot lootTable, int delay, boolean createNewYValue, boolean launchFireball) {
        this.location = location;
        this.loot = lootTable;
        this.delay = delay;
        random = new Random();
        this.launchFireball = launchFireball;
        plugin = Ascension.getInstance();
        x = location.getBlockX();
        z = location.getBlockZ();
        y = createNewYValue ? location.getWorld().getHighestBlockYAt(x, z) + 1 : location.getBlockY();
        delayLoop();

    }

    public FeastChests(Location location, CustomChestLoot lootTable) {
        this(location, lootTable, 0, true, true);
    }

    private void delayLoop() {
        new BukkitRunnable() {
            int timer = delay;
            @Override
            public void run() {
                if (!plugin.getGameState().inGame() || timer <= 0) {
                    spawnInChest();
                    cancel();
                    return;
                }
                plugin.getWorld().playSound(location, Sound.ENTITY_BLAZE_SHOOT, 10.0F, 1.0F);
                TextUtil.debugText("FeastChest Delay Timer: " + timer);
                timer--;
            }
        }.runTaskTimer(plugin, 0,  20);
    }

    private void spawnInChest() {
        TextUtil.debugText("FeastChest Location" + x + ", " + y + ", " + z);
        lightningStrike();
        if (launchFireball) {
            fireBall();
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                Block block = plugin.getWorld().getBlockAt(x, y, z);
                block.setType(Material.CHEST);
                BlockState state = block.getState();
                Chest chest = (Chest) state;
                loot.populateLoot(chest.getBlockInventory(), random, 1);
                lightningStrike();
            }
        }.runTaskLater(plugin, 8 * 20);
        TextUtil.debugText("Chest Spawn");
    }

    private void lightningStrike() {
        location.getWorld().strikeLightningEffect(new Location(location.getWorld(), x, y - 1, z));
        location.getWorld().playSound(location, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 5.0F, 1.0F);
        location.getWorld().playSound(location, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 5.0F, 0.0F);
    }

    private void fireBall() {
        Fireball fireball = (Fireball) plugin.getWorld().spawnEntity(new Location(location.getWorld(),
                x,
                y + 250,
                z),
                EntityType.FIREBALL);
        Vector v = new Vector(0, -0.1, 0);
//        fireball.setDirection(v);
        fireball.setVelocity(v);
        fireball.setInvulnerable(true);

        Fireball fireball2 = (Fireball) plugin.getWorld().spawnEntity(new Location(location.getWorld(),
                        x + random.nextInt(-10, 10),
                        y + 100,
                        z + random.nextInt(- 10, 10)),
                EntityType.FIREBALL);
        Vector v2 = new Vector(0, -0.1, 0);
        fireball2.setVelocity(v2);
        fireball2.setInvulnerable(true);
    }
}
