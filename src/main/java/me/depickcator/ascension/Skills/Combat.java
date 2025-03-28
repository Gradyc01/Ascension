package me.depickcator.ascension.Skills;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.ArrayList;

public class Combat implements Skills {
    private final PlayerData playerData;
    private final Ascension plugin;
    private Player player;
    private int experience;
    private int level;
    private final String NAME = "Combat";
    private final int MAXLEVEL = 5;
    private final static ArrayList<Integer> LEVELREQUIREMENTS= new ArrayList<>(
            Arrays.asList(25, 100, 250, 750, 1500));
    private final ArrayList<SkillRewards> rewards = new ArrayList<>(
            Arrays.asList(
                    Combat.level1Rewards(),
                    Combat.level2Rewards(),
                    Combat.level3Rewards(),
                    Combat.level4Rewards(),
                    Combat.level5Rewards()
            )
    );
//     private final static ArrayList<>

    public Combat(Ascension plugin, PlayerData playerData) {
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
        SkillRewards reward = rewards.get(level-1);
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
    public ArrayList<Component> getRewardText(int level) {
        return parseRewardText(rewards.get(level - 1));
    }

    @Override
    public String getExpOverTotalNeeded() {
        if (level >= MAXLEVEL) {
            return " (" + experience + "/" + Combat.LEVELREQUIREMENTS.get(MAXLEVEL - 1) + ")";
        }
        return " (" + experience + "/" + Combat.LEVELREQUIREMENTS.get(level) + ")";
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void updatePlayer() {
        player = playerData.getPlayer();
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
//        shards.setAmount(1);
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
                        shards
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
                        new ItemStack(Material.NETHER_STAR, 1)
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
                        new ItemStack(Material.NETHER_STAR, 1)
                )
        );
        skillRewards.setItems(rewards);
        skillRewards.setUnlockTokens(PlayerUnlocks.AMOUNT_LEGENDARY);
        return skillRewards;
    }



}
