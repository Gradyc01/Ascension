package me.depickcator.ascension;


import me.depickcator.ascension.General.GameCommand;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.LootTables.Blocks.BlockUtil;
import me.depickcator.ascension.LootTables.Entities.EntityUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Teams.TeamCommand;
import me.depickcator.ascension.Timeline.Timeline;
import me.depickcator.ascension.listeners.*;
import me.depickcator.ascension.MainMenu.BingoBoard.BingoData;
import me.depickcator.ascension.MainMenu.OpenMainMenuCommand;
import me.depickcator.ascension.MainMenu.GiveMainMenuItem;
import me.depickcator.ascension.MainMenu.mainMenuCommands;
import me.depickcator.ascension.listeners.ChestGeneration.ChestLootModifier;
import me.depickcator.ascension.testingCommands.*;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;
import java.util.logging.Logger;

public final class Ascension extends JavaPlugin {
    // private static final org.slf4j.Logger log = LoggerFactory.getLogger(Ascension.class);
//    public static Map<UUID, Pair<Inventory, AscensionGUI>> guiMap = new HashMap<>();
    public static Map<UUID, PlayerData> playerDataMap = new HashMap<>();
    private static Ascension instance;
    private static Location spawn;
    private GameStates gameState;
    private BingoData bingoData;
    private UnlocksData unlocksData;
    private BukkitScheduler scheduler;
    private Timeline timeline;
    private World world;
    private World nether;
    private Logger logger = getLogger();
    private int uniqueModelNumber;
    private Map<UUID, Pair<Inventory, AscensionGUI>> guiMap;

    @Override
    public void onEnable() {
        // Plugin startup logic
        uniqueModelNumber = 2;
        instance = this;
        logger.info("Ascension has been enabled!");
        registerListeners();
        registerCommands();
        registerCrafts();
        new EntityUtil();
        new BlockUtil();
        scheduler = this.getServer().getScheduler();
        gameState = new GameStates();
        timeline = new Timeline(this);
        world = Bukkit.getWorld("world");
        nether = Bukkit.getWorld("world_nether");
        guiMap = new HashMap<>();
    }

    @Override
    public void onDisable() {
        logger.info("Ascension has been Disabled!");
    }

    private void registerCommands() {
        // PluginManager pluginManager = getServer().getPluginManager();
        Objects.requireNonNull(getCommand("open-main-menu")).setExecutor(new OpenMainMenuCommand());
        Objects.requireNonNull(getCommand("give-main-menu")).setExecutor(new GiveMainMenuItem());
        Objects.requireNonNull(getCommand("game")).setExecutor(new GameCommand());
        Objects.requireNonNull(getCommand("changeBingoScore")).setExecutor(new changeBingoScore());
        Objects.requireNonNull(getCommand("openmenu")).setExecutor(new mainMenuCommands());
        Objects.requireNonNull(getCommand("party")).setExecutor(new TeamCommand());
        Objects.requireNonNull(getCommand("debugger")).setExecutor(new Debugger());
        Objects.requireNonNull(getCommand("timeline")).setExecutor(new setTimeline());
        Objects.requireNonNull(getCommand("setUnlockTokens")).setExecutor(new setUnlockToken());
        Objects.requireNonNull(getCommand("givePlayerExp")).setExecutor(new giveExp());
        Objects.requireNonNull(getCommand("startEvent")).setExecutor(new startEvents());
        Objects.requireNonNull(getCommand("giveCustomItem")).setExecutor(new giveCustomItem());
    }

    private void registerListeners() {
        Server server = getServer();
        PluginManager manager = server.getPluginManager();
//        manager.registerEvents(new onInventoryChange(), this);
        manager.registerEvents(new PlayerConsumeItem(), this);
        manager.registerEvents(new ProjectileAttacks(), this);
        manager.registerEvents(new PlayerJoinLeave(), this);
        manager.registerEvents(new MobSpawning(), this);
        manager.registerEvents(new InventoryClose(), this);
        manager.registerEvents(new InventoryClickListener(), this);
        manager.registerEvents(new PlayerCombat(), this);
        manager.registerEvents(new RecipeCrafted(), this);
        manager.registerEvents(new PlayerInteractListener(), this);
        manager.registerEvents(new LootTableGeneration(), this);
        manager.registerEvents(new ChestLootModifier(), this);
    }

    private void registerCrafts() {
        unlocksData = new UnlocksData();
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

    public int generateModelNumber() {
        logger.info("Current model number" + uniqueModelNumber);
        return uniqueModelNumber++;
    }

    public static Ascension getInstance() {
        return instance;
    }

    public Map<UUID, Pair<Inventory, AscensionGUI>> getGuiMap() {
        return guiMap;
    }

    public void registerGUI(Player player, Inventory inventory, AscensionGUI gui) {
        guiMap.put(player.getUniqueId(), new MutablePair<>(inventory, gui));
    }

    public Pair<Inventory, AscensionGUI> findInventory(Player player) {
        return guiMap.get(player.getUniqueId());
    }

    public void removeGUI(Player player) {
        guiMap.remove(player.getUniqueId());
    }

}
