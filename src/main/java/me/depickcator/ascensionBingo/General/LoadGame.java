package me.depickcator.ascensionBingo.General;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.mainMenu.BingoBoard.BingoData;

import static me.depickcator.ascensionBingo.General.BuildLobby.fillArea;

public class LoadGame implements Runnable {
    AscensionBingo plugin;
    Player player;
    ArmorStand spawnCoordsArmorStand;
    public final static String spawnCoordsArmorStandName = "SpawnCoords";
    public LoadGame(AscensionBingo plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        plugin.setBingoData(new BingoData(plugin));
        plugin.getGameState().setCurrentState(GameStates.LOBBY);

        deletePreviousIterations();

        run();
    }

    private void deletePreviousIterations() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[type=minecraft:armor_stand, name=\"SpawnCoords\"]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[tag=lobby]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"forceload remove all");
    }

//    private void loadGameRules(World world) {
//        world.setGameRule(GameRule.SPAWN_RADIUS, 0);
//        world.setGameRule(GameRule.KEEP_INVENTORY, true);
//        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
//        // world.setGameRule(GameRule.DO_LIMITED_CRAFTING, true); //TODO: REMOVE WHEN DONE
//        world.setGameRule(GameRule.NATURAL_REGENERATION, false);
//        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
//        // world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, true);
//        world.setGameRule(GameRule.DO_INSOMNIA, false);
//        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
//    }

    private void centerSettingConfigurations() {
        World world = plugin.getWorld();
        WorldBorder border = world.getWorldBorder();
        int x = spawnCoordsArmorStand.getLocation().getBlockX();
        int z = spawnCoordsArmorStand.getLocation().getBlockZ();
        border.setCenter(x, z);
        world.setChunkForceLoaded(x, z, true);
    }

    private ArmorStand setSpawnCoordsArmorStand() {
        Location loc = player.getLocation();
        return ArmorStandUtil.makeMarkerArmorStand(loc, plugin.getWorld(), spawnCoordsArmorStandName);
    }

    @Override
    public void run() {
        BukkitScheduler scheduler = plugin.getScheduler();
        // this.plugin.getServer().broadcast(Component.text("Hello, World!"));
        scheduler.runTaskLater(plugin, () -> {
            spawnCoordsArmorStand = setSpawnCoordsArmorStand();
            centerSettingConfigurations();
            AscensionBingo.setSpawn(spawnCoordsArmorStand.getLocation());
        }, 10);
        scheduler.runTaskLater(plugin, this::buildPlatform, 30);
        scheduler.runTaskLater(plugin, this::buildLobby, 40);
        scheduler.runTaskLater(plugin, () -> new ResetGame(plugin, player), 50);
       
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
        new BuildLobby(spawnCoordsArmorStand, plugin);
    }
}
