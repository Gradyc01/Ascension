package me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Locations.Biomes;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.block.Biome;
import org.bukkit.util.BiomeSearchResult;

public class MushroomIsland extends EcholocatorLocations {
    public MushroomIsland() {
        super("Mushroom Island", Biome.SWAMP, Ascension.getInstance().getWorld());
    }

    @Override
    protected org.bukkit.Location locateNearest(PlayerData pD) {
        return locateNearestBiome(Ascension.getSpawn());
    }
}
