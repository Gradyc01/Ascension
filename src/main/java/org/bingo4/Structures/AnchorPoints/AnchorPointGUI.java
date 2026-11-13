package org.bingo4.Structures.AnchorPoints;

import org.bingo4.Interfaces.AscensionMenuGUI;
import org.bingo4.Player.Cooldowns.AnchorPointCooldown;
import org.bingo4.Teams.TeamAnchors;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class AnchorPointGUI extends AscensionMenuGUI {
    private final Map<ItemStack, AnchorPoint> anchorPoints;
    private final ItemStack createAnchorPointItem;
    private final TeamAnchors teamAnchors;
    private final Location blockLoc;
    public AnchorPointGUI(PlayerData playerData, Location attemptedBlockLocation) {
        super(playerData, 3, TextUtil.makeText("Anchor Points", TextUtil.AQUA), true);
        this.anchorPoints = new HashMap<>();
        this.blockLoc = attemptedBlockLocation;
        teamAnchors = playerData.getPlayerTeam().getTeam().getTeamAnchors();
        playerHeadButton(22);
        createAnchorPointItem = AnchorPointCooldown.getInstance().makeItem();
        inventory.setItem(13, createAnchorPointItem);
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (item.equals(createAnchorPointItem)) {
            player.closeInventory();
            new CreateAnchorPointAnimation(playerData.getPlayerTeam().getTeam(), new AnchorPoint(blockLoc, playerData));
        }
    }
}
