package org.bingo4.Structures.MiningNode;

import net.kyori.adventure.text.Component;
import org.bingo4.Interfaces.AscensionBlockGUI;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MiningNodeBlockGUI extends AscensionBlockGUI {

    private final MiningNodeInstance instance;

    public MiningNodeBlockGUI(Block block, MiningNodeInstance instance) {
        super(block, 6, TextUtil.makeText("Pain"));
        this.instance = instance;

    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        //Purposefully nothing
    }

    @Override
    public boolean interactWithBlock(PlayerData playerData, Block block, PlayerInteractEvent event) {
        block.getLocation().getWorld().playSound(block.getLocation(), Sound.AMBIENT_CAVE, 5f, 2f);
        playerData.getPlayer().sendMessage(TextUtil.makeText("You've activated a Mining Probe. Watch out as it may draw unwanted attention!", TextUtil.DARK_GREEN));
        block.setType(Material.IRON_BARS);
        instance.start();
        removeGUI(this);
        return true;
    }
}
