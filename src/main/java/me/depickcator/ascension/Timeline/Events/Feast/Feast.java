package me.depickcator.ascension.Timeline.Events.Feast;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Teams.TeamUtil;
import net.kyori.adventure.audience.Audience;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Feast {
    private final Location spawn;
    private final List<Location> locations;
    private final WorldBorder border;
    private final Ascension plugin;
    public Feast() {
        spawn = Ascension.getSpawn();
        plugin = Ascension.getInstance();
        locations = new ArrayList<>();
        PlayerDeath.getInstance().respawnEveryone();
        initLocations();
        spreadAllTeams();
        loop();
        border = spawn.getWorld().getWorldBorder();
        border.setSize(205, 15);
        plugin.getGameState().setCurrentState(GameStates.GAME_FEAST_LOADING);
        TextUtil.debugText("Feast");
    }

    private void initLocations() {
        int x = 0;
        int z = 100;
        for (int i = 0; i < 9; i++) {
            if (x == 0) {
                locations.add(new Location(spawn.getWorld(),
                        (int) spawn.getX() + x,
                        spawn.getWorld().getHighestBlockYAt((int) spawn.getX() +  x,
                                (int) spawn.getZ() + z), (int) spawn.getZ() + z));
            } else {
                double y1 = spawn.getWorld().getHighestBlockYAt((int) spawn.getX() + x * -1  ,  (int) spawn.getZ() + z );
                double y2 = spawn.getWorld().getHighestBlockYAt((int) spawn.getX() + x,  (int) spawn.getZ() + z);
                locations.add(new Location(spawn.getWorld(),
                        (int) spawn.getX() + x * -1 + 0.5,
                        y1 + 1,
                        (int) spawn.getZ() + z + 0.5));
                locations.add(new Location(spawn.getWorld(),
                        (int) spawn.getX() + x + 0.5,
                        y2 + 1,
                        (int) spawn.getZ() + z + 0.5));
            }
            TextUtil.debugText(x + " , " + z + "Size of " + locations.size() + " locations");
            x = (i < 4) ? x + 25 : x - 25;
            z -= 25;
        }
        Collections.shuffle(locations);
    }

    private void spreadAllTeams() {
        List<Team> teams = TeamUtil.getEveryTeam(true);
        int index = 0;
        for (Team team : teams) {
            if (index >= locations.size()) {
                TextUtil.debugText("Overflowed On Locations");
                index = 0;
            }
            for (Player p : team.getTeamMembers()) {
                Location loc = locations.get(index);
                p.teleport(loc);
                p.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 1 , loc.getBlockZ()).setType(Material.GLASS);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, PotionEffect.INFINITE_DURATION, 128, false, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, PotionEffect.INFINITE_DURATION, 1, false, false));
                p.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0);
            }
            index++;
        }
    }



    private void loop() {
        new BukkitRunnable() {
            int time = 30;
            @Override
            public void run() {
                if (time == 0) {
                    TextUtil.debugText("Feast Started");
                    launch();
                    cancel();
                    return;
                }
                if (time <= 5 || time % 5 == 0) {
                    TextUtil.broadcastMessage(
                            TextUtil.makeText("The Feast", TextUtil.GOLD)
                                    .append(TextUtil.makeText(" begins in ", TextUtil.AQUA))
                                    .append(TextUtil.makeText(time + " Seconds", TextUtil.GREEN)));
                    SoundUtil.broadcastSound(Sound.UI_BUTTON_CLICK, 5, 1);
                }
                time--;
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    private void launch() {
        List<Team> teams = TeamUtil.getEveryTeam(true);
        for (Team team : teams) {
            for (Player p : team.getTeamMembers()) {
                p.removePotionEffect(PotionEffectType.SLOWNESS);
                p.removePotionEffect(PotionEffectType.REGENERATION);
                p.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0.41999998688697815);
            }
        }
        plugin.getGameState().setCurrentState(GameStates.GAME_AFTER_GRACE);
        new FeastChests(new Location(spawn.getWorld(), spawn.getX(), spawn.getY() + 3, spawn.getZ()), FeastSpecialChestLoot.getInstance(), 60, false, false);
        spawnRandomChests();
    }

    private void spawnRandomChests() {
        new BukkitRunnable() {
            int time = 60;
            Random r = new Random();
            @Override
            public void run() {
                if (time <= 0 || !plugin.getGameState().inGame()) {
                    TextUtil.debugText("Random Chests Stopped");
                    int worldBorderSize = plugin.getSettingsUI().getSettings().getWorldBorderSize();
                    border.setSize(worldBorderSize * 2, (int) (worldBorderSize - (border.getSize()/2)));
                    cancel();
                    return;
                }
                if (time % 2 == 0) {
                    int xM = r.nextInt(2) == 1 ? 1 : -1;
                    int zM = r.nextInt(2) == 1 ? 1 : -1;
                    int x = (int) (spawn.getX() + r.nextInt(30, 75) * xM);
                    int z = (int) (spawn.getZ() + r.nextInt(30, 75) * zM);
                    if (time % 4 == 0) {
                        new FeastChests(new Location(spawn.getWorld(), x, spawn.getY(), z), FeastRegularChestLoot.getInstance());
                    } else {
                        new FeastChests(new Location(spawn.getWorld(), x, spawn.getY(), z), FeastResourceChestLoot.getInstance());
                    }

                }
                time--;
            }
        }.runTaskTimer(plugin, 0, 20);
    }



}
