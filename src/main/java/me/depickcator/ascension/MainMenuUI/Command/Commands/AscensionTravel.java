package me.depickcator.ascension.MainMenuUI.Command.Commands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Player.Cooldowns.TeleportCooldown;
import me.depickcator.ascension.Effects.TeleportSequence;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AscensionTravel implements Commands {
    public final static String NAME = "ascension_travel";
    private final ItemStack item;
    private final Ascension plugin;
    private final int purchasePrice;
    public AscensionTravel() {
        purchasePrice = 150;
        item = makeButton();
        this.plugin = Ascension.getInstance();
    }
    @Override
    public boolean uponEvent(InventoryClickEvent event, PlayerData playerData) {
        Player p = playerData.getPlayer();
        if (TeleportCooldown.getInstance().isOnCooldown(p)) return true;
        if (!inAscension(p)) return true;
        if (canPurchase(playerData)) {
            Random rand = new Random();
            Location ascensionLocation = plugin.getSettingsUI().getSettings().getTimeline().getAscensionEvent().getAscendingLocation().getSpawnLocation();
            int spreadDistance = ascensionLocation.getWorld().getWorldBorder().getSize() < 250 ?
                    (int) ascensionLocation.getWorld().getWorldBorder().getSize() : 250;
            int zM = rand.nextInt(2) == 1 ? 1 : -1;
            int xM = rand.nextInt(2) == 1 ? 1 : -1;
            int z = ascensionLocation.getBlockZ() + rand.nextInt(125, spreadDistance) * zM;
            int x = ascensionLocation.getBlockX() + rand.nextInt(125, spreadDistance) * xM;
            TeleportCooldown.getInstance().setCooldownTimer(playerData.getPlayer());
            Location newLoc = plugin.getWorld().getHighestBlockAt(x, z).getLocation();
            new TeleportSequence(playerData, newLoc.add(0, 1, 0), 10);
        } else {
            TextUtil.errorMessage(p, "You do not have the souls to purchase this teleport!");
        }

        return true;

    }

    private boolean inAscension(Player p) {
        if (plugin.getGameState().checkState(GameStates.GAME_ASCENSION)) {
            return true;
        } else {
            TextUtil.errorMessage(p, "There is no active Ascension Location currently!");
            return false;
        }

    }

    private boolean canPurchase(PlayerData playerData) {
        PlayerUnlocks playerUnlocks = playerData.getPlayerUnlocks();
        if (playerUnlocks.getUnlockTokens() >= purchasePrice) {
            playerUnlocks.addUnlockTokens(-purchasePrice);
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getButton() {
        return item;
    }

    private ItemStack makeButton() {
        ItemStack item = new ItemStack(Material.END_PORTAL_FRAME);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(TextUtil.makeText("Ascension Travel", TextUtil.AQUA));
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(" Teleports you to an active Ascension Location", TextUtil.DARK_PURPLE),
                TextUtil.makeText("Can only be used when there is an Ascension Active", TextUtil.DARK_RED),
                TextUtil.makeText("              COST: [" + purchasePrice + " Souls]", TextUtil.GOLD)
        ));
        itemMeta.lore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }
}
