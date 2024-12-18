package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.PlayerData;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TeamPortalGUI extends AscensionGUI {

    public TeamPortalGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Team Portal", TextUtil.AQUA), true);
    }


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {

    }
}

