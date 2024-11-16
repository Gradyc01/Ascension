package me.depickcator.ascensionBingo.Skills;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Player.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.ArrayList;

public class Combat implements Skills {
    private final PlayerData playerData;
    private final AscensionBingo plugin;
    private final Player player;
    private int experience;
    private int level;
    private final static ArrayList<Integer> LEVELREQUIREMENTS= new ArrayList<>(
            Arrays.asList(25, 100, 250, 750, 1500));
//     private final static ArrayList<>

    public Combat(AscensionBingo plugin, PlayerData playerData) {
        this.plugin = plugin;
        this.playerData = playerData;
        this.player = playerData.getPlayer();
        experience = 0;
        level = 1;
    }

    @Override
    public void addExp(int amount) {
        experience+=amount;
        if (canLevelUp()) {
            levelUp(level++);
        }
        playGainedExpSound(player);
        Component message = TextUtil.makeText("+" + experience + " combat   " + getExpOverTotalNeeded(), TextUtil.AQUA);
        TextUtil.sendActionBar(player, message, 1, plugin);
    }

    private boolean canLevelUp() {
        return experience >= LEVELREQUIREMENTS.get(level-1);
    }

    private void levelUp(int newLevel) {
        plugin.getServer().broadcast(TextUtil.makeText("level up here",  TextUtil.WHITE));
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
        return " [" + experience + "/" + Combat.LEVELREQUIREMENTS.get(level - 1) + "]";
    }


}
