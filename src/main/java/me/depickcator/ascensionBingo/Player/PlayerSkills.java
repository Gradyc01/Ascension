package me.depickcator.ascensionBingo.Player;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Skills.Combat;
import org.bukkit.entity.Player;

public class PlayerSkills{
    private final AscensionBingo plugin;
    private final PlayerData playerData;
    private Combat combat;
    public PlayerSkills(PlayerData playerData, AscensionBingo plugin) {
        this.plugin = plugin;
        this.playerData = playerData;
        combat = new Combat(this.plugin, this.playerData);
    }





}