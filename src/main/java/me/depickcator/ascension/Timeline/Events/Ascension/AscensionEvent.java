package me.depickcator.ascension.Timeline.Events.Ascension;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.EntityInteraction;
import me.depickcator.ascension.Items.Craftable.Unlocks.FlintShovel;
import me.depickcator.ascension.Player.PlayerData;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Teams.TeamStats;
import me.depickcator.ascension.Timeline.Events.Winner.Winner;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.util.Vector;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class AscensionEvent {

    private final Ascension plugin;
    private boolean eventOngoing;
    private final List<AscensionLocation> locations;
    private AscensionLocation ascendingLocation;
    public AscensionEvent() {
        plugin = Ascension.getInstance();
        eventOngoing = false;
        locations = generateLocations();
        broadcastLocations();
    }

    private List<AscensionLocation> generateLocations() {
        Location spawn = Ascension.getSpawn();
        int x = spawn.getBlockX(); int z = spawn.getBlockZ(); int spread = 750;
        List<AscensionLocation> locations = new ArrayList<>();
        locations.add(new AscensionLocation(x + spread, z + spread, this));
        locations.add(new AscensionLocation(x + spread, z - spread, this));
        locations.add(new AscensionLocation(x - spread, z - spread, this));
        locations.add(new AscensionLocation(x - spread, z + spread, this));

        return locations;
    }

    public void start(AscensionLocation ascensionLocation) {
        this.ascendingLocation = ascensionLocation;
        eventOngoing = true;
        locations.remove(ascensionLocation);
        ascensionLocation.startAnimation();
        plugin.getGameState().setCurrentState(GameStates.GAME_ASCENSION);
        loop(ascensionLocation);
        plugin.getTimeline().pauseTimeline();
    }

    private void loop(AscensionLocation ascensionLocation) {
        new BukkitRunnable() {
            final TeamStats teamStats = ascensionLocation.getAscendingTeam().getTeamStats();
            int timer = teamStats.getAscensionTimer();
            final Wither e = (Wither) ascensionLocation.getEntity();
            @Override
            public void run() {
                if (!eventOngoing || e.isDead() || !plugin.getGameState().checkState(GameStates.GAME_ASCENSION)) {
                    failed();
                    cancel();
                    return;
                }
                if (timer <= 0) {
                    success();
                    cancel();
                    return;
                }
                if (timer % 60 == 0) {
                    teamStats.addGameScore(1);
                }
                timer--;
                TextUtil.debugText("Ascension Timer: " + timer);
                plugin.getTimeline().updatePlayers();
                teamStats.addAscensionTimer(-1);
            }
        }.runTaskTimer(Ascension.getInstance(), 0, 20);
    }

    public void success() {
        new Winner(new ArrayList<>(List.of(ascendingLocation.getAscendingTeam())));
        stop();
        TextUtil.debugText("Ascension Success");
    }

    public void failed() {
        plugin.getGameState().setCurrentState(GameStates.GAME_AFTER_GRACE);
        plugin.getTimeline().startTimeline();
        stop();
        TextUtil.debugText("Ascension Failed");
    }

    private void stop() {
        eventOngoing = false;
        ascendingLocation = null;

    }

    private void broadcastLocations() {
        Component text = TextUtil.topBorder(TextUtil.GOLD);
        text = text.append(TextUtil.makeText("\nAscension Locations: ", TextUtil.AQUA, true, false));
        for (AscensionLocation location : locations) {
            Location loc = location.getSpawnLocation();
            text = text.append(TextUtil.makeText(
                    "\n        (" +
                    loc.getBlockX() +
                    ", " +
                    loc.getBlockZ() +
                    ")", TextUtil.YELLOW));
        }
        TextUtil.broadcastMessage(text.append(TextUtil.bottomBorder(TextUtil.GOLD)));
    }

    public boolean canStartEvent(PlayerData pD) {
        if (eventOngoing) return false;
        int score = pD.getPlayerTeam().getTeam().getTeamStats().getGameScore();
        if (score < 25/*|| !pD.getPlayer().getInventory().getItemInMainHand().equals(FlintShovel.getInstance().getItem())*/) return false;
        return true;
    }

//    public Team getAscendingTeam() {
//        return ascendingTeam;
//    }

    public AscensionLocation getAscendingLocation() {
        return ascendingLocation;
    }
}
