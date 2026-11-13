package org.bingo4.Structures.AnchorPoints;

import org.bingo4.Bingo4;
import org.bingo4.Teams.Team;
import org.bingo4.Utility.FloodBuild.FloodBlocks;
import org.bingo4.Utility.FloodBuild.Floodable;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.Random;

public class CreateAnchorPointAnimation implements Floodable {
    private final ArmorStand armorStand;
    private final AnchorPoint anchorPoint;
    private final Team team;
    private final FloodBlocks floodBlocks;

    public CreateAnchorPointAnimation(Team team, AnchorPoint anchorPoint) {
        this.team = team;
        this.anchorPoint = anchorPoint;
        armorStand = initArmorstand();
        floodBlocks = new FloodBlocks(anchorPoint.getLocation().clone().add(0, -1, 0), 1.10, this);
        start();
    }

    private void start() {
        anchorPoint.getLocation().getWorld().playSound(anchorPoint.getLocation(), Sound.AMBIENT_NETHER_WASTES_MOOD, 20, 1);
        int ticks = 10 * 20;
        Random random = new Random();
        floodBlocks.start(random);
        new BukkitRunnable() {
            int timePassed = 0;
            @Override
            public void run() {
                if (timePassed % 50 == 0) {
                    spinParticle(0); spinParticle(Math.PI / 2);
                    spinParticle(Math.PI); spinParticle( 3 * Math.PI / 2);
                    spinParticle(Math.PI /4);spinParticle( 3 * Math.PI / 4);
                    spinParticle(5 * Math.PI / 4);spinParticle( 7 * Math.PI / 4);
                    floodBlocks.flood(random);
                    anchorPoint.getLocation().getWorld().playSound(anchorPoint.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 20, 1);
                }

                if (timePassed % 20 == 0) {
                    armorStand.customName(TextUtil.makeText("A Rift Opens in: ", TextUtil.GOLD)
                            .append(TextUtil.makeText(TextUtil.formatTime(ticks / 20 - timePassed / 20), TextUtil.AQUA)));
                    TextUtil.debugText("[Create Anchor Point animation] " + timePassed / 20 + " Seconds Passed" );
                }
                if (timePassed++ >= ticks) {
                    armorStand.remove();
                    new AnchorPointInProgress(anchorPoint, floodBlocks).start();
                    cancel();
                }
            }
        }.runTaskTimer(Bingo4.getInstance(), 0, 1);
    }

    private ArmorStand initArmorstand() {
        ArmorStand entity = (ArmorStand) anchorPoint.getLocation().getWorld().spawnEntity(anchorPoint.getLocation().clone().add(0, 1.5, 0), EntityType.ARMOR_STAND);
        entity.setMarker(true);
        entity.setCustomNameVisible(true);
        entity.setVisible(false);
        return entity;
    }

    private void spinParticle(double initialTime) {
        new BukkitRunnable() {
            double t = initialTime;
            double y = 0;
            @Override
            public void run() {
                t += 0.05;
                y += 0.03;
                double x = 2 * Math.cos(t);
                double z = 2 * Math.sin(t);
                Location loc = anchorPoint.getLocation().clone().add(x, y, z);
                loc.getWorld().spawnParticle(Particle.FLAME, loc, 1, 0, 0, 0, 0.2);
                loc.getWorld().spawnParticle(Particle.PORTAL, loc, 1, 0, 0, 0, 0);
//                TextUtil.debugText("RealmAnimation", "Spawned particle at (" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ")");
                if (t >= Math.PI * 4 + initialTime) cancel();
            }
        }.runTaskTimer(Bingo4.getInstance(), 0, 1);

    }

    @Override
    public Map<Material, Integer> getUnFloodables() {
        return Map.of(
                Material.OBSIDIAN, 40,
                Material.CRYING_OBSIDIAN, 30,
                Material.PURPLE_CONCRETE, 40,
                Material.ENDER_CHEST, 0,
                Material.AIR, 0
        );
    }

    @Override
    public Block changeBlock(Block block, Random r, FloodBlocks floodBlocks) {
        block.setType(floodBlocks.chooseNextMaterial(r));
        block.setMetadata("UNBREAKABLE", new FixedMetadataValue(Bingo4.getInstance(), true));
        return block;
    }

    @Override
    public boolean isUnFloodable(Block b) {
        return getUnFloodables().containsKey(b.getType());
    }

    @Override
    public double getNewSuccessRate(double oldSuccessRate, Random r) {
        return oldSuccessRate - r.nextDouble(0.1, 0.20);
    }
}
