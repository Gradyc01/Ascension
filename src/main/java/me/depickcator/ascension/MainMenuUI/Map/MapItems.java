package me.depickcator.ascension.MainMenuUI.Map;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.common.location.ApolloBlockLocation;
import com.lunarclient.apollo.module.waypoint.Waypoint;
import com.lunarclient.apollo.module.waypoint.WaypointModule;
import com.lunarclient.apollo.recipients.Recipients;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Location;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapItems {
    private final List<MapItem> mapItems;
    private final HashMap<MapItem, String> lunarNames;
    private final WaypointModule waypointModule;
    public MapItems() {
        mapItems = new ArrayList<>();
        lunarNames = new HashMap<>();
        waypointModule = Apollo.getModuleManager().getModule(WaypointModule.class);
    }

    public List<MapItem> getMapItems() {
        return mapItems;
    }

    public void addMapItem(MapItem mapItems) {
        this.mapItems.add(mapItems);
        displayLunarWaypoint(mapItems);
    }

    public void removeMapItem(MapItem mapItems) {
        this.mapItems.remove(mapItems);
        removeLunarWaypoint(mapItems);
    }

    public void reMapItemsForLunar() {
        waypointModule.resetWaypoints(Recipients.ofEveryone());
        lunarNames.clear();
        for (MapItem mapItem : mapItems) {
            displayLunarWaypoint(mapItem);
        }
    }

    /*Removes a Lunar waypoint for all players */
    private void removeLunarWaypoint(MapItem mapItem) {
        try {
            WaypointModule waypointModule = Apollo.getModuleManager().getModule(WaypointModule.class);
            waypointModule.removeWaypoint(Recipients.ofEveryone(), lunarNames.get(mapItem));
        } catch (Exception e) {
            TextUtil.debugText("Error Occurred: " + e.getMessage());
        }
//        Optional<ApolloPlayer> apolloPlayerOpt = Apollo.getPlayerManager().getPlayer(p.getUniqueId());
//        apolloPlayerOpt.ifPresent(apolloPlayer -> waypointModule.removeWaypoint(apolloPlayer, "Spawn"));
    }

    private void displayLunarWaypoint(MapItem mapItem) {
        Location coords = mapItem.getLocation();
        String name = findLunarWaypointName(mapItem);
        Color waypointColor = findLunarWaypointColor(mapItem);
        Waypoint waypoint = Waypoint.builder()
                .location(ApolloBlockLocation.builder()
                        .world("world") // The world the waypoint should display in
                        .x(coords.getBlockX())
                        .y(coords.getBlockY())
                        .z(coords.getBlockZ())
                        .build()
                )
                .name(name)
                .color(waypointColor)
                .hidden(false)
                .build();
        waypointModule.displayWaypoint(Recipients.ofEveryone(), waypoint);
    }

    private String findLunarWaypointName(MapItem mapItem) {
        String name = mapItem.getName();
        while (lunarNames.containsValue(name)) {
            name = name + " ";
        }
        lunarNames.put(mapItem, name);
        return name;
    }

    private Color findLunarWaypointColor(MapItem mapItem) {
        return switch (mapItem.getType()) {
            case MapItem.ASCENSION -> Color.DARK_GRAY;
            case MapItem.SPAWN -> Color.BLUE;
            case MapItem.SCAVENGER ->Color.GREEN;
            case MapItem.CARE_PACKAGE -> Color.RED;
            default -> Color.WHITE;
        };
    }




}
