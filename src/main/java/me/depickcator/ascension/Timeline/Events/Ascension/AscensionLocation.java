package me.depickcator.ascension.Timeline.Events.Ascension;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.SoundUtil;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.EntityInteraction;
import me.depickcator.ascension.MainMenu.Map.MapItem;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Teams.Team;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
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
    private final MapItem mapItem;

    public AscensionLocation(int x, int z, AscensionEvent event) {
        this.event = event;
        plugin = Ascension.getInstance();
        this.spawnLocation = findLocation(x, z);
        forceLoadChunk(true);
        this.pillarLocations = new ArrayList<>();
        entity = spawnEntity();
        mapItem = new MapItem("Ascension", x, z, MapItem.ASCENSION);
        plugin.getTimeline().getMapItems().addMapItem(mapItem);
        addInteraction(entity, this);
    }

    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        PlayerData pD = PlayerUtil.getPlayerData(p);
        TextUtil.debugText("Right Clicked a gatekeeper");
        if (!this.event.canStartEvent(pD)) {
            p.sendMessage(TextUtil.makeText("Go away! I can't help you", TextUtil.DARK_GRAY));
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
        plugin.getWorld().strikeLightningEffect(spawnLocation);
        plugin.getWorld().playSound(spawnLocation, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 3, 1);
        plugin.getWorld().playSound(spawnLocation, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 3, 0);
        entity.teleport(new Location(spawnLocation.getWorld(), spawnLocation.getX(), spawnLocation.getY() + 10, spawnLocation.getZ()));
        entity.setVelocity(new Vector(0, 1, 0));
        // Wither w = (Wither) entity;
        plugin.getServer().broadcast(TextUtil.makeText("The ascension wither activate"));
        startText();
    }

    public void closeLocation() {
        plugin.getTimeline().getMapItems().removeMapItem(mapItem);
        entity.remove();
        forceLoadChunk(false);
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
