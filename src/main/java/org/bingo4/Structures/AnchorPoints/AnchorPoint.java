
package org.bingo4.Structures.AnchorPoints;

import net.kyori.adventure.text.Component;
import org.bingo4.Bingo4;
import org.bingo4.Effects.TeleportSequence;
import org.bingo4.Player.Cooldowns.CombatTimer;
import org.bingo4.Player.Cooldowns.TeleportCooldown;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Teams.Team;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.HashMap;

public class AnchorPoint {

    private final String name;
    private final Location location;
    private final UUID uuid;
    private final Material material;
    private final PlayerData creator;

    public static final String ANCHOR_POINT = "ANCHOR_POINT";

    private Block chest;

    private final Bingo4 plugin = Bingo4.getInstance();
    private String state;

    public static final String UNINITIALIZED = "uninitialized";
    public static final String CLOSED = "closed";
    public static final String OPEN = "open";

    // All currently active portal sessions.
    private static final Map<UUID, PortalSession> ACTIVE_PORTAL_SESSIONS = new HashMap<>();

    public AnchorPoint(Location location, PlayerData creator) {
        this.location = location.clone();
        this.uuid = UUID.randomUUID();
        this.creator = creator;
        this.material = generateMaterial();
        this.name = generateName(location);
        this.state = UNINITIALIZED;
    }

    public void initializeAnchor() {
        generateChest();
        openAnchor();
        creator.getPlayerTeam().getTeam().getTeamAnchors().addAnchorPoint(this);
    }

    public void openAnchor() {
        state = OPEN;
    }

    public void closeAnchor() {
        state = CLOSED;

        // Interrupt anyone currently browsing this anchor.
        for (PortalSession session : new ArrayList<>(ACTIVE_PORTAL_SESSIONS.values())) {
            if (session.isAssociatedWith(this)) {
                session.finishAt(this);
            }
        }
    }

    public void destroyAnchor(PlayerData playerData) {
        // Close the anchor before destroying it.
        if (!isState(CLOSED)) {
            closeAnchor();
        }

        creator.getPlayerTeam().getTeam().getTeamAnchors().removeAnchorPoint(this);
        destroyChest();

        Team destroyerTeam = playerData.getPlayerTeam().getTeam();

        // Destroying your own team's anchor has no penalty.
        if (Objects.equals(destroyerTeam, getCreatorTeam())) {
            return;
        }

        // Enemy destroyed the anchor.
        Player creatorPlayer = creator.getPlayer();

        if (creatorPlayer == null || !creatorPlayer.isOnline()) {
            return;
        }

        Location destination = location.clone().add(0, 1, 0);

        // Forcefully teleport the creator.
        creatorPlayer.teleport(destination);
    }

    public void enterPortal(PlayerData playerData) {
        Player player = playerData.getPlayer();

        if (player == null || !player.isOnline() || !isState(OPEN)) return;

        // Prevent duplicate sessions.
        if (ACTIVE_PORTAL_SESSIONS.containsKey(player.getUniqueId())) return;

        PortalSession session = new PortalSession(playerData);

        ACTIVE_PORTAL_SESSIONS.put(player.getUniqueId(), session);

        // Initially stand above this anchor's Ender Chest.
        session.teleportTo(this);

        // Check for sneaking every tick.
        session.runTaskTimer(plugin, 0L, 1L);
    }

    private void generateChest() {
        chest = location.getBlock();
        chest.setType(Material.ENDER_CHEST);

        chest.setMetadata(
                ANCHOR_POINT,
                new FixedMetadataValue(plugin, getUUID().toString())
        );

        chest.setMetadata(
                "UNBREAKABLE",
                new FixedMetadataValue(plugin, true)
        );
    }

    private void destroyChest() {
        if (chest == null) return;

        chest.removeMetadata(ANCHOR_POINT, plugin);
        chest.removeMetadata("UNBREAKABLE", plugin);

        if (chest.getType() == Material.ENDER_CHEST) {
            chest.setType(Material.AIR);
        }

        // Fake explosion effects.
        Location destination = chest.getLocation().clone().add(0, 1, 0);
        destination.getWorld().spawnParticle(
                Particle.EXPLOSION,
                destination,
                1
        );

        destination.getWorld().spawnParticle(
                Particle.SMOKE,
                destination,
                25,
                0.5, 0.5, 0.5,
                0.05
        );

        destination.getWorld().playSound(
                destination,
                Sound.ENTITY_GENERIC_EXPLODE,
                1.5f,
                0.8f
        );
    }

    private String generateName(Location location) {
        List<String> strings = new ArrayList<>(List.of(
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4",
                "5", "6", "7", "8", "9", "0"
        ));

        String str = location.getWorld().getBiome(location).getKey().getKey() + " - ";

        Random random = new Random();

        str += strings.get(random.nextInt(strings.size()));
        str += strings.get(random.nextInt(strings.size()));

        return str.toUpperCase();
    }

    private Material generateMaterial() {
        List<Material> materials = List.of(
                Material.GRASS_BLOCK,
                Material.GRAVEL,
                Material.WATER_BUCKET,
                Material.APPLE,
                Material.LAVA_BUCKET,
                Material.ANDESITE,
                Material.STONE,
                Material.CACTUS,
                Material.BONE,
                Material.FURNACE,
                Material.CRAFTING_TABLE
        );

        Random random = new Random();

        return materials.get(random.nextInt(materials.size()));
    }

