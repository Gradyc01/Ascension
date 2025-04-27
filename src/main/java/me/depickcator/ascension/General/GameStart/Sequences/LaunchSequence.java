package me.depickcator.ascension.General.GameStart.Sequences;

import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.StartGame;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class LaunchSequence extends GameStartSequence {
    @Override
    public void run(StartGame game) {
        new BukkitRunnable() {
            @Override
            public void run() {
                BukkitScheduler scheduler = plugin.getServer().getScheduler();
                scheduler.runTaskLater(plugin, () -> {
                    makeAndShowTitle("Game Begins In", " ", 1, 5, 0);
                }, 20);
                scheduler.runTaskLater(plugin, () -> {
                    makeAndShowTitle("Game Begins In", "3", 0, 5, 0);
                    SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1f);
                }, 60);
                scheduler.runTaskLater(plugin, () -> {
                    makeAndShowTitle("Game Begins In", "2", 0, 5, 0);
                    SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                    game.callback(); // //TODO I put call back here
                }, 80);
                scheduler.runTaskLater(plugin, () -> {
                    makeAndShowTitle("Game Begins In", "1", 0, 5, 0);
                    SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                }, 100);
                scheduler.runTaskLater(plugin, () -> {
//                    gameState.setCurrentState(GameStates.GAME_BEFORE_GRACE);
                    makeAndShowTitle("GO!!!!", " ", 0, 2, 3);
                    SoundUtil.broadcastSound(Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 2.0f);
//                    plugin.getTimeline().startTimeline();
                }, 120);
            }
        }.runTaskLater(this.plugin, 20);

    }


    private void makeAndShowTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        Component titleT = TextUtil.makeText(title, TextUtil.YELLOW, false, false);
        Component subtitleT = TextUtil.makeText(subtitle, TextUtil.YELLOW, false, false);
        Title t = TextUtil.makeTitle(titleT, subtitleT, fadeIn, stay, fadeOut);
        plugin.getServer().showTitle(t);
    }
}
