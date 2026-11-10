package org.bingo4.Items.Uncraftable.XPTome;

import org.bingo4.Bingo4;
import org.bingo4.Interfaces.ItemClick;
import org.bingo4.Items.CustomItem;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class XPTome extends CustomItem implements ItemClick {
    private static XPTome instance;
    private XPTome() {
        super("XP Tome", "xp_tome");
        registerItem();
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(Bingo4.getInstance().generateModelNumber());
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.DARK_GREEN).append(TextUtil.rightClickText()));
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("  Holds a large amount ", TextUtil.DARK_PURPLE),
                TextUtil.makeText("of skill experience", TextUtil.DARK_PURPLE)
        ));
        meta.lore(lore);
        meta.setEnchantmentGlintOverride(true);
        meta.setMaxStackSize(1);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public ItemStack getItem() {
        return getResult();
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (isMainHandRightClick(e)) {
//            e.getItem().setAmount(0);
            new XPTomeGUI(pD);
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }

    public static XPTome getInstance() {
        if (instance == null) {
            instance = new XPTome();
        }
        return instance;
    }
}
