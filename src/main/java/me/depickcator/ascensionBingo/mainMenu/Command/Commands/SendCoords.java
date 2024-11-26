package me.depickcator.ascensionBingo.mainMenu.Command.Commands;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.GameStates;
import me.depickcator.ascensionBingo.General.SoundUtil;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Player.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SendCoords implements Commands {
    public final static String NAME = "send_coords";
    private final ItemStack item;
    private final AscensionBingo plugin;
    public SendCoords(AscensionBingo plugin) {
        item = makeButton();
        this.plugin = plugin;
    }
    @Override
    public void uponEvent(InventoryClickEvent event, PlayerData playerData) {
        if (!plugin.getGameState().inGame()) {
            TextUtil.errorMessage(playerData.getPlayer(), "You can't currently use this command");
            return;
        }
        Location loc = playerData.getPlayer().getLocation();
        for (Player p : playerData.getPlayerTeam().getTeam().getTeamMembers()) {
            Component coordsText = TextUtil.makeText(
                        "(" +
                            loc.getBlockX() +
                            ", " + loc.getBlockY() +
                            ", " + loc.getBlockZ() +
                            ")", TextUtil.GREEN);
            p.sendMessage(TextUtil.makeText(p.getName() + " is currently at ", TextUtil.AQUA).append(coordsText));
            SoundUtil.playHighPitchPling(p);
        }

    }

    @Override
    public ItemStack getButton() {
        return item;
    }

    private ItemStack makeButton() {
        ItemStack item = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(TextUtil.makeText("Send Coordinates", TextUtil.AQUA));
        item.setItemMeta(itemMeta);
        return item;
    }
}
