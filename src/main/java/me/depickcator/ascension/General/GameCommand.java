package me.depickcator.ascension.General;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStart.StartGame;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
// import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;


public class GameCommand implements CommandExecutor {
    private final Ascension ab;
    public GameCommand() {
        this.ab = Ascension.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        if (strings.length == 2 || strings.length > 4) return false;

        Player p = ((Player) commandSender).getPlayer();
        try {
            assert p != null;
        } catch (Exception e) {
            return false;
        }

        switch (strings[0].toLowerCase()) {
            case "start" -> {
                forceStartGame(p);
            }
            case "reset" -> {
                resetGame(p);
            }
            case "load" -> {
                if (strings.length == 4) {
                    loadGame(p, Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
                } else if (strings.length == 3) {
                    int x = Integer.parseInt(strings[1]);
                    int z = Integer.parseInt(strings[2]);
                    loadGame(p, x, p.getWorld().getHighestBlockYAt(x, z), z);
                } else {
                    loadGame(p, p.getLocation());
                }

            }
            default -> {
                TextUtil.errorMessage(p, "The command is misused");
                return false;
            }
        }


        return true;

    }

    private void resetGame(Player p) {
        new ResetGame();
    }

    private void loadGame(Player p, int x, int y, int z) {
        Location loc = new Location(p.getWorld(), x + 0.5, y, z + 0.5);
        loadGame(p, loc);
    }

    private void loadGame(Player p, Location loc) {
        new LoadGame(p, loc);
        p.sendMessage(TextUtil.makeText(
                "Successfully loaded game at ("
                + loc.getBlockX() + ", "
                + loc.getBlockY() + ", "
                + loc.getBlockZ() + ")", TextUtil.GRAY));
    }

    private void forceStartGame(Player p) {
        if (ab.getGameState().checkState(GameStates.LOBBY_NORMAL)) {
            p.sendMessage("Successfully force started game!");
            new StartGame();
        }
    }


}
