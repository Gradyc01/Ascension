package me.depickcator.ascensionBingo.Skills;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerSkills;
import me.depickcator.ascensionBingo.Player.PlayerUnlocks;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class Global implements Skills {
    private final PlayerData playerData;
    private final AscensionBingo plugin;
    private final Player player;
    private int experience;
    private int level;
    private final String NAME = "Level";
    private final int MAXLEVEL = 5;
//     private final static ArrayList<>

    public Global(AscensionBingo plugin, PlayerData playerData) {
        this.plugin = plugin;
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
        return  "";
    }



}
