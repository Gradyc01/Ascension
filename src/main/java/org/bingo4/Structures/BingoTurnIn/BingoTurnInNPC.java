package org.bingo4.Structures.BingoTurnIn;

import org.bingo4.Bingo4;
import org.bingo4.Interfaces.EntityInteraction;
import org.bingo4.MainMenuUI.BingoBoard.BingoData;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.DisplayUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class BingoTurnInNPC extends EntityInteraction {

    private final Bingo4 plugin = Bingo4.getInstance();
    private final BingoTurnInInstance instance;
    private Interaction display;
    private ItemDisplay actualItem;
    public BingoTurnInNPC(BingoTurnInInstance instance) {
        this.instance = instance;
    }

    public void spawn(Location location) {
        createEntity(location);
        addInteraction(display, this);
    }

    public void start(ItemStack item) {
        actualItem = DisplayUtil.makeItemDisplay(instance.getCenter().clone().add(0, 10, 0), new ItemStack(item), 0, 0, 4);
        display.remove();
        Entity e = plugin.getWorld().getEntity(display.getUniqueId());
        if (e != null) {
            e.remove();
        }

    }

    public void stop() {
        actualItem.remove();
        removeInteraction(display);
    }

    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        BingoData bingoData = plugin.getBingoData();
        PlayerData playerData = PlayerUtil.getPlayerData(player);
        if (playerData  != null && display.getBoundingBox().contains(player.getBoundingBox()) && bingoData.canUnlockItem(playerData, item)) {
            instance.start(PlayerUtil.getPlayerData(player), item, display.getBoundingBox().clone());
            return true;
        } else {
            return false;
        }
    }

    private void createEntity(Location location) {
        display = (Interaction) location.getWorld().spawnEntity(location, EntityType.INTERACTION);
        display.setInteractionWidth(8.0f);
        display.setInteractionHeight(6.0f);
        display.setCustomNameVisible(true);
        display.customName(TextUtil.makeText("Turn In Your Item Here", TextUtil.GOLD));

        // Optional: Determine if it fires immediately or blocks attacks
        display.setResponsive(false);
    }
}
