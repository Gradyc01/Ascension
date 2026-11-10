package org.bingo4.Lobby.NPCs.WeaponsValues;

import org.bingo4.Interfaces.AscensionMenuGUI;
import org.bingo4.Items.Craftable.Unlocks.DragonSword;
import org.bingo4.Items.Craftable.Unlocks.LeapingAxe;
import org.bingo4.Items.Craftable.Unlocks.MakeshiftMace;
import org.bingo4.Items.Craftable.Unlocks.Poseidon;
import org.bingo4.Items.Craftable.Vanilla.*;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class WeaponValuesGUI extends AscensionMenuGUI {
    public WeaponValuesGUI(PlayerData playerData) {
        super(playerData, (char) 5, TextUtil.makeText("Weapon Damage Values", TextUtil.AQUA), true);
        inventory.setItem(31, getCloseButton());
        initWeapons();
    }

    private void initWeapons() {
        inventory.setItem(11, WoodenSword.getInstance().getResult());
        inventory.setItem(12, StoneSword.getInstance().getResult());
        inventory.setItem(13, IronSword.getInstance().getResult());
        inventory.setItem(14, DiamondSword.getInstance().getResult());
        inventory.setItem(15, NetheriteSword.getInstance().getResult());
        inventory.setItem(20, WoodenAxe.getInstance().getResult());
        inventory.setItem(21, StoneAxe.getInstance().getResult());
        inventory.setItem(22, IronAxe.getInstance().getResult());
        inventory.setItem(23, DiamondAxe.getInstance().getResult());
        inventory.setItem(24, NetheriteAxe.getInstance().getResult());
        inventory.setItem(29, DragonSword.getInstance().getResult());
        inventory.setItem(30, LeapingAxe.getInstance().getResult());
        inventory.setItem(31, Mace.getInstance().getResult());
        inventory.setItem(32, MakeshiftMace.getInstance().getResult());
        inventory.setItem(33, Poseidon.getInstance().getResult());
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item.equals(getCloseButton())) {
            event.setCancelled(true);
            player.closeInventory();
        }
    }
}