    public boolean teleport(PlayerData playerData) {
        Player player = playerData.getPlayer();

        if (!Bingo4.getInstance().getGameState().canTeleport(player)) return false;
        if (TeleportCooldown.getInstance().isOnCooldown(player)) return false;
        if (CombatTimer.getInstance().isOnCooldown(player)) return false;

        TeleportCooldown.getInstance().setCooldownTimer(player, 45);

        new TeleportSequence(playerData, location, 30);

        return true;
    }

    // --------------------------------------------------
    // Portal Session
    // --------------------------------------------------

    private class PortalSession extends BukkitRunnable {

        private final PlayerData playerData;
        private final Player player;

        private AnchorPoint selectedAnchor;
        private int ticks = 0;

        private static final int CYCLE_INTERVAL = 20 * 5;

        public PortalSession(PlayerData playerData) {
            this.playerData = playerData;
            this.player = playerData.getPlayer();
            this.selectedAnchor = AnchorPoint.this;

            // Sound effect for entering another realm.
            player.playSound(
                    player.getLocation(),
                    Sound.BLOCK_PORTAL_TRAVEL,
                    0.8f,
                    0.8f
            );
        }

        @Override
        public void run() {
            if (!player.isOnline() || !playerData.checkState(PlayerData.STATE_ALIVE)) {
                finish();
                return;
            }

            if (ticks > 20 && !player.getLocation().getBlock().getType().equals(Material.ENDER_CHEST)) {
                finishAt(selectedAnchor);
                return;
            }

            ticks++;

            if (ticks >= CYCLE_INTERVAL || (player.isSneaking() && ticks > 20)) {
                ticks = 0;
                cycleAnchor();
            }

            if (ticks == 0) return;

            // Update action bar every second.
            if (ticks % 10 == 0) {
                int secondsRemaining = (CYCLE_INTERVAL - ticks + 19) / 20;

                Component message = Component.text(
                        "Next location in " + secondsRemaining +
                                "s: " + selectedAnchor.getName()
                );

                TextUtil.sendActionBar(player, message, 10);
            }

            // Play a ticking sound every half second.
            if (ticks % 10 == 0) {
                float progress = ticks / (float) CYCLE_INTERVAL;

                // Pitch gradually increases from 0.7 to 2.0.
                float pitch = 0.7f + (1.3f * progress);

                player.playSound(
                        player.getLocation(),
                        Sound.BLOCK_NOTE_BLOCK_HAT,
                        0.6f,
                        pitch
                );
            }
        }

        private void cycleAnchor() {
            List<AnchorPoint> anchors = new ArrayList<>(
                    creator.getPlayerTeam()
                            .getTeam()
                            .getTeamAnchors()
                            .getAnchorPoints()
            );

            if (anchors.isEmpty()) return;

            int currentIndex = anchors.indexOf(selectedAnchor);

            int nextIndex = (currentIndex + 1) % anchors.size();

            AnchorPoint nextAnchor = anchors.get(nextIndex);

            teleportTo(nextAnchor);
        }

        private void teleportTo(AnchorPoint anchor) {
            if (!player.isOnline() || !playerData.checkState(PlayerData.STATE_ALIVE)) return;

            selectedAnchor = anchor;

            Location destination = anchor.getLocation().add(0, 1, 0);

            player.teleport(destination);

            player.addPotionEffect(
                    new PotionEffect(
                            PotionEffectType.BLINDNESS,
                            20,
                            0,
                            false,
                            false
                    )
            );

            // Sound effect for teleporting between anchors.
            player.playSound(
                    destination,
                    Sound.ENTITY_ENDERMAN_TELEPORT,
                    0.8f,
                    1.0f
            );

            Location blockLocations = destination.clone();
            blockLocations.getBlock().setType(Material.AIR);
            blockLocations.add(0, 1, 0).getBlock().setType(Material.AIR);
        }

        private boolean isAssociatedWith(AnchorPoint anchor) {
            return selectedAnchor == anchor || AnchorPoint.this == anchor;
        }

        public void finishAt(AnchorPoint anchor) {
            if (isCancelled()) return;

            anchor.getLocation().getWorld().playSound(anchor.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 4F, 2);
            TextUtil.sendActionBar(player, TextUtil.makeText("Exited From Rift at " + anchor.getName(), TextUtil.DARK_PURPLE), 20);
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 5 * 20, 0, false, false, false));

            finish();
        }

        private void finish() {
            cancel();

            ACTIVE_PORTAL_SESSIONS.remove(player.getUniqueId());
        }
    }

    // --------------------------------------------------
    // Getters
    // --------------------------------------------------

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location.clone();
    }

    public UUID getUUID() {
        return uuid;
    }

    public PlayerData getCreator() {
        return creator;
    }

    public Team getCreatorTeam() {
        return creator.getPlayerTeam().getTeam();
    }

    public boolean isState(String state) {
        return state.equals(this.state);
    }

    public Material getMaterial() {
        return material;
    }
}
