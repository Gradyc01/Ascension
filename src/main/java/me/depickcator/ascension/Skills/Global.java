package me.depickcator.ascension.Skills;

import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Player.PlayerData;
import me.depickcator.ascension.Player.PlayerSkills;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Global implements Skills {
    private final PlayerData playerData;
    // private final Ascension plugin;
    private final Player player;
    private int experience;
    private int level;
    private final String NAME = "Global";
    private final int MAXLEVEL = 5;
//     private final static ArrayList<>

    public Global(PlayerData playerData) {
        // this.plugin = plugin;
        this.playerData = playerData;
        this.player = playerData.getPlayer();
        experience = 0;
        level = 0;
    }

    @Override
    public void addExp(int amount) {
        if (canLevelUp()) {
            levelUp(++level);
        }
    }

    private boolean canLevelUp() {
        if (level >= MAXLEVEL) {
            return false;
        }
        PlayerSkills playerSkills = playerData.getPlayerSkills();
        int mining = Integer.parseInt(playerSkills.getMining().getExpLevel());
        int foraging = Integer.parseInt(playerSkills.getForaging().getExpLevel());
        int combat = Integer.parseInt(playerSkills.getCombat().getExpLevel());
        int req = level + 1;
        return mining >= req && foraging >= req && combat >= req;
    }

    private void levelUp(int newLevel) {
        double health = player.getAttribute(Attribute.MAX_HEALTH).getBaseValue();
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 0, true, true));
        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health + 4);
        levelUpText();
        if (canLevelUp()) {
            levelUp(++level);
        }
    }

    private void levelUpText() {
        player.sendMessage(TextUtil.topBorder(TextUtil.GOLD));
        player.sendMessage(TextUtil.makeText("                    LEVEL UP!!!!", TextUtil.AQUA, true, false));
        player.sendMessage(TextUtil.makeText("                     " + NAME + " " + TextUtil.toRomanNumeral(level) , TextUtil.GOLD, true, false));
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
        return  "";
    }



}
