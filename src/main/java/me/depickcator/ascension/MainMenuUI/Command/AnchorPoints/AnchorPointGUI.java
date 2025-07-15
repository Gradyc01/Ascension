package me.depickcator.ascension.MainMenuUI.Command.AnchorPoints;

import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AnchorPointGUI extends AscensionMenuGUI {
    public AnchorPointGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Anchor Points", TextUtil.AQUA), true);
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {

    }
}
