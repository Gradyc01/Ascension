package me.depickcator.ascensionBingo.Skills;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public interface Skills {

    void addExp(int amount);
//    void giveRewards(int level, Player p);
    String getExp();
    String getExpLevel();
    ArrayList<String> getRewardText(int level);
    String getExpOverTotalNeeded();
//    boolean canLevelUp();
//    void levelUp(int newLevel)

    default void playerGainedExpNotification(Player player, int amount, String skillName, AscensionBingo plugin) {
        playGainedExpSound(player);
        Component message = TextUtil.makeText("+" + amount + " " + skillName + " " + getExpOverTotalNeeded(), TextUtil.AQUA);
        TextUtil.sendActionBar(player, message, 20, plugin);
    }

    default void playGainedExpSound(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.6f);
    }

    default void playLevelUpSound(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1f);
    }

    default void levelUpMessage(int newLevel, SkillRewards reward, Player player, String skillName) {
        Component spacing = TextUtil.makeText("      ", TextUtil.AQUA);
        player.sendMessage(TextUtil.topBorder(TextUtil.GOLD));
        player.sendMessage(TextUtil.makeText("                    LEVEL UP!!!!", TextUtil.AQUA, true, false));
        player.sendMessage(TextUtil.makeText("                     " + skillName + " " + newLevel, TextUtil.GOLD, true, false));
        player.sendMessage(TextUtil.makeText("\n\n   Rewards: ", TextUtil.GOLD, true, false));
        for (ItemStack item : reward.getItems()) {
            Component displayName = item.displayName().color(TextUtil.RED);
            Component amount = TextUtil.makeText(" x" + item.getAmount(), TextUtil.RED);
            player.sendMessage(spacing.append(displayName).append(amount));
        }
        player.sendMessage(spacing.append(TextUtil.makeText(reward.getUnlockTokens() + " Unlock Tokens", TextUtil.RED)));
        player.sendMessage(TextUtil.bottomBorder(TextUtil.GOLD));
    }

}
