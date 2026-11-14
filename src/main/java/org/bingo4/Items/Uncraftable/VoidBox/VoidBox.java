package org.bingo4.Items.Uncraftable.VoidBox;

import org.bingo4.Interfaces.ItemClick;
import org.bingo4.Items.CustomItem;
import org.bingo4.Structures.AnchorPoints.AnchorPointGUI;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VoidBox extends CustomItem implements ItemClick {
    private static VoidBox instance;
    private VoidBox() {
        super("Void Box", "voidbox");
        registerItem();
    }

    public static VoidBox getInstance() {
        if (instance == null) {
            instance = new VoidBox();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.ENDER_CHEST);
        ItemMeta meta = item.getItemMeta();
        meta.customName(TextUtil.makeText("Void Box", TextUtil.DARK_PURPLE).append(TextUtil.rightClickText()));
        meta.addEnchant(Enchantment.INFINITY, 1, true);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public ItemStack getItem() {
        return getResult();
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        Block b = e.getClickedBlock();
        e.setCancelled(true);
        if (b == null || !e.getAction().isRightClick()) return false;
        Block placedPosition = b.getRelative(e.getBlockFace());

        if (!placedPosition.getLocation().getWorld().getBlockAt(placedPosition.getLocation().add(0, -1, 0)).isSolid() || !placedPosition.getType().isAir()) return false;
        new AnchorPointGUI(pD, placedPosition.getLocation().clone().add(0.5, 0, 0.5));

        return true;
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }
}
