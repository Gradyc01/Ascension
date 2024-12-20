package me.depickcator.ascension.Timeline.Events.Ascension;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.EntityInteraction;
import me.depickcator.ascension.Player.PlayerData;
import me.depickcator.ascension.Player.PlayerUtil;
import me.depickcator.ascension.Teams.Team;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class AscensionLocation extends EntityInteraction {
    private final Location spawnLocation;
    private final List<Location> pillarLocations;
    private final LivingEntity entity;
    private final Ascension plugin;
    private final AscensionEvent event;
    private Team ascendingTeam;

    public AscensionLocation(int x, int z, AscensionEvent event) {
        this.event = event;
        plugin = Ascension.getInstance();
        this.spawnLocation = findLocation(x, z);
        this.pillarLocations = new ArrayList<>();
        entity = spawnEntity();
        addInteraction(entity, this);
    }

    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        PlayerData pD = PlayerUtil.getPlayerData(p);
        if (!this.event.canStartEvent(pD)) {
            p.sendMessage(TextUtil.makeText("Go away! I can't help you", TextUtil.DARK_GRAY));
            return false;
        }
        removeInteraction(entity);
        ascendingTeam = pD.getPlayerTeam().getTeam();
        this.event.start(this);
        return true;
    }

    public void startAnimation() {
        entity.setInvulnerable(false);
//        entity.setAI(true);
//        entity.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0);
//        entity.getAttribute(Attribute.FLYING_SPEED).setBaseValue(0);
//        entity.getAttribute(Attribute.KNOCKBACK_RESISTANCE).setBaseValue(1);

        plugin.getWorld().strikeLightningEffect(spawnLocation);
        plugin.getWorld().playSound(spawnLocation, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 3, 1);
        plugin.getWorld().playSound(spawnLocation, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 3, 0);
        entity.teleport(new Location(spawnLocation.getWorld(), spawnLocation.getX(), spawnLocation.getY() + 10, spawnLocation.getZ()));
        entity.setVelocity(new Vector(0, 1, 0));
        Wither w = (Wither) entity;
        plugin.getServer().broadcast(TextUtil.makeText("The ascension wither activate"));

    }

    private void forceLoadChunk(boolean forceLoad) {
        plugin.getWorld().setChunkForceLoaded(
                (int) Math.floor((double) spawnLocation.getBlockX() /16),
                (int) Math.floor((double) spawnLocation.getBlockZ() /16),
                forceLoad);
    }

    private Location findLocation(int x, int z) {
        int y = plugin.getWorld().getHighestBlockYAt(x, z);
        return new Location(plugin.getWorld(), x, y + 1, z);
    }

    private LivingEntity spawnEntity() {
//        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
//
//        protocolManager.addPacketListener(new PacketAdapter(this, com.comphenix.protocol.PacketType.Play.Server.ENTITY_METADATA) {
//            @Override
//            public void onPacketSending(PacketEvent event) {
//                // Check if the packet is related to a Wither
//                WrappedDataWatcher watcher = new WrappedDataWatcher(event.getPacket().getWatchableCollectionModifier().read(0));
//                if (watcher != null && watcher.getEntity(event.getPlayer()) instanceof Wither) {
//                    event.setCancelled(true); // Prevent the boss bar from being displayed
//                }
//            }
//        });

        Entity e = plugin.getWorld().spawnEntity(spawnLocation, EntityType.WITHER);
        LivingEntity livingEntity = (LivingEntity) e;
        livingEntity.setCustomNameVisible(true);
        livingEntity.customName(TextUtil.makeText("Gatekeeper", TextUtil.GRAY));
        livingEntity.setAI(false);
        livingEntity.setSilent(true);
        livingEntity.setPersistent(true);
        livingEntity.setInvulnerable(true);
        return livingEntity;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public Team getAscendingTeam() {
        return ascendingTeam;
    }
}
