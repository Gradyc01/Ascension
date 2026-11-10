//package org.bingo4.Timeline.Events.FinalAscension;
//
//import org.bingo4.Bingo4;
//import org.bingo4.General.Game.GameStates;
//import org.bingo4.Player.Cooldowns.Death.PlayerDeath;
//import org.bingo4.Player.Data.PlayerData;
//import org.bingo4.Player.Data.PlayerUtil;
//import org.bingo4.Player.Data.Scoreboards.GameBoard;
//import org.bingo4.Teams.Team;
//import org.bingo4.Teams.TeamStats;
//import org.bingo4.Teams.TeamUtil;
//import org.bingo4.Utility.SoundUtil;
//import org.bingo4.Utility.TextUtil;
//import net.kyori.adventure.text.Component;
//import net.kyori.adventure.title.Title;
//import org.bukkit.Location;
//import org.bukkit.Sound;
//import org.bukkit.WorldBorder;
//import org.bukkit.entity.Player;
//import org.bukkit.scheduler.BukkitRunnable;
//import org.bukkit.scheduler.BukkitTask;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FinalAscension {
//    private final Bingo4 plugin;
//    private final WorldBorder worldBorder;
//    private final BukkitTask finalBorderShrinkTask;
//    public FinalAscension(int initialBorderShrinkSize) {
//        this.plugin = Bingo4.getInstance();
//        TextUtil.debugText("Final Bingo4 Ran");
//        PlayerDeath.getInstance().respawnEveryone();
//        setGameState();
//        calculateTeamTimers();
//        worldBorder =plugin.getWorld().getWorldBorder();
//        finalBorderShrinkTask = initialSettings(initialBorderShrinkSize);
//        finalAscensionStartAnnouncement();
//        timer();
//    }
//
//    private BukkitTask initialSettings(int initialBorderShrinkSize) {
////        worldBorder.setSize(initialBorderShrinkSize * 2, (int) (worldBorder.getSize() - initialBorderShrinkSize * 2) / 4);
//        worldBorder.setSize(initialBorderShrinkSize * 2, 0);
//        int time = (int) (worldBorder.getSize() - initialBorderShrinkSize);
//        worldBorder.setSize((double) initialBorderShrinkSize / 2, time);
//        Location center = worldBorder.getCenter();
//        for (PlayerData pD : PlayerUtil.getAllPlayingPlayers()) {
//            Player p = pD.getPlayer();
//            if (Math.abs(p.getX()) >= initialBorderShrinkSize + center.getX() ||
//            Math.abs(p.getZ()) >= initialBorderShrinkSize + center.getZ()) {
//                Location pLoc = p.getLocation().clone();
//                double newX = p.getX() < 0 ? Math.max(-initialBorderShrinkSize + center.getX() + 10, p.getX()) :
//                                             Math.min(initialBorderShrinkSize + center.getX() - 10, p.getX());
//                double newZ = p.getZ() < 0 ? Math.max(-initialBorderShrinkSize + center.getZ() + 10, p.getZ()) :
//                                             Math.min(initialBorderShrinkSize + center.getZ() - 10, p.getZ());
//                pLoc.setY(worldBorder.getWorld().getHighestBlockYAt((int) newX,(int) newZ) + 1);
//                pLoc.setX(newX);
//                pLoc.setZ(newZ);
//                p.teleport(pLoc);
//            }
//        }
//
//        return new BukkitRunnable() {
//            @Override
//            public void run() {
//                plugin.getWorld().getWorldBorder().setSize(25, 600);
//            }
//        }.runTaskLater(plugin, time * 20L);
//    }
//
//    private void setGameState() {
//        GameStates gameState = plugin.getGameState();
//        gameState.setCurrentState(GameStates.GAME_FINAL_ASCENSION);
//        TextUtil.debugText("Game States Ran");
//    }
//
//    private void calculateTeamTimers() {
//        List<Team> allTeams = TeamUtil.getEveryTeam();
//        for (Team t : allTeams) {
//            TeamStats teamStats = t.getTeamStats();
//            int time = calculateTimer(teamStats.getGameScore());
//            teamStats.setFinalAscensionTimer(time);
//            TextUtil.debugText("Team Score: " + teamStats.getFinalAscensionTimer());
//        }
//    }
//
//    private void timer() {
//        new BukkitRunnable() {
//            int time = 0;
//            List<Team> activeTeams = TeamUtil.getEveryTeam(true);
//            @Override
//            public void run() {
//                if (!plugin.getGameState().checkState(GameStates.GAME_FINAL_ASCENSION)) {
//                    cancel();
//                    finalBorderShrinkTask.cancel();
//                    TextUtil.debugText("No Longer in the correct state");
//                    return;
//                }
//                if (activeTeams.size() <= 1) {
//                    cancel();
//                    finalBorderShrinkTask.cancel();
////                    new Winner(activeTeams);
//                    TextUtil.debugText("win");
//                    return;
//                }
//                time++;
//                activeTeams = updateTeams(activeTeams);
//            }
//
//        }.runTaskTimer(plugin, 2 * 20, 20);
//    }
//
//    private List<Team> updateTeams(List<Team> teams) {
//        List<Team> activeTeams = new ArrayList<>(teams);
//        for (Team t : activeTeams) {
//            updateScoreboard(t);
//            TeamStats teamStats = t.getTeamStats();
//            if (teamStats.getFinalAscensionTimer() <= 0) {
//                timerExpired(t);
//            }
//            if (t.checkSTATE(Team.STATE_DEPRECATED)) {
//                activeTeams.remove(t);
//                TextUtil.debugText("team lost");
//                return updateTeams(activeTeams);
//            }
//
//            teamStats.addFinalAscensionTimer(-1);
//        }
//        return activeTeams;
//    }
//
//    //Effects after a teams timer expired
//    private void timerExpired(Team team) {
//        List<Player> players = team.getTeamMembers();
//        for (Player p : players) {
//            PlayerData pD = PlayerUtil.getPlayerData(p);
//            PlayerDeath.getInstance().playerDied(pD);
//        }
//    }
//
//    //Calculates the ascension timer
//    private int calculateTimer(int gameScore) {
//        int defaultTimer = 300;
//        int multiplier = 30;
//        return defaultTimer + gameScore * multiplier;
//    }
//
//    //updates the scoreboard
//    private void updateScoreboard(Team team) {
//        for (Player p : team.getTeamMembers()) {
//            PlayerData pD = PlayerUtil.getPlayerData(p);
//            GameBoard b = (GameBoard) pD.getPlayerScoreboard().getBoards();
//            b.updateTime();
//        }
//    }
//
//    private void finalAscensionStartAnnouncement() {
//        Component titleText = TextUtil.makeText("FINAL ASCENSION", TextUtil.YELLOW);
//        Component subtitleText = TextUtil.makeText("The border will now begin to shrink", TextUtil.YELLOW);
//        Title title = TextUtil.makeTitle(titleText, subtitleText, 1, 3, 1);
//        TextUtil.broadcastTitle(title);
//        SoundUtil.broadcastSound(Sound.ENTITY_WITHER_DEATH, 1f, 1f);
//    }
//
//}
//
