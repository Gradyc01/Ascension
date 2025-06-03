package me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Locations.Structures.Villages;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Location;
import org.bukkit.generator.structure.Structure;

public class Village_Taiga extends EcholocatorLocations {
    public Village_Taiga() {
        super("Village: Taiga", Structure.VILLAGE_TAIGA, Ascension.getInstance().getWorld());
    }

    @Override
    protected Location locateNearest(PlayerData pD) {
        Location loc = locateNearestStructure(pD.getPlayer().getLocation(), worldBorderSize);
        if (loc == null) {
            loc = locateNearestStructure(Ascension.getSpawn(), worldBorderSize);
        }
        return loc;
    }
}
