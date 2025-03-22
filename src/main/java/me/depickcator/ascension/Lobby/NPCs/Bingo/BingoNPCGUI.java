package me.depickcator.ascension.Lobby.NPCs.Bingo;

import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class BingoNPCGUI extends AscensionGUI {
    private final List<PlayerData> playerDataList;
    private final int pageNum;
    private final int playersPerGUI;
    public BingoNPCGUI(PlayerData playerData, int pageNum) {
        super(playerData, (char) 6, TextUtil.makeText("Choose a player", TextUtil.AQUA), true);
        inventory.setItem(49, getCloseButton());
        inventory.setItem(48, makePlayerSkull(player));
        playerDataList = new ArrayList<>(PlayerUtil.getAllPlayingPlayers());
        this.pageNum = pageNum;
        playersPerGUI = 5 * 9;
        if (playerDataList.size() / playersPerGUI > pageNum) inventory.setItem(53, nextPageItem());
        if (pageNum != 0) inventory.setItem(45, previousPageItem());
        initHeads();

    }

    private void initHeads() {
        int index = /*0 + */playersPerGUI * pageNum;
        for (PlayerData playerData : playerDataList) {
            if (playerData.equals(this.playerData)) continue;
            inventory.setItem(index, makePlayerSkull(playerData.getPlayer()));
        }
    }

    private ItemStack makePlayerSkull(Player p) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setPlayerProfile(p.getPlayerProfile());
        meta.displayName(TextUtil.makeText(p.getName(), TextUtil.GOLD));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item.equals(getCloseButton())) {
            event.setCancelled(true);
            player.closeInventory();
        } else if (item.equals(previousPageItem())) {
            new BingoNPCGUI(playerData, pageNum - 1);
        } else if (item.equals(nextPageItem())) {
            new BingoNPCGUI(playerData, pageNum + 1);
        }

        if (item.getType().equals(Material.PLAYER_HEAD)) {
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            Player p = meta.getOwningPlayer().getPlayer();
            plugin.getLobby().getBoardDisplay().displayBoard(player, p);
            player.sendMessage(TextUtil.makeText("Now Displaying ", TextUtil.AQUA)
                    .append(TextUtil.makeText(p.getName() + "'s", TextUtil.GOLD))
                    .append(TextUtil.makeText(" Board", TextUtil.AQUA)));
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 100f, 2);
            event.setCancelled(true);
            player.closeInventory();
        }
    }
}
