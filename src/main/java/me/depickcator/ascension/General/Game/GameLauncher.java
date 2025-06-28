package me.depickcator.ascension.General.Game;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public abstract class GameLauncher {
    protected final Ascension plugin;
    protected final List<GameSequences> sequence;
    protected final Settings settings;
    public GameLauncher() {
        plugin = Ascension.getInstance();
        settings = plugin.getSettingsUI().getSettings();
        this.sequence = new ArrayList<>();
//        sequence = new ArrayList<>(settings.getSequence());

    }

    protected abstract List<GameSequences> initSequence();

    public void start() {
        if (canStart()) {
            this.sequence.clear();
            this.sequence.addAll(initSequence());
            loop();
        }
    }

    protected abstract boolean canStart();

//    public abstract void start();

    private void loop() {
        if (sequence.isEmpty()) {
            TextUtil.debugText("GameLauncher: Sequences Finished");
            end();
            return;
        }
        GameSequences seq = sequence.getFirst();
        seq.run(this);
        TextUtil.debugText("GameLauncher: Ran Next Sequence");
        sequence.removeFirst();
    }


    protected abstract void end();


    /* What each GameStartSequence should call at the end of their run
    * Delays the next sequence by delay ticks
    * delay > 0*/
    public void callback(int delay) {
        TextUtil.debugText("GameLauncher: Callback called");
        new BukkitRunnable() {
            public void run() {
                loop();
            }
        }.runTaskLater(Ascension.getInstance(), delay);
    }

    public void callback() {
        callback(40);
    }
}
