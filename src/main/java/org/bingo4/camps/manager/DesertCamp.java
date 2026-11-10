package org.bingo4.camps.manager;

import org.bingo4.camps.paper.PaperStructureTemplate;
import org.bukkit.Material;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static java.util.Map.entry;

public class DesertCamp extends AbstractCampType {
    public DesertCamp() throws IOException {
        super("camp_desert", PaperStructureTemplate.load("camp_desert", new File("./plugins/Ascension/Structures/camp_desert.nbt"), 0));
    }

    @Override
    public Map<Material, Integer> getUnFloodables() {
        return Map.ofEntries(
                entry(Material.SAND, 50),
                entry(Material.SANDSTONE, 30),
                entry(Material.RED_SAND, 20),
                entry(Material.AIR, 0),
                entry(Material.WHITE_GLAZED_TERRACOTTA, 0),
                entry(Material.LIME_TERRACOTTA, 0),
                entry(Material.CLAY, 0),
                entry(Material.CACTUS, 0),
                entry(Material.SMOOTH_SANDSTONE_STAIRS, 0),
                entry(Material.HAY_BLOCK, 0),
                entry(Material.SANDSTONE_WALL, 0),
                entry(Material.CUT_SANDSTONE, 0),
                entry(Material.SMOOTH_SANDSTONE, 0)
        );

    }
}
