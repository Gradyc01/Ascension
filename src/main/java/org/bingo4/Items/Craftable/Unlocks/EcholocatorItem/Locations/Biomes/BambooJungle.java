package org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.Locations.Biomes;

import org.bingo4.Bingo4;
import org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import org.bingo4.Player.Data.PlayerData;
import org.bukkit.block.Biome;

public class BambooJungle extends EcholocatorLocations {
    public BambooJungle() {
        super("Bamboo Jungle", Biome.BAMBOO_JUNGLE, Bingo4.getInstance().getWorld());
    }

    @Override
    protected org.bukkit.Location locateNearest(PlayerData pD) {
        return locateNearestBiome(Bingo4.getSpawn());
    }
}
