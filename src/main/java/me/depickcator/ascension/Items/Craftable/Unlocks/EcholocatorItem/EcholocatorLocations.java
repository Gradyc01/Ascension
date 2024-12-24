package me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem;

import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.generator.structure.Structure;
import org.bukkit.util.BiomeSearchResult;
import org.bukkit.util.StructureSearchResult;

public abstract class EcholocatorLocations {
    private final String name;
    private final Structure structure;
    private final Biome biome;
    // private final Ascension plugin;
    private final World world;
    public EcholocatorLocations(String name, Structure structure, World world) {
        this.name = name;
        this.structure = structure;
        this.biome = null;
        // this.plugin = Ascension.getInstance();
        this.world = world;
    }

    public EcholocatorLocations(String name, Biome biome, World world) {
        this.name = name;
        this.structure = null;
        this.biome = biome;
        // this.plugin = Ascension.getInstance();
        this.world = world;
    }

    public String getName() {
        return name;
    }

    public boolean locate(PlayerData pD) {
        Player p = pD.getPlayer();
        if (!p.getWorld().equals(world)) {
            TextUtil.errorMessage(p, "You are not in the correct world!");
            return false;
        }
        Location loc = locateNearest(pD);
        Component text = TextUtil.topBorder(TextUtil.BLUE);
        if (loc == null || !world.getWorldBorder().isInside(loc)) {
            text = text.append(TextUtil.makeText("                  " + name + " not found", TextUtil.RED));
            text = text.append(TextUtil.bottomBorder(TextUtil.BLUE));
            TextUtil.broadcastMessage(text, pD.getPlayerTeam().getTeam().getTeamMembers());
            return false;
        }
        text = text.append(TextUtil.makeText("\n                     " + name + " found!", TextUtil.GOLD));
        text = text.append(TextUtil.makeText("\n\n                (" + loc.getBlockX() + /*", " + loc.getBlockY() + */", " + loc.getBlockZ() + ")\n", TextUtil.DARK_RED));
        text = text.append(TextUtil.bottomBorder(TextUtil.BLUE));
        TextUtil.broadcastMessage(text, pD.getPlayerTeam().getTeam().getTeamMembers());
        return true;

    }

//    protected int searchRadius() {
//        return world.getWorldBorder().getSize()/2 < 3000 ? (int) (world.getWorldBorder().getSize() / 2) : 3000;
//    }

    protected abstract Location locateNearest(PlayerData pD);

    protected Location locateNearestStructure(Location loc) {
        return locateNearestStructure(loc, 3000);
    }

    protected Location locateNearestStructure(Location loc, int radius) {
        StructureSearchResult result = world.locateNearestStructure(loc, structure, radius, false);
        return result == null ? null : result.getLocation();
    }

    protected Location locateNearestBiome(Location loc) {
        BiomeSearchResult result = world.locateNearestBiome(loc, 3000, biome);
        return result == null ? null : result.getLocation();
    }

    protected Structure getStructure() {
        return structure;
    }

    protected Biome getBiome() {
        return biome;
    }

    protected World getWorld() {
        return world;
    }
}
