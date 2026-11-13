
package org.bingo4.Structures.AnchorPoints;

import org.bingo4.Bingo4;
import org.bingo4.Utility.FloodBuild.FloodBlocks;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bingo4.Structures.AnchorPoints.AnchorPoint.OPEN;
import static org.bingo4.Structures.AnchorPoints.AnchorPoint.UNINITIALIZED;

public class AnchorPointInProgress {

    private final AnchorPoint anchorPoint;
    private final Bingo4 plugin = Bingo4.getInstance();

    private static final String UNBREAKABLE = "UNBREAKABLE";

    // Duration the anchor point remains open (seconds).
    private static final int OPEN_DURATION = 30;

    private BukkitRunnable task;
    private boolean active = false;

    private Location location;
    private final FloodBlocks floodBlocks;

    public AnchorPointInProgress(AnchorPoint anchorPoint, FloodBlocks floodBlocks) {
        this.anchorPoint = anchorPoint;
        this.floodBlocks = floodBlocks;
    }

    public void start() {
        if (active) return;

        location = anchorPoint.getLocation().getBlock().getLocation();

        location.getWorld().spawnParticle(Particle.EXPLOSION, location.clone().add(0, 2,0), 200, 5, 5, 5);
        location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 10f, 0f);
        // Build the Ender Chest.

        if (anchorPoint.isState(UNINITIALIZED)) {
            anchorPoint.initializeAnchor();
            new AnchorPointBlockGUI(anchorPoint.getCreator(), location.getBlock(), anchorPoint);
        } else {
            anchorPoint.openAnchor();
        }

        active = true;

        loop();
    }

    private void loop() {
        task = new BukkitRunnable() {

            private int timeRemaining = OPEN_DURATION;

            @Override
            public void run() {
                if (timeRemaining <= 0 || !plugin.getGameState().inGame() || !anchorPoint.isState(OPEN)) {
                    end();
                    cancel();
                    return;
                }

                if (timeRemaining % 3 == 0) {
                    location.getWorld().playSound(location, Sound.BLOCK_PORTAL_AMBIENT, 3f, 1f);
                }

                spawnPortalParticles();

                timeRemaining--;
            }
        };

        task.runTaskTimer(plugin, 20L, 20L);
    }

    public void end() {
        if (!active) return;

        active = false;

        if (task != null) {
            task.cancel();
            task = null;
        }

        anchorPoint.closeAnchor();
        unFloodBlocks();
    }

    private void unFloodBlocks() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!floodBlocks.unFlood()) {
                    end();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }

    public boolean isActive() {
        return active;
    }

    private void spawnPortalParticles() {
        Location center = location.clone().add(0.5, 1, 0.5);

        // 1x2 vertical particle pattern.
        for (double y = 0; y <= 2; y += 0.2) {
            for (double x = -0.4; x <= 0.4; x += 0.2) {
                location.getWorld().spawnParticle(
                        org.bukkit.Particle.PORTAL,
                        center.clone().add(x, y, 0),
                        2,
                        0.02, 0.02, 0.02,
                        0.01
                );
            }
        }
    }
}
