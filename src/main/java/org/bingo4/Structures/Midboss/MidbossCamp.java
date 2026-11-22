package org.bingo4.Structures.Midboss;

import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.BlockVector3;
import org.bingo4.Structures.CampInstance;
import org.bingo4.Structures.Interfaces.AbstractCampType;
import org.bingo4.Structures.NetherKoth.NetherKothInstance;
import org.bingo4.Utility.SchematicPlacer;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.bingo4.Structures.Structures.MIDBOSS;
import static org.bingo4.Structures.Structures.NETHER_KOTH;

public class MidbossCamp extends AbstractCampType {

    private static final String SCHEMATIC_NAME = "midboss.schem";

    private static final int BASE_WIDTH = 13; //Maybe try 25
    private static final int BASE_DEPTH = 13;

    private static final List<BlockVector3> BASE_SUPPORT_POINTS = List.of(
            BlockVector3.at(-5, 0, 0),
            BlockVector3.at(-4, 0, 0),
            BlockVector3.at(-3, 0, 0),
            BlockVector3.at(-2, 0, 0),
            BlockVector3.at(-1, 0, 0),
            BlockVector3.at(0, 0, 0),
            BlockVector3.at(1, 0, 0),
            BlockVector3.at(2, 0, 0),
            BlockVector3.at(3, 0, 0),
            BlockVector3.at(4, 0, 0),
            BlockVector3.at(5, 0, 0)
    );

    // Base's minimum X/Z position relative to the schematic minimum.
    private final int baseOffsetX = 0;
    private final int baseOffsetZ = 0;

    private Clipboard clipboard;

    public MidbossCamp() throws Exception {
        super(MIDBOSS.getId());
        loadSchematic();
    }

    private void loadSchematic() throws Exception {
        File schematicFile = new File(
                "./plugins/Ascension/Structures/" + SCHEMATIC_NAME
        );

        if (!schematicFile.exists()) {
            throw new IllegalStateException(
                    "Missing schematic: " + schematicFile.getAbsolutePath()
            );
        }

        ClipboardFormat format = ClipboardFormats.findByPath(schematicFile.toPath());

        if (format == null) {
            throw new IllegalStateException(
                    "Could not determine schematic format: " + schematicFile
            );
        }

        try (FileInputStream input = new FileInputStream(schematicFile);
             var reader = format.getReader(input)) {

            clipboard = reader.read();
        }

        // Full schematic dimensions, not the 16x16 base dimensions.
        setWidth(clipboard.getRegion().getWidth());
        setDepth(clipboard.getRegion().getLength());
        setHeight(clipboard.getRegion().getHeight());
    }

    @Override
    public void place(CampInstance instance) throws IllegalStateException, SchematicPlacer.TerrainNotSuitableException, WorldEditException {
        if (clipboard == null) {
            throw new IllegalStateException(SCHEMATIC_NAME +  " schematic has not been loaded.");
        }

        List<BlockVector3> supportPoints = BASE_SUPPORT_POINTS.stream()
                .map(point -> BlockVector3.at(
                        baseOffsetX + (BASE_WIDTH / 2) + point.x(),
                        0,
                        baseOffsetZ + (BASE_DEPTH / 2) + point.z()
                ))
                .toList();

        SchematicPlacer.SchematicSpec spec =
                new SchematicPlacer.SchematicSpec(
                        clipboard,
                        baseOffsetX,
                        baseOffsetZ,
                        BASE_WIDTH,
                        BASE_DEPTH,
                        supportPoints
                );

        // Origin represents the minimum X/Z corner of the base.
        int baseWorldX = instance.getOrigin().getBlockX();
        int baseWorldZ = instance.getOrigin().getBlockZ();

        SchematicPlacer schematicPlacer = new SchematicPlacer();

        SchematicPlacer.PlacementResult result = schematicPlacer.placeChecked(
                instance.getOrigin().getWorld(),
                spec,
                baseWorldX,
                baseWorldZ,
                5,
                Material.NETHER_BRICKS,
                "UNBREAKABLE"
        );

        instance.setCenter(instance.getOrigin().clone().add((double) BASE_WIDTH /2, result.heightDifference(), (double) BASE_DEPTH /2));
        TextUtil.debugText("[Mid Boss Camp] Origin " + TextUtil.formatLocation(instance.getOrigin()));
        TextUtil.debugText("[Mid Boss Camp] Center " + TextUtil.formatLocation(instance.getCenter()));

        if (instance instanceof MidbossInstance midbossInstance) {
            midbossInstance.buildFinished(result.blocks());
        } else {
            throw new IllegalStateException("CampInstance is not a MidbossInstance");
        }
    }

    @Override
    public CampInstance createInstance(Location origin, Location center) throws Exception {
        return new MidbossInstance(this, origin, center);
    }
}