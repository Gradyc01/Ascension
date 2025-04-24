package me.depickcator.ascension.Skills;

import me.depickcator.ascension.Items.Craftable.Unlocks.MakeshiftSkull;
import me.depickcator.ascension.Items.Uncraftable.NetherStar.NetherStar;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Foraging extends Skills {

    public Foraging(PlayerData playerData) {
        super(playerData, "Foraging");
    }

    @Override
    public List<SkillRewards> initRewards() {
        return List.of(
                Foraging.level1Rewards(),
                Foraging.level2Rewards(),
                Foraging.level3Rewards(),
                Foraging.level4Rewards(),
                Foraging.level5Rewards()
        );
    }

    private static SkillRewards level1Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        new ItemStack(Material.APPLE, 1),
                        new ItemStack(Material.WHEAT_SEEDS, 3)
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_COMMON);
        return skillRewards;
    }

    private static SkillRewards level2Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        new ItemStack(Material.LEATHER, 2),
                        new ItemStack(Material.EGG, 1)
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_UNCOMMON);
        return skillRewards;
    }

    private static SkillRewards level3Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        new ItemStack(Material.PUMPKIN, 1),
                        new ItemStack(Material.MELON_SLICE, 4),
                        new ItemStack(Material.EMERALD, 1)
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
                        new ItemStack(Material.EMERALD, 5),
                        new ItemStack(Material.RABBIT_FOOT, 1),
                        NetherStar.getInstance().getResult()
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_VERY_RARE);
        return skillRewards;
    }

    private static SkillRewards level5Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        new ItemStack(Material.EMERALD, 9),
                        new ItemStack(Material.HONEY_BLOCK, 1),
                        MakeshiftSkull.getInstance().getResult(),
                        NetherStar.getInstance().getResult(2)
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_LEGENDARY);
        return skillRewards;
    }



}
