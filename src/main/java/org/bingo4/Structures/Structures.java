package org.bingo4.Structures;

public enum Structures {
    BINGO_TURN_IN("bingo_turn_in"),
    MINING_CAMP_1("mining_camp_1"),
    MINING_CAMP_2("mining_camp_2"),
    MINING_CAMP_3("mining_camp_3"),
    NETHER_KOTH("nether_koth");


    private final String id;
    Structures(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}


