package me.depickcator.ascension.Settings.Presets;

import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.Sequences.*;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Timeline.Timelines.BrawlTimeline;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Brawl extends Settings {
    public Brawl() {
        super("Brawl", 300, 25, new BrawlTimeline());
    }

    @Override
    protected List<GameStartSequence> initSequence() {
        return new ArrayList<>(List.of(
                new ResetPlayers(),
                new SetWorldBorder(getWorldBorderSize()),
                new SpreadPlayers(getWorldBorderSize()),
                new InitBingoBoard(getItemDistribution()),
                new GiveBrawlItems(),
                new AdjustStartingGameScore(10),
                new TextSequence(5),
                new ResetWorld(),
                new LaunchSequence()
        ));
    }

    @Override
    protected List<Integer> initItemDistribution() {
        return new ArrayList<>(List.of(
                0,
                0,
                0,
                0
        ));
    }

    @Override
    public List<ItemStack> getKillReward(PlayerDeathEvent e, PlayerData victim) {
        Player p = victim.getPlayer();
        List<ItemStack> rewards = new ArrayList<>(List.of(

        ));
        rewards.addAll(Arrays.stream(p.getInventory().getContents()).filter(Objects::nonNull).toList());
        ItemStack shards = ShardOfTheFallen.result().clone();
        shards.setAmount(7);
        rewards.add(shards);
        return rewards;
    }
}
