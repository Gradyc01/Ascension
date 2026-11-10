//package org.bingo4.Timeline.Events.Ascension;
//
//import org.bingo4.Bingo4;
//import org.bingo4.General.Game.GameStates;
//import org.bingo4.Items.Craftable.Unlocks.AscensionKey;
//import org.bingo4.Player.Cooldowns.EntityInteractionCooldown;
//import org.bingo4.Player.Data.PlayerData;
//import org.bingo4.Teams.Team;
//import org.bingo4.Teams.TeamAscension;
//import org.bingo4.Timeline.Events.Winner.Winner;
//import org.bingo4.Timeline.Timeline;
//import org.bingo4.Utility.SoundUtil;
//import org.bingo4.Utility.TextUtil;
//import net.kyori.adventure.text.Component;
//import net.kyori.adventure.text.format.TextColor;
//import org.bukkit.Location;
//import org.bukkit.Sound;
//import org.bukkit.entity.Wither;
//import org.bukkit.scheduler.BukkitRunnable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class AscensionEvent {
//
//    private final Bingo4 plugin;
//    private boolean eventOngoing;
//    private final List<AscensionLocation> locations;
//    private AscensionLocation ascendingLocation;
//    private final Timeline timeline;
//    private final int radius;
//    public AscensionEvent(int radius) {
//        plugin = Bingo4.getInstance();
//        eventOngoing = false;
//        this.radius = radius;
//        locations = generateLocations();
//        timeline = plugin.getSettingsUI().getSettings().getTimeline();
//
//        broadcastLocations();
//    }
//
//    private List<AscensionLocation> generateLocations() {
//        Location spawn = Bingo4.getSpawn();
//        int x = spawn.getBlockX(); int z = spawn.getBlockZ();
//        List<AscensionLocation> locations = new ArrayList<>();
//        locations.add(new AscensionLocation(x + radius, z + radius, this));
//        locations.add(new AscensionLocation(x + radius, z - radius, this));
//        locations.add(new AscensionLocation(x - radius, z - radius, this));
//        locations.add(new AscensionLocation(x - radius, z + radius, this));
//
//        return locations;
//    }
//
//    /*Starts the Bingo4 event*/
//    public void start(AscensionLocation ascensionLocation) {
//        this.ascendingLocation = ascensionLocation;
//        eventOngoing = true;
//        locations.remove(ascensionLocation);
//        ascensionLocation.startAnimation();
//        ascensionLocation.getAscendingTeam().getTeamAscension().startAscension();
//        plugin.getGameState().setCurrentState(GameStates.GAME_ASCENSION);
//        loop(ascensionLocation);
////        timeline.pauseTimeline();
//    }
//
//    private void loop(AscensionLocation ascensionLocation) {
//        new BukkitRunnable() {
//            final TeamAscension team = ascensionLocation.getAscendingTeam().getTeamAscension();
//            int timer = team.getAscensionTimer();
//            final Wither e = (Wither) ascensionLocation.getEntity();
//            @Override
//            public void run() {
//                if (!eventOngoing || e.isDead() || !plugin.getGameState().checkState(GameStates.GAME_ASCENSION)) {
//                    failed();
//                    cancel();
//                    return;
//                }
//                if (timer <= 0) {
//                    success();
//                    cancel();
//                    return;
//                }
//                if (timer % 60 == 2 && !e.isCharged()) {
//                    e.getWorld().createExplosion(e, e.getLocation(), 4f, false, true, true);
//                }
//                timer--;
//                timeline.updatePlayers();
//                ascensionLocation.update();
////                ascensionLocation.updateBossBarHealth();
////                team.addAscensionTimer(-1);
//                team.checkForRewards();
//            }
//        }.runTaskTimer(Bingo4.getInstance(), 0, 20);
//    }
//
//    public void success() {
//        new Winner(new ArrayList<>(List.of(ascendingLocation.getAscendingTeam())));
//        successText();
//        stop();
//        TextUtil.debugText("Bingo4 Success");
//    }
//
//    public void failed() {
//        plugin.getGameState().setCurrentState(GameStates.GAME_AFTER_GRACE);
//        failedText();
//        ascendingLocation.getAscendingTeam().getTeamAscension().failedAscension();
//        stop();
//        checkForAscensionRemaining();
//        timeline.updatePlayers();
////        timeline.startTimeline();
//        TextUtil.debugText("Bingo4 Failed");
//    }
//
//    private void checkForAscensionRemaining() {
//        int numberOfAscensionsBeforeFinal = plugin.getSettingsUI().getSettings().getAscensionsBeforeFinal();
//        if (4 - locations.size() >= numberOfAscensionsBeforeFinal) {
//            timeline.setTime(5);
//            for (AscensionLocation ascensionLocation : locations) {
//                ascensionLocation.closeLocation();
//            }
//        }
//    }
//
//    public void clear() {
//        for(AscensionLocation location : locations) {
//            location.closeLocation();
//        }
//    }
//
//    private void stop() {
//        eventOngoing = false;
//        ascendingLocation.closeLocation();
//        ascendingLocation = null;
//    }
//
//    private void failedText() {
//        Component text = TextUtil.topBorder(TextUtil.DARK_GRAY);
//        text = text.append(TextUtil.makeText("\n            ASCENSION DENIED\n", TextUtil.WHITE, true, false));
//        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GRAY));
//        TextUtil.broadcastMessage(text);
//        SoundUtil.broadcastSound(Sound.ENTITY_WITHER_DEATH, 100, 1);
//    }
//
//    private void successText() {
//        Component text = TextUtil.topBorder(TextUtil.DARK_GRAY);
//        text = text.append(TextUtil.makeText("\n            ASCENSION COMPLETE\n", TextUtil.WHITE, true, false));
//        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GRAY));
//        TextUtil.broadcastMessage(text);
//        SoundUtil.broadcastSound(Sound.ENTITY_ENDER_DRAGON_DEATH, 100, 1);
//    }
//
//    private void broadcastLocations() {
//        Component text = TextUtil.topBorder(TextUtil.GOLD);
//        text = text.append(TextUtil.makeText("\nAscension Locations: ", TextUtil.AQUA, true, false));
//        for (AscensionLocation location : locations) {
//            Location loc = location.getSpawnLocation();
//            text = text.append(TextUtil.makeText(
//                    "\n        (" +
//                    loc.getBlockX() +
//                    ", " +
//                    loc.getBlockZ() +
//                    ")", TextUtil.YELLOW));
//        }
//        TextUtil.broadcastMessage(text.append(TextUtil.bottomBorder(TextUtil.GOLD)));
//    }
//
//    public boolean canStartEvent(PlayerData pD) {
//        if (EntityInteractionCooldown.getInstance().isOnCooldown(pD.getPlayer())) return false;
//        TextUtil.debugText("Ran Bingo4 Check");
//        Team team = pD.getPlayerTeam().getTeam();
//        boolean canBeginAscension = team.getTeamStats().canBeginAscension();
//        boolean hasAscensionKey = pD.getPlayer().getInventory().getItemInMainHand().equals(AscensionKey.getInstance().getResult());
//        int ascTime = team.getTeamAscension().getAscensionTimer();
//        int minutes = ascTime % 60 == 0 ? ascTime / 60 : (ascTime / 60) + 1;
//        boolean hasEnoughTime = minutes <= timeline.getTimeTillNextBigEvent();
//        Component text = TextUtil.topBorder(TextUtil.DARK_GRAY)
//                .append(TextUtil.makeText("            Failed to Start Bingo4", TextUtil.WHITE, true, false))
//                .append(createRejectionText(!eventOngoing, "No Bingo4 Event Happening"))
//                .append(createRejectionText(canBeginAscension, "Is Bingo4 Ready"))
//                .append(createRejectionText(hasAscensionKey, "Has an Bingo4 Key"))
//                .append(createRejectionText(hasEnoughTime, "Enough time for Bingo4"))
//                .append(TextUtil.bottomBorder(TextUtil.DARK_GRAY));
////        if (eventOngoing) return false;
////        int score = pD.getPlayerTeam().getTeam().getTeamStats().getGameScore();
//        if (!eventOngoing && hasAscensionKey && canBeginAscension && hasEnoughTime && !plugin.getGameState().checkState(GameStates.GAME_ENDING)) {
//            return true;
//        } else {
//            pD.getPlayer().sendMessage(text);
//            return false;
//        }
//
//    }
//
//    private Component createRejectionText(boolean canQualify, String text) {
//        TextColor color = canQualify ? TextUtil.GREEN : TextUtil.RED;
//        return TextUtil.makeText("\n   " + text, color);
//    }
//
////    public Team getAscendingTeam() {
////        return ascendingTeam;
////    }
//
//    public AscensionLocation getAscendingLocation() {
//        return ascendingLocation;
//    }
//}
