package org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.Locations.Structures;

import org.bingo4.Bingo4;
import org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import org.bingo4.Player.Data.PlayerData;
import org.bukkit.generator.structure.Structure;

public class Mineshaft extends EcholocatorLocations {
    public Mineshaft() {
        super("Mineshaft", Structure.MINESHAFT, Bingo4.getInstance().getWorld());
    }

    @Override
    protected org.bukkit.Location locateNearest(PlayerData pD) {
        return locateNearestStructure(pD.getPlayer().getLocation());
    }
}
