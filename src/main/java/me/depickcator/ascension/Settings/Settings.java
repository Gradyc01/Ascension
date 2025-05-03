package me.depickcator.ascension.Settings;

import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.Sequences.*;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Timeline.Timeline;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
    private final int teamSize;
    /*Settings controls the settings of the Game
    * This includes:
    *   World Border Size
    *   The timeline
    *   The Starting Sequence
    *   The itemDistribution
    *   The Name of this particular setting
    *   How much the Ascension Requirement and Timer are
    *   Team Size*/
    public Settings(String name, int worldBorderSize, int ascensionGameScoreRequirement, Timeline timeline) {
        this(name, worldBorderSize, ascensionGameScoreRequirement, timeline, 900, 3);
    }

    public Settings(String name,
                    int worldBorderSize,
                    int ascensionGameScoreRequirement,
                    Timeline timeline,
                    int startingAscensionTimer,
                    int teamSize) {
        this.name = name;
        this.worldBorderSize = worldBorderSize;
        this.timeline = timeline;
        this.itemDistribution = initItemDistribution();
        this.ascensionGameScoreRequirement = ascensionGameScoreRequirement;
        this.startingAscensionTimer = startingAscensionTimer;
        this.teamSize = teamSize;
        this.sequence = initSequence();
    }

    protected Settings(String name,
                       int worldBorderSize,
                       int ascensionGameScoreRequirement,
                       Timeline timeline,
                       int startingAscensionTimer,
                       List<Integer> itemDistribution,
                       int teamSize) {
        this.name = name;
        this.worldBorderSize = worldBorderSize;
        this.timeline = timeline;
        this.itemDistribution = itemDistribution;
        this.startingAscensionTimer = startingAscensionTimer;
        this.ascensionGameScoreRequirement = ascensionGameScoreRequirement;
        this.teamSize = teamSize;

        this.sequence = initSequence();
    }

    /*Returns the default initial starting sequence*/
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

    /*Returns the default item distribution
    * List of Integers must be size of 4 and sums up to 25*/
    protected List<Integer> initItemDistribution() {
        return new ArrayList<>(List.of(
                5,
                10,
                5,
                5
        ));
    }

    /*  What a player victim drops to the floor when killed
     * Returns a list of ItemStacks*/
    public List<ItemStack> getKillReward(PlayerDeathEvent e, PlayerData victim) {
        List<ItemStack> rewards = new ArrayList<>(List.of(
                new ItemStack(Material.GOLD_NUGGET, 21)
        ));
        ItemStack shards = ShardOfTheFallen.getInstance().getResult(7);
        rewards.add(shards);
        PlayerInventory inv = victim.getPlayer().getInventory();
        for (ItemStack item : inv.getContents().clone()) {
            if (item == null) continue;
            if (item.getEnchantments().get(Enchantment.VANISHING_CURSE) != null) {
                inv.removeItem(item);
                rewards.add(item);
            }
        }
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

    public int getTeamSize() {
        return teamSize;
    }
}
