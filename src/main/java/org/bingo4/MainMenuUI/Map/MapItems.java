package org.bingo4.MainMenuUI.Map;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.common.location.ApolloBlockLocation;
import com.lunarclient.apollo.module.waypoint.Waypoint;
import com.lunarclient.apollo.module.waypoint.WaypointModule;
import com.lunarclient.apollo.recipients.Recipients;
import org.bingo4.Bingo4;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MapItems {
    private final List<MapItem> mapItems;
    private final HashMap<MapItem, String> lunarNames;
    private final Map<MapItem, UUID> locatorEntities;
    private final WaypointModule waypointModule;
    private final Bingo4 plugin = Bingo4.getInstance();
    private final List<MapItem> forceLoadSchedulerList;
    private BukkitTask forceLoadScheduler;
    public MapItems() {
        mapItems = new ArrayList<>();
        lunarNames = new HashMap<>();
        locatorEntities = new HashMap<>();
        forceLoadSchedulerList = new ArrayList<>();
        waypointModule = Apollo.getModuleManager().getModule(WaypointModule.class);
    }

    public List<MapItem> getMapItems() {
        return mapItems;
    }

    public void addMapItem(MapItem mapItem) {
        this.mapItems.add(mapItem);
        displayLunarWaypoint(mapItem);
        addLocatorEntity(mapItem);
        forceLoadMapItem(mapItem);
    }

    public void removeMapItem(MapItem mapItem) {
        this.mapItems.remove(mapItem);
        removeLunarWaypoint(mapItem);
        removeLocatorEntity(mapItem);
    }

    private void addLocatorEntity(MapItem mapItem) {
        ArmorStand entity = (ArmorStand) plugin.getWorld().spawnEntity(mapItem.getLocation(), EntityType.ARMOR_STAND);
        entity.setVisible(false);
        entity.setPersistent(true);
        entity.setSmall(true);
        entity.setInvulnerable(true);
        entity.setSilent(true);
        entity.getAttribute(Attribute.WAYPOINT_TRANSMIT_RANGE).setBaseValue(mapItem.getTransmitRange());
        entity.setWaypointColor(mapItem.getWaypointColor());

        locatorEntities.put(mapItem, entity.getUniqueId());
    }

    private void removeLocatorEntity(MapItem mapItem) {
        Entity entity = plugin.getWorld().getEntity(locatorEntities.get(mapItem));
        if (entity != null) entity.remove();
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
    }

    private void displayLunarWaypoint(MapItem mapItem) {
        Location coords = mapItem.getLocation();
        String name = findLunarWaypointName(mapItem);
        Color waypointColor = findLunarWaypointColor(mapItem);
        Waypoint waypoint = Waypoint.builder()
                .location(ApolloBlockLocation.builder()
                        .world(Bingo4.getInstance().getWorld().getName()) // The world the waypoint should display in
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

    private void forceLoadMapItem(MapItem mapItem) {
        forceLoadSchedulerList.add(mapItem);
        TextUtil.debugText(String.format("[Map Items] Added %s mapItem at %s to force loader", mapItem.getName(), mapItem.getLocation()));
        if (forceLoadScheduler != null) return;
        forceLoadScheduler = new BukkitRunnable() {
            @Override
            public void run() {
                if (forceLoadSchedulerList.isEmpty()) {
                    forceLoadScheduler.cancel();
                    forceLoadScheduler = null;
                    TextUtil.debugText("[Map Items] Force loader finished and cancelled");
                    return;
                }

                forceLoadItem(forceLoadSchedulerList.removeFirst());

            }
        }.runTaskTimer(plugin, 20L, 5 * 20L);
    }

    private void forceLoadItem(MapItem mapItem) {
        Location loc = mapItem.getLocation();
        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        TextUtil.debugText(String.format("[Map Items] Force loading chunk %s", TextUtil.formatLocation(loc)));
        loc.getWorld().setChunkForceLoaded(x, z, true);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (loc.getWorld().isChunkForceLoaded(x, z)) {
                    TextUtil.debugText(String.format("[Map Items] Remove force loaded chunk %s", TextUtil.formatLocation(loc)));
                    loc.getWorld().setChunkForceLoaded(x, z, false);
                }
            }
        }.runTaskLater(plugin, 60L);
    }





}
