package me.depickcator.ascension.Settings;

import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.Sequences.*;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Timeline.Timeline;
import org.bukkit.Material;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Settings {
    private final int worldBorderSize;
    private final Timeline timeline;
    private final List<GameStartSequence> sequence;
    private final List<Integer> itemDistribution; // Easy | Medium | Hard | Custom
    private final String name;
    private final int startingAscensionTimer;
    private final int ascensionGameScoreRequirement;

    public Settings(String name, int worldBorderSize, int ascensionGameScoreRequirement, Timeline timeline) {
        this(name, worldBorderSize, ascensionGameScoreRequirement, timeline, 900);
    }

    public Settings(String name, int worldBorderSize, int ascensionGameScoreRequirement, Timeline timeline, int startingAscensionTimer) {
        this.name = name;
        this.worldBorderSize = worldBorderSize;
        this.timeline = timeline;
        this.itemDistribution = initItemDistribution();
        this.ascensionGameScoreRequirement = ascensionGameScoreRequirement;
        this.startingAscensionTimer = startingAscensionTimer;
        this.sequence = initSequence();
    }

    protected Settings(String name, int worldBorderSize, int ascensionGameScoreRequirement, Timeline timeline, int startingAscensionTimer, List<Integer> itemDistribution) {
        this.name = name;
        this.worldBorderSize = worldBorderSize;
        this.timeline = timeline;
        this.itemDistribution = itemDistribution;
        this.startingAscensionTimer = startingAscensionTimer;
        this.ascensionGameScoreRequirement = ascensionGameScoreRequirement;

        this.sequence = initSequence();
    }

    protected List<GameStartSequence> initSequence() {
        return new ArrayList<>(List.of(
                new ResetPlayers(),
                new SetWorldBorder(worldBorderSize),
                new SpreadPlayers(worldBorderSize),
                new InitBingoBoard(itemDistribution),
                new TextSequence(),
                new ResetWorld(),
                new LaunchSequence()
        ));
    }

    protected List<Integer> initItemDistribution() {
        return new ArrayList<>(List.of(
                5,
                10,
                5,
                5
        ));
    }


    public List<ItemStack> getKillReward(PlayerDeathEvent e, PlayerData victim) {
        List<ItemStack> rewards = new ArrayList<>(List.of(
                new ItemStack(Material.GOLD_NUGGET, 21)
        ));
        ItemStack shards = ShardOfTheFallen.result().clone();
        shards.setAmount(7);
        rewards.add(shards);
        return rewards;
    }


    public int getWorldBorderSize() {
        return worldBorderSize;
    };

    public Timeline getTimeline() {
        return timeline;
    }

    public int getStartingAscensionTimer() {
        return startingAscensionTimer;
    }

    public List<GameStartSequence> getSequence() {
        return sequence;
    }

    public List<Integer> getItemDistribution() {
        return itemDistribution;
    }

    public String getName() {
        return name;
    }

    public int getAscensionGameScoreRequirement() {
        return ascensionGameScoreRequirement;
    }
}
