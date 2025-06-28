package me.depickcator.ascension.Lobby;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Persistence.AscensionStats;
import me.depickcator.ascension.Persistence.PlayerStats;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.DisplayUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.util.*;

public class StatsBoard extends Boards {
    private final List<TextDisplay> displays;
    private final Map<UUID, List<TextDisplay>> playerDisplays;
    private final AscensionStats stats;
    public StatsBoard() {
        displays = new ArrayList<>();
        playerDisplays = new HashMap<>();
        stats = new AscensionStats();
        initOtherDisplays();
    }
    @Override
    protected TextDisplay initTextDisplay() {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText("Leaderboards", TextUtil.YELLOW, true, false)
        ));
        Location loc = new Location(plugin.getSpawnWorld(), spawn.getX() - 13.3 , spawn.getY() + 105.1, spawn.getZ() - 13.3);

        return DisplayUtil.makeTextDisplay(loc, text, 315, 0, 450);
    }

    public void initOtherDisplays() {
        for (TextDisplay display : displays) {
            display.remove();
        }
        displays.clear();
        removeAllSoloDisplays();
        makeLeaderboardsAsync();
    }

    /*Sets up Player player after player recently logged in*/
    public void setupLoginPlayer(Player player) {
        for (List<TextDisplay> displays : playerDisplays.values()) {
            for (TextDisplay display : displays) {
                player.hideEntity(plugin, display);
            }
        }
        if (playerDisplays.containsKey(player.getUniqueId())) {
            for (TextDisplay display : playerDisplays.get(player.getUniqueId())) {
                player.showEntity(plugin, display);
            }
        } else {
            initLeaderboardPosition(player);
        }
    }

    private void initLeaderboardPosition(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Pair<Integer, Integer> killStats = stats.readStatsAndRankings("kills", player.getUniqueId());
                Pair<Integer, Integer> winStats = stats.readStatsAndRankings("wins", player.getUniqueId());
                Pair<Integer, Integer> itemStats = stats.readStatsAndRankings("items_obtained", player.getUniqueId());
                buildSoloDisplay(player, killStats, buildLocation(-15.5, 101.5, -11.1));
                buildSoloDisplay(player, itemStats, buildLocation(-11.1, 101.5, -15.5));
                buildSoloDisplay(player, winStats, buildLocation(-13.3, 101.5, -13.3));
            }
        }.runTaskAsynchronously(plugin);
    }
    private void makeLeaderboardsAsync() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    List<PlayerStats> playerStats = stats.collectStats("./plugins/Ascension/PlayerData/");
                    List<Map.Entry<PlayerStats, Integer>> killStats = stats.getSortedStatList("kills", playerStats);
                    List<Map.Entry<PlayerStats, Integer>> itemStats = stats.getSortedStatList("items_obtained", playerStats);
                    List<Map.Entry<PlayerStats, Integer>> winStats = stats.getSortedStatList("wins", playerStats);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            buildLeaderboard("Wins", winStats, buildLocation(-13.3, 102.2, -13.3));
                            buildLeaderboard("Items Obtained", itemStats, buildLocation(-11.1, 102.2, -15.5));
                            buildLeaderboard("Kills", killStats, buildLocation(-15.5, 102.2, -11.1));
                            for (PlayerData pD : PlayerUtil.getAllPlayingPlayers()) {
                                initLeaderboardPosition(pD.getPlayer());
                            }
                        }
                    }.runTask(plugin);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);

    }
    private void buildLeaderboard(String name, List<Map.Entry<PlayerStats, Integer>> statList, Location location) {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText(name, TextUtil.GOLD, true, false)
        ));

        for (int i = 1; i <= 5; i++) {
            String playerName;
            String num;
            try {
                Map.Entry<PlayerStats, Integer> entry = statList.get(i - 1);
                playerName = entry.getKey().getName();
                num = entry.getValue().toString();
            } catch (Exception ignored) {
                playerName = "Not Applicable";
                num = "NA";
            }
            text.add(TextUtil.makeText("\n" + i + ". ", TextUtil.YELLOW, true, false)
                    .append(TextUtil.makeText(playerName, TextUtil.AQUA))
                    .append(TextUtil.makeText(" - ", TextUtil.GRAY))
                    .append(TextUtil.makeText(num, TextUtil.GREEN)));
        }

        displays.add(DisplayUtil.makeTextDisplay(location, text, 315, 0, 450));

    }
    private void buildSoloDisplay(Player player, Pair<Integer, Integer> stat, Location location) {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                List<Component> text;
                if (stat != null) {
                    text = new ArrayList<>(List.of(
                            TextUtil.makeText(stat.getRight() + ". ", TextUtil.YELLOW, true, false)
                                    .append(TextUtil.makeText(player.getName(), TextUtil.AQUA))
                                    .append(TextUtil.makeText(" - ", TextUtil.GRAY))
                                    .append(TextUtil.makeText(stat.getLeft().toString(), TextUtil.GREEN))
                    ));
                } else {
                    text = new ArrayList<>(List.of(
                            TextUtil.makeText("You have no data yet", TextUtil.GRAY)
                    ));
                }

                showDisplay(player, DisplayUtil.makeTextDisplay(location, text, 315, 0, 450));
            }
        }.runTask(plugin);
    }
    private void showDisplay(Player player, TextDisplay display) {
        if (playerDisplays.containsKey(player.getUniqueId())) {
            playerDisplays.get(player.getUniqueId()).add(display);
        } else {
            playerDisplays.put(player.getUniqueId(), new ArrayList<>(List.of(display)));
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.equals(player)) {
                p.showEntity(plugin, display);
            } else {
                p.hideEntity(plugin, display);
            }
        }
    }
    private void removeAllSoloDisplays() {
        for (List<TextDisplay> displays : playerDisplays.values()) {
            for (TextDisplay display : displays) {
                display.remove();
            }
        }
        playerDisplays.clear();
    }
    private Location buildLocation(double offsetX, double offsetY, double offsetZ) {
        return new Location(
                plugin.getSpawnWorld(),
                spawn.getX() + offsetX ,
                spawn.getY() + offsetY,
                spawn.getZ() + offsetZ);
    }

}
