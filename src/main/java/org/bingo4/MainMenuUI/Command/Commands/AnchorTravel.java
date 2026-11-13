package org.bingo4.MainMenuUI.Command.Commands;

import org.bingo4.Bingo4;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AnchorTravel implements Commands {
    public final static String NAME = "anchor_travel";
    private final ItemStack item;
    private final Bingo4 plugin;
    public AnchorTravel() {
        item = makeButton();
        this.plugin = Bingo4.getInstance();
    }
    @Override
    public boolean uponEvent(InventoryClickEvent event, PlayerData playerData) {
        if (!plugin.getGameState().inGame()) {
            TextUtil.errorMessage(playerData.getPlayer(), "You can't currently use this command");
            return false;
        }
//        new AnchorPointGUI(playerData, event.);
        return false;

    }

    @Override
    public ItemStack getButton() {
        return item;
    }

    private ItemStack makeButton() {
        ItemStack item = new ItemStack(Material.LODESTONE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(TextUtil.makeText("Anchor Points", TextUtil.AQUA));
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(" Access to your anchor points", TextUtil.DARK_PURPLE)
        ));
        itemMeta.lore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }
}
