package me.depickcator.ascensionBingo.Skills;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUnlocks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.ArrayList;

public class Combat implements Skills {
    private final PlayerData playerData;
    private final AscensionBingo plugin;
    private final Player player;
    private int experience;
    private int level;
    private final String NAME = "Combat";
    private final int MAXLEVEL = 5;
    private final static ArrayList<Integer> LEVELREQUIREMENTS= new ArrayList<>(
            Arrays.asList(25, 100, 250, 750, 1500));
    private static final ArrayList<SkillRewards> rewards = new ArrayList<>(
            Arrays.asList(
                    Combat.level1Rewards(),
                    Combat.level2Rewards(),
                    Combat.level3Rewards(),
                    Combat.level4Rewards(),
                    Combat.level5Rewards()
            )
    );
//     private final static ArrayList<>

    public Combat(AscensionBingo plugin, PlayerData playerData) {
        this.plugin = plugin;
        this.playerData = playerData;
        this.player = playerData.getPlayer();
        experience = 0;
        level = 0;
    }

    @Override
    public void addExp(int amount) {
        experience+=amount;
        playerData.getPlayerUnlocks().addUnlockTokens(amount);
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
        SkillRewards reward = Combat.rewards.get(level-1);
        levelUpMessage(newLevel, reward, player, NAME);
        playLevelUpSound(player);
        reward.giveRewards(playerData);
        playerData.getPlayerSkills().getGlobal().addExp(1);
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
            return " (" + experience + "/" + Combat.LEVELREQUIREMENTS.get(MAXLEVEL - 1) + ")";
        }
        return " (" + experience + "/" + Combat.LEVELREQUIREMENTS.get(level) + ")";
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
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        new ItemStack(Material.GOLDEN_APPLE, 1)
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_UNCOMMON);
        return skillRewards;
    }

    private static SkillRewards level3Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ItemStack shards = ShardOfTheFallen.result();
        shards.setAmount(2);
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        shards
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_RARE);
        return skillRewards;
    }

    private static SkillRewards level4Rewards() {
        SkillRewards skillRewards = new SkillRewards();
        ItemStack shards = ShardOfTheFallen.result();
        shards.setAmount(5);
        ArrayList<ItemStack> rewards = new ArrayList<>(
                Arrays.asList(
                        shards
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
                        new ItemStack(Material.NETHER_STAR, 1)
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_LEGENDARY);
        return skillRewards;
    }



}
