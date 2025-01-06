package me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Locations.Biomes;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Location;
import org.bukkit.block.Biome;

public class PaleGarden extends EcholocatorLocations {
    public PaleGarden() {
        super("Pale Garden", Biome.PALE_GARDEN, Ascension.getInstance().getWorld());
    }

    @Override
    protected Location locateNearest(PlayerData pD) {
        return locateNearestBiome(Ascension.getSpawn());
    }
}
