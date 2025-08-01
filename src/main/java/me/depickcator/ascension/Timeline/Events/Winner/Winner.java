package me.depickcator.ascension.Timeline.Events.Winner;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.General.Game.Reset.ResetGame;
import me.depickcator.ascension.Persistence.SeedChooser;
import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Teams.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;


public class Winner {
    private final Team team;
    private final Ascension plugin;
    public Winner(List<Team> teams) {
        this.plugin = Ascension.getInstance();
        plugin.getSettingsUI().getSettings().getTimeline().pauseTimeline();
        if (teams.size() == 1) {
            this.team = teams.getFirst();
            team.getTeamStats().setWin();
            globalAnnouncement();
            winnerAnnouncement();
        } else {
            this.team = null;
        }
        setState();
        resetGame();
    }

    private void setState() {
        plugin.getGameState().setCurrentState(GameStates.GAME_ENDING);
    }

    private void globalAnnouncement() {
        Component text = TextUtil.makeText("\n       Winners -  ", TextUtil.YELLOW);
        Component playerNames = appendPlayerNames(team.getTeamMembers());
        TextUtil.broadcastMessage(
                TextUtil.topBorder(TextUtil.DARK_GREEN)
                .append(text)
                .append(playerNames)
                .append(TextUtil.makeText("\n\n\n\n"))
                .append(TextUtil.bottomBorder(TextUtil.DARK_GREEN)));
        SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 10f, 2f);
    }

    private Component appendPlayerNames(List<Player> players) {
        List<Player> playerList = new ArrayList<>(players);
        if (playerList.isEmpty()) {
            return TextUtil.makeText("");
        } else {
            return TextUtil.makeText(playerList.removeFirst().getName()  + "       ", TextUtil.AQUA).append(appendPlayerNames(playerList));
        }
    }

    private void winnerAnnouncement() {
        Component text = TextUtil.makeText("WINNER", TextUtil.GOLD, true, false);
        Title title = TextUtil.makeTitle(text, TextUtil.makeText(""), 0, 12, 3);
        TextUtil.broadcastTitle(title, team.getTeamMembers());
    }

    private void resetGame() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Settings settings = plugin.getSettingsUI().getSettings();
                Location loc = Ascension.getSpawn();
                long seed = plugin.getWorld().getSeed();
                if (settings.isReseedAfterGame()) {
                    Pair<Location, Long> newLocation = new SeedChooser().findSeed();
                    if (newLocation != null) {
                        loc = newLocation.getLeft();
                        seed = newLocation.getRight();
                    }
                }
                new ResetGame(settings.isCountTowardsLeaderboards(), loc, seed);
            }
        }.runTaskLater(Ascension.getInstance(), 60 * 20);
    }



}
