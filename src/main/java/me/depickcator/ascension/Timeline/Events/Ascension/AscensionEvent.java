package me.depickcator.ascension.Timeline.Events.Ascension;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Player.Cooldowns.EntityInteractionCooldown;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Teams.TeamAscension;
import me.depickcator.ascension.Teams.TeamStats;
import me.depickcator.ascension.Timeline.Timeline;
import me.depickcator.ascension.Utility.ItemComparison;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.Craftable.Unlocks.AscensionKey;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Timeline.Events.Winner.Winner;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Wither;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class AscensionEvent {

    private final Ascension plugin;
    private boolean eventOngoing;
    private final List<AscensionLocation> locations;
    private AscensionLocation ascendingLocation;
    private final Timeline timeline;
    private final int radius;
    public AscensionEvent(int radius) {
        plugin = Ascension.getInstance();
        eventOngoing = false;
        this.radius = radius;
        locations = generateLocations();
        timeline = plugin.getSettingsUI().getSettings().getTimeline();

        broadcastLocations();
    }

    private List<AscensionLocation> generateLocations() {
        Location spawn = Ascension.getSpawn();
        int x = spawn.getBlockX(); int z = spawn.getBlockZ();
        List<AscensionLocation> locations = new ArrayList<>();
        locations.add(new AscensionLocation(x + radius, z + radius, this));
        locations.add(new AscensionLocation(x + radius, z - radius, this));
        locations.add(new AscensionLocation(x - radius, z - radius, this));
        locations.add(new AscensionLocation(x - radius, z + radius, this));

        return locations;
    }

    /*Starts the Ascension event*/
    public void start(AscensionLocation ascensionLocation) {
        this.ascendingLocation = ascensionLocation;
        eventOngoing = true;
        locations.remove(ascensionLocation);
        ascensionLocation.startAnimation();
        ascensionLocation.getAscendingTeam().getTeamAscension().startAscension();
        plugin.getGameState().setCurrentState(GameStates.GAME_ASCENSION);
        loop(ascensionLocation);
//        timeline.pauseTimeline();
    }

    private void loop(AscensionLocation ascensionLocation) {
        new BukkitRunnable() {
            final TeamAscension team = ascensionLocation.getAscendingTeam().getTeamAscension();
            int timer = team.getAscensionTimer();
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
                if (timer % 60 == 2 && !e.isCharged()) {
                    e.getWorld().createExplosion(e, e.getLocation(), 4f, false, true, true);
//                    e.getHealth()
                }
                timer--;
                timeline.updatePlayers();
                ascensionLocation.updateBossBarHealth();
//                team.addAscensionTimer(-1);
                team.checkForRewards();
            }
        }.runTaskTimer(Ascension.getInstance(), 0, 20);
    }

    public void success() {
        new Winner(new ArrayList<>(List.of(ascendingLocation.getAscendingTeam())));
        successText();
        stop();
        TextUtil.debugText("Ascension Success");
    }

    public void failed() {
        plugin.getGameState().setCurrentState(GameStates.GAME_AFTER_GRACE);
        failedText();
        ascendingLocation.getAscendingTeam().getTeamAscension().failedAscension();
//        TeamAscension teamStats = ascendingLocation.getAscendingTeam().getTeamStats();
//        teamStats.addAscensionTimer((int) (teamStats.getAscensionTimer() * 0.3));
//        teamStats.setAscensionTimer(Integer.max((int) (teamStats.getAscensionTimer() * 1.3), 300));
        stop();
        checkForAscensionRemaining();
        timeline.updatePlayers();
//        timeline.startTimeline();
        TextUtil.debugText("Ascension Failed");
    }

    private void checkForAscensionRemaining() {
        int numberOfAscensionsBeforeFinal = plugin.getSettingsUI().getSettings().getAscensionsBeforeFinal();
        if (4 - locations.size() >= numberOfAscensionsBeforeFinal) {
            timeline.setTime(5);
            for (AscensionLocation ascensionLocation : locations) {
                ascensionLocation.closeLocation();
            }
        }
    }

    public void clear() {
        for(AscensionLocation location : locations) {
            location.closeLocation();
        }
    }

    private void stop() {
        eventOngoing = false;
        ascendingLocation.closeLocation();
        ascendingLocation = null;
    }

    private void failedText() {
        Component text = TextUtil.topBorder(TextUtil.DARK_GRAY);
        text = text.append(TextUtil.makeText("\n            ASCENSION DENIED\n", TextUtil.WHITE, true, false));
        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GRAY));
        TextUtil.broadcastMessage(text);
        SoundUtil.broadcastSound(Sound.ENTITY_WITHER_DEATH, 100, 1);
    }

    private void successText() {
        Component text = TextUtil.topBorder(TextUtil.DARK_GRAY);
        text = text.append(TextUtil.makeText("\n            ASCENSION COMPLETE\n", TextUtil.WHITE, true, false));
        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GRAY));
        TextUtil.broadcastMessage(text);
        SoundUtil.broadcastSound(Sound.ENTITY_ENDER_DRAGON_DEATH, 100, 1);
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
        if (EntityInteractionCooldown.getInstance().isOnCooldown(pD.getPlayer())) return false;
        TextUtil.debugText("Ran Ascension Check");
        Team team = pD.getPlayerTeam().getTeam();
        boolean canBeginAscension = team.getTeamStats().canBeginAscension();
        boolean hasAscensionKey = pD.getPlayer().getInventory().getItemInMainHand().equals(AscensionKey.getInstance().getResult());
        int ascTime = team.getTeamAscension().getAscensionTimer();
        int minutes = ascTime % 60 == 0 ? ascTime / 60 : (ascTime / 60) + 1;
        boolean hasEnoughTime = minutes <= timeline.getTimeTillNextBigEvent();
        Component text = TextUtil.topBorder(TextUtil.DARK_GRAY)
                .append(TextUtil.makeText("            Failed to Start Ascension", TextUtil.WHITE, true, false))
                .append(createRejectionText(!eventOngoing, "No Ascension Event Happening"))
                .append(createRejectionText(canBeginAscension, "Is Ascension Ready"))
                .append(createRejectionText(hasAscensionKey, "Has an Ascension Key"))
                .append(createRejectionText(hasEnoughTime, "Enough time for Ascension"))
                .append(TextUtil.bottomBorder(TextUtil.DARK_GRAY));
//        if (eventOngoing) return false;
//        int score = pD.getPlayerTeam().getTeam().getTeamStats().getGameScore();
        if (!eventOngoing && hasAscensionKey && canBeginAscension && hasEnoughTime) {
            return true;
        } else {
            pD.getPlayer().sendMessage(text);
            return false;
        }

    }

    private Component createRejectionText(boolean canQualify, String text) {
        TextColor color = canQualify ? TextUtil.GREEN : TextUtil.RED;
        return TextUtil.makeText("\n   " + text, color);
    }

//    public Team getAscendingTeam() {
//        return ascendingTeam;
//    }

    public AscensionLocation getAscendingLocation() {
        return ascendingLocation;
    }
}
