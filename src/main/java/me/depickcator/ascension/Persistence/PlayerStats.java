package me.depickcator.ascension.Persistence;

import java.util.HashMap;
import java.util.UUID;

public class PlayerStats {
    private final HashMap<String, Integer> stats;
    private final UUID uuid;
    private final String name;
    public PlayerStats(UUID playerUUID, String playerName) {
        this.uuid = playerUUID;
        this.name = playerName;
        stats = new HashMap<>();
    }

    public HashMap<String, Integer> getStats() {
        return stats;
    }

    public void addStat(String stat, int amount) {
        stats.put(stat, amount);
    }

    public int getStat(String stat) {
        return stats.getOrDefault(stat, -1);
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}

