
package org.bingo4.Structures.Midboss;

import net.kyori.adventure.audience.Audience;
import org.bingo4.Items.Uncraftable.HadesBook.HadesBook;
import org.bingo4.MainMenuUI.Map.MapItem;
import org.bingo4.MainMenuUI.Map.MapTransmitRange;
import org.bingo4.Mobs.ItemDisplayDetection;
import org.bingo4.Structures.CampInstance;
import org.bingo4.Structures.Interfaces.AbstractCampType;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class MidbossInstance extends CampInstance {

    private WitherBoss witherBoss;
    private MapItem mapItem;
    private Location witherSpawnLocation;
    public MidbossInstance(AbstractCampType type, Location origin, Location center) {
        super(type, origin, center);
        witherBoss = new WitherBoss(this, 300, 150);
    }

    /**
     * Called when the camp structure has finished being placed.
     */
    public void buildFinished(Set<Block> blocks) {
        for (Block block : blocks) {
            if (block.getBlockData().getMaterial().equals(Material.BEACON)) {
                setCenter(block.getLocation());
                witherSpawnLocation = block.getLocation().clone().add(0.5, 2, 0.5);
                break;
            }
        }
        mapItem = new MapItem("Gatekeeper", getCenter(), MapItem.SPAWN, MapTransmitRange.FARTHEST, Color.BLACK);
        plugin.getSettingsUI().getSettings().getTimeline().getMapItems().addMapItem(mapItem);
    }

    public void spawn() {
        witherBoss.spawn(witherSpawnLocation);
        TextUtil.broadcastMessage(TextUtil.makeText("The Gatekeeper has arrived", TextUtil.DARK_PURPLE, true, false));
        SoundUtil.broadcastSound(Sound.ENTITY_WITHER_SPAWN, 2.0F, 0.65F);
    }

    /**
     * Called when the Wither first takes damage.
     */
    public void start() {
        TextUtil.broadcastMessage(TextUtil.makeText("The Gatekeeper has taken damage", TextUtil.DARK_GRAY));
    }

    /**
     * Called when the Wither regenerates back to full health.
     */
    public void stop() {
        Audience audience = Audience.audience(getCenter().getWorld().getNearbyPlayers(getCenter(), 40));
        audience.sendMessage(TextUtil.makeText("The Gatekeeper has healed", TextUtil.DARK_GRAY));
    }

    /**
     * Called when the Wither dies
     */
    public void bossKilled() {
        TextUtil.broadcastMessage(TextUtil.makeText("The Gatekeeper has fallen", TextUtil.DARK_GRAY));
        new ItemDisplayDetection(witherSpawnLocation, HadesBook.getInstance().getResult());
    }

    /**
     * Called when the Wither triggers its powered explosion.
     */
    public void poweredExplosion() {
        SoundUtil.broadcastSound(Sound.ENTITY_RAVAGER_ROAR, 2.0F, 0.7F);
    }
}
