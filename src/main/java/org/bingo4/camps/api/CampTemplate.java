package org.bingo4.camps.api;

/**
 * A pasteable camp structure.
 *
 * width/depth/height describe the FULL structure. The "base" is the flat ground
 * footprint inside that structure: a baseWidth x baseDepth rectangle whose minimum
 * corner sits at (baseOffsetX, baseOffsetZ) in the template's unrotated local
 * coordinates. Terrain search, flattening and blending all work on the base;
 * the rest of the structure simply hangs off it.
 *
 * Example: a 24x20 structure with a 16x16 base at offset (4, 2) has its base on
 * local X=4..19 and Z=2..17.
 *
 * Local coordinates are always relative to the structure's minimum corner, and
 * rotated coordinates are relative to the rotated structure's minimum corner.
 */
public interface CampTemplate {
    String id();
    int width();
    int depth();
    int height();
    int groundOffsetY();

    int baseWidth();
    int baseDepth();
    int baseOffsetX();
    int baseOffsetZ();

    /**
     * Pastes the rotated camp. (x, y, z) is the minimum corner of the ROTATED
     * structure's bounding box, not of the base.
     */
    PreparedCamp prepare(CampRotation rotation);

    // ---------------------------------------------------------------------
    // Rotation math. Uses the full structure dimensions, never an assumed 16.
    // ---------------------------------------------------------------------

    /** Width (X size) of the full structure after rotation. */
    default int rotatedWidth(CampRotation rotation) {
        return swapsAxes(rotation) ? depth() : width();
    }

    /** Depth (Z size) of the full structure after rotation. */
    default int rotatedDepth(CampRotation rotation) {
        return swapsAxes(rotation) ? width() : depth();
    }

    /** Width (X size) of the base after rotation. */
    default int rotatedBaseWidth(CampRotation rotation) {
        return swapsAxes(rotation) ? baseDepth() : baseWidth();
    }

    /** Depth (Z size) of the base after rotation. */
    default int rotatedBaseDepth(CampRotation rotation) {
        return swapsAxes(rotation) ? baseWidth() : baseDepth();
    }

    /**
     * Rotates a local position inside the full structure. The result is relative
     * to the rotated structure's minimum corner (matches Minecraft's structure rotation).
     */
    default LocalPos rotate(LocalPos p, CampRotation rotation) {
        return switch (rotation) {
            case NONE -> p;
            case CLOCKWISE_90 -> new LocalPos(depth() - 1 - p.z(), p.y(), p.x());
            case CLOCKWISE_180 -> new LocalPos(width() - 1 - p.x(), p.y(), depth() - 1 - p.z());
            case COUNTERCLOCKWISE_90 -> new LocalPos(p.z(), p.y(), width() - 1 - p.x());
        };
    }

    /**
     * Minimum X/Z corner of the base after rotation, relative to the rotated
     * structure's minimum corner (y is always 0).
     *
     * Rotation maps an axis-aligned rectangle onto another axis-aligned rectangle,
     * so rotating two opposite corners and taking the minimums is equivalent to
     * rotating all four.
     */
    default LocalPos rotatedBaseMin(CampRotation rotation) {
        LocalPos a = rotate(new LocalPos(baseOffsetX(), 0, baseOffsetZ()), rotation);
        LocalPos b = rotate(new LocalPos(baseOffsetX() + baseWidth() - 1, 0,
                baseOffsetZ() + baseDepth() - 1), rotation);
        return new LocalPos(Math.min(a.x(), b.x()), 0, Math.min(a.z(), b.z()));
    }

    /**
     * Worst-case distance the structure can stick out past the base's edge in any
     * rotation. Used to decide how many chunks must be loaded before pasting.
     */
    default int structureMargin() {
        return Math.max(0, Math.max(width(), depth()) - Math.min(baseWidth(), baseDepth()));
    }

    private static boolean swapsAxes(CampRotation rotation) {
        return rotation == CampRotation.CLOCKWISE_90
                || rotation == CampRotation.COUNTERCLOCKWISE_90;
    }
}
