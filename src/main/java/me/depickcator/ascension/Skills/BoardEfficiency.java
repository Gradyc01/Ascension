package me.depickcator.ascension.Skills;

import me.depickcator.ascension.Items.Craftable.Unlocks.MakeshiftSkull;
import me.depickcator.ascension.Items.Uncraftable.NetherStar.NetherStar;
import me.depickcator.ascension.Items.Uncraftable.RejuvenationBook;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardEfficiency extends Skills {
    public BoardEfficiency(PlayerData playerData) {
        super(playerData, "Board");
    }

    /*Initializes the level requirements of this particular skill
     * Returns a list of Integers
     * The List must have a size greater than MAXLEVEL*/
    @Override
    public List<Integer> initLevelRequirements() {
        return new ArrayList<>(List.of(
                2, 4, 6, 8, 10
        ));
    }

    @Override
    public List<SkillRewards> initRewards() {
        return List.of(
                BoardEfficiency.level1Rewards(),
                BoardEfficiency.level2Rewards(),
                BoardEfficiency.level3Rewards(),
                BoardEfficiency.level4Rewards(),
                BoardEfficiency.level5Rewards()
        );
    }

    private static SkillRewards level1Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        skillRewards.setItems(List.of(
                ShardOfTheFallen.getInstance().getResult(2),
                NetherStar.getInstance().getResult(),
                new ItemStack(Material.DIAMOND, 2)
        ));
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_RARE);
        return skillRewards;
    }

    private static SkillRewards level2Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        skillRewards.setItems(List.of(
                ShardOfTheFallen.getInstance().getResult(3),
                NetherStar.getInstance().getResult(),
                new ItemStack(Material.DIAMOND, 5)
        ));
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_VERY_RARE);
        return skillRewards;
    }

    private static SkillRewards level3Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        skillRewards.setItems(List.of(
                ShardOfTheFallen.getInstance().getResult(5),
                NetherStar.getInstance().getResult(2),
                new ItemStack(Material.DIAMOND, 5)
        ));
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_VERY_RARE);
        return skillRewards;
    }

    private static SkillRewards level4Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        skillRewards.setItems(List.of(
                MakeshiftSkull.getInstance().getResult(),
                NetherStar.getInstance().getResult(2),
                new ItemStack(Material.DIAMOND, 7)
        ));
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_LEGENDARY);
        return skillRewards;
    }

    private static SkillRewards level5Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        skillRewards.setItems(List.of(
                MakeshiftSkull.getInstance().getResult(),
                NetherStar.getInstance().getResult(2),
                new ItemStack(Material.NETHERITE_INGOT, 1)
        ));
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_LEGENDARY);
        return skillRewards;
    }

}
