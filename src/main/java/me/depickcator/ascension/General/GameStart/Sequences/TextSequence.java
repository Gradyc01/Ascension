package me.depickcator.ascension.General.GameStart.Sequences;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.StartGame;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TextSequence extends GameStartSequence {
    private final List<Component> texts;
    private final int seconds;
    public TextSequence() {
        this(15);
    }

    public TextSequence(int seconds) {
        this.texts = new ArrayList<>(List.of(
                text1(),
                text2(),
                text3(),
                text4(),
                text5()
        ));
        this.seconds = seconds;
    }

    @Override
    public void run(StartGame game) {

        new BukkitRunnable() {
            List<Component> gameTexts = new ArrayList<>(texts);
            @Override
            public void run() {
                if (gameTexts.isEmpty()) {
                    game.callback();
                    cancel();
                    return;
                }
                Component text = gameTexts.getFirst();
                TextUtil.broadcastMessage(text);
                gameTexts.removeFirst();
                if (gameTexts.isEmpty()) SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 100, 0);
            }
        }.runTaskTimer(Ascension.getInstance(), 0, seconds * 20L);
    }

    private Component text1() {
        return TextUtil.makeText("Text 1", TextUtil.GRAY);
//        SoundUtil.broadcastSound(Sound.AMBIENT_CAVE, 100, 0);
    }

    private Component text2() {
        return TextUtil.makeText("Text 2", TextUtil.GRAY);
//        SoundUtil.broadcastSound(Sound.AMBIENT_CAVE, 100, 0.6);
    }

    private Component text3() {
        return TextUtil.makeText("Text 3", TextUtil.GRAY);
//        SoundUtil.broadcastSound(Sound.AMBIENT_CAVE, 100, 1);
    }

    private Component text4() {
        return TextUtil.makeText("Text 4", TextUtil.GRAY);
//        SoundUtil.broadcastSound(Sound.AMBIENT_CAVE, 100, 1.2);
    }

    private Component text5() {
        Component topBorder = TextUtil.topBorder(TextUtil.GRAY);
        Component text = TextUtil.makeText("\n\n                        ASCENSION\n\n", TextUtil.WHITE, true, false);
        Component bottomBorder = TextUtil.bottomBorder(TextUtil.GRAY);
        return topBorder.append(text).append(bottomBorder);
//        SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 100, 0);
//        SoundUtil.broadcastSound(Sound.AMBIENT_CAVE, 100, 2);
    }
}
