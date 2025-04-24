package me.depickcator.ascension.Skills;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerSkills;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Global extends Skills {

    public Global(PlayerData playerData) {
        super(playerData, "Global");
    }

    @Override
    public List<SkillRewards> initRewards() {
        return List.of();
    }

    @Override
    public void addExp(int amount) {
        if (canLevelUp()) {
            levelUp(++level);
        }
    }

    @Override
    protected boolean canLevelUp() {
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

    @Override
    protected void levelUp(int newLevel) {
        double health = player.getAttribute(Attribute.MAX_HEALTH).getBaseValue();
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 12 * 20, 0, true, true));
        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health + 4);
        levelUpText();
        if (canLevelUp()) {
            levelUp(++level);
        }
    }

    private void levelUpText() {
        player.sendMessage(TextUtil.topBorder(TextUtil.GOLD));
        player.sendMessage(TextUtil.makeText("                    LEVEL UP!!!!", TextUtil.AQUA, true, false));
        player.sendMessage(TextUtil.makeText("                     " + getSkillName() + " " + TextUtil.toRomanNumeral(level) , TextUtil.GOLD, true, false));
        player.sendMessage(TextUtil.bottomBorder(TextUtil.GOLD));
    }

    @Override
    public List<Component> getRewardText(int level) {
        return new ArrayList<>(List.of(
                TextUtil.makeText(" +4 HP", TextUtil.DARK_PURPLE)
        ));
    }

    @Override
    public String getExpOverTotalNeeded() {
        return  "";
    }


}
