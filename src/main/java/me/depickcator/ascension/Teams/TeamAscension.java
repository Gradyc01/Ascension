package me.depickcator.ascension.Teams;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Timeline.Events.Ascension.Rewards.AscensionRewards;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TeamAscension {
    private final Team team;
    private final Ascension plugin;
    private int ascensionTimer;
    private int ascensionAttempts;
    private int rewardLevelAchieved;
    private final List<Integer> rewardTimes;
    private AscensionRewards rewards;
    public TeamAscension(Team team) {
        this.team = team;
        plugin = Ascension.getInstance();
        ascensionTimer = plugin.getSettingsUI().getSettings().getStartingAscensionTimer();
        rewardLevelAchieved = 0;
        int timerSplit = ascensionTimer / 5;
        rewardTimes = new ArrayList<>(List.of(
                timerSplit * 4,
                timerSplit * 3,
                timerSplit * 2,
                timerSplit
        ));
    }

    /*Called when an Ascension is started for the Team: team*/
    public void startAscension() {
        rewards = new AscensionRewards(team);
        addAscensionAttempts();
        TextUtil.debugText("Starting Ascension");
        if (getAscensionAttempts() == 1) {
            rewards.addRewardTier(rewardLevelAchieved);
        }
    }

    /*Called when an Ascension is failed for the Team: team*/
    public void failedAscension() {
        TextUtil.debugText("Failed Ascension");
        rewards.giveRewards();
        setAscensionTimer(Integer.max((int) (getAscensionTimer() * 1.3), 300));
    }

    //Ascension Timer
    public int getAscensionTimer() {
        return ascensionTimer;
    }

    private void addAscensionTimer(int ascensionTimer) {
        this.ascensionTimer += ascensionTimer;
    }

    private void setAscensionTimer(int ascensionTimer) {
        this.ascensionTimer = ascensionTimer;
    }

    //Ascension Attempts
    public int getAscensionAttempts() {
        return ascensionAttempts;
    }

    private void addAscensionAttempts() {
        this.ascensionAttempts++;
        team.updateTeamScoreboards();
    }

    /*Checks for Rewards for Team: team if rewards are achieved adds to the rewardBundle*/
    public void checkForRewards() {
        addAscensionTimer(-1);
        if (rewardTimes.isEmpty()) return;
//        TextUtil.debugText("Ascension Timer: " + ascensionTimer + "     Reward Timer: " + rewardTimes.getFirst());
        if (getAscensionTimer() <= rewardTimes.getFirst()) {
            rewardLevelAchieved++;
            rewardTimes.removeFirst();
            rewards.addRewardTier(rewardLevelAchieved);
        }
    }


}
