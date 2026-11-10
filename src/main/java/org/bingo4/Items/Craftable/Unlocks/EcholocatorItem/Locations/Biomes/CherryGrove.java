package org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.Locations.Biomes;

import org.bingo4.Bingo4;
import org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import org.bingo4.Player.Data.PlayerData;
import org.bukkit.Location;
import org.bukkit.block.Biome;

public class CherryGrove extends EcholocatorLocations {
    public CherryGrove() {
        super("Cherry Grove", Biome.CHERRY_GROVE, Bingo4.getInstance().getWorld());
    }

    @Override
    protected Location locateNearest(PlayerData pD) {
        return locateNearestBiome(Bingo4.getSpawn());
    }
}
