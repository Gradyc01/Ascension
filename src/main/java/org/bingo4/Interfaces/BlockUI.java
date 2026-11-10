package org.bingo4.Interfaces;

import org.bingo4.Player.Data.PlayerData;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;

public interface BlockUI {
    boolean interactWithBlock(PlayerData playerData, Block block, PlayerInteractEvent event);
    Block getBlock();

}
