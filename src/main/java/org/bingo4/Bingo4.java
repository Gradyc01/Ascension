package org.bingo4;


import io.papermc.paper.command.brigadier.BasicCommand;
import org.bingo4.General.Game.GameCommand;
import org.bingo4.General.Game.GameStates;
import org.bingo4.General.LocationChecker.LocationCheck;
import org.bingo4.General.Queue.QueueCommand;
import org.bingo4.Interfaces.AscensionGUI;
//import org.bingo4.Items.UnlocksData;
import org.bingo4.Items.UnlocksData;
import org.bingo4.Lobby.Lobby;
import org.bingo4.LootTables.Blocks.BlockUtil;
import org.bingo4.LootTables.Entities.EntityUtil;
import org.bingo4.Persistence.SettingsReader;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Settings.SettingObserver;
import org.bingo4.Teams.TeamCommand;
import org.bingo4.Utility.TextUtil;
import org.bingo4.commands.*;
import org.bingo4.listeners.*;
import org.bingo4.MainMenuUI.BingoBoard.BingoData;
import org.bingo4.MainMenuUI.OpenMainMenuCommand;
import org.bingo4.MainMenuUI.mainMenuCommands;
import org.bingo4.listeners.ChestGeneration.ChestLootModifier;
import org.bingo4.listeners.Combat.*;
import org.bingo4.testingCommands.*;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public final class Bingo4 extends JavaPlugin {
    public static Map<UUID, PlayerData> playerDataMap = new HashMap<>();
    private static Bingo4 instance;
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
    private World spawnWorld;
    private Logger logger = getLogger();
    private int uniqueModelNumber;
    private Map<UUID, Pair<Inventory, AscensionGUI>> guiMap;

    @Override
    public void onEnable() {
        // Plugin startup logic
//        if (getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
//            getLogger().severe("ProtocolLib not found! This plugin requires ProtocolLib to function.");
//            getServer().getPluginManager().disablePlugin(this);
//            return;
//        }
        uniqueModelNumber = 2;
        instance = this;
        settings = new SettingObserver();
        logger.info("Bingo4 has been enabled!");
        registerListeners();
        registerCommands();
        registerCrafts();
        new EntityUtil();
        new BlockUtil();
        scheduler = this.getServer().getScheduler();
        gameState = new GameStates();
        spawnWorld =  Bukkit.getWorld("world");
        world = Bukkit.getWorld("world");
        nether = Bukkit.getWorld("world_nether");
        guiMap = new HashMap<>();
        try {
            SettingsReader reader = new SettingsReader();
            reader.read();
        } catch (IOException e) {
            TextUtil.debugText("No save file!, Game has to be manually launched.");
        }

    }

    @Override
    public void onDisable() {
        logger.info("Bingo4 has been Disabled!");
    }

    private void registerCommands() {
        registerCommand(new OpenMainMenuCommand(), "open-main-menu");
        registerCommand(new GameCommand(), "game");
//        registerCommand(new changeBingoScore(), "changeBingoScore");
        registerCommand(new mainMenuCommands(), "openmenu");
        registerCommand(new Who(), "who");
        registerCommand(new Info(), "info");
        registerCommand(new TeamCommand(), "party");
        registerCommand(new Debugger(), "debugger");
        registerCommand(new setTimeline(), "timeline");
////        registerCommand(new setUnlockToken(), "setUnlockTokens");
////        registerCommand(new giveExp(), "givePlayerExp");
        registerCommand(new startEvents(), "startEvent");
        registerCommand(new giveCustomItem(), "giveCustomItem");
        registerCommand(new Shout(), "shout");
//        this.registerCommand("queue", new QueueCommand());
        registerCommand(new QueueCommand(), "queue");
        registerCommand(new printWorldInformation(), "printWorldInfo");
        registerCommand(new SetSetting(), "settings");
//        registerCommand(new Backpack(), "bp");
        registerCommand(new CraftCommand(), "craft");
        registerCommand(new TravelWorlds(), "travel");
        registerCommand(new placeCamp(), "placeCamp");
        registerCommand(new addMapPoint(), "addMapPoint");

    }

    private void registerCommand(BasicCommand commandExecutor, String command) {
        super.registerCommand(command, commandExecutor);
    }

    private void registerListeners() {
        Server server = getServer();
        PluginManager manager = server.getPluginManager();
//        manager.registerEvents(new onInventoryChange(), this);
        manager.registerEvents(new PlayerConsumeItem(), this);
        manager.registerEvents(new ProjectileAttacks(), this);
        manager.registerEvents(new PlayerJoinLeave(), this);
        manager.registerEvents(new MobSpawning(), this);
        manager.registerEvents(new InventoryListener(), this);
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
        manager.registerEvents(new DimensionalTravel(), this);
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
    public World getSpawnWorld() {
        return spawnWorld;
    }
    public void setWorld(World world) {
        this.world = world;
    }
    public void setNether(World nether) {
        this.nether = nether;
    }
    public static Location getSpawn() {
        return spawn.clone();
    }
    public UnlocksData getUnlocksData() {
        return unlocksData;
    }
    public static void setSpawn(Location spawn) {
        Bingo4.spawn = spawn;
    }
    public SettingObserver getSettingsUI() {
        return settings;
    }
    public int generateModelNumber() {
        logger.info("Generated a new model number " + uniqueModelNumber);
        return uniqueModelNumber++;
    }
    public static Bingo4 getInstance() {
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
