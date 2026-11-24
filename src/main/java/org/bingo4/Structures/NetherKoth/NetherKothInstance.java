package org.bingo4.Structures.NetherKoth;

import org.bingo4.Bingo4;
import org.bingo4.MainMenuUI.Map.MapItem;
import org.bingo4.MainMenuUI.Map.MapItems;
import org.bingo4.MainMenuUI.Map.MapTransmitRange;
import org.bingo4.Structures.CampInstance;
import org.bingo4.Structures.Interfaces.AbstractCampType;
import org.bingo4.Utility.DisplayUtil;
import org.bingo4.Utility.FloodBuild.FloodBlocks;
import org.bingo4.Utility.FloodBuild.Floodable;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

import static org.bingo4.MainMenuUI.Map.MapItem.NETHER_PORTAL;

public class NetherKothInstance extends CampInstance implements Floodable {

//    private final BingoTurnInNPC bingoTurnInNPC;
//    private final BingoTurnInProgress bingoTurnInProgress;
    private Set<Block> blocks;
    private MapItem mapItem;
    private Set<UUID> portalTimeDisplay;
    private BukkitTask portalLoop;
    private final List<Block> portalBlocks;

    public NetherKothInstance(AbstractCampType type, Location origin, Location center) {
        super(type, origin, center);
        portalBlocks = new ArrayList<>();
    }

    public void buildFinished(Set<Block> blocks) {
        this.blocks = blocks;
        for (Block block : blocks) {
            if (block.getBlockData().getMaterial().equals(Material.BEACON)) {
                setCenter(block.getLocation());
                block.setType(Material.AIR);
                portalBlocks.add(block);
            } else if (block.getType().equals(Material.SPONGE)) {
                block.setType(Material.AIR);
                portalBlocks.add(block);
            }
        }
        floodFloor(getCenter().add(0, -1, 0));
        mapItem = new MapItem("Nether Portal", getCenter(), NETHER_PORTAL, MapTransmitRange.FAR, Color.RED);
        plugin.getSettingsUI().getSettings().getTimeline().getMapItems().addMapItem(mapItem);
    }

    public void start() {
        Location instanceLoc = getCenter();
        checkAndBuildPortal();
        instanceLoc.getWorld().setChunkForceLoaded(instanceLoc.getBlockX(), instanceLoc.getBlockZ(), true);
        instanceLoc.getWorld().playSound(instanceLoc, Sound.BLOCK_END_PORTAL_SPAWN, 4F, 1.5F);
        portalTimeDisplay = new HashSet<>();
//        portalTimeDisplay = initTextDisplay();
    }

    public void stop() {
        Location instanceLoc = getCenter();
        destroyBuild();
        if (portalLoop != null) portalLoop.cancel();
        instanceLoc.getWorld().setChunkForceLoaded(instanceLoc.getBlockX(), instanceLoc.getBlockZ(), false);
        removePortalTextDisplays();
    }

    private void checkAndBuildPortal() {
        if (!portalBlocks.getFirst().getType().equals(Material.NETHER_PORTAL)) {
            for (Block b : portalBlocks) {
                b.setType(Material.NETHER_PORTAL);
            }
        }
    }

    private void floodFloor(Location location) {
        if (!location.getBlock().getType().isSolid()) {
            floodFloor(location.add(0, -1, 0));
        } else {
            new FloodBlocks(location, 1.10, this).autoFlood(new Random());
        }
    }

    public void portalLoop(int timePassed, int seconds) {
        removePortalTextDisplays();
        for (TextDisplay textDisplay : initTextDisplay()) {
            textDisplay.text(
                    TextUtil.makeText("Portal closes in ", TextUtil.GOLD)
                            .append(TextUtil.makeText(TextUtil.formatTime(seconds - timePassed), TextUtil.AQUA)));
            portalTimeDisplay.add(textDisplay.getUniqueId());
        }

        checkAndBuildPortal();
    }

    private void removePortalTextDisplays() {
        for (UUID textDisplayUUID : new HashSet<>(portalTimeDisplay)) {
            Entity textDisplay = getCenter().getWorld().getEntity(textDisplayUUID);
            if (textDisplay != null) {
                textDisplay.remove();
                portalTimeDisplay.remove(textDisplayUUID);
            }
        }
    }

    private List<TextDisplay> initTextDisplay() {
        Location loc = getCenter();
        Vector v = loc.getDirection();
        int x = Math.abs(v.getX()) < Math.abs(v.getZ()) ? 0 : 2;
        int z = Math.abs(v.getX()) < Math.abs(v.getZ()) ? 2 : 0;
        List<TextDisplay> textDisplays = new ArrayList<>();
        for  (int i = -1; i <= 1; i+=2) {
            TextDisplay textDisplay = DisplayUtil.makeTextDisplay(
                    loc.clone().add(x * i, 1, z * i),
                    List.of(TextUtil.makeText("", TextUtil.GOLD)),
                    0, 0, 200);
            textDisplay.setBillboard(Display.Billboard.CENTER);
            textDisplay.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
            textDisplays.add(textDisplay);
        }
        return textDisplays;
    }

    private void destroyBuild() {
        Set<Block> portalBlocksSet = new HashSet<>(portalBlocks);
        new BukkitRunnable() {
            @Override
            public void run() {
                TextUtil.debugText("[Bingo Turn In] Destroying Build at " + TextUtil.formatLocation(getOrigin()));
                if (portalBlocksSet.isEmpty()) {
                    cancel();
                }

                int i = 10;
                for (Block b : new HashSet<>(portalBlocksSet)) {
                    if (i-- <= 0) {
                        break;
                    }
                    portalBlocksSet.remove(b);

                    b.removeMetadata("UNBREAKABLE", Bingo4.getInstance());
                    b.setType(Material.AIR);
                }

                getOrigin().getWorld().playSound(getCenter(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 3F, 0);
            }
        }.runTaskTimer(Bingo4.getInstance(), 20, 20);
    }

    @Override
    public Map<Material, Integer> getUnFloodables() {
        return Map.of(
                Material.NETHERRACK, 75,
                Material.MAGMA_BLOCK, 20,
                Material.GOLD_BLOCK, 1,
                Material.LAVA, 5,
                Material.OBSIDIAN, 0,
                Material.AIR, 0
        );
    }

    @Override
    public Block changeBlock(Block block, Random r, FloodBlocks floodBlocks) {
        block.setType(floodBlocks.chooseNextMaterial(r));
        block.setMetadata("UNBREAKABLE", new FixedMetadataValue(Bingo4.getInstance(), true));
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
