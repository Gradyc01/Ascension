package me.depickcator.ascensionBingo;


import me.depickcator.ascensionBingo.General.commands.loadPluginCommand;
import me.depickcator.ascensionBingo.General.commands.GameCommand;
import me.depickcator.ascensionBingo.mainMenu.BingoBoard.BingoData;
import me.depickcator.ascensionBingo.commands.OpenMainMenuCommand;
import me.depickcator.ascensionBingo.listeners.InventoryClickListener;
import me.depickcator.ascensionBingo.listeners.InventoryClose;
import me.depickcator.ascensionBingo.listeners.onInventoryChange;
import me.depickcator.ascensionBingo.mainMenu.GiveMainMenuItem;
import me.depickcator.ascensionBingo.mainMenu.mainMenuCommands;
import me.depickcator.ascensionBingo.testingCommands.changeBingoScore;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Server;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public final class AscensionBingo extends JavaPlugin {

    public static Map<UUID, Pair<Inventory, String>>guiMap = new HashMap<>();
    BingoData bingoData;
    Logger logger = getLogger();

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("AscensionBingo has been enabled!");
        registerListeners();
        registerCommands();
    }


    @Override
    public void onDisable() {
        logger.info("AscensionBingo has been Disabled!");
    }

    private void registerCommands() {
        PluginManager pluginManager = getServer().getPluginManager();
        Objects.requireNonNull(getCommand("open-main-menu")).setExecutor(new OpenMainMenuCommand());
        Objects.requireNonNull(getCommand("give-main-menu")).setExecutor(new GiveMainMenuItem(pluginManager, this));
        Objects.requireNonNull(getCommand("game")).setExecutor(new GameCommand(pluginManager,this));
        Objects.requireNonNull(getCommand("loadgame")).setExecutor(new loadPluginCommand(this));
        Objects.requireNonNull(getCommand("changeBingoScore")).setExecutor(new changeBingoScore(this));
        Objects.requireNonNull(getCommand("openmenu")).setExecutor(new mainMenuCommands(this));
    }

    private void registerListeners() {
        Server server = getServer();
        PluginManager manager = server.getPluginManager();
        manager.registerEvents(new onInventoryChange(), this);
        manager.registerEvents(new InventoryClose(), this);
        manager.registerEvents(new InventoryClickListener(), this);
    }

    public BingoData getBingoData() {
        return bingoData;
    }

//    public @NotNull Logger getLogger() {
//        assert logger != null;
//        return logger;
//    }

    public void setBingoData(BingoData bingoData) {
        this.bingoData = bingoData;
    }


}
