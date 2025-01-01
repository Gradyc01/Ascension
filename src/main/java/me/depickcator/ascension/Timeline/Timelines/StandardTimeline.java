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

public class StandardTimeline extends Timeline {

    public StandardTimeline() {
        super(120);
    }

    @Override
    protected void checkForMidGameEvents() {
        switch (getTimePassed()) {
            case 3 -> {
                setScavenger(new Scavenger());
                getScavenger().announceTrades();
//                scavenger = new Scavenger();
//                scavenger.announceTrades();
            }
            case 20 -> {
                plugin.getGameState().setCurrentState(GameStates.GAME_AFTER_GRACE);
                plugin.getServer().broadcast(TextUtil.makeText("Grace Period has Ended", TextUtil.BLUE));
                SoundUtil.broadcastSound(Sound.ENTITY_WITHER_DEATH, 30, 1);
            }
            case 25 -> {
                setAscensionEvent(new AscensionEvent());
            }
            case 35, 65, 85, 112 -> {
                new CarePackage();
            }
            case 45, 95 -> {
                getScavenger().announceSpawnLocation();
            }
            case 50, 100 -> {
                getScavenger().spawnInScavenger();
            }
            case 80 -> {
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
                new MutablePair<>("Grace Period Ends", 20),
                new MutablePair<>("Feast", 80),
                new MutablePair<>("Final Ascension", getStartingMinutes())
        ));
    }
}
