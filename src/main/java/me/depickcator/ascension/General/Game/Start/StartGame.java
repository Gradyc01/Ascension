package me.depickcator.ascension.General.Game.Start;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class StartGame extends GameLauncher {
//    private final Ascension plugin;
//    private final List<GameStartSequence> sequence;
//    private final Settings settings;
    private List<Location> spawnCages;
    private List<Entity> cageItemDisplay;
    public StartGame() {
//        plugin = Ascension.getInstance();
//        settings = plugin.getSettingsUI().getSettings();
//        sequence = new ArrayList<>(settings.getSequence());
        super();
        start();
    }

    @Override
    protected List<GameSequences> initSequence() {
        return settings.getSequence();
    }

    @Override
    protected boolean canStart() {
        if (plugin.getLocationCheck().isCheckCompleted()) {
            plugin.getGameState().setCurrentState(GameStates.GAME_LOADING);
            return true;
        } else {
            TextUtil.broadcastMessage(TextUtil.makeText("Failed to Start Game: Location Check is incomplete", TextUtil.DARK_RED));
            SoundUtil.broadcastSound(Sound.ENTITY_PLAYER_TELEPORT, 10f, 0);
            return false;
        }
    }

//    @Override
//    public void start() {
//        if (plugin.getLocationCheck().isCheckCompleted()) {
//            plugin.getGameState().setCurrentState(GameStates.GAME_LOADING);
//            loop();
//        } else {
//            TextUtil.broadcastMessage(TextUtil.makeText("Failed to Start Game: Location Check is incomplete", TextUtil.DARK_RED));
//            SoundUtil.broadcastSound(Sound.ENTITY_PLAYER_TELEPORT, 10f, 0);
//        }
//    }


//    private void loop() {
//        if (sequence.isEmpty()) {
//            end();
//            return;
//        }
//        GameStartSequence seq = sequence.getFirst();
//        seq.run(this);
//        TextUtil.debugText("StartGame: Ran Next Sequence");
//        sequence.removeFirst();
//    }

    @Override
    protected void end() {
        plugin.getGameState().setCurrentState(GameStates.GAME_BEFORE_GRACE);
        settings.getTimeline().startTimeline();
        int gracePeriodDuration = settings.getTimeline().getNextBigEvent().getRight();
        for (PlayerData pD : PlayerUtil.getAllPlayingPlayers()) {
            pD.resetAfterStartGame(gracePeriodDuration);
        }
        breakFloors();
        deleteCageDisplays();
    }

    private void breakFloors() {
        Material mat = Material.AIR;
        for (Location loc : spawnCages) {
            World world = loc.getWorld();
            int x1 = loc.getBlockX() + 1;
            int y = loc.getBlockY() + 100;
            int z1 = loc.getBlockZ() + 1;

            int x2 = x1 + 4;
            int z2 = z1 + 4;

            for (int x = x1; x <= x2; x++) {
                for (int z = z1; z <= z2; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    block.setType(mat);
                }
            }
        }
    }

    private void deleteCageDisplays() {

        new BukkitRunnable() {
            @Override
            public void run() {
                if (cageItemDisplay.isEmpty()) {
                    cancel();
                    TextUtil.debugText("All Cage Item Displays successfully removed");
                    return;
                }

                Entity e =cageItemDisplay.getFirst();
                cageItemDisplay.removeFirst();
                e.remove();
            }
        }.runTaskTimer(Ascension.getInstance(), 20L, 2L);

    }

    /* What each GameStartSequence should call at the end of their run */
//    public void callback() {
//        new BukkitRunnable() {
//            public void run() {
//                loop();
//            }
//        }.runTaskLater(Ascension.getInstance(), 40);
//    }

    public List<Location> getSpawnCages() {
        return spawnCages;
    }

    public void setSpawnCages(List<Location> spawnCages) {
        this.spawnCages = spawnCages;
    }

    public List<Entity> getCageItemDisplay() {
        return cageItemDisplay;
    }

    public void setCageItemDisplay(List<Entity> cageItemDisplay) {
        this.cageItemDisplay = cageItemDisplay;
    }
}
