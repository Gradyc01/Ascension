package me.depickcator.ascensionBingo.Player;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Skills.Combat;
import me.depickcator.ascensionBingo.Skills.Foraging;
import me.depickcator.ascensionBingo.Skills.Global;
import me.depickcator.ascensionBingo.Skills.Mining;

public class PlayerSkills{
    private final AscensionBingo plugin;
    private final PlayerData playerData;
    private final Combat combat;
    private final Mining mining;
    private final Foraging foraging;
    private final Global global;
    public PlayerSkills(PlayerData playerData, AscensionBingo plugin) {
        this.plugin = plugin;
        this.playerData = playerData;
        combat = new Combat(this.plugin, this.playerData);
        mining = new Mining(this.plugin, this.playerData);
        foraging = new Foraging(this.plugin, this.playerData);
        global = new Global(this.plugin, this.playerData);
    }

    public Combat getCombat() {
        return combat;
    }

    public Mining getMining() {
        return mining;
    }

    public Foraging getForaging() {
        return foraging;
    }

    public Global getGlobal() {
        return global;
    }
}