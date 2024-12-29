package me.depickcator.ascension.Player.Data;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Skills.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerSkills implements PlayerDataObservers {
    private final Ascension plugin;
    private final PlayerData playerData;
    private final Combat combat;
    private final Mining mining;
    private final Foraging foraging;
    private final Global global;
    private final List<Skills> allSkills;
    public PlayerSkills(PlayerData playerData) {
        this.plugin = Ascension.getInstance();
        this.playerData = playerData;
        combat = new Combat(this.plugin, this.playerData);
        mining = new Mining(this.plugin, this.playerData);
        foraging = new Foraging(this.plugin, this.playerData);
        global = new Global(this.playerData);
        allSkills = new ArrayList<>(List.of(
                combat,
                mining,
                foraging,
                global
        ));
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

    @Override
    public void updatePlayer() {
        for (Skills skill : allSkills) {
            skill.updatePlayer();
        }
    }
}