package org.bingo4.camps.core;

import org.bingo4.camps.api.*;
import org.bingo4.camps.storage.CampRegistry;
import org.bingo4.camps.storage.CampStore;
import java.util.SplittableRandom;

public final class CampPlanner {
    private final int regionSize;
    private final double campChance;

    public CampPlanner(int regionSize, double campChance) {
        if (regionSize < 64) throw new IllegalArgumentException("regionSize must be >= 64");
        this.regionSize = regionSize;
        this.campChance = Math.max(0.0, Math.min(1.0, campChance));
    }

    public Candidate candidate(long worldSeed, int regionX, int regionZ) {
        long seed = mix64(worldSeed ^ ((long) regionX * 0x9E3779B97F4A7C15L)
                ^ ((long) regionZ * 0xC2B2AE3D27D4EB4FL));
        SplittableRandom random = new SplittableRandom(seed);
        int jitter = Math.min(24, regionSize / 8);
        int x = regionX * regionSize + regionSize / 2 + random.nextInt(-jitter, jitter + 1);
        int z = regionZ * regionSize + regionSize / 2 + random.nextInt(-jitter, jitter + 1);
        boolean spawn = random.nextDouble() < campChance;
        CampRotation[] rotations = CampRotation.values();
        CampRotation rotation = rotations[random.nextInt(rotations.length)];
        return new Candidate(x, z, rotation, seed, spawn);
    }

    public static long mix64(long value) {
        value = (value ^ (value >>> 30)) * 0xBF58476D1CE4E5B9L;
        value = (value ^ (value >>> 27)) * 0x94D049BB133111EBL;
        return value ^ (value >>> 31);
    }
}
