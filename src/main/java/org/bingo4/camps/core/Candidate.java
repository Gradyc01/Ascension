package org.bingo4.camps.core;

import org.bingo4.camps.api.*;
import org.bingo4.camps.storage.CampRegistry;
import org.bingo4.camps.storage.CampStore;

public record Candidate(
        int x,
        int z,
        CampRotation rotation,
        long seed,
        boolean shouldSpawn
) {}
