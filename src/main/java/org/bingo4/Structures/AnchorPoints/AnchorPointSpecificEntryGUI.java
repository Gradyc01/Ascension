
package org.bingo4.Structures.AnchorPoints;

import org.bingo4.Interfaces.AscensionMenuGUI;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Teams.TeamAnchors;
import org.bingo4.Utility.ItemComparison;
import org.bingo4.Utility.TextUtil;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnchorPointSpecificEntryGUI extends AscensionMenuGUI {

    private final Map<ItemStack, AnchorPoint> anchorPoints;
    private final TeamAnchors teamAnchors;
    private final AnchorPoint startingPoint;

    private final List<AnchorPoint> availableAnchorPoints;
    private int page;

    private static final int ITEMS_PER_PAGE = 5;

    public AnchorPointSpecificEntryGUI(PlayerData playerData, AnchorPoint startingPoint, int page) {
        super(playerData, 3, TextUtil.makeText("Anchor Points", TextUtil.AQUA), true);

        this.anchorPoints = new HashMap<>();
        this.startingPoint = startingPoint;
        this.teamAnchors = playerData.getPlayerTeam().getTeam().getTeamAnchors();
        this.availableAnchorPoints = teamAnchors.getAnchorPoints();
        this.page = Math.max(0, page);

        playerHeadButton(22);

        updateGUI();
    }

    public AnchorPointSpecificEntryGUI(PlayerData playerData, AnchorPoint startingPoint) {
        this(playerData, startingPoint, 0);
    }

    private void updateGUI() {
        anchorPoints.clear();

        // Clear existing inventory items before rendering the new page.
        inventory.clear();

        int startIndex = page * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, availableAnchorPoints.size());

        if (startIndex >= availableAnchorPoints.size() && page > 0) {
            page--;
            updateGUI();
            return;
        }

        // Display five anchor points per page.
        int guiIndex = 11;
        for (int i = startIndex; i < endIndex; i++) {
            AnchorPoint anchorPoint = availableAnchorPoints.get(i);

            ItemStack item = initExplainerItem(anchorPoint.getMaterial(), List.of(), TextUtil.makeText(anchorPoint.getName(), TextUtil.AQUA));

            if (item != null) {
                inventory.setItem(guiIndex++, item);
                anchorPoints.put(item, anchorPoint);
            }
        }

        // Navigation buttons.
        if (page > 0) {
            inventory.setItem(10, previousPageItem());
        }

        if (endIndex < availableAnchorPoints.size()) {
            inventory.setItem(16, nextPageItem());
        }
    }


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();

        if (item == null) return;

        // Check if an anchor point was clicked.
        AnchorPoint selectedAnchor = anchorPoints.get(item);

        if (selectedAnchor != null) {
            startingPoint.enterPortal(playerData, selectedAnchor);
            return;
        }

        // Next page
        if (item.isSimilar(nextPageItem())) {
            new AnchorPointSpecificEntryGUI(playerData, startingPoint, page + 1);
            return;
        }

        // Previous page
        if (item.isSimilar(previousPageItem())) {
            new AnchorPointSpecificEntryGUI(playerData, startingPoint, page - 1);
        }
    }

}
