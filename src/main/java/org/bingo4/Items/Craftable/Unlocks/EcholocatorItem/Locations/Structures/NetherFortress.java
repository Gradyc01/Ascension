package org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.Locations.Structures;

import org.bingo4.Bingo4;
import org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import org.bingo4.Player.Data.PlayerData;
import org.bukkit.Location;
import org.bukkit.generator.structure.Structure;

public class NetherFortress extends EcholocatorLocations {
    public NetherFortress() {
        super("Nether Fortress", Structure.FORTRESS, Bingo4.getInstance().getNether());
    }

    @Override
    protected Location locateNearest(PlayerData pD) {
        return locateNearestStructure(pD.getPlayer().getLocation(), 1000);
    }
}
