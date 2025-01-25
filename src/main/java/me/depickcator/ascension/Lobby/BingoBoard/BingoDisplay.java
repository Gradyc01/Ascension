package me.depickcator.ascension.Lobby.BingoBoard;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Lobby.Boards;
import me.depickcator.ascension.Lobby.EventsBoard.Boards.*;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.ArmorStandUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.TextDisplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BingoDisplay extends Boards {
    private final HashMap<String, Events> keys;
    public BingoDisplay() {
        keys = new HashMap<>();
        initOtherDisplays();
    }
    @Override
    protected TextDisplay initTextDisplay() {
        List<Component> text = new ArrayList<>(List.of(
                TextUtil.makeText("Board", TextUtil.YELLOW, true, false)
        ));
        Location spawn = Ascension.getSpawn();
        Location loc = new Location(plugin.getWorld(), spawn.getX() + 18.4 , spawn.getY() + 105.3, spawn.getZ());
        return ArmorStandUtil.makeTextDisplay(loc, text, 90, 0, 450);
    }

    private void initOtherDisplays() {
    }

    // private void registerEventBoard(Events events) {
    //     keys.put(events.getKey(), events);
    // }

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
                TextUtil.debugText("Hiding " + e.getKey() + " board from " + playerData.getPlayer().getName());
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
