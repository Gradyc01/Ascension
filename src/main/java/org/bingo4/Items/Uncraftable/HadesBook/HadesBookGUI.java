
package org.bingo4.Items.Uncraftable.HadesBook;

import org.bingo4.Interfaces.AscensionMenuGUI;
import org.bingo4.MainMenuUI.BingoBoard.BingoSlot;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class HadesBookGUI extends AscensionMenuGUI {

    private static final int REQUIRED_SELECTIONS = 3;
    private static final int CONFIRM_SLOT = 53;

    private static final Material INVALID_MATERIAL = Material.BLACK_STAINED_GLASS_PANE;
    private static final Material SELECTED_MATERIAL = Material.LIME_STAINED_GLASS_PANE;
    private static final Material CONFIRM_MATERIAL = Material.LIME_CONCRETE;

    private final List<BingoSlot> bingoSlots;

    // Inventory slot -> BingoSlot
    private final Map<Integer, BingoSlot> board = new LinkedHashMap<>();

    // Inventory slot -> Board coordinates
    private final Map<Integer, int[]> coordinates = new HashMap<>();

    // Selected inventory slots
    private final Set<Integer> selectedSlots = new LinkedHashSet<>();

    // Inventory slots that cannot currently be selected
    private final Set<Integer> invalidSlots = new HashSet<>();

    public HadesBookGUI(PlayerData playerData) {
        super(playerData, 6, TextUtil.makeText("Select 3 Slots", TextUtil.AQUA), true);

        bingoSlots = plugin.getBingoData().getBingoSlots();

        loadBingoBoard();
        updateBoard();
    }

    private void loadBingoBoard() {

        int index = 2;

        for (int i = 0; i < bingoSlots.size(); i++) {

            int row = i / 5;
            int col = i % 5;

            BingoSlot slot = bingoSlots.get(i);

            board.put(index, slot);
            coordinates.put(index, new int[]{row, col});

            index++;

            // Skip the remaining inventory slots to reach
            // the beginning of the next bingo row.
            if (col == 4) {
                index += 4;
            }
        }
    }

    /**
     * Recalculates invalid selections and refreshes the GUI.
     */
    private void updateBoard() {

        invalidSlots.clear();

        // Determine which board positions are invalid.
        for (int selected : selectedSlots) {

            int[] selectedCoordinate = coordinates.get(selected);

            int selectedRow = selectedCoordinate[0];
            int selectedCol = selectedCoordinate[1];

            for (Map.Entry<Integer, int[]> entry : coordinates.entrySet()) {

                int inventorySlot = entry.getKey();

                if (selectedSlots.contains(inventorySlot)) {
                    continue;
                }

                int row = entry.getValue()[0];
                int col = entry.getValue()[1];

                if (sharesBingoLine(selectedRow, selectedCol, row, col)) {
                    invalidSlots.add(inventorySlot);
                }
            }
        }

        // Render the board.
        for (Map.Entry<Integer, BingoSlot> entry : board.entrySet()) {

            int inventorySlot = entry.getKey();
            ItemStack originalItem = entry.getValue().getItem();

            if (selectedSlots.contains(inventorySlot)) {
                inventory.setItem(inventorySlot,
                        createIndicator(SELECTED_MATERIAL, "Selected"));
            } else if (invalidSlots.contains(inventorySlot) || entry.getValue().isLocked()) {
                inventory.setItem(inventorySlot,
                        createIndicator(INVALID_MATERIAL, "Unavailable"));
            } else {
                inventory.setItem(inventorySlot, originalItem);
            }
        }

        // Confirmation button.
        if (selectedSlots.size() == REQUIRED_SELECTIONS) {

            inventory.setItem(CONFIRM_SLOT,
                    createIndicator(CONFIRM_MATERIAL, "Confirm Selection"));

        } else {

            inventory.setItem(CONFIRM_SLOT, null);
        }
    }

    /**
     * Checks whether two positions share a bingo line.
     *
     * Horizontal: same row
     * Vertical: same column
     * Diagonal: same diagonal
     */
    private boolean sharesBingoLine(
            int row1, int col1,
            int row2, int col2
    ) {

        // Same horizontal line.
        if (row1 == row2) {
            return true;
        }

        // Same vertical line.
        if (col1 == col2) {
            return true;
        }

        // Main diagonal.
        if (row1 == col1 && row2 == col2) {
            return true;
        }

        // Reverse diagonal.
        return row1 + col1 == 4 && row2 + col2 == 4;
    }

    /**
     * Creates visual indicators for the selection GUI.
     */
    private ItemStack createIndicator(Material material, String name) {

        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.displayName(TextUtil.makeText(name, TextUtil.GREEN));
            item.setItemMeta(meta);
        }

        return item;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {

        event.setCancelled(true);

        if (event.getClickedInventory() != inventory) {
            return;
        }

        int clickedSlot = event.getSlot();

        // Confirmation button.
        if (clickedSlot == CONFIRM_SLOT) {

            if (selectedSlots.size() == REQUIRED_SELECTIONS) {
                claimSelection(event);
            }

            return;
        }

        // Ignore clicks outside the bingo board.
        if (!board.containsKey(clickedSlot)) {
            return;
        }

        // Cannot select invalid positions.
        if (invalidSlots.contains(clickedSlot)) {
            return;
        }

        // Toggle selection.
        if (selectedSlots.contains(clickedSlot)) {

            selectedSlots.remove(clickedSlot);

        } else {

            if (selectedSlots.size() >= REQUIRED_SELECTIONS) {
                return;
            }

            selectedSlots.add(clickedSlot);
        }

        updateBoard();
    }

    /**
     * Called once the player has selected all required items.
     */
    private void claimSelection(InventoryClickEvent event) {

        // Make sure the player still has the Hades Book.
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem == null ||
                !heldItem.isSimilar(HadesBook.getInstance().getItem())) {
            return;
        }

        if (selectedSlots.size() != REQUIRED_SELECTIONS) {
            return;
        }

        // Retrieve the selected BingoSlots.
        List<BingoSlot> selectedBingoSlots = new ArrayList<>();

        for (int slot : selectedSlots) {
            BingoSlot bingoSlot = board.get(slot);
            plugin.getBingoData().completeClaimItem(playerData, bingoSlot, true);
            selectedBingoSlots.add(bingoSlot);
        }

        successfulPurchase(event);
    }

    private void successfulPurchase(InventoryClickEvent event) {

        SoundUtil.playHighPitchPling(player);

        event.setCancelled(true);
        player.closeInventory();

        player.getInventory().getItemInMainHand().setAmount(0);

        player.sendMessage(
                TextUtil.makeText(
                        "You have successfully claimed your selections!",
                        TextUtil.GREEN
                )
        );
    }
}
