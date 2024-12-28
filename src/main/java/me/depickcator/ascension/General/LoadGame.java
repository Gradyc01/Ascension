package me.depickcator.ascension.General;

import me.depickcator.ascension.General.LocationChecker.LocationCheck;
import me.depickcator.ascension.Utility.ArmorStandUtil;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.MainMenu.BingoBoard.BingoData;

import static me.depickcator.ascension.General.BuildLobby.fillArea;

public class LoadGame implements Runnable {
    private final Ascension plugin;
    // private final Player player;
    private ArmorStand spawnCoordsArmorStand;
    public final static String spawnCoordsArmorStandName = "SpawnCoords";
    private Location spawnCoordsLocation;
    public LoadGame(Player player, Location loc) {
        this.plugin = Ascension.getInstance();
        // this.player = player;
        this.spawnCoordsLocation = loc;
        plugin.setBingoData(new BingoData(plugin));
        deletePreviousIterations();
        run();
    }

    private void deletePreviousIterations() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[type=minecraft:armor_stand, name=\"SpawnCoords\"]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[tag=lobby]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"forceload remove all");
    }

    private void centerSettingConfigurations() {
        World world = plugin.getWorld();
        WorldBorder border = world.getWorldBorder();
        int x = spawnCoordsArmorStand.getLocation().getBlockX();
        int z = spawnCoordsArmorStand.getLocation().getBlockZ();
        border.setCenter(x, z);
        world.setChunkForceLoaded((int) Math.floor((double) x /16), (int) Math.floor((double) z /16), true);
    }

    private ArmorStand setSpawnCoordsArmorStand() {
        Location loc = spawnCoordsLocation;
        return ArmorStandUtil.makeMarkerArmorStand(loc, plugin.getWorld(), spawnCoordsArmorStandName);
    }

    @Override
    public void run() {
        BukkitScheduler scheduler = plugin.getScheduler();
        // this.plugin.getServer().broadcast(Component.text("Hello, World!"));
        scheduler.runTaskLater(plugin, () -> {
            spawnCoordsArmorStand = setSpawnCoordsArmorStand();
            centerSettingConfigurations();
            Ascension.setSpawn(spawnCoordsArmorStand.getLocation());
        }, 10);
        scheduler.runTaskLater(plugin, this::buildPlatform, 30);
        scheduler.runTaskLater(plugin, this::buildLobby, 40);
        scheduler.runTaskLater(plugin, () -> new ResetGame(), 50);
        scheduler.runTaskLater(plugin, () -> plugin.setLocationCheck(new LocationCheck(spawnCoordsLocation)), 90);
       
    }

    private void buildPlatform() {
        String quartzBlock = "quartz_block";
        fillArea(2, 2, 2, -2, 2, -2, quartzBlock, spawnCoordsArmorStand);
        fillArea(1, 2, 3, -1, 2, 3, quartzBlock, spawnCoordsArmorStand);
        fillArea(1, 2, -3, -1, 2, -3, quartzBlock, spawnCoordsArmorStand);
        fillArea(3, 2, 1, 3, 2, -1, quartzBlock, spawnCoordsArmorStand);
        fillArea(-3, 2, 1, -3, 2, -1, quartzBlock, spawnCoordsArmorStand);

        String grassBlock = "grass_block";
        fillArea(-5, 1, 2, 5, 1, -2, grassBlock, spawnCoordsArmorStand);
         fillArea(-2, 1, 5, 2, 1, -5, grassBlock, spawnCoordsArmorStand);
         fillArea(-3, 1, 4, 3, 1, -4, grassBlock, spawnCoordsArmorStand);
         fillArea(4, 1, 3, -4, 1, -3, grassBlock, spawnCoordsArmorStand);

         fillArea(-7, 0, 4, 7, 0, -4, grassBlock, spawnCoordsArmorStand);
         fillArea(-4, 0, 7, 4, 0, -7, grassBlock, spawnCoordsArmorStand);
         fillArea(-5, 0, 6, 5, 0, -6, grassBlock, spawnCoordsArmorStand);
         fillArea(6, 0, 5, -6, 0, -5, grassBlock, spawnCoordsArmorStand);

         fillArea(-9, -1, 6, 9, -1, -6, grassBlock, spawnCoordsArmorStand);
         fillArea(-6, -1, 9, 6, -1, -9, grassBlock, spawnCoordsArmorStand);
         fillArea(-7, -1, 8, 7, -1, -8, grassBlock, spawnCoordsArmorStand);
         fillArea(8, -1, 7, -8, -1, -7, grassBlock, spawnCoordsArmorStand);

         fillArea(-12, -2, 9, 12, -2, -9, grassBlock, spawnCoordsArmorStand);
         fillArea(-9, -2, 12, 9, -2, -12, grassBlock, spawnCoordsArmorStand);
         fillArea(-10, -2, 11, 10, -2, -11, grassBlock, spawnCoordsArmorStand);
         fillArea(11, -2, 10, -11, -2, -10, grassBlock, spawnCoordsArmorStand);

         fillArea(-16, -3, 13, 16, -3, -13, grassBlock, spawnCoordsArmorStand);
         fillArea(-13, -3, 16, 13, -3, -16, grassBlock, spawnCoordsArmorStand);
         fillArea(-14, -3, 15, 14, -3, -15, grassBlock, spawnCoordsArmorStand);
         fillArea(15, -3, 14, -15, -3, -14, grassBlock, spawnCoordsArmorStand);
    }

    private void buildLobby() {
        new BuildLobby(spawnCoordsArmorStand);
    }
}
