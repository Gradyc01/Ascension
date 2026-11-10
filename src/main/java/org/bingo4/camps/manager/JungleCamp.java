package org.bingo4.camps.manager;

import org.bingo4.camps.paper.PaperStructureTemplate;
import org.bukkit.Material;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static java.util.Map.entry;

public class JungleCamp extends AbstractCampType {
    public JungleCamp() throws IOException {
        super("camp_jungle", PaperStructureTemplate.load("camp_jungle", new File("./plugins/Ascension/Structures/camp_jungle.nbt"), 0, 16, 16, 0, 4));
    }

    @Override
    public Map<Material, Integer> getUnFloodables() {
        return Map.ofEntries(
                entry(Material.DIRT, 80),
                entry(Material.COARSE_DIRT, 10),
                entry(Material.ROOTED_DIRT, 10),
                entry(Material.BAMBOO, 0),
                entry(Material.MELON, 0),
                entry(Material.JUNGLE_LOG, 0),
                entry(Material.VINE, 0),
                entry(Material.AIR, 0)
        );

    }
}
