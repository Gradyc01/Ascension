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

    @SuppressWarnings("null")
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
//        if (!(commandSender instanceof Player)) {
//            return false;
//        }
        if (strings.length == 2 || strings.length > 4) return false;

        Player p = null;
        boolean playerSent = false;
        if (commandSender instanceof Player) {
            p = (Player) commandSender;
            playerSent = true;
        }
//        Player p = ((Player) commandSender).getPlayer();
//        try {
//            assert p != null;
//        } catch (Exception e) {
//            return false;
//        }

        switch (strings[0].toLowerCase()) {
            case "start" -> {
                forceStartGame();
            }
            case "reset" -> {
                resetGame();
            }
            case "load" -> {
                if (strings.length == 4) {
                    loadGame(Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
                } else if (strings.length == 3) {
                    int x = Integer.parseInt(strings[1]);
                    int z = Integer.parseInt(strings[2]);
                    loadGame(x, Ascension.getInstance().getWorld().getHighestBlockYAt(x, z), z);
                } else {
                    if (playerSent) {
                        loadGame(p.getLocation());
                    }
                }

            }
            default -> {
                if (playerSent) {
                    TextUtil.errorMessage(p, "The command is misused");
                } else {
                    TextUtil.debugText("The command is misused");
                }

                return false;
            }
        }


        return true;

    }

    private void resetGame() {
        new ResetGame();
    }

    private void loadGame(int x, int y, int z) {
        Location loc = new Location(Ascension.getInstance().getWorld(), x + 0.5, y, z + 0.5);
        loadGame(loc);
    }

    private void loadGame(Location loc) {
        new LoadGame(loc);
        String text = "Successfully loaded game at ("
                + loc.getBlockX() + ", "
                + loc.getBlockY() + ", "
                + loc.getBlockZ() + ")";
//        p.sendMessage(TextUtil.makeText(
//                text, TextUtil.GRAY));
        TextUtil.debugText(text);
    }

    private void forceStartGame() {
        if (ab.getGameState().checkState(GameStates.LOBBY_NORMAL)) {
            TextUtil.debugText("Successfully force started game!");
            new StartGame();
        }
    }


}
