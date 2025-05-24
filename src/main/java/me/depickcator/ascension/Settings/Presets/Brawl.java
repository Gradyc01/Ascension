package me.depickcator.ascension.Settings.Presets;

import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Start.Sequences.*;
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
    protected List<GameSequences> initSequence() {
        return new ArrayList<>(List.of(
                new ResetPlayers(),
                new SetWorldBorder(getWorldBorderSize()),
                new SpreadPlayers(getWorldBorderSize()),
                new InitBingoBoard(getItemDistribution()),
                new GiveBrawlItems2(),
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
        ItemStack shards = ShardOfTheFallen.getInstance().getResult(7);
//        shards.setAmount(7);
        rewards.add(shards);
        return rewards;
    }
}
