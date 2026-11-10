//package org.bingo4.Timeline.Events.CarePackage;
//
//import org.bingo4.Bingo4;
//import org.bingo4.General.BuildLobby;
//import org.bingo4.MainMenuUI.Map.MapItem;
//import org.bingo4.Utility.SoundUtil;
//import org.bingo4.Utility.TextUtil;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.Particle;
//import org.bukkit.Sound;
//import org.bukkit.block.Block;
//import org.bukkit.block.BlockState;
//import org.bukkit.block.Chest;
//import org.bukkit.entity.ArmorStand;
//import org.bukkit.entity.EntityType;
//import org.bukkit.entity.Fireball;
//import org.bukkit.scheduler.BukkitRunnable;
//import org.bukkit.util.Vector;
//
//import java.util.Random;
//
//public class CarePackage {
//    private final Bingo4 plugin;
//    private final int RADIUS;
//    private Location spawnPoint;
//    private ArmorStand armorStand;
//    public CarePackage(int radius) {
//        this.plugin = Bingo4.getInstance();
//        RADIUS = radius;
//        load();
//    }
//
//    private void load() {
//        Location spawn = Bingo4.getSpawn();
//        Random r = new Random();
//        int x = spawn.getBlockX() + (int) ((r.nextDouble() * (RADIUS - (- RADIUS))) + 1 - RADIUS);
//        int z = spawn.getBlockZ() + (int) ((r.nextDouble() * (RADIUS - (- RADIUS))) + 1 - RADIUS);
//        spawnPoint = new Location(plugin.getWorld(), x, 255, z);
//        plugin.getServer().broadcast(TextUtil.makeText("A Care Package will drop at: (" + x + ", " + z + ") in 5 minutes", TextUtil.AQUA));
//        Bingo4.getInstance().
//                getSettingsUI().getSettings().getTimeline().
//                getMapItems().addMapItem(new MapItem("Care Package", x, z, MapItem.CARE_PACKAGE));
//        SoundUtil.broadcastSound(Sound.EVENT_RAID_HORN, 100, 0);
//        plugin.getWorld().setChunkForceLoaded((int) Math.floor((double) x /16), (int) Math.floor((double) z /16), true);
//        makeCarePackageArmorStand();
//        countDown();
//    }
//
//    private void makeCarePackageArmorStand() {
//        armorStand = (ArmorStand) plugin.getWorld().spawnEntity(spawnPoint, EntityType.ARMOR_STAND);
//        armorStand.setVisible(true);         // Hides the armor stand body
//        armorStand.customName(TextUtil.makeText("Care Package Incoming", TextUtil.GOLD));
//        armorStand.setInvisible(true);
//        armorStand.setInvulnerable(true);
//        armorStand.setSilent(true);
//        armorStand.setCustomNameVisible(true);
//    }
//
//    private void countDown() {
//        new BukkitRunnable() {
//            int timer = 300;
//            @Override
//            public void run() {
//                if (timer <= 0 || !plugin.getGameState().inGame()) {
//                    unload();
//                    cancel();
//                    return;
//                }
//                if (timer == 4) {
//                    fireballAnimation();
//                }
//
//                timer--;
////                plugin.getServer().broadcast(TextUtil.makeText("[Debug] Care Package Timer Ran", TextUtil.BLUE));
//                plugin.getWorld().spawnParticle(Particle.FLAME, armorStand.getLocation().add(0, 0.5, 0), 50, 3, 3, 3, 0.1);
//                plugin.getWorld().playSound(armorStand.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 3, 1);
//            }
//        }.runTaskTimer(plugin, 30, 20);
//    }
//
//    private void unload() {
//        armorStand.remove();
//        createStructure();
//        // plugin.getServer().broadcast(TextUtil.makeText("[Debug] Care Package Stopped", TextUtil.BLUE));
//        plugin.getWorld().setChunkForceLoaded(
//                (int) Math.floor((double) spawnPoint.getBlockX() /16),
//                (int) Math.floor((double) spawnPoint.getBlockZ() /16),
//                false);
//    }
//
//
//    private void createStructure() {
//        BuildLobby.fillArea(-1, -1, -1, 1, 1, 1, Material.OBSIDIAN.createBlockData(), armorStand);
//        Block block = plugin.getWorld().getBlockAt(armorStand.getLocation());
//        block.setType(Material.CHEST);
//        BlockState state = block.getState();
//        Chest chest = (Chest) state;
//        Random random = new Random();
//        CarePackageLoot carePackageLoot = new CarePackageLoot();
//        carePackageLoot.populateLoot(chest.getBlockInventory(), random, 1);
//    }
//
//    private void fireballAnimation() {
//        Fireball fireball = (Fireball) plugin.getWorld().spawnEntity(armorStand.getLocation().add(0, 105, 0), EntityType.FIREBALL);
//        Vector v = new Vector(0, -1, 0);
//        fireball.setDirection(v);
//        fireball.setInvulnerable(true);
//
//    }
//
//
//}
