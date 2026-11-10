package org.bingo4.Structures.BingoTurnIn;

import net.kyori.adventure.audience.Audience;
import org.bingo4.Bingo4;
import org.bingo4.MainMenuUI.Map.MapItem;
import org.bingo4.MainMenuUI.Map.MapItems;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Structures.CampInstance;
import org.bingo4.Structures.Interfaces.AbstractCampType;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.bingo4.MainMenuUI.Map.MapItem.BINGO_TURN_IN;

public class BingoTurnInInstance extends CampInstance {

    private final BingoTurnInNPC bingoTurnInNPC;
    private final BingoTurnInProgress bingoTurnInProgress;
    private ItemStack bingoItem;
    private Set<Block> blocks;
    private MapItem mapItem;

    public BingoTurnInInstance(AbstractCampType type, Location origin, Location center) {
        super(type, origin, center);
        bingoTurnInNPC = new BingoTurnInNPC(this);
        bingoTurnInProgress = new BingoTurnInProgress(this);
    }

    public void buildFinished(Set<Block> blocks) {
        for (Block block : blocks) {
            if (block.getBlockData().getMaterial().equals(Material.BEACON)) {
                setCenter(block.getLocation());
                break;
            }
        }
        bingoTurnInNPC.spawn(getCenter().clone().add(0, 0, 0));
        mapItem = new MapItem("Altar", getCenter(), BINGO_TURN_IN);

        MapItems mapItems = plugin.getSettingsUI().getSettings().getTimeline().getMapItems();
        mapItems.addMapItem(mapItem);
        this.blocks = blocks;
    }

    public void start(PlayerData playerData, ItemStack item, BoundingBox boundingBox) {
        Location instanceLoc = getCenter();
        Collection<Player> players = instanceLoc.getNearbyPlayers(64);
        instanceLoc.getWorld().playSound(instanceLoc, Sound.BLOCK_END_PORTAL_SPAWN, 4F, 1.2F);
        Audience.audience(players).sendMessage(TextUtil.makeText("A nearby player is attempting to secure an item!", TextUtil.DARK_GRAY));
        bingoTurnInProgress.start(playerData, boundingBox, new ItemStack(item));
        bingoTurnInNPC.start(new ItemStack(item));
        bingoItem = new ItemStack(item);
        item.setAmount(item.getAmount() - 1);
    }

    public void stop() {
        bingoTurnInNPC.stop();
        destroyBuild();
        MapItems mapItems = plugin.getSettingsUI().getSettings().getTimeline().getMapItems();
        mapItems.removeMapItem(mapItem);
        plugin.getSettingsUI().getSettings().getTimeline().getCampManager().removeCamp(this);
    }

    private void destroyBuild() {
        new BukkitRunnable() {

            @Override
            public void run() {
                TextUtil.debugText("[Bingo Turn In] Destroying Build at " + TextUtil.formatLocation(getOrigin()));
                if (blocks.isEmpty()) {
                    cancel();
                    return;
                }

                int i = 10;
                for (Block b : new HashSet<>(blocks)) {
                    if (i-- <= 0) {
                        break;
                    }
                    blocks.remove(b);

                    b.removeMetadata("UNBREAKABLE", Bingo4.getInstance());
                    b.setType(Material.AIR);
                }

                getOrigin().getWorld().playSound(getCenter(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 3F, 0);
            }
        }.runTaskTimer(Bingo4.getInstance(), 5 * 20, 20);
    }

    public BingoTurnInProgress getBingoTurnInProgress() {
        return bingoTurnInProgress;
    }

    public ItemStack getBingoItem() {
        return bingoItem;
    }
}
