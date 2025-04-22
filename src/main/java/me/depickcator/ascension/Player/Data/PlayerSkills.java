package me.depickcator.ascension.Player.Data;

import me.depickcator.ascension.Skills.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerSkills implements PlayerDataObservers {
    private final PlayerData playerData;
    private final Combat combat;
    private final Mining mining;
    private final Foraging foraging;
    private final Global global;
    private final List<Skills> allSkills;
    public PlayerSkills(PlayerData playerData) {
        this.playerData = playerData;
        combat = new Combat(this.playerData);
        mining = new Mining(this.playerData);
        foraging = new Foraging(this.playerData);
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