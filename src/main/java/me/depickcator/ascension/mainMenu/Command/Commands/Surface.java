package me.depickcator.ascension.MainMenu.Command.Commands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Player.Data.Cooldowns.TeleportCooldown;
import me.depickcator.ascension.Player.Data.Cooldowns.TeleportSequence;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Surface implements Commands {
    public final static String NAME = "surface";
    private final ItemStack item;
    private final Ascension plugin;
    public Surface() {
        item = makeButton();
        this.plugin = Ascension.getInstance();
    }
    @Override
    public void uponEvent(InventoryClickEvent event, PlayerData playerData) {
        Player p = playerData.getPlayer();
        if (!plugin.getGameState().canTeleport(p) ||
                TeleportCooldown.getInstance().isOnCooldown(p)) {
            TextUtil.errorMessage(p, "You can't currently use this command");
            return;
        }
        Player player = playerData.getPlayer();
        Location loc = player.getLocation();
        loc = player.getWorld().getHighestBlockAt(loc.getBlockX(), loc.getBlockZ()).getLocation();
        TeleportCooldown.getInstance().setCooldownTimer(player);
        new TeleportSequence(playerData, loc.add(0, 1, 0),15);
    }

    @Override
    public ItemStack getButton() {
        return item;
    }

    private ItemStack makeButton() {
        ItemStack item = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(TextUtil.makeText("Surface", TextUtil.AQUA));
        item.setItemMeta(itemMeta);
        return item;
    }
}
