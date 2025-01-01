package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Timeline.Events.Ascension.BuildLayers.AscensionBuildLayers;
import me.depickcator.ascension.Timeline.Timeline;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class setTimeline implements CommandExecutor {
    private Timeline timeline;
    public setTimeline() {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
        PlayerData playerData = PlayerUtil.getPlayerData(p);
        if (playerData == null) return false;
        this.timeline = Ascension.getInstance().getSettingsUI().getSettings().getTimeline();

        if (strings.length != 2) return false;
        String mode = strings[0];
        int time = Integer.parseInt(strings[1]);
        if (mode.equals("set")) {
            timeline.setTime(time);
        } else if (mode.equals("run")) {
            timeline.pauseTimeline();
            timeline.setTime(time);
            timeline.startTimeline();
        }
        if (mode.equals("build")) {
            AscensionBuildLayers a = new AscensionBuildLayers(p.getLocation());
            a.buildInitialLayer();
        }
        return false;
    }
}
