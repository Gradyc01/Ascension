package org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.Locations.Structures.Villages;

import org.bingo4.Bingo4;
import org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import org.bingo4.Player.Data.PlayerData;
import org.bukkit.Location;
import org.bukkit.generator.structure.Structure;

public class Village_Savanna extends EcholocatorLocations {
    public Village_Savanna() {
        super("Village: Savanna", Structure.VILLAGE_SAVANNA, Bingo4.getInstance().getWorld());
    }

    @Override
    protected Location locateNearest(PlayerData pD) {
        Location loc = locateNearestStructure(pD.getPlayer().getLocation(), worldBorderSize);
        if (loc == null) {
            loc = locateNearestStructure(Bingo4.getSpawn(), worldBorderSize);
        }
        return loc;
    }
}
