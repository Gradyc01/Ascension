package me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Locations.Biomes;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Location;
import org.bukkit.block.Biome;

public class CherryGrove extends EcholocatorLocations {
    public CherryGrove() {
        super("Cherry Grove", Biome.CHERRY_GROVE, Ascension.getInstance().getWorld());
    }

    @Override
    protected Location locateNearest(PlayerData pD) {
        return locateNearestBiome(Ascension.getSpawn());
    }
}
