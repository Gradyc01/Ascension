package org.bingo4.Structures;

public enum Structures {
    BINGO_TURN_IN("bingo_turn_in"),
    MINING_CAMP_1("mining_camp_1"),
    MINING_CAMP_2("mining_camp_2"),
    MINING_CAMP_2_GEODE_VARIANT("mining_camp_2_geode"),
    MINING_CAMP_2_TRIAL_VARIANT("mining_camp_2_trial"),
    MINING_CAMP_3("mining_camp_3"),
    MINING_CAMP_3_LUSH_VARIANT("mining_camp_3_lush"),
    MINING_CAMP_3_SULFUR_VARIANT("mining_camp_3_sulfur"),
    NETHER_KOTH("nether_koth"),
    MIDBOSS("mid_boss_camp");



    private final String id;
    Structures(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}


