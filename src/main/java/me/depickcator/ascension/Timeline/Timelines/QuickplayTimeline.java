package me.depickcator.ascension.Timeline.Timelines;

import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Timeline.Events.Ascension.AscensionEvent;
import me.depickcator.ascension.Timeline.Events.CarePackage.CarePackage;
import me.depickcator.ascension.Timeline.Events.Feast.Feast;
import me.depickcator.ascension.Timeline.Events.Scavenger.Scavenger;
import me.depickcator.ascension.Timeline.Timeline;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

public class QuickplayTimeline extends Timeline {

    public QuickplayTimeline() {
        super(70);
    }

    @Override
    protected void checkForMidGameEvents() {
        switch (getTimePassed()) {
            case 1 -> {
                setScavenger(new Scavenger());
                getScavenger().announceTrades();
            }
            case 10 -> {
                plugin.getGameState().setCurrentState(GameStates.GAME_AFTER_GRACE);
                plugin.getServer().broadcast(TextUtil.makeText("Grace Period has Ended", TextUtil.BLUE));
                SoundUtil.broadcastSound(Sound.ENTITY_WITHER_DEATH, 30, 1);
            }
            case 11 -> {
                setAscensionEvent(new AscensionEvent());
            }
            case 15, 32, 45, 60  -> {
                new CarePackage();
            }
            case 22, 52 -> {
                getScavenger().announceSpawnLocation();
            }
            case 27, 57 -> {
                getScavenger().spawnInScavenger();
            }
            case 40 -> {
                new Feast();

            }

        }
    }

//    public AscensionEvent getAscensionEvent() {
//        return ascensionEvent;
//    }
//
//    public Scavenger getScavenger() {
//        return scavenger;
//    }

    @Override
    protected List<Pair<String, Integer>> initNextBigEvents() {
        return new ArrayList<>(List.of(
                new MutablePair<>("Grace Period Ends", 10),
                new MutablePair<>("Feast", 40),
                new MutablePair<>("Final Ascension", getStartingMinutes())
        ));
    }
}
