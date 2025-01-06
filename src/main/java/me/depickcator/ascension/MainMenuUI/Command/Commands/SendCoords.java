package me.depickcator.ascension.MainMenuUI.Command.Commands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SendCoords implements Commands {
    public final static String NAME = "send_coords";
    private final ItemStack item;
    private final Ascension plugin;
    public SendCoords() {
        item = makeButton();
        this.plugin = Ascension.getInstance();
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
            p.sendMessage(TextUtil.makeText(playerData.getPlayer().getName() + " is currently at ", TextUtil.AQUA).append(coordsText));
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
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(" Sends your coordinates to teammates", TextUtil.DARK_PURPLE)
        ));
        itemMeta.lore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }
}
