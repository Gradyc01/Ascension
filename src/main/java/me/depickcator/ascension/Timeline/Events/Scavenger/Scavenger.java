package me.depickcator.ascension.Timeline.Events.Scavenger;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.SoundUtil;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.EntityInteraction;
import me.depickcator.ascension.Player.PlayerUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class Scavenger extends EntityInteraction {
    private final ScavengerTrades scavengerTrades;
    private Location location;
    private LivingEntity entity;
    private final Ascension plugin;

    public Scavenger() {
        this.plugin = Ascension.getInstance();
        scavengerTrades = new ScavengerTrades();
    }

    public void announceTrades() {
        announcementText(scavengerTrades.generateTrades());
        SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 2f, 0f);
    }

    public void announceSpawnLocation() {
        generateSpawningLocation();
        TextUtil.broadcastMessage(TextUtil.makeText(
                "A Scavenger will arrive at ("
                +  location.getBlockX() +
                ", " +
                location.getBlockZ() +
                ") in 5 minutes",
                TextUtil.AQUA));
        SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 2f, 0f);
    }

    public void spawnInScavenger() {
        forceLoadChunk(true);
        Location loc = new Location(plugin.getWorld(), location.getBlockX() + 0.5, location.getBlockY() + 1, location.getBlockZ() + 0.5);
        createEntity(loc);
        createHut(location);
        addInteraction(entity, this);
        TextUtil.debugText("Loaded Scavenger");
        deSpawnTimer();
    }

    public void deSpawnScavenger() {
        TextUtil.debugText("Unloaded Scavenger");
        entity.remove();
        forceLoadChunk(false);
        removeInteraction(entity);
    }

    private void announcementText(List<Pair<ItemStack, ItemStack>> trades) {
        Component arrow = TextUtil.makeText(" ---> ", TextUtil.YELLOW, true, false);
        TextUtil.broadcastMessage(TextUtil.topBorder(TextUtil.GOLD));
        TextUtil.broadcastMessage(TextUtil.makeText("Scavenger Trades: ", TextUtil.AQUA, true, false));
        for (Pair<ItemStack, ItemStack> trade : trades) {
            TextUtil.broadcastMessage(
                    TextUtil.makeText("    ").
                            append(trade.getLeft().displayName().
                                    append(arrow).
                                    append(trade.getRight().displayName())));
        }
        TextUtil.broadcastMessage(TextUtil.bottomBorder(TextUtil.GOLD));
    }

    private void generateSpawningLocation() {
        final int Radius = 500;
        Random r = new Random();
        int x = (int) Ascension.getSpawn().getX() + r.nextInt(Radius * -1, Radius);
        int z = (int) Ascension.getSpawn().getZ() + r.nextInt(Radius * -1, Radius);
        Block b = plugin.getWorld().getHighestBlockAt(x, z);

        location = new Location(b.getWorld(), x, b.getLocation().getY(), z);
    }

    private void deSpawnTimer() {
        new BukkitRunnable() {
            int MINUTES = 10;
            @Override
            public void run() {
                if (!plugin.getGameState().inGame() || MINUTES <= 0) {
                    deSpawnScavenger();
                    cancel();
                    return;
                }
                MINUTES--;
                TextUtil.debugText("Scavenger Timer Ran");

            }
        }.runTaskTimer(plugin, 0,  60 * 20);
    }

    private void forceLoadChunk(boolean forceLoad) {
        plugin.getWorld().setChunkForceLoaded(
                (int) Math.floor((double) location.getBlockX() /16),
                (int) Math.floor((double) location.getBlockZ() /16),
                forceLoad);
    }

    private void createEntity(Location loc) {
//        wanderingTrader = (WanderingTrader)
        Entity e = plugin.getWorld().spawnEntity(loc, EntityType.WARDEN);
        LivingEntity livingEntity = (LivingEntity) e;
        livingEntity.setCustomNameVisible(true);
        livingEntity.customName(TextUtil.makeText("Timmy the Warden", TextUtil.GRAY));
        livingEntity.setAI(false);
        livingEntity.setSilent(true);
        livingEntity.setPersistent(true);
        livingEntity.setInvulnerable(true);
        entity = livingEntity;
    }

    private void createHut(Location loc) {
        fillBlock(loc, -3, 0, -2, 3, 0, 2, Material.DARK_OAK_PLANKS);
        fillBlock(loc, -3 , 0, -2, -3, 3, -2, Material.DARK_OAK_FENCE);
        fillBlock(loc, 3 , 0, 2, 3, 3, 2, Material.DARK_OAK_FENCE);
        fillBlock(loc, -3 , 0, 2, -3, 3, 2, Material.DARK_OAK_FENCE);
        fillBlock(loc, 3 , 0, -2, 3, 3, -2, Material.DARK_OAK_FENCE);
        fillBlock(loc, -3, 4, -2, 3, 4, 2, Material.DARK_OAK_SLAB);
    }

    private void fillBlock(Location loc, int x1, int y1, int z1, int x2, int y2, int z2, Material material) {
        int blockX = loc.getBlockX();
        int blockY = loc.getBlockY();
        int blockZ = loc.getBlockZ();
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    Block block = loc.getWorld().getBlockAt(blockX + x, blockY + y, blockZ + z);
                    block.setType(material);
                    block.setMetadata("UNBREAKABLE", new FixedMetadataValue(plugin, true));
                }
            }
        }
    }

    // private void setBlock(Location loc, int x1, int y1, int z1, Material material) {
    //     fillBlock(loc, x1, y1, z1, x1, y1, z1, material);
    // }


    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        new ScavengerGUI(this, PlayerUtil.getPlayerData(event.getPlayer()), true);
        return true;
    }

    public ScavengerTrades getScavengerTrades() {
        return scavengerTrades;
    }
}
