package me.depickcator.ascension.Skills;


public enum SkillExpAmount {
    COMBAT_COMMON(10),
    COMBAT_UNCOMMON(16),
    COMBAT_RARE(24),
    COMBAT_VERY_RARE(64),
    COMBAT_LEGENDARY(128),
    FORAGING_COMMON(3),
    FORAGING_UNCOMMON(12),
    FORAGING_RARE(48),
    FORAGING_VERY_RARE(64),
    FORAGING_LEGENDARY(128),
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
        return expAmount;
    }


}
