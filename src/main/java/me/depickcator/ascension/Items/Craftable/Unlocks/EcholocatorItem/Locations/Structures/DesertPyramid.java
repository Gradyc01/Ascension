package me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Locations.Structures;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Location;
import org.bukkit.generator.structure.Structure;

public class DesertPyramid extends EcholocatorLocations {
    public DesertPyramid() {
        super("Desert Temple", Structure.DESERT_PYRAMID, Ascension.getInstance().getWorld());
    }

    @Override
    protected Location locateNearest(PlayerData pD) {
        return locateNearestStructure(pD.getPlayer().getLocation());
    }
}
