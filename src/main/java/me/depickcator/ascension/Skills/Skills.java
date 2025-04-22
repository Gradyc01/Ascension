package me.depickcator.ascension.Skills;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Skills {
    protected final PlayerData playerData;
    private final Ascension plugin;
    protected Player player;
    protected int experience;
    protected int level;
    private final String skillName;
    protected final int MAXLEVEL = 5;
    private final List<Integer> levelRequirements;
    private final List<SkillRewards> rewards;
    /*Initializes a Skill Named skillName for Player playerData*/
    public Skills(PlayerData playerData, String skillName) {
        this.playerData = playerData;
        this.skillName = skillName;
        plugin = Ascension.getInstance();
        player = playerData.getPlayer();
        experience = 0;
        level = 0;
        levelRequirements = initLevelRequirements();
        rewards = initRewards();

    }

    /*Initializes the rewards of this particular skill
    * Returns a list of SkillRewards
    * The List must have a size greater than MAXLEVEL*/
    public abstract List<SkillRewards> initRewards();

    /*Initializes the level requirements of this particular skill
     * Returns a list of Integers
     * The List must have a size greater than MAXLEVEL*/
    public List<Integer> initLevelRequirements() {
        return new ArrayList<>(List.of(
                25, 100, 250, 750, 1500
        ));
    }

    /*Adds Experience to this particular skill*/
    public void addExp(int amount) {
        experience+=amount;
        playerData.getPlayerUnlocks().addUnlockTokens(amount);
        if (canLevelUp()) {
            levelUp(++level);
        }
        playerGainedExpNotification(player, amount, skillName, plugin);
    }


    protected boolean canLevelUp() {
        if (level >= MAXLEVEL) {
            return false;
        }
        return experience >= levelRequirements.get(level);
    }

    protected void levelUp(int newLevel) {
        SkillRewards reward = rewards.get(level-1);
        levelUpMessage(newLevel, reward, player, skillName);
        playLevelUpSound(player);
        reward.giveRewards(playerData);
        playerData.getPlayerSkills().getGlobal().addExp(1);
        if (canLevelUp()) {
            levelUp(++level);
        }
    }



    public String getExp() {
        return experience + "";
    }

    public String getExpLevel() {
        return level + "";
    }

    public List<Component> getRewardText(int level) {
        return parseRewardText(rewards.get(level - 1));
    }

    public String getExpOverTotalNeeded() {
        if (level >= MAXLEVEL) {
            return " (" + experience + "/" + levelRequirements.get(MAXLEVEL - 1) + ")";
        }
        return " (" + experience + "/" + levelRequirements.get(level) + ")";
    }

    public void updatePlayer() {
        player = playerData.getPlayer();
    };

    public String getSkillName() {
        return skillName;
    }

    private void playerGainedExpNotification(Player player, int amount, String skillName, Ascension plugin) {
        playGainedExpSound(player);
        Component message = TextUtil.makeText("+" + amount + " " + skillName + " " + getExpOverTotalNeeded(), TextUtil.AQUA);
        TextUtil.sendActionBar(player, message, 20, plugin);
    }

    private void playGainedExpSound(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.6f);
    }

    private List<Component> parseRewardText(SkillRewards rewards) {
        ArrayList<Component> texts = new ArrayList<>();
        for (ItemStack item : rewards.getItems()) {
            Component displayName = item.displayName().color(TextUtil.DARK_PURPLE).decoration(TextDecoration.ITALIC, false);
            Component amount = TextUtil.makeText(" x" + item.getAmount(), TextUtil.DARK_PURPLE);
            texts.add(displayName.append(amount));
        }
        texts.add(TextUtil.makeText(rewards.getUnlockTokens() + " Souls", TextUtil.DARK_PURPLE));
        return texts;
    }

    private void playLevelUpSound(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1f);
    }

    private void levelUpMessage(int newLevel, SkillRewards reward, Player player, String skillName) {
        Component spacing = TextUtil.makeText("      ", TextUtil.AQUA);
        player.sendMessage(TextUtil.topBorder(TextUtil.GOLD));
        player.sendMessage(TextUtil.makeText("                    LEVEL UP!!!!", TextUtil.AQUA, true, false));
        player.sendMessage(TextUtil.makeText("                     " + skillName + " " + TextUtil.toRomanNumeral(newLevel), TextUtil.GOLD, true, false));
        player.sendMessage(TextUtil.makeText("\n\n   Rewards: ", TextUtil.GOLD, true, false));
        for (ItemStack item : reward.getItems()) {
            Component displayName = item.displayName().color(TextUtil.RED);
            Component amount = TextUtil.makeText(" x" + item.getAmount(), TextUtil.RED);
            player.sendMessage(spacing.append(displayName).append(amount));
        }
        player.sendMessage(spacing.append(TextUtil.makeText(reward.getUnlockTokens() + " Souls", TextUtil.RED)));
        player.sendMessage(TextUtil.bottomBorder(TextUtil.GOLD));
    }

}
