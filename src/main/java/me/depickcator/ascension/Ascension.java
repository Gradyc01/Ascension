package me.depickcator.ascension;


import me.depickcator.ascension.General.GameCommand;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.General.LocationChecker.LocationCheck;
import me.depickcator.ascension.General.Queue.QueueCommand;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Lobby.Lobby;
import me.depickcator.ascension.LootTables.Blocks.BlockUtil;
import me.depickcator.ascension.LootTables.Entities.EntityUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Settings.SettingObserver;
import me.depickcator.ascension.Teams.TeamCommand;
import me.depickcator.ascension.commands.*;
import me.depickcator.ascension.listeners.*;
import me.depickcator.ascension.MainMenuUI.BingoBoard.BingoData;
import me.depickcator.ascension.MainMenuUI.OpenMainMenuCommand;
import me.depickcator.ascension.MainMenuUI.mainMenuCommands;
import me.depickcator.ascension.listeners.ChestGeneration.ChestLootModifier;
import me.depickcator.ascension.listeners.Combat.*;
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
    private SettingObserver settings;
    private LocationCheck locationCheck;
    private Lobby lobby;
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
        settings = new SettingObserver();
        logger.info("Ascension has been enabled!");
        registerListeners();
        registerCommands();
        registerCrafts();
        new EntityUtil();
        new BlockUtil();
        scheduler = this.getServer().getScheduler();
        gameState = new GameStates();

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
        getCommand("open-main-menu").setExecutor(new OpenMainMenuCommand());
//        getCommand("give-main-menu").setExecutor(new GiveMainMenuItem());
        getCommand("game").setExecutor(new GameCommand());
        getCommand("changeBingoScore").setExecutor(new changeBingoScore());
        getCommand("openmenu").setExecutor(new mainMenuCommands());
        getCommand("who").setExecutor(new Who());

        getCommand("info").setExecutor(new Info());

        TeamCommand teamCommand = new TeamCommand();
        getCommand("party").setExecutor(teamCommand);
        getCommand("p").setExecutor(teamCommand);

        getCommand("debugger").setExecutor(new Debugger());
        getCommand("timeline").setExecutor(new setTimeline());
        getCommand("setUnlockTokens").setExecutor(new setUnlockToken());
        getCommand("givePlayerExp").setExecutor(new giveExp());
        getCommand("startEvent").setExecutor(new startEvents());
        getCommand("giveCustomItem").setExecutor(new giveCustomItem());
        getCommand("shout").setExecutor(new Shout());
        getCommand("queue").setExecutor(new QueueCommand());
        getCommand("printWorldInfo").setExecutor(new printWorldInformation());
        getCommand("settings").setExecutor(new SetSetting());
        getCommand("bp").setExecutor(new Backpack());
    }

    private void registerListeners() {
        Server server = getServer();
        PluginManager manager = server.getPluginManager();
//        manager.registerEvents(new onInventoryChange(), this);
        manager.registerEvents(new PlayerConsumeItem(), this);
//        manager.registerEvents(new ProjectileAttacks(), this);
        manager.registerEvents(new PlayerJoinLeave(), this);
        manager.registerEvents(new MobSpawning(), this);
        manager.registerEvents(new InventoryClose(), this);
        manager.registerEvents(new InventoryClickListener(), this);
        manager.registerEvents(new PlayerAchievements(), this);
        manager.registerEvents(new PlayerExpGain(), this);
        manager.registerEvents(new onDamage(), this);
        manager.registerEvents(new onDeath(), this);
        manager.registerEvents(new onElytraFlight(), this);

        manager.registerEvents(new RecipeCrafted(), this);
        manager.registerEvents(new PlayerInteractListener(), this);
        manager.registerEvents(new LootTableGeneration(), this);
        manager.registerEvents(new ChestLootModifier(), this);
        manager.registerEvents(new PlayerChatting(), this);
        manager.registerEvents(new FastSmelt(), this);
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

    public SettingObserver getSettingsUI() {
        return settings;
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

    public LocationCheck getLocationCheck() {
        return locationCheck;
    }

    public void setLocationCheck(LocationCheck locationCheck) {
        if (this.locationCheck != null) this.locationCheck.cancelCheck();
        this.locationCheck = locationCheck;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }
}
