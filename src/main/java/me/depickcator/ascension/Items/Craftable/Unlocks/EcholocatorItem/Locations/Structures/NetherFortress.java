package me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Locations.Structures;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Location;
import org.bukkit.generator.structure.Structure;
import org.bukkit.util.StructureSearchResult;

public class NetherFortress extends EcholocatorLocations {
    public NetherFortress() {
        super("Nether Fortress", Structure.FORTRESS, Ascension.getInstance().getNether());
    }

    @Override
    protected Location locateNearest(PlayerData pD) {
        return locateNearestStructure(pD.getPlayer().getLocation(), 1000);
    }
}
