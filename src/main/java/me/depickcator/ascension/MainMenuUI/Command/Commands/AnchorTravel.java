package me.depickcator.ascension.MainMenuUI.Command.Commands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.MainMenuUI.Command.AnchorPoints.AnchorPointGUI;
import me.depickcator.ascension.Player.Data.PlayerAnchors;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.SoundUtil;
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

public class AnchorTravel implements Commands {
    public final static String NAME = "anchor_travel";
    private final ItemStack item;
    private final Ascension plugin;
    public AnchorTravel() {
        item = makeButton();
        this.plugin = Ascension.getInstance();
    }
    @Override
    public boolean uponEvent(InventoryClickEvent event, PlayerData playerData) {
        if (!plugin.getGameState().inGame()) {
            TextUtil.errorMessage(playerData.getPlayer(), "You can't currently use this command");
            return false;
        }
        new AnchorPointGUI(playerData);
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
