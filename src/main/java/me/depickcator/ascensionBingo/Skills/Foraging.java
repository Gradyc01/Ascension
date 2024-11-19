package me.depickcator.ascensionBingo.Skills;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Player.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class Foraging implements Skills {
    private final PlayerData playerData;
    private final AscensionBingo plugin;
    private final Player player;
    private int experience;
    private int level;
    private final int MAXLEVEL = 5;
    private final String NAME = "Foraging";
    private final static ArrayList<Integer> LEVELREQUIREMENTS= new ArrayList<>(
            Arrays.asList(25, 100, 250, 750, 1500));
    private static final ArrayList<SkillRewards> rewards = new ArrayList<>(
            Arrays.asList(
                    Foraging.level1Rewards(),
                    Foraging.level2Rewards(),
                    Foraging.level3Rewards(),
                    Foraging.level4Rewards(),
                    Foraging.level5Rewards()
            )
    );
//     private final static ArrayList<>

    public Foraging(AscensionBingo plugin, PlayerData playerData) {
        this.plugin = plugin;
        this.playerData = playerData;
        this.player = playerData.getPlayer();
        experience = 0;
        level = 0;
    }

    @Override
    public void addExp(int amount) {
        experience+=amount;
        if (canLevelUp()) {
            levelUp(++level);
        }
        playerGainedExpNotification(player, amount, NAME, plugin);
    }

    private boolean canLevelUp() {
        if (level >= MAXLEVEL) {
            return false;
        }
        return experience >= LEVELREQUIREMENTS.get(level);
    }

    private void levelUp(int newLevel) {
        SkillRewards reward = Foraging.rewards.get(level-1);
        levelUpMessage(newLevel, reward, player, NAME);
        playLevelUpSound(player);
        reward.giveRewards(playerData);
        if (canLevelUp()) {
            levelUp(++level);
        }
    }


    @Override
    public String getExp() {
        return experience + "";
    }

    @Override
    public String getExpLevel() {
        return level + "";
    }

    @Override
    public ArrayList<String> getRewardText(int level) {
        return null;
    }

    @Override
    public String getExpOverTotalNeeded() {
        if (level >= MAXLEVEL) {
            return " (" + experience + "/" + Foraging.LEVELREQUIREMENTS.get(MAXLEVEL - 1) + ")";
        }
        return " (" + experience + "/" + Foraging.LEVELREQUIREMENTS.get(level) + ")";
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
        skillRewards.setUnlockTokens(1);
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
        skillRewards.setUnlockTokens(2);
        return skillRewards;
    }

    private static SkillRewards level3Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        new ItemStack(Material.PUMPKIN, 1),
                        new ItemStack(Material.MELON_SLICE, 4)
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(3);
        return skillRewards;
    }

    private static SkillRewards level4Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        new ItemStack(Material.EMERALD, 5),
                        new ItemStack(Material.RABBIT_FOOT, 1)
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(3);
        return skillRewards;
    }

    private static SkillRewards level5Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        new ItemStack(Material.EMERALD, 9),
                        new ItemStack(Material.HONEY_BLOCK, 1)
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(4);
        return skillRewards;
    }



}
