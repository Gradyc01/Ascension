package me.depickcator.ascension.MainMenuUI.BingoBoard;

import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.depickcator.ascension.MainMenuUI.BingoBoard.BingoBoardGUI.rightClickClaim;

public class ClaimItemGUI extends AscensionMenuGUI {
    private final ItemStack item;
    private final ItemStack fullClaimItem;
    private final ItemStack halfClaimItem;
    private final ItemStack cancelClaimItem;
    public ClaimItemGUI(PlayerData playerData, ItemStack item) {
        super(playerData, (char) 6,
                TextUtil.makeText("Claim: " + TextUtil.getItemNameString(item), TextUtil.AQUA), true);
        this.item = item;
        inventory.setItem(13, item);
        this.fullClaimItem = initFullClaimItem(29);
        this.cancelClaimItem = initCancelClaimItem(31);
        this.halfClaimItem = initHalfClaimItem(33);
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        BingoData bingoData = plugin.getBingoData();
        if (item.equals(this.cancelClaimItem)) {
            //Purposefully Empty
        } else if (item.equals(this.fullClaimItem)) {
            bingoData.claimItem(player, this.item, true);
        } else if (item.equals(this.halfClaimItem)) {
            PlayerUnlocks playerUnlocks = playerData.getPlayerUnlocks();
            if (bingoData.claimItem(player, this.item, true, false)) {
                playerUnlocks.addUnlockTokens(-rightClickClaim);
            }
        } else {
            return;
        }
        new BingoBoardGUI(playerData);
    }

    private ItemStack initFullClaimItem(int index) {
        List<Component> lore = List.of(
                TextUtil.makeText("Cost: ", TextUtil.GOLD),
                TextUtil.makeText(" -1 " + TextUtil.getItemNameString(item), TextUtil.RED),
                TextUtil.makeText(""),
                TextUtil.makeText("Solo Rewards:", TextUtil.DARK_PURPLE),
                TextUtil.makeText(" XP Tome", TextUtil.DARK_GREEN),
                TextUtil.makeText(" +" + PlayerUnlocks.AMOUNT_RARE + " Souls", TextUtil.AQUA),
                TextUtil.makeText(" +2 Board Exp", TextUtil.AQUA),
                TextUtil.makeText(" Regeneration I (00:16)", TextUtil.PINK),
                TextUtil.makeText(""),
                TextUtil.makeText("Team Rewards", TextUtil.DARK_PURPLE),
                TextUtil.makeText(" XP Tome", TextUtil.DARK_GREEN),
                TextUtil.makeText(" +" + PlayerUnlocks.AMOUNT_UNCOMMON + " Souls", TextUtil.AQUA),
                TextUtil.makeText(" +1 Board Exp", TextUtil.AQUA),
                TextUtil.makeText(" Regeneration I (00:16)", TextUtil.PINK)
        );
        ItemStack item = initExplainerItem(Material.GREEN_CONCRETE, lore, TextUtil.makeText("Full Claim", TextUtil.GREEN));
        inventory.setItem(index, item);
        return item;
    }
    private ItemStack initHalfClaimItem(int index) {
        List<Component> lore = List.of(
                TextUtil.makeText("Cost: ", TextUtil.GOLD),
                TextUtil.makeText(" -" + rightClickClaim + " Souls", TextUtil.GOLD),
                TextUtil.makeText(""),
                TextUtil.makeText("Solo Rewards:", TextUtil.DARK_PURPLE),
                TextUtil.makeText(" +2 Board Exp", TextUtil.AQUA),
                TextUtil.makeText(""),
                TextUtil.makeText("Team Rewards", TextUtil.DARK_PURPLE),
                TextUtil.makeText(" +1 Board Exp", TextUtil.AQUA)
        );
        ItemStack item = initExplainerItem(Material.YELLOW_CONCRETE, lore, TextUtil.makeText("Half Claim", TextUtil.GREEN));
        if (playerData.getPlayerUnlocks().getUnlockTokens() > rightClickClaim) inventory.setItem(index, item);
        return item;
    }
    private ItemStack initCancelClaimItem(int index) {
        ItemStack item = initExplainerItem(Material.RED_CONCRETE, List.of(), TextUtil.makeText("Cancel Claim", TextUtil.RED));
        inventory.setItem(index, item);
        return item;
    }
}
