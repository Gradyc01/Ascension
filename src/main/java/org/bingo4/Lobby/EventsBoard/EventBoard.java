package org.bingo4.Lobby.EventsBoard;

import org.bingo4.Lobby.Boards;
import org.bingo4.Lobby.EventsBoard.Boards.*;
import org.bingo4.Lobby.NPCs.EventsNPC;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.DisplayUtil;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.TextDisplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventBoard extends Boards {
    private final HashMap<String, Events> keys;
//    private final HashMap<Events, List<PlayerData>> boards;
    public EventBoard() {
//        boards = new HashMap<>();
        keys = new HashMap<>();
        initOtherDisplays();
    }
    @Override
    protected TextDisplay initTextDisplay() {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText("Events", TextUtil.YELLOW, true, false)
        ));
        Location loc = new Location(plugin.getSpawnWorld(), spawn.getX() - 18.4 , spawn.getY() + 105.3, spawn.getZ());
        new EventsNPC(spawn.getX() - 17, spawn.getY() + 101, spawn.getZ() - 7, new ImmutablePair<>(315, 0));
        return DisplayUtil.makeTextDisplay(loc, text, 270, 0, 450);
    }

    private void initOtherDisplays() {
        registerEventBoard(new CarePackage());
        registerEventBoard(new Feast());
        registerEventBoard(new Ascension());
        registerEventBoard(new Scavenger());
        registerEventBoard(new FinalAscension());
    }

    private void registerEventBoard(Events events) {
        keys.put(events.getKey(), events);
//        boards.put(events, new ArrayList<>());
    }

    public List<Pair<Material, String>> getEventRepresentations() {
        List<Pair<Material, String>> ans = new ArrayList<>();
        for (Events e : keys.values()) {
            ans.add(new ImmutablePair<>(e.getMaterial(), e.getKey()));
        }
        return ans;
    }

    public boolean showPlayerBoard(String key, PlayerData playerData) {
        Events event = keys.get(key);
        if (event == null) return false;
        for (Events e : keys.values()) {
            if (!e.equals(event)) {
//                TextUtil.debugText("Hiding " + e.getKey() + " board from " + playerData.getPlayer().getName());
                e.makeHiddenToPlayer(playerData.getPlayer());
            }
        }
        TextUtil.debugText("Showing " + event.getKey() + " board from " + playerData.getPlayer().getName());
        event.makeVisibleToPlayer(playerData.getPlayer());
        return true;
    }

    public void showDefaultBoard(PlayerData playerData) {
        for (String e : keys.keySet()) {
            showPlayerBoard(e, playerData);
            break;
        }
    }




}
