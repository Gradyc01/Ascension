package me.depickcator.ascension.Items.Craftable.Unlocks.NetheriteInfusionItem;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface CustomInfusion {
    /*Manages the infusion in a custom way to make sure it is correct*/
    void customInfusion(ItemStack item, Player p);
}
