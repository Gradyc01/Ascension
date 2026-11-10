package org.bingo4.Player.Data;

import org.bingo4.Bingo4;
import org.bingo4.MainMenuUI.Map.NeutralMapRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.map.MapView;

import java.util.HashMap;
import java.util.Map;

import static org.bukkit.map.MapView.Scale.*;

public class PlayerMapData implements PlayerDataObservers {

    private final PlayerData playerData;
    private Player player;
    private final Map<MapView.Scale, MapView> mapViews;
    private MapView.Scale currentScale;

    public PlayerMapData(PlayerData playerData) {
        this.playerData = playerData;
        this.player = playerData.getPlayer();

        this.mapViews = new HashMap<>();
        currentScale = MapView.Scale.CLOSE;
        initializeMap(CLOSE);
        initializeMap(NORMAL);
        initializeMap(FAR);
        initializeMap(FARTHEST);
    }

    public void initializeMap(MapView.Scale scale) {
        MapView mapView = Bukkit.createMap(Bingo4.getInstance().getWorld());
        mapView.addRenderer(new NeutralMapRenderer());

        mapView.setScale(scale);
        mapView.setTrackingPosition(true);
        mapView.setUnlimitedTracking(false);
        mapViews.put(scale, mapView);

        updateMapCenter();

    }

    /**
     * Updates the player reference and synchronizes the map
     * with the player's current world and position.
     */
    @Override
    public void updatePlayer() {
        player = playerData.getPlayer();

        if (player == null || !player.isOnline()) {
            return;
        }

        // Keep the map associated with the player's current world.
        if (getCurrentMapView().getWorld() != player.getWorld()) {
            getCurrentMapView().setWorld(player.getWorld());
        }

        updateMapCenter();
    }

    /**
     * Centers the map around the player's current position.
     */
    public void updateMapCenter() {
        if (player == null || !player.isOnline()) {
            return;
        }

        int blocksPerPixel = getBlocksPerPixel();

        Location location = player.getLocation();

        int centerX = Math.floorDiv(
                location.getBlockX(),
                blocksPerPixel
        ) * blocksPerPixel;

        int centerZ = Math.floorDiv(
                location.getBlockZ(),
                blocksPerPixel
        ) * blocksPerPixel;

        getCurrentMapView().setCenterX(centerX);
        getCurrentMapView().setCenterZ(centerZ);
    }

    public Player getPlayer() {
        return player;
    }

    private int getBlocksPerPixel() {
        return switch (getCurrentMapView().getScale()) {
            case CLOSEST -> 1;
            case CLOSE -> 2;
            case NORMAL -> 4;
            case FAR -> 8;
            case FARTHEST -> 16;
        };
    }

    public MapView getCurrentMapView() {
        return mapViews.get(currentScale);
    }

    public MapView getNextMapView() {
        currentScale = switch (currentScale) {
            case CLOSE -> NORMAL;
            case NORMAL -> FAR;
            case FAR -> FARTHEST;
            default -> CLOSE;
        };
        return getCurrentMapView();
    }

}
