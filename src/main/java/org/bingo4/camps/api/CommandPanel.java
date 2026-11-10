package org.bingo4.camps.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public record CommandPanel(
        Location position,
        Material material,
        BlockFace facing,
        String command
) {}
