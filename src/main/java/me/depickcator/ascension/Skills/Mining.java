package me.depickcator.ascension.Skills;

import me.depickcator.ascension.Items.Craftable.Unlocks.MakeshiftSkull;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mining extends Skills {

    public Mining(PlayerData playerData) {
        super(playerData, "Mining");
    }

    @Override
    public List<SkillRewards> initRewards() {
        return List.of(
                Mining.level1Rewards(),
                Mining.level2Rewards(),
                Mining.level3Rewards(),
                Mining.level4Rewards(),
                Mining.level5Rewards()
        );
    }

    private static SkillRewards level1Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        new ItemStack(Material.COAL, 10),
                        new ItemStack(Material.IRON_INGOT, 3)
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
                        new ItemStack(Material.IRON_INGOT, 8),
                        new ItemStack(Material.GOLD_INGOT, 4)
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
                        new ItemStack(Material.GOLD_INGOT, 4),
                        new ItemStack(Material.DIAMOND, 2)
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
                        new ItemStack(Material.DIAMOND, 5),
                        new ItemStack(Material.ANCIENT_DEBRIS, 1)
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
                        new ItemStack(Material.NETHERITE_INGOT, 1),
                        MakeshiftSkull.getInstance().getResult()
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_LEGENDARY);
        return skillRewards;
    }

}
