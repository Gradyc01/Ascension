package org.bingo4.Structures.MiningNode;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.BlockVector3;
import org.bingo4.Structures.BingoTurnIn.BingoTurnInInstance;
import org.bingo4.Structures.CampInstance;
import org.bingo4.Structures.Interfaces.AbstractCampType;
import org.bingo4.Structures.MiningNode.LootTables.MiningNodeCampLoot;
import org.bingo4.Utility.FloodBuild.FloodBlocks;
import org.bingo4.Utility.FloodBuild.Floodable;
import org.bingo4.Utility.SchematicPlacer;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MiningNodeCamp extends AbstractCampType {

    private static final String SCHEMATIC_NAME = "mining_camp.schem";

    private static final int BASE_WIDTH = 5;
    private static final int BASE_DEPTH = 5;

    private Clipboard clipboard;
    private final SchematicPlacer schematicPlacer = new SchematicPlacer();
    private final MiningNodeCampLoot loot;

    private static final List<BlockVector3> BASE_SUPPORT_POINTS = List.of(
            BlockVector3.at(2, 0, 0),
            BlockVector3.at(-2, 0, 0),
            BlockVector3.at(0, 0, -2),
            BlockVector3.at(0, 0, -2)
    );

    public MiningNodeCamp(MiningNodeCampLoot loot) throws Exception {
        super(loot.getId());
        this.loot = loot;
        loadSchematic();
    }

    @Override
    public void place(CampInstance instance) throws Exception {
        if (clipboard == null) {
            throw new IllegalStateException("Bingo turn-in schematic has not been loaded.");
        }

        List<BlockVector3> supportPoints = BASE_SUPPORT_POINTS.stream()
                .map(point -> BlockVector3.at(
                        (BASE_WIDTH / 2) + point.x(),
                        0,
                        (BASE_DEPTH / 2) + point.z()
                ))
                .toList();

        SchematicPlacer.SchematicSpec spec =
                new SchematicPlacer.SchematicSpec(
                        clipboard,
                        0,
                        0,
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
                4,
                Material.QUARTZ_PILLAR,
                "UNBREAKABLE"
        );

        // The center of the 16x16 base.
        instance.setCenter(instance.getOrigin().clone().add((double) BASE_WIDTH /2, 0, (double) BASE_DEPTH /2));
        instance.getCenter().setY(result.schematicMinY());
        TextUtil.debugText("[Bingo Turn In Camp] Origin " + TextUtil.formatLocation(instance.getOrigin()));
        TextUtil.debugText("[Bingo Turn In Camp] Center " + TextUtil.formatLocation(instance.getCenter()));


        if (instance instanceof MiningNodeInstance bingoInstance) {
            bingoInstance.buildFinished(result.blocks());
        } else {
            throw new IllegalStateException("CampInstance is not a bingo instance");
        }
    }

    @Override
    public CampInstance createInstance(Location origin, Location center)  {
        return new MiningNodeInstance(this, origin, center, loot);
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
}
