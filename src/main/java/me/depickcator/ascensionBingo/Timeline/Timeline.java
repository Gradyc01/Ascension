package me.depickcator.ascensionBingo.Timeline;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Player.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.scheduler.BukkitRunnable;

public class Timeline {
    private final AscensionBingo plugin;
    private int MINUTES;
    private int SECONDS;
    private boolean keepRunning;
    private static final int STARTING_MINUTES = 160;
    public Timeline(AscensionBingo plugin) {
        this.plugin = plugin;
        this.MINUTES = STARTING_MINUTES;

    }

    public void startTimeline() {
        keepRunning = true;
        mainTimelineMinutes();
        plugin.getServer().broadcast(TextUtil.makeText("[Debug] Started Timeline", TextUtil.BLUE));
    }

    private void mainTimelineMinutes() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!keepRunning) {
                    cancel();
                    plugin.getServer().broadcast(TextUtil.makeText("[Debug] Timeline Forcefully Stopped", TextUtil.BLUE));
                    return;
                }

                updatePlayers();

                if (MINUTES > 1) {
                    MINUTES--;
                } else {
                    cancel();
                    plugin.getServer().broadcast(TextUtil.makeText("[Debug] Timeline Ended", TextUtil.BLUE));
                    mainTimelineSeconds();
                    return;
                }
                plugin.getServer().broadcast(TextUtil.makeText("[Debug] Timeline Ran", TextUtil.BLUE));

            }
        }.runTaskTimer(plugin, 0, 60 * 20);
    }

    private void mainTimelineSeconds() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!keepRunning) {
                    cancel();
                    plugin.getServer().broadcast(TextUtil.makeText("[Debug] Timeline Forcefully Stopped", TextUtil.BLUE));
                    return;
                }

                updatePlayers();

                if (SECONDS > 1) {
                    SECONDS--;
                } else {
                    cancel();
                    plugin.getServer().broadcast(TextUtil.makeText("[Debug] Timeline Ended", TextUtil.BLUE));
                    return;
                }
                plugin.getServer().broadcast(TextUtil.makeText("[Debug] Timeline Ran", TextUtil.BLUE));

            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public Component getTime() {
        if (MINUTES > 1) {
            return TextUtil.makeText("    " + MINUTES + " Minutes", TextUtil.WHITE);
        } else {
            return TextUtil.makeText("    " + SECONDS + " Seconds", TextUtil.WHITE);
        }
    }

    public void resetTimeline() {
        keepRunning = false;
        MINUTES = STARTING_MINUTES;
        SECONDS = 60;
    }

    public void setTime(int time) {
        MINUTES = time;
    }

    private void updatePlayers() {
        for (PlayerData p: AscensionBingo.playerDataMap.values()) {
            p.getPlayerScoreboard().updateGameBoard(true);
        }
    }




}
