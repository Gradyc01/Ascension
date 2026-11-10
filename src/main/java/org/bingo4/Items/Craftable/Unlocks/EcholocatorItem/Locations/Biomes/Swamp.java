package org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.Locations.Biomes;

import org.bingo4.Bingo4;
import org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import org.bingo4.Player.Data.PlayerData;
import org.bukkit.block.Biome;

public class Swamp extends EcholocatorLocations {
    public Swamp() {
        super("Swamp", Biome.SWAMP, Bingo4.getInstance().getWorld());
    }

    @Override
    protected org.bukkit.Location locateNearest(PlayerData pD) {
        return locateNearestBiome(Bingo4.getSpawn());
    }
}
