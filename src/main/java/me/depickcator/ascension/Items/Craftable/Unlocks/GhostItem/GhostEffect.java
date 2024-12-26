package me.depickcator.ascension.Items.Craftable.Unlocks.GhostItem;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GhostEffect {
//    private final PlayerData playerData;
    private final Player player;
    private final Location startingLocation;
    private BukkitTask task;
    private final Ascension plugin;
    public GhostEffect(PlayerData playerData) {
//        this.playerData = playerData;
        this.player = playerData.getPlayer();
        this.startingLocation = player.getLocation();
        this.plugin = Ascension.getInstance();
        start();
    }

    private void start() {
        player.setGameMode(GameMode.SPECTATOR);
        player.getWorld().playSound(startingLocation, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 15f, 1);
        TextUtil.broadcastMessage(TextUtil.makeText(player.getName() + " has ghosted", TextUtil.BLACK));
        loop();
    }

    private void loop() {
        task = new BukkitRunnable() {
            int time = 60;
            @Override
            public void run() {
                if (!plugin.getGameState().inGame()) {
                    stop();
                }
                TextUtil.debugText("Ghost Timer for " + player.getName() + ": " + time);
                if (time > 0) {
                    if (time <= 5) {
                        player.sendMessage(TextUtil.makeText(time + "", TextUtil.GRAY));
                        SoundUtil.playHighPitchPling(player);
                    }
                    time--;

                } else {
                    eventComplete();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    private void eventComplete() {
        player.teleport(startingLocation);
        player.getWorld().playSound(startingLocation, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 3, 2);
        stop();
    }

//    private void eventComplete() {
//        player.setGameMode(GameMode.SURVIVAL);
//    }


    private void stop() {
        task.cancel();
        player.setGameMode(GameMode.SURVIVAL);
    }
}
