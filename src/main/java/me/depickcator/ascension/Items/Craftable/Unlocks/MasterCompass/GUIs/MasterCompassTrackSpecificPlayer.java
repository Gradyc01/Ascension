package me.depickcator.ascension.Items.Craftable.Unlocks.MasterCompass.GUIs;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class MasterCompassTrackSpecificPlayer extends MasterCompassGUIs {
    private final List<PlayerData> playerDataList;
    private final int pageNum;
    private final int playersPerGUI;
    public MasterCompassTrackSpecificPlayer(PlayerData playerData, ItemStack compass, int pageNum) {
        super(playerData, (char) 6, TextUtil.makeText("Select A Player", TextUtil.AQUA), compass);
        inventory.setItem(49, getCloseButton());
        playerDataList = new ArrayList<>(PlayerUtil.getAllPlayingPlayers());
        this.pageNum = pageNum;
        playersPerGUI = 5 * 9;
        if (playerDataList.size() / playersPerGUI > pageNum) inventory.setItem(53, nextPageItem());
        if (pageNum != 0) inventory.setItem(45, previousPageItem());
        initHeads();
    }

    public MasterCompassTrackSpecificPlayer(PlayerData playerData, ItemStack compass) {
        this(playerData, compass, 0);
    }

    private void initHeads() {
        int index = /*0 + */playersPerGUI * pageNum;
        for (PlayerData playerData : playerDataList) {
            if (playerData.equals(this.playerData)) continue;
            inventory.setItem(index, makePlayerSkull(playerData));
        }
    }

    private ItemStack makePlayerSkull(PlayerData pD) {
        Player p = pD.getPlayer();
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setPlayerProfile(p.getPlayerProfile());
        meta.displayName(TextUtil.makeText(p.getName(), TextUtil.GOLD));
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(pD.getPlayerTeam().getTeam().getLeader().getName() + "'s Team", TextUtil.AQUA),
                TextUtil.makeText("World: " + p.getLocation().getWorld().getEnvironment().name(), TextUtil.DARK_PURPLE)
        ));
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item.equals(getCloseButton())) {
            event.setCancelled(true);
            player.closeInventory();
            return;
        }

        if (item.getType() == Material.PLAYER_HEAD) {
            Player p = ((SkullMeta) item.getItemMeta()).getOwningPlayer().getPlayer();
            trackPlayer(p);

            event.setCancelled(true);
            player.closeInventory();
        }

    }
}
