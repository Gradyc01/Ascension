package org.bingo4.Structures.BingoTurnIn;

import org.bingo4.Bingo4;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Player.Data.Scoreboards.GameBoard;
import org.bingo4.Teams.Team;
import org.bingo4.Teams.TeamUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BoundingBox;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BingoTurnInProgress {
    private static final int CAPTURE_TIME = 5;

    private final BingoTurnInInstance instance;
    private final Bingo4 plugin = Bingo4.getInstance();

    private final Map<Team, Integer> startingTime = new HashMap<>();
    private final Map<Team, Integer> timeToCompletion = new HashMap<>();
    private final Map<Team, Integer> captureProgress = new HashMap<>();
    private final Set<PlayerData> bingoCaptureBoardPlayers = new HashSet<>();

    private Team controllingTeam;
    private BukkitTask task;

    public BingoTurnInProgress(BingoTurnInInstance instance) {
        this.instance = instance;
    }

    public void start(PlayerData playerData, BoundingBox boundingBox, ItemStack item) {
        bingoCaptureBoardPlayers.clear();
        for (Team team : TeamUtil.getEveryTeam()) {
            timeToCompletion.put(team, 30);
            startingTime.put(team, 30);
            captureProgress.put(team, 0);
        }
        captureProgress.put(playerData.getPlayerTeam().getTeam(), CAPTURE_TIME);

        loop(boundingBox, plugin.getWorld(), playerData, item);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }

        for (PlayerData playerData : bingoCaptureBoardPlayers) {
            playerData.getPlayerScoreboard().makeGameBoard();
        }

        timeToCompletion.clear();
        captureProgress.clear();
        controllingTeam = null;
        instance.stop();
    }

    /**
     * Gets the completion percentage of how close each team is to completing the capture.
     * @return an integer between 0 - 100 representing percentage
     */
    public Map<Team, Integer> getCompletionPercentage() {
        Map<Team, Integer> percentages = new HashMap<>();

        for (Map.Entry<Team, Integer> entry : timeToCompletion.entrySet()) {
            int remainingTime = entry.getValue();
            int starting = startingTime.getOrDefault(entry.getKey(), 30);

            int percentage = (int) (((double) (starting - remainingTime) / starting) * 100);
            percentages.put(entry.getKey(), Math.clamp(percentage, 0, 100));
        }

        return percentages;
    }

    /**
     * Gets the completion percentage of how close each team is to capturing and beginning progress.
     * @return an integer between 0 - 100 representing percentage
     */
    public Map<Team, Integer> getCapturePercentage() {
        Map<Team, Integer> percentages = new HashMap<>();

        for (Map.Entry<Team, Integer> entry : captureProgress.entrySet()) {
            int progress = entry.getValue();

            int percentage = (int) (((double) progress / CAPTURE_TIME) * 100);
            percentages.put(entry.getKey(), Math.clamp(percentage, 0, 100));
        }

        return percentages;
    }

    private void loop(BoundingBox box, World world, PlayerData playerData, ItemStack item) {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                TextUtil.debugText("[Bingo Turn In] Progress is still running");

                Set<Team> presentTeams = new HashSet<>();
                Set<PlayerData> playerDataSet = new HashSet<>();

                for (Entity entity : world.getNearbyEntities(
                        box, e -> e instanceof Player p && p.getGameMode().equals(GameMode.SURVIVAL) && PlayerUtil.getPlayerData(p) != null)) {

                    PlayerData data = PlayerUtil.getPlayerData((Player) entity);

                    if (data == null || data.getPlayerTeam() == null) continue;

                    playerDataSet.add(data);

                    if (bingoCaptureBoardPlayers.add(data)) {
                        data.getPlayerScoreboard().makeBingoCaptureBoard(instance);

                        if (data.getPlayerScoreboard().getBoards() instanceof GameBoard board) {
                            board.updateTime();
                        }
                    }

                    Team team = data.getPlayerTeam().getTeam();

                    if (team != null) {
                        presentTeams.add(team);
                    }
                }

                updateScoreboard();

                boolean contested = presentTeams.size() > 1;

                // Update capture progress for every team.
                for (Team team : captureProgress.keySet()) {

                    int progress = captureProgress.getOrDefault(team, 0);

                    if (presentTeams.contains(team)) {

                        // Teams standing on the point gain capture progress.
                        progress = Math.min(CAPTURE_TIME, progress + 1);

                    } else if (presentTeams.stream().anyMatch(other -> other != team)) {

                        // Teams lose capture progress when an enemy is present.
                        progress = Math.max(0, progress - 1);
                    }

                    captureProgress.put(team, progress);

                    // Transfer ownership once a team completes its capture.
                    if (progress >= CAPTURE_TIME
                            && presentTeams.contains(team)
                            && controllingTeam != team) {
                        controllingTeam = team;
                    }
                }

                // Update hold progress for EVERY team that has fully captured.
                for (Team team : timeToCompletion.keySet()) {

                    if (captureProgress.getOrDefault(team, 0) < CAPTURE_TIME) {
                        continue;
                    }

                    int time = timeToCompletion.getOrDefault(team, 30);

                    if (time > 0) {

                        // Contested teams still gain hold progress,
                        // but cannot reach completion.
                        if (contested) {
                            time = Math.max(1, time - 1);
                        } else {
                            time--;
                        }

                        timeToCompletion.put(team, time);
                    }

                    // Only the controlling team can complete the objective.
                    if (time <= 0 && !contested && controllingTeam == team) {
                        onCaptureComplete(team, playerDataSet, playerData, item);
                        stop();
                        cancel();
                        return;
                    }
                }


                if (!plugin.getGameState().inGame()) {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private void onCaptureComplete(Team team, Set<PlayerData> playerDatas, PlayerData original, ItemStack item) {
        // Your completion logic here.
        if (original.getPlayerTeam().getTeam().equals(team)) {
            plugin.getBingoData().completeClaimItemRewards(item, true, false, original, null);
        } else {
            for (PlayerData pD : team.getTeamMembersData()) {
                if (playerDatas.contains(pD)) {
                    plugin.getBingoData().completeClaimItemRewards(item, true, false, pD, null);
                    return;
                }
            }
            plugin.getBingoData().completeClaimItemRewards(item, true, false, PlayerUtil.getPlayerData(team.getLeader()), null);
        }
    }

    private void updateScoreboard() {
        for (PlayerData pD : bingoCaptureBoardPlayers) {
            pD.getPlayerScoreboard().update();
        }
    }

}
