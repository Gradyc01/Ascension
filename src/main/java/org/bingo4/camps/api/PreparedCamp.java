package org.bingo4.camps.api;

import org.bukkit.World;
import java.util.List;

public interface PreparedCamp {
    List<CampMarker> markers();
    void paste(World world, int x, int y, int z, long seed);
}
