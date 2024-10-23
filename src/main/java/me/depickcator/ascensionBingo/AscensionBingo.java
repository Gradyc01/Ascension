package me.depickcator.ascensionBingo;


import me.depickcator.ascensionBingo.General.commands.loadPluginCommand;
import me.depickcator.ascensionBingo.General.commands.resetGameCommand;
import me.depickcator.ascensionBingo.Player.BingoData;
import me.depickcator.ascensionBingo.commands.OpenMainMenuCommand;
import me.depickcator.ascensionBingo.listeners.InventoryClickListener;
import me.depickcator.ascensionBingo.listeners.InventoryClose;
import me.depickcator.ascensionBingo.listeners.onInventoryChange;
import me.depickcator.ascensionBingo.mainMenu.GiveMainMenuItem;
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

    @Override
    public void onEnable() {
        // Plugin startup logic
        Logger logger = getLogger();
        logger.info("AscensionBingo has been enabled!");
        registerListeners();
        registerCommands();
    }


    @Override
    public void onDisable() {
        Logger logger = getLogger();
        logger.info("AscensionBingo has been Disabled!");
    }

    private void registerCommands() {
        PluginManager pluginManager = getServer().getPluginManager();
        Objects.requireNonNull(getCommand("open-main-menu")).setExecutor(new OpenMainMenuCommand());
        Objects.requireNonNull(getCommand("give-main-menu")).setExecutor(new GiveMainMenuItem(pluginManager, this));
        Objects.requireNonNull(getCommand("resetgame")).setExecutor(new resetGameCommand(pluginManager,this));
        Objects.requireNonNull(getCommand("loadgame")).setExecutor(new loadPluginCommand(this));
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

    public void setBingoData(BingoData bingoData) {
        this.bingoData = bingoData;
    }


}
