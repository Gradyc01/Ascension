package me.depickcator.ascensionBingo.Skills;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascensionBingo.Player.PlayerData;
import net.kyori.adventure.text.Component;
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
        if (canLevelUp()) {
            levelUp(++level);
        }
        playGainedExpSound(player);
        Component message = TextUtil.makeText("+" + amount + " combat " + getExpOverTotalNeeded(), TextUtil.AQUA);
        TextUtil.sendActionBar(player, message, 20, plugin);
    }

    private boolean canLevelUp() {
        if (level >= MAXLEVEL) {
            return false;
        }
        return experience >= LEVELREQUIREMENTS.get(level);
    }

    private void levelUp(int newLevel) {
        SkillRewards reward = Combat.rewards.get(level-1);
        levelUpMessage(newLevel, reward);
        playLevelUpSound(player);
        reward.giveRewards(playerData);
        if (canLevelUp()) {
            levelUp(++level);
        }
    }

    private void levelUpMessage(int newLevel, SkillRewards reward) {
        Component spacing = TextUtil.makeText("      ", TextUtil.AQUA);
        player.sendMessage(TextUtil.topBorder(TextUtil.GOLD));
        player.sendMessage(TextUtil.makeText("                    LEVEL UP!!!!", TextUtil.AQUA, true, false));
        player.sendMessage(TextUtil.makeText("                      Combat " + newLevel, TextUtil.GOLD, true, false));
        player.sendMessage(TextUtil.makeText("\n\n   Rewards: ", TextUtil.GOLD, true, false));
        for (ItemStack item : reward.getItems()) {
            Component displayName = item.displayName().color(TextUtil.RED);
            Component amount = TextUtil.makeText(" x" + item.getAmount(), TextUtil.RED);
            player.sendMessage(spacing.append(displayName).append(amount));
        }
        player.sendMessage(spacing.append(TextUtil.makeText(reward.getUnlockTokens() + " Unlock Tokens", TextUtil.RED)));
        player.sendMessage(TextUtil.bottomBorder(TextUtil.GOLD));
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
        skillRewards.setUnlockTokens(1);
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
        skillRewards.setUnlockTokens(2);
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
        skillRewards.setUnlockTokens(3);
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
        skillRewards.setUnlockTokens(3);
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
        skillRewards.setUnlockTokens(4);
        return skillRewards;
    }



}
