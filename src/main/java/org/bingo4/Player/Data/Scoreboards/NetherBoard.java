package org.bingo4.Player.Data.Scoreboards;

import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Timeline.Events.NetherManager.NetherManager;
import org.bingo4.Utility.TextUtil;
import org.bukkit.scoreboard.Objective;

public class NetherBoard extends GameBoard{
    private final NetherManager netherManager;
    public NetherBoard(Objective board, PlayerData playerData, NetherManager manager) {
        super(board, playerData);
        this.netherManager = manager;
    }

    @Override
    public void updateTime() {
        editLine(board, 13, TextUtil.makeText("  " + "Nether Closes In:  ", TextUtil.GOLD));
        editLine(board, 12, TextUtil.makeText("       " + TextUtil.formatTime(netherManager.getTimeRemaining())));
    }

}
