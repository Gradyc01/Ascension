package org.bingo4.Interfaces;

import net.kyori.adventure.text.Component;
import org.bingo4.Lobby.EventsBoard.Boards.Ascension;
import org.bingo4.Utility.TextUtil;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

public abstract class AscensionBlockGUI extends AscensionGUI implements BlockUI {
    private final static Map<Block, BlockUI> GUIMAP = new HashMap<>();
    private final Block block;
    public AscensionBlockGUI(Block block, int GUILines, Component name) {
        super(null, GUILines, name);
        this.block = block;
        AscensionBlockGUI.registerGUI(this);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    public static void registerGUI(BlockUI gui) {
        GUIMAP.put(gui.getBlock(), gui);
    }

    public static BlockUI findGUI(Block block) {
        return GUIMAP.get(block);
    }

    public static void removeGUI(BlockUI gui) {
        TextUtil.debugText("Removing GUI: " + gui.getBlock().getType().name());
        GUIMAP.remove(gui.getBlock());
    }

}
