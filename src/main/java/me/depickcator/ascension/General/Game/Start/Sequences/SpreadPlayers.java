package me.depickcator.ascension.General.Game.Start.Sequences;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Start.StartGame;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Teams.TeamUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class SpreadPlayers extends GameSequences {
    private final int radius;
    private final Random r;
    private World world;
    private int spreadDistance;
    public SpreadPlayers(int radius) {
        this.radius = radius;
        r = new Random();

        spreadDistance = Math.min(radius / 10, 200);

    }

    @Override
    public void run(GameLauncher game) {
        if (!(game instanceof StartGame)) {
            throw new IllegalArgumentException("Game is not a StartGame");
        }
        world = Ascension.getSpawn().getWorld();
        List<Team> teams = TeamUtil.getEveryTeam(true);
        List<Location> locations = generateSpawnLocations(teams.size());
        ((StartGame) game).setSpawnCages(new ArrayList<>(locations));
//        List<Location> locations = generateSpawnLocations(teams.size());
        new BukkitRunnable() {
            @Override
            public void run() {
                if (locations.isEmpty()) {
                    cancel();
                    game.callback();
                    return;
                }

                Location loc = locations.getFirst();
                locations.remove(loc);
                Team team = teams.getFirst();
                teams.remove(team);
                loc = new Location(world, loc.getX(), loc.getY(), loc.getZ());
                loc.add(0, 100, 0);
                buildGlassBox(loc);
                loc.add(3.5, 1, 3.5);
                for (Player p: team.getTeamMembers()) {
                    p.teleport(loc);
                    p.removePotionEffect(PotionEffectType.BLINDNESS);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 20 * 20, 0, false, false));
                }


            }
        }.runTaskTimer(plugin, 20, 20);
    }

    private void buildGlassBox(Location loc) {
        World world = loc.getWorld();
        int y1 = loc.getBlockY();
        int x1 = loc.getBlockX();
        int z1 = loc.getBlockZ();

        int y2 = y1 + 6;
        int x2 = x1 + 6;
        int z2 = z1 + 6;
        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                for (int z = z1; z <= z2; z++) {
//                    world.setBlockData(x, y, z, world.getBlockData(x, y, z));
                    Material mat = Material.AIR;
                    if (x == x1 || z == z1 || y == y1 || x == x2 || z == z2 || y == y2) {
                        mat = y == y1 && x != x1 && z != z1 && z != z2 && x != x2? Material.BARRIER : Material.GLASS;
                    }
                    Block block = world.getBlockAt(x, y, z);
                    block.setType(mat);
                }
            }
        }
    }

    private List<Location> generateSpawnLocations(int num) {
        List<Location> locs = new ArrayList<>();
        int fails = 0;
        while (locs.size() < num) {
            Location loc = generateCoordinates();
            if (loc.getBlock().isLiquid()) {
                TextUtil.debugText("Retrying...  Location Generated was on Liquid ");
            } else if (locs.isEmpty()) {
                TextUtil.debugText("Success! First Location added ");
                locs.add(loc);
            } else {
                boolean passed = true;
                for (Location l : locs) {
                    if (l.distance(loc) < spreadDistance) {
                        TextUtil.debugText("Retrying... Location too close to previously generated");
                        fails++;
                        passed = false;
                        if (fails > 10) {
                            TextUtil.debugText("Failed! Location Generation has failed over 10 times");
                            spreadDistance = -1;
                        }
                        break;
                    }
                }
                if (passed) {
                    locs.add(loc);
                    TextUtil.debugText("Success! Location added " + locs.size());
                }
            }
        }
        return locs;
    }

    private Location generateCoordinates() {
        Location spawn = Ascension.getSpawn();
        int x = spawn.getBlockX() + r.nextInt(-radius, radius + 1);
        int z = spawn.getBlockZ() + r.nextInt(-radius, radius + 1);
        return world.getHighestBlockAt(x, z).getLocation();
    }
}
