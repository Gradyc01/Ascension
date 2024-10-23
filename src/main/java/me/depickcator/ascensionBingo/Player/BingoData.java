package me.depickcator.ascensionBingo.Player;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Collection;
import java.util.Objects;

public class BingoData {
    ScoreboardManager scoreboardManager;
    Scoreboard bingoScoreboard;
    Objective bingodata;
    Objective bingoLines;
    Objective bingoItemsObtained;

    public BingoData() {
        scoreboardManager = Bukkit.getServer().getScoreboardManager();
        bingoScoreboard = scoreboardManager.getMainScoreboard();
        bingodata = bingoScoreboard.registerNewObjective("bingo", Criteria.DUMMY , Component.text("Ascension Bingo"), RenderType.INTEGER);
        resetPlayers();
    }

    public void resetPlayers() {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        bingoScoreboard.resetScores("bingo");
        for (Player player : players) {
            Score score = Objects.requireNonNull(bingoScoreboard.getObjective("bingo")).getScore(player.getName());
            score.setScore(0);
//            player.setScoreboard(bingoScoreboard);
            Objects.requireNonNull(bingoScoreboard.getObjective("bingo")).setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }


}
