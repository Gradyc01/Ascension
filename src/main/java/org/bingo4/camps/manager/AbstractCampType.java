package org.bingo4.camps.manager;

import org.bingo4.Utility.FloodBuild.FloodBlocks;
import org.bingo4.Utility.FloodBuild.Floodable;
import org.bingo4.camps.api.CampInstance;
import org.bingo4.camps.api.CampTemplate;
import org.bingo4.camps.api.LocalPos;
import org.bingo4.camps.api.SpawnRole;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * Base class for a camp category (for example, bandit camps or abandoned camps).
 *
 * A camp type owns the template used to build it and the roles assigned to the
 * bedrock spawn markers in that template. Extend this class for each new type.
 */
public abstract class AbstractCampType implements Floodable {
    private final String id;
    private final CampTemplate template;
    private final Map<LocalPos, SpawnRole> spawnRoles;

    protected AbstractCampType(
            String id,
            CampTemplate template,
            Map<LocalPos, SpawnRole> spawnRoles
    ) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Camp type id cannot be blank");
        }
        this.id = id;
        this.template = Objects.requireNonNull(template, "template");
        this.spawnRoles = Map.copyOf(
                spawnRoles == null ? Map.of() : spawnRoles
        );
    }

    protected AbstractCampType(String id, CampTemplate template) {
        this(id, template, Map.of());
    }

    public final String getId() {
        return id;
    }

    public final CampTemplate getTemplate() {
        return template;
    }

    /**
     * Coordinates are local coordinates in the original, unrotated template.
     * Any bedrock marker not listed here is assigned UNASSIGNED.
     */
    public final Map<LocalPos, SpawnRole> getSpawnRoles() {
        return spawnRoles;
    }

    /**
     * Called after this type has been successfully pasted and registered.
     * Override this to initialize type-specific state or gameplay.
     */
    public void onCampPlaced(CampInstance camp) {
        // Optional hook for subclasses.
    }


    @Override
    public Block changeBlock(Block block, Random r, FloodBlocks floodBlocks) {
        block.setType(floodBlocks.chooseNextMaterial(r));
        return block;
    }

    @Override
    public boolean isUnFloodable(Block b) {
        return getUnFloodables().containsKey(b.getType());
    }

    @Override
    public double getNewSuccessRate(double oldSuccessRate, Random r) {
        return oldSuccessRate - r.nextDouble(0.1, 0.20);
    }
}
