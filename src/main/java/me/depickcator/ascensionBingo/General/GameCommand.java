package me.depickcator.ascensionBingo.General;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import me.depickcator.ascensionBingo.mainMenu.BingoBoard.BingoData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GameCommand implements CommandExecutor {
    PluginManager pm;
    AscensionBingo ab;
    ScoreboardManager scoreboardManager;
    Scoreboard bingoScoreboard;
    Objective bingodata;
    public GameCommand(PluginManager manager, AscensionBingo plugin) {
        pm = manager;
        this.ab = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        if (strings.length != 1) return false;

        Player p = ((Player) commandSender).getPlayer();
        try {
            assert p != null;
        } catch (Exception e) {
            return false;
        }
//        if (p.hasPermission("ascensionbingo.command.changeBingoScore")) {
//            p.sendMessage("You do not have permission to use this command!");
//            return false;
//        }

        switch (strings[0].toLowerCase()) {
            case "start" -> {
                p.sendMessage("PLACEHOLDER MESSAGE FOR WHEN SOMEONE STARTS THE GAME");
            }
            case "reset" -> {
                resetGame(p);
            }
            case "forcestart" -> {
                forceStartGame(p);
            }
            case "load" -> {
                loadGame(p);
            }
            default -> {
                p.sendMessage("ERRORED");
                return false;
            }
        }


        return true;

    }

    private void resetGame(Player p) {
        ab.getBingoData().resetPlayers();
        PlayerUtil.assignNewPlayerData(ab);
        System.out.println(AscensionBingo.playerDataMap);
        ab.getGameState().setCurrentState(GameStates.LOBBY);
        p.sendMessage("Placeholder Message For Reset Game");
    }

    private void loadGame(Player p) {
        ab.setBingoData(new BingoData(ab));
        resetGame(p);
        ab.getGameState().setCurrentState(GameStates.LOBBY);
        p.sendMessage("Successfully loaded game");
    }

    private void forceStartGame(Player p) {
        if (!ab.getGameState().checkState(GameStates.LOBBY)) return;
        BingoData bingoData = ab.getBingoData();
        bingoData.setItems(bingoData.getItemList().get25());
        ArrayList<ItemStack> item = bingoData.getItems();
        for (ItemStack i : item) {
            p.sendMessage(i.toString());
        }
        createTeamsForEveryone();
        ab.getGameState().setCurrentState(GameStates.GAME);
        p.sendMessage("Successfully force started game!");
    }

    private void createTeamsForEveryone() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData playerData = AscensionBingo.playerDataMap.get(p.getUniqueId());
            if (playerData.getTeam() == null) {
                playerData.createOrGetTeam();
            }
        }
    }
}
