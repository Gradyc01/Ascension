package me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Locations.Structures;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.EcholocatorLocations;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.generator.structure.Structure;

public class Trials extends EcholocatorLocations {
    public Trials() {
        super("Trials", Structure.TRIAL_CHAMBERS, Ascension.getInstance().getWorld());
    }

    @Override
    protected org.bukkit.Location locateNearest(PlayerData pD) {
        return locateNearestStructure(Ascension.getSpawn());
    }
}
