package org.bingo4.Structures.BingoTurnIn;

import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;

import org.bingo4.Bingo4;
import org.bingo4.Structures.CampInstance;
import org.bingo4.Structures.Interfaces.AbstractCampType;
import org.bingo4.Utility.SchematicPlacer;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class BingoTurnInCamp extends AbstractCampType {

    private static final String SCHEMATIC_NAME = "bingo_turn_in.schem";

    private static final int BASE_WIDTH = 13;
    private static final int BASE_DEPTH = 13;

    private static final List<BlockVector3> BASE_SUPPORT_POINTS = List.of(
            BlockVector3.at(4, 0, 4),
            BlockVector3.at(-4, 0, 4),
            BlockVector3.at(-4, 0, -4),
            BlockVector3.at(4, 0, -4)
    );

    private final Bingo4 plugin;
    private final SchematicPlacer schematicPlacer;

    // Base's minimum X/Z position relative to the schematic minimum.
    private final int baseOffsetX;
    private final int baseOffsetZ;

    private Clipboard clipboard;

    public BingoTurnInCamp(
            Bingo4 plugin,
            SchematicPlacer schematicPlacer,
            int baseOffsetX,
            int baseOffsetZ
    ) throws Exception {

        super("bingo_turn_in");

        this.plugin = plugin;
        this.schematicPlacer = schematicPlacer;
        this.baseOffsetX = baseOffsetX;
        this.baseOffsetZ = baseOffsetZ;

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
            throw new IllegalStateException("Bingo turn-in schematic has not been loaded.");
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

        SchematicPlacer.PlacementResult result = schematicPlacer.placeChecked(
                instance.getOrigin().getWorld(),
                spec,
                baseWorldX,
                baseWorldZ,
                5,
                Material.QUARTZ_PILLAR,
                "UNBREAKABLE"
        );

        // The center of the 16x16 base.
        instance.setCenter(instance.getOrigin().clone().add((double) BASE_WIDTH /2, result.heightDifference(), (double) BASE_DEPTH /2));
//        instance.getCenter().add();
        TextUtil.debugText("[Bingo Turn In Camp] Origin " + TextUtil.formatLocation(instance.getOrigin()));
        TextUtil.debugText("[Bingo Turn In Camp] Center " + TextUtil.formatLocation(instance.getCenter()));



        if (instance instanceof BingoTurnInInstance bingoInstance) {
            bingoInstance.buildFinished(result.blocks());
        } else {
            throw new IllegalStateException("CampInstance is not a bingo instance");
        }
    }

    @Override
    public CampInstance createInstance(Location origin, Location center) throws Exception {
        return new BingoTurnInInstance(this, origin, center);
    }
}