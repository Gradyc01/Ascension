package me.depickcator.ascension.Skills;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class Mining implements Skills {
    private final PlayerData playerData;
    private final Ascension plugin;
    private Player player;
    private int experience;
    private int level;
    private final int MAXLEVEL = 5;
    private final String NAME = "Mining";
    private final static ArrayList<Integer> LEVELREQUIREMENTS= new ArrayList<>(
            Arrays.asList(25, 100, 250, 750, 1500));
    private static final ArrayList<SkillRewards> rewards = new ArrayList<>(
            Arrays.asList(
                    Mining.level1Rewards(),
                    Mining.level2Rewards(),
                    Mining.level3Rewards(),
                    Mining.level4Rewards(),
                    Mining.level5Rewards()
            )
    );
//     private final static ArrayList<>

    public Mining(Ascension plugin, PlayerData playerData) {
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
        SkillRewards reward = Mining.rewards.get(level-1);
        levelUpMessage(newLevel, reward, player, NAME);
        playLevelUpSound(player);
        reward.giveRewards(playerData);
        playerData.getPlayerSkills().getGlobal().addExp(1);
        if (canLevelUp()) {
            levelUp(++level);
        }
    }

    @Override
    public void updatePlayer() {
        player = playerData.getPlayer();
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
    public ArrayList<Component> getRewardText(int level) {
        return parseRewardText(rewards.get(level - 1));
    }

    @Override
    public String getExpOverTotalNeeded() {
        if (level >= MAXLEVEL) {
            return " (" + experience + "/" + Mining.LEVELREQUIREMENTS.get(MAXLEVEL - 1) + ")";
        }
        return " (" + experience + "/" + Mining.LEVELREQUIREMENTS.get(level) + ")";
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
                        new ItemStack(Material.NETHERITE_INGOT, 1)
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_LEGENDARY);
        return skillRewards;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
