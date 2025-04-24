package me.depickcator.ascension.Skills;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.MakeshiftSkull;
import me.depickcator.ascension.Items.Uncraftable.RejuvenationBook;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combat extends Skills {
    public Combat(PlayerData playerData) {
        super(playerData, "Combat");
    }

    @Override
    public List<SkillRewards> initRewards() {
        return List.of(
                Combat.level1Rewards(),
                Combat.level2Rewards(),
                Combat.level3Rewards(),
                Combat.level4Rewards(),
                Combat.level5Rewards()
        );
    }

    private static SkillRewards level1Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        new ItemStack(Material.FEATHER, 2),
                        new ItemStack(Material.FLINT, 2)
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_COMMON);
        return skillRewards;
    }

    private static SkillRewards level2Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ItemStack shards = ShardOfTheFallen.getInstance().getResult(1);
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        new ItemStack(Material.GOLDEN_APPLE, 1),
                        shards
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_UNCOMMON);
        return skillRewards;
    }

    private static SkillRewards level3Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ItemStack shards = ShardOfTheFallen.getInstance().getResult(4);
//        shards.setAmount(4);
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        shards,
                        RejuvenationBook.getInstance().getResult()
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_RARE);
        return skillRewards;
    }

    private static SkillRewards level4Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        ShardOfTheFallen.getInstance().getResult(5),
                        RejuvenationBook.getInstance().getResult()
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_VERY_RARE);
        return skillRewards;
    }

    private static SkillRewards level5Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ItemStack shards = ShardOfTheFallen.getInstance().getResult(5);
//        shards.setAmount(5);
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        shards,
                        MakeshiftSkull.getInstance().getResult()
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_LEGENDARY);
        return skillRewards;
    }

}
