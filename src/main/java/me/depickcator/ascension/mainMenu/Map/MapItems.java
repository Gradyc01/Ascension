package me.depickcator.ascension.MainMenu.Map;

import java.util.ArrayList;
import java.util.List;

public class MapItems {
    private final List<MapItem> mapItems;
    public MapItems() {
        mapItems = new ArrayList<>();
    }

    public List<MapItem> getMapItems() {
        return mapItems;
    }

    public void addMapItem(MapItem mapItems) {
        this.mapItems.add(mapItems);
    }

    public void removeMapItem(MapItem mapItems) {
        this.mapItems.remove(mapItems);
    }
}
