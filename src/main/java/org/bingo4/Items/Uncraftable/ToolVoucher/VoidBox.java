package org.bingo4.Items.Uncraftable.ToolVoucher;

import org.bingo4.Bingo4;
import org.bingo4.Interfaces.ItemClick;
import org.bingo4.Items.CustomItem;
import org.bingo4.Items.Uncraftable.HadesBook.HadesBookGUI;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class VoidBox extends CustomItem implements ItemClick {
    private static VoidBox instance;
    private VoidBox() {
        super("Void Box", "void_box");
        registerItem();
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        Player p = pD.getPlayer();
        if (!p.hasCooldown(getResult())) {
            new HadesBookGUI(pD);
            p.setCooldown(getResult(), 3 * 20);
            return true;
        }
        return false;
    }

    public static VoidBox getInstance() {
        if (instance == null) {
            instance = new VoidBox();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(Bingo4.getInstance().generateModelNumber());
        meta.lore(new ArrayList<>(List.of(
                TextUtil.makeText(" This remnant of the gods allows", TextUtil.DARK_PURPLE),
                TextUtil.makeText("whoever to grant themselves any", TextUtil.DARK_PURPLE),
                TextUtil.makeText("item that they wish from the board", TextUtil.DARK_PURPLE)
        )));
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.GOLD, true, false).append(TextUtil.rightClickText()));
        item.setItemMeta(meta);
        addCooldownGroup(item);
        return item;
    }

    @Override
    public ItemStack getItem() {
        return getResult();
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }
}
