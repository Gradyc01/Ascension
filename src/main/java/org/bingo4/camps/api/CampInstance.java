package org.bingo4.camps.api;

import org.bukkit.Location;

import java.util.List;

public record CampInstance(
        String instanceId,
        String templateId,
        CampRotation rotation,
        Location origin,
        List<SpawnPoint> spawnPoints,
        List<CommandPanel> commandPanels
) {
    public CampInstance {
        spawnPoints = List.copyOf(spawnPoints);
        commandPanels = List.copyOf(commandPanels);
    }
}
