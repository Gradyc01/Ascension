package me.depickcator.ascension.Skills;

import me.depickcator.ascension.Ascension;

public enum SkillExpAmount {
    COMBAT_COMMON(8),
    COMBAT_UNCOMMON(12),
    COMBAT_RARE(16),
    COMBAT_VERY_RARE(64),
    COMBAT_LEGENDARY(128),
    FORAGING_COMMON(3),
    FORAGING_UNCOMMON(8),
    FORAGING_RARE(24),
    FORAGING_VERY_RARE(48),
    FORAGING_LEGENDARY(96),
    MINING_COMMON(1),
    MINING_UNCOMMON(5),
    MINING_RARE(10),
    MINING_VERY_RARE(20),
    MINING_LEGENDARY(25),;

    private final int expAmount;

    SkillExpAmount(int expAmount) {
        this.expAmount = expAmount;
    }

    public int getExp() {
//        Ascension.getInstance().getSettingsUI().getSettings().get
        return expAmount;
    }


}
