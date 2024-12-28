package me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Locations.Biomes;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Location;
import org.bukkit.block.Biome;

public class LushCaves extends EcholocatorLocations {
    public LushCaves() {
        super("Lush Caves", Biome.LUSH_CAVES, Ascension.getInstance().getWorld());
    }

    @Override
    protected Location locateNearest(PlayerData pD) {
        return locateNearestBiome(pD.getPlayer().getLocation());
    }
}
