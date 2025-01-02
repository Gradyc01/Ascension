package me.depickcator.ascension.Timeline.Timelines;

import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Items.ItemList;
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
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BrawlTimeline extends Timeline {

    public BrawlTimeline() {
        super(35);
    }

    @Override
    protected void checkForMidGameEvents() {
        switch (getTimePassed()) {
            case 1 -> {
                setAscensionEvent(new AscensionEvent(150));
            }
            case 2 -> {
                plugin.getGameState().setCurrentState(GameStates.GAME_AFTER_GRACE);
                plugin.getServer().broadcast(TextUtil.makeText("Grace Period has Ended", TextUtil.BLUE));
                SoundUtil.broadcastSound(Sound.ENTITY_WITHER_DEATH, 30, 1);
            }
            case 5, 12, 30  -> {
                new CarePackage(200);
            }
            case 20 -> {
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
                new MutablePair<>("Grace Period Ends", 2),
                new MutablePair<>("Feast", 20),
                new MutablePair<>("Final Ascension", getStartingMinutes())
        ));
    }
}
