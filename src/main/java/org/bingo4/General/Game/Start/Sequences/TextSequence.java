package org.bingo4.General.Game.Start.Sequences;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.General.LocationChecker.LocationCheck;
import org.bingo4.Settings.Settings;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TextSequence extends GameSequences {
    private List<Component> texts;
    private final int seconds;
    private Settings settings;
    public TextSequence() {
        this(15);
    }

    public TextSequence(int seconds) {
//        settings = Bingo4.getInstance().getSettingsUI().getSettings();
        ;
        this.seconds = seconds;

    }

    @Override
    public void run(GameLauncher game) {
        settings = Bingo4.getInstance().getSettingsUI().getSettings();
        this.texts = new ArrayList<>(List.of(
                text1(),
                text2(),
                text3(),
                text4(),
                text5()
        ));
//
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
                if (gameTexts.isEmpty()) {
                    SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 100, 0);
                } else {
                    SoundUtil.broadcastSound(Sound.UI_BUTTON_CLICK, 100, 1);
                }
            }
        }.runTaskTimer(Bingo4.getInstance(), 0, seconds * 20L);
    }

    private Component text1() {
//        return TextUtil.makeText("Text 1", TextUtil.GRAY);
        Component text = TextUtil.topBorder(TextUtil.GOLD);
//        text = text.append(TextUtil.makeText("\n Collect Items on the Board & Level up skills to earn souls", TextUtil.YELLOW));
//        text = text.append(TextUtil.makeText("\n     Gear up to defend yourself against mobs and peers", TextUtil.YELLOW));
        text = text.append(TextUtil.makeText("\n Collect Items on the Board to become more enlightened", TextUtil.YELLOW));
        text = text.append(TextUtil.makeText("\n Level up skills to earn souls to help with your journey", TextUtil.YELLOW));
        text = text.append(TextUtil.makeText("\n   Gear up to defend yourself against mobs and peers", TextUtil.YELLOW));
        text = text.append(TextUtil.makeText("\n    When ready find a Gatekeeper to begin Bingo4\n", TextUtil.YELLOW));

        text = text.append(TextUtil.bottomBorder(TextUtil.GOLD));
        return text;
    }

    private Component text2() {
        Component text = TextUtil.topBorder(TextUtil.GOLD);

        text = text.append(TextUtil.makeText("\n           Game Information", TextUtil.YELLOW, true, false));
        text = text.append(TextUtil.makeText("\n\n Preset: " + settings.getName(), TextUtil.GOLD));
        text = text.append(TextUtil.makeText("\n\n Score Requirement: " + settings.getBingoLinesRequirement(), TextUtil.AQUA));
        text = text.append(TextUtil.makeText("\n World Border: " + settings.getWorldBorderSize(), TextUtil.AQUA));
        LocationCheck locationCheck = Bingo4.getInstance().getLocationCheck();
        if (locationCheck.isCheckCompleted()) text = text.append(TextUtil.makeText("\n\n World Score: " + locationCheck.getPercentageScore() +  "%\n", TextUtil.AQUA));

        text = text.append(TextUtil.bottomBorder(TextUtil.GOLD));

        return text;
    }

    private Component text3() {
        Component text = TextUtil.topBorder(TextUtil.GOLD);
        List<Integer> distribution = settings.getItemDistribution();
        text = text.append(TextUtil.makeText("\n           Item Distribution", TextUtil.AQUA, true, false));
        text = text.append(TextUtil.makeText("\n\n Easy Items: " + distribution.get(0), TextUtil.GREEN));
        text = text.append(TextUtil.makeText("\n Medium Items: " + distribution.get(1), TextUtil.YELLOW));
        text = text.append(TextUtil.makeText("\n Hard Items: " + distribution.get(2), TextUtil.RED));
        text = text.append(TextUtil.makeText("\n Custom Items: " + distribution.get(3) + "\n", TextUtil.BLUE));
        text = text.append(TextUtil.bottomBorder(TextUtil.GOLD));
        return text;
    }

    private Component text4() {
        return TextUtil.makeText("Happy Hunting!", TextUtil.GRAY);
    }

    private Component text5() {
        Component topBorder = TextUtil.topBorder(TextUtil.GRAY);
        Component text = TextUtil.makeText("\n\n                        ASCENSION\n\n", TextUtil.WHITE, true, false);
        Component bottomBorder = TextUtil.bottomBorder(TextUtil.GRAY);
        return topBorder.append(text).append(bottomBorder);
    }
}
