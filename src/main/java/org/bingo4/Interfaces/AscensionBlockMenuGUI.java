package org.bingo4.Interfaces;

import net.kyori.adventure.text.Component;
import org.bingo4.Player.Data.PlayerData;
import org.bukkit.block.Block;

public abstract class AscensionBlockMenuGUI extends AscensionMenuGUI implements BlockUI {
    private final String KEY;
    private final Block block;
    public AscensionBlockMenuGUI(PlayerData playerData, Block block, int GUILines, Component name, boolean setBackground) {
        super(playerData, GUILines, name, setBackground, false);
        this.KEY = "";
        this.block = block;
        AscensionBlockGUI.registerGUI(this);
    }

    @Override
    public Block getBlock() {
        return block;
    }
}