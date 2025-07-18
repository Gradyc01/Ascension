package me.depickcator.ascension.General.Queue;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.Start.StartGame;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

public class Queue {
    private Set<PlayerData> allPlayers;
    private Set<PlayerData> readiedPlayers;
    private static Queue instance;
    private boolean isQueueOngoing;
    private final Ascension plugin;
    private int timer;
    private BukkitTask task;
    private Queue() {
        plugin = Ascension.getInstance();
        isQueueOngoing = false;
    }

    public boolean startQueue(PlayerData pD) {
        if (isQueueOngoing || !plugin.getGameState().checkState(GameStates.LOBBY_NORMAL)) return false;
        isQueueOngoing = true;
        allPlayers = new HashSet<>();
        allPlayers.addAll(PlayerUtil.getAllPlayingPlayers());
        readiedPlayers = new HashSet<>();
        plugin.getGameState().setCurrentState(GameStates.LOBBY_QUEUE);
        loop();
        playerReadied(pD);
        startQueueText();
        return true;
    }

    public void stopQueue() {
        if (isQueueOngoing) {
            failed();
            task.cancel();
        }
    }

    private void startQueueText() {
        Component text = TextUtil.topBorder(TextUtil.DARK_GREEN);
        text = text.append(TextUtil.makeText("\n                    Queue started", TextUtil.YELLOW, true, false));
        text = text.append(TextUtil.makeText("\n\n  Ready up by clicking ", TextUtil.AQUA));
        text = text.append(TextUtil.makeText("[Here]", TextUtil.DARK_PURPLE)
                .hoverEvent(HoverEvent.showText(TextUtil.makeText("Click here to ready up", TextUtil.DARK_PURPLE)))
                .clickEvent(ClickEvent.runCommand("/queue accept")));
        text = text.append(TextUtil.makeText(" or by typing /queue accept\n", TextUtil.AQUA));
        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GREEN));
        TextUtil.broadcastMessage(text);
        SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 100, 0);
    }

    private void loop() {
        timer = 30;
        task = new BukkitRunnable() {
            public void run() {
                if (!isQueueOngoing || timer <= 0) {
                    failed();
                    cancel();
                    return;
                }
                if ((float)readiedPlayers.size()/(float)allPlayers.size() >= 0.75 ) {
                    success();
                    cancel();
                    return;
                }
                TextUtil.debugText("Queue timeline ran (" + timer + ")");
                updatePlayers();
                timer--;
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    private void failed() {
        Component text = TextUtil.topBorder(TextUtil.DARK_GREEN);
        text = text.append(TextUtil.makeText("\n                    Queue failed", TextUtil.YELLOW, true, false));
        text = text.append(TextUtil.makeText("\n              Not every player had readied up\n", TextUtil.AQUA));
        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GREEN));
        TextUtil.broadcastMessage(text);
        stop();
    }

    private void success() {
        Component text = TextUtil.topBorder(TextUtil.DARK_GREEN);
        text = text.append(TextUtil.makeText("\n                    Queue Success!", TextUtil.YELLOW, true, false));
        text = text.append(TextUtil.makeText("\n               Spreading players please be patient\n", TextUtil.AQUA));
        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GREEN));
        TextUtil.broadcastMessage(text);
        SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);
        stop();
        new BukkitRunnable() {
            public void run() {
                new StartGame();
            }
        }.runTaskLater(plugin, 3 * 20);

    }

    private void stop() {
        isQueueOngoing = false;
        plugin.getGameState().setCurrentState(GameStates.LOBBY_NORMAL);
        updatePlayers();
    }

    public boolean playerReadied(PlayerData pD) {
        if (isQueueOngoing) {
            return readiedPlayers.add(pD);
        }
        return false;
    }

    public static Queue getInstance() {
        if (instance == null) instance = new Queue();
        return instance;
    }

    private void updatePlayers() {
        for (PlayerData pD : allPlayers) {
            pD.getPlayerScoreboard().update();
        }
    }

    public Pair<Integer, Integer> getPlayerQueueNumbers() {
        return new MutablePair<>(readiedPlayers.size(), allPlayers.size());
    }

    public int getTimer() {
        return timer;
    }



}
