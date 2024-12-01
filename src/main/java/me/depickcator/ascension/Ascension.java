package me.depickcator.ascension;


import me.depickcator.ascension.General.GameCommand;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.LootTables.Blocks.BlockUtil;
import me.depickcator.ascension.LootTables.Entities.EntityUtil;
import me.depickcator.ascension.Player.PlayerData;
import me.depickcator.ascension.Teams.TeamCommand;
import me.depickcator.ascension.Timeline.Timeline;
import me.depickcator.ascension.listeners.*;
import me.depickcator.ascension.mainMenu.BingoBoard.BingoData;
import me.depickcator.ascension.mainMenu.OpenMainMenuCommand;
import me.depickcator.ascension.mainMenu.GiveMainMenuItem;
import me.depickcator.ascension.mainMenu.mainMenuCommands;
import me.depickcator.ascension.testingCommands.*;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public final class Ascension extends JavaPlugin {
    public static Map<UUID, Pair<Inventory, AscensionGUI>> guiMap = new HashMap<>();
    public static Map<UUID, PlayerData> playerDataMap = new HashMap<>();
    private static Location spawn;
    private GameStates gameState;
    private BingoData bingoData;
    private UnlocksData unlocksData;
    private BukkitScheduler scheduler;
    private Timeline timeline;
    private World world;
    private World nether;
    private Logger logger = getLogger();
    private static int uniqueModelNumber = 0;


    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("Ascension has been enabled!");
        registerListeners();
        registerCommands();
        registerCrafts();
        new EntityUtil(this);
        new BlockUtil(this);
        scheduler = this.getServer().getScheduler();
        gameState = new GameStates(this);
        timeline = new Timeline(this);
        world = Bukkit.getWorld("world");
        nether = Bukkit.getWorld("world_nether");
    }

    @Override
    public void onDisable() {
        logger.info("Ascension has been Disabled!");
    }

    private void registerCommands() {
        PluginManager pluginManager = getServer().getPluginManager();
        Objects.requireNonNull(getCommand("open-main-menu")).setExecutor(new OpenMainMenuCommand());
        Objects.requireNonNull(getCommand("give-main-menu")).setExecutor(new GiveMainMenuItem(pluginManager, this));
        Objects.requireNonNull(getCommand("game")).setExecutor(new GameCommand(pluginManager,this));
        Objects.requireNonNull(getCommand("changeBingoScore")).setExecutor(new changeBingoScore(this));
        Objects.requireNonNull(getCommand("openmenu")).setExecutor(new mainMenuCommands(this));
        Objects.requireNonNull(getCommand("party")).setExecutor(new TeamCommand(this));
        Objects.requireNonNull(getCommand("printPlayerData")).setExecutor(new printPlayerDataCommand(this));
        Objects.requireNonNull(getCommand("timeline")).setExecutor(new setTimeline(this));
        Objects.requireNonNull(getCommand("setUnlockTokens")).setExecutor(new setUnlockToken(this));
        Objects.requireNonNull(getCommand("givePlayerExp")).setExecutor(new giveExp(this));
        Objects.requireNonNull(getCommand("startEvent")).setExecutor(new startEvents(this));
    }

    private void registerListeners() {
        Server server = getServer();
        PluginManager manager = server.getPluginManager();
//        manager.registerEvents(new onInventoryChange(), this);
        manager.registerEvents(new PlayerConsumeItem(), this);
        manager.registerEvents(new ProjectileAttacks(), this);
        manager.registerEvents(new PlayerJoinLeave(this), this);
        manager.registerEvents(new MobSpawning(this), this);
        manager.registerEvents(new InventoryClose(), this);
        manager.registerEvents(new InventoryClickListener(this), this);
        manager.registerEvents(new PlayerCombat(this), this);
        manager.registerEvents(new RecipeCrafted(this), this);
        manager.registerEvents(new PlayerInteractListener(this), this);
        manager.registerEvents(new LootTableGeneration(this), this);
    }

    private void registerCrafts() {
        unlocksData = new UnlocksData(this);
    }

    public BingoData getBingoData() {
        return bingoData;
    }

    public void setBingoData(BingoData bingoData) {
        this.bingoData = bingoData;
    }

    public GameStates getGameState() {
        return gameState;
    }

    public BukkitScheduler getScheduler() {
        return scheduler;
    }

    public World getWorld() {
        return world;
    }

    public World getNether() {
        return nether;
    }

    public static Location getSpawn() {
        return spawn;
    }

    public UnlocksData getUnlocksData() {
        return unlocksData;
    }

    public static void setSpawn(Location spawn) {
        Ascension.spawn = spawn;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public static int generateModelNumber() {
        uniqueModelNumber++;
        return uniqueModelNumber - 1;
    }
}
