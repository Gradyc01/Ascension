package me.depickcator.ascension.Lobby.NPCs;

import me.depickcator.ascension.MainMenuUI.Skills.SkillsGUI;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ViewSkills extends LobbyNPCs {
    public ViewSkills(double x, double y, double z, Pair<Integer, Integer> rotation) {
        super(x, y, z, rotation,
                EntityType.DROWNED, TextUtil.makeText("Skills", TextUtil.GOLD, true, false));
    }

    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        new SkillsGUI(PlayerUtil.getPlayerData(event.getPlayer()));
        return true;
    }
}
