package org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.Locations.Biomes;

import org.bingo4.Bingo4;
import org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import org.bingo4.Player.Data.PlayerData;
import org.bukkit.Location;
import org.bukkit.block.Biome;

public class LushCaves extends EcholocatorLocations {
    public LushCaves() {
        super("Lush Caves", Biome.LUSH_CAVES, Bingo4.getInstance().getWorld());
    }

    @Override
    protected Location locateNearest(PlayerData pD) {
        return locateNearestBiome(pD.getPlayer().getLocation());
    }
}
