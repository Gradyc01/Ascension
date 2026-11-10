package org.bingo4.Items.Uncraftable.ToolVoucher;

import org.bukkit.inventory.ItemStack;

public record VouchableTool(ItemStack item, int cost, String displayName, int index) {
}
