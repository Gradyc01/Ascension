package me.depickcator.ascension.General.Queue;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.General.StartGame;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class Queue {
    private Set<PlayerData> allPlayers;
    private Set<PlayerData> readiedPlayers;
    private static Queue instance;
    private boolean isQueueOngoing;
    private final Ascension plugin;
    private Queue() {
        plugin = Ascension.getInstance();
//        allPlayers = new HashSet<>();
//        allPlayers.addAll(PlayerUtil.getAllPlayingPlayers());
//        readiedPlayers = new HashSet<>();
        isQueueOngoing = false;
    }

    public boolean startQueue() {
        if (isQueueOngoing || !plugin.getGameState().checkState(GameStates.LOBBY)) return false;
        isQueueOngoing = true;
        allPlayers = new HashSet<>();
        allPlayers.addAll(PlayerUtil.getAllPlayingPlayers());
        readiedPlayers = new HashSet<>();
        return true;
    }

    private void loop() {
        new BukkitRunnable() {
            int timer = 30;
            public void run() {
                if (!isQueueOngoing || timer <= 0) {
                    failed();
                    cancel();
                    return;
                }
                if (allPlayers.size() == readiedPlayers.size()) {
                    success();
                    cancel();
                    return;
                }
                updatePlayers();
                timer--;
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    private void failed() {
        Component text = TextUtil.topBorder(TextUtil.DARK_GREEN);
        text = text.append(TextUtil.makeText("\n         Queue failed", TextUtil.YELLOW));
        text = text.append(TextUtil.makeText("\n  Not every player had readied up\n", TextUtil.AQUA));
        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GREEN));
        TextUtil.broadcastMessage(text);
        stop();
    }

    private void success() {
        Component text = TextUtil.topBorder(TextUtil.DARK_GREEN);
        text = text.append(TextUtil.makeText("\n         Queue Success!", TextUtil.YELLOW));
        text = text.append(TextUtil.makeText("\n          Game Started\n", TextUtil.AQUA));
        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GREEN));
        TextUtil.broadcastMessage(text);
        stop();
        new StartGame();
    }

    private void stop() {
        isQueueOngoing = false;
        updatePlayers();
    }

    public boolean playerReadied(PlayerData pD) {
        if (isQueueOngoing) {
            readiedPlayers.add(pD);
            return true;
        }
        return false;
    }

    public static Queue getInstance() {
        if (instance == null) instance = new Queue();
        return instance;
    }

    private void updatePlayers() {

    }



}
