package me.depickcator.ascensionBingo.Skills;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface Skills {

    void addExp(int amount);
//    void giveRewards(int level, Player p);
    String getExp();
    String getExpLevel();
    ArrayList<String> getRewardText(int level);
    String getExpOverTotalNeeded();

    default void playGainedExpSound(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.6f);
    }

}
