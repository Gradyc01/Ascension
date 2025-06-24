package me.depickcator.ascension.Timeline.Events.SoulShop;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class SoulShopItem {
    private final String displayName;
    private final int price;
    private final ItemStack item;
    private final Shop shop;
    private final UUID key;
    private int quantity;

    public SoulShopItem(String displayName, int price, int quantity, ItemStack item, Shop shop) {
        this.displayName = displayName;
        this.price = price;
        this.item = item;
        this.quantity = quantity;
        this.shop = shop;
        this.key = shop.addShopItem(this);
    }

    /*Purchases this particular soul shop item for Player playerData
    * Returns true if successful, False otherwise*/
    public boolean purchase(PlayerData playerData) {
        PlayerUnlocks playerUnlocks = playerData.getPlayerUnlocks();
        int unlockTokens = playerUnlocks.getUnlockTokens();
        if (unlockTokens >= price && quantity > 0) {
            playerUnlocks.addUnlockTokens(-price, false);
            decrementQuantity();
            giveItem(playerData);
            successfulPurchaseEffect(playerData);
            return true;
        }
        TextUtil.errorMessage(playerData.getPlayer(), "Failed to purchase item!");
        return false;
    }

    /*Gives Player playerData the item*/
    private void giveItem(PlayerData playerData) {
        Player p = playerData.getPlayer();
        PlayerUtil.giveItem(p, item);
    }

    /*Decrements the quantity of the SoulsShopItem if the quantity reaches 0 it removes it from the shop*/
    private void decrementQuantity() {
        quantity--;
        if (quantity <= 0) shop.removeShopItem(key);
    }

    /*The Effect that is played the playerData after a successful purchase from the Soul Shop*/
    private void successfulPurchaseEffect(PlayerData playerData) {
        Player p = playerData.getPlayer();
        Component soulShopTag = TextUtil.makeText("[Soul Shop]", TextUtil.GOLD);
        Component displayName = TextUtil.makeText(this.displayName, TextUtil.YELLOW);
        p.sendMessage(soulShopTag
                .append(TextUtil.makeText(" You have purchased ", TextUtil.DARK_GREEN))
                .append(displayName)
                .append(TextUtil.makeText(" from the Soul Shop", TextUtil.DARK_GREEN)));
        SoundUtil.playHighPitchPling(p);
        Audience audience = Audience.audience(playerData.getPlayerTeam().getTeam().getOtherTeamMembers(p));
        audience.sendMessage(soulShopTag
                .append(TextUtil.makeText(p.getName() +" has purchased ", TextUtil.DARK_GREEN))
                .append(displayName)
                .append(TextUtil.makeText(" from their Soul Shop", TextUtil.DARK_GREEN)));
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPrice() {
        return price;
    }

    /*Gets the displayIcon for this particular SoulShopItem*/
    public ItemStack getIcon() {
        ItemStack item = this.item.clone();
        ItemMeta meta = item.getItemMeta();
//        meta.setMaxStackSize(quantity);
        Component displayName = TextUtil.makeText(this.displayName, TextUtil.YELLOW);
        displayName = quantity != 1 ?
                displayName.append(TextUtil.makeText(" (" + quantity + ")", TextUtil.YELLOW))
                : displayName;
        meta.displayName(displayName);
        meta.lore(List.of(
                TextUtil.makeText("[" + getPrice() + " Souls]",TextUtil.GOLD),
                TextUtil.makeText(quantity + " Purchases", TextUtil.DARK_GREEN)
        ));
        item.setItemMeta(meta);
//        item.setAmount(quantity);
        return item;
    }
}
