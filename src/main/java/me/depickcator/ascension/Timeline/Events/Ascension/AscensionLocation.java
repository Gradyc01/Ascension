package me.depickcator.ascension.Timeline.Events.Ascension;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Timeline.Timeline;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.EntityInteraction;
import me.depickcator.ascension.MainMenuUI.Map.MapItem;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Timeline.Events.Ascension.BuildLayers.AscensionBuildLayers;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

public class AscensionLocation extends EntityInteraction {
    private final Location spawnLocation;
//    private final List<Location> pillarLocations;
    private final LivingEntity entity;
    private final Ascension plugin;
    private final AscensionEvent event;
    private Team ascendingTeam;
    private MapItem mapItem;
    private final AscensionBuildLayers buildLayers;
    private final Timeline timeline;

    private int timeTillSummon;

    public AscensionLocation(int x, int z, AscensionEvent event) {
        this.event = event;
        plugin = Ascension.getInstance();
        timeline = plugin.getSettingsUI().getSettings().getTimeline();
        this.spawnLocation = findLocation(x, z);
        this.spawnLocation.add(0.49, 0, 0.49);
        buildLayers = new AscensionBuildLayers(spawnLocation);
        forceLoadChunk(true);
        buildLayers.buildInitialLayer();
        entity = spawnEntity();
        mapItem = new MapItem("Ascension", x, z, MapItem.ASCENSION);
        timeline.getMapItems().addMapItem(mapItem);
        addInteraction(entity, this);
        timeTillSummon = 0;
    }

    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        PlayerData pD = PlayerUtil.getPlayerData(p);
        TextUtil.debugText(p.getName() + " Right Clicked a gatekeeper");
        if (!this.event.canStartEvent(pD)) {
            return false;
        }
        removeInteraction(entity);
        ascendingTeam = pD.getPlayerTeam().getTeam();
        p.getInventory().getItemInMainHand().setAmount(0);
        this.event.start(this);
        return true;
    }

    public void startAnimation() {
        entity.setInvulnerable(false);
        entity.setSilent(false);
        plugin.getWorld().strikeLightningEffect(spawnLocation);
        plugin.getWorld().playSound(spawnLocation, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 3, 1);
        plugin.getWorld().playSound(spawnLocation, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 3, 0);
        entity.teleport(spawnLocation.clone().add(0, 10, 0));
        entity.setVelocity(new Vector(0, 1, 0));
        // Wither w = (Wither) entity;
        startText();
        changeToActiveAscension();
        buildLayers.buildPillars(entity.getLocation().getBlockY());
    }

    public void closeLocation() {
        resetBorder();
        timeline.getMapItems().removeMapItem(mapItem);
        entity.remove();
        buildLayers.destroyCrystals();
        forceLoadChunk(false);
    }

    public void updateBossBarHealth() {
        Wither wither = (Wither) entity;
        BossBar bossBar = wither.getBossBar();
        bossBar.setProgress(wither.getHealth() / wither.getAttribute(Attribute.MAX_HEALTH).getBaseValue());
    }

    public void update() {
        if (timeTillSummon < 30) {
            if (timeTillSummon % 15 == 0 || timeTillSummon >= 25) {
                TextUtil.broadcastMessage(
                        TextUtil.makeText("All players will be summoned to Ascension in " + (30 - timeTillSummon) + " seconds",
                                TextUtil.YELLOW));
                if (timeTillSummon != 0) {
                    SoundUtil.broadcastSound(Sound.UI_BUTTON_CLICK, 100, 1);
                }
            }
            timeTillSummon++;
        } else if (timeTillSummon == 30) {
            closeBorderAroundLocation();
            timeTillSummon++;
        }

    }

    private void resetBorder() {
        WorldBorder border = spawnLocation.getWorld().getWorldBorder();
        TextUtil.debugText("Resetting Border");
        double oldBorderSize = border.getSize();
        int originalBorderRadius = plugin.getSettingsUI().getSettings().getWorldBorderSize();
        border.setSize(originalBorderRadius * 2 + oldBorderSize);
        border.setCenter(Ascension.getSpawn());
        new BukkitRunnable() {
            @Override
            public void run() {
                border.setSize(originalBorderRadius * 2, (long) oldBorderSize/3);
            }
        }.runTaskLater(plugin, 30);
    }

    private void closeBorderAroundLocation() {
        TextUtil.debugText("Closing Border Around Location");
        WorldBorder border = spawnLocation.getWorld().getWorldBorder();
        List<PlayerData> players = PlayerUtil.getAllPlayingPlayers();
        int newBorderRadius = Math.min(Math.max(250, players.size() * 50), 600);
        teleportPlayers(players, newBorderRadius);
        new BukkitRunnable() {
            @Override
            public void run() {
                border.setCenter(spawnLocation);
                border.setSize(newBorderRadius * 2);
            }
        }.runTaskLater(plugin, 30);

    }

    private void teleportPlayers(List<PlayerData> players, int newBorderRadius) {
        Location center = spawnLocation;
        Random r = new Random();
        for (PlayerData pD : players) {
            Player p = pD.getPlayer();
            if (Math.abs(p.getX() - center.getX()) >= newBorderRadius ||
                    Math.abs(p.getZ() - center.getZ()) >= newBorderRadius) {
                Location pLoc = p.getLocation().clone();
                double newX = p.getX() < center.getX() ?
                        Math.max(-newBorderRadius + center.getX() + r.nextInt(10, 100), p.getX()) :
                        Math.min(newBorderRadius + center.getX() - r.nextInt(10, 100), p.getX());
                double newZ = p.getZ() < center.getZ() ?
                        Math.max(-newBorderRadius + center.getZ() + r.nextInt(10, 100), p.getZ()) :
                        Math.min(newBorderRadius + center.getZ() - r.nextInt(10, 100), p.getZ());
                pLoc.setY(center.getWorld().getHighestBlockYAt((int) newX,(int) newZ) + 1);
                pLoc.setX(newX);
                pLoc.setZ(newZ);
                p.teleport(pLoc);
                TextUtil.debugText("Teleporting " + p.getName() + " to (" + newX + ", " + newZ + ")");
            }
        }
    }

    private void changeToActiveAscension() {
        int x = mapItem.getCoords().getLeft();
        int z = mapItem.getCoords().getRight();
        timeline.getMapItems().removeMapItem(mapItem);
        mapItem = new MapItem("Active Ascension", x, z, MapItem.ACTIVE_ASCENSION);
        timeline.getMapItems().addMapItem(mapItem);
    }

    private void startText() {
//        plugin.getServer()
        Component text = TextUtil.topBorder(TextUtil.DARK_GRAY);
        text = text.append(TextUtil.makeText("\n        ASCENSION STARTED\n", TextUtil.WHITE));
        text = text.append(TextUtil.makeText("    " + ascendingTeam.getLeader().getName() + "'s Team has started Ascension at", TextUtil.DARK_GRAY));
        text = text.append(TextUtil.makeText("\n                 (" + spawnLocation.getBlockX() + ", " + spawnLocation.getBlockZ() + ")", TextUtil.DARK_GRAY));
        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GRAY));
        plugin.getServer().broadcast(text);
        TextUtil.broadcastTitle(TextUtil.makeTitle(TextUtil.makeText("ASCENSION STARTED", TextUtil.YELLOW), 3, 5, 2));
        SoundUtil.broadcastSound(Sound.ENTITY_WITHER_DEATH, 20f, 0.7);
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
//        Entity e = plugin.getWorld().spawnEntity(spawnLocation, EntityType.WITHER);
        Entity e = new GatekeeperWither(spawnLocation).getBukkitEntity();
        LivingEntity livingEntity = (LivingEntity) e;
        livingEntity.setCustomNameVisible(true);
        livingEntity.customName(TextUtil.makeText("Gatekeeper", TextUtil.GRAY));
        livingEntity.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0);
//        livingEntity.setAI(false);
        livingEntity.getAttribute(Attribute.MAX_HEALTH).setBaseValue(250);
        livingEntity.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(20);
        livingEntity.setHealth(250);
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
