package me.depickcator.ascension.Items.Uncraftable;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.ItemClick;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Kits.KitBookGUI;
import me.depickcator.ascension.Kits.Kits.Ecologist;
import me.depickcator.ascension.Kits.Kits.Hunter;
import me.depickcator.ascension.Kits.Kits.IronTools;
import me.depickcator.ascension.Kits.Kits.Looter;
import me.depickcator.ascension.Player.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitBook implements ItemClick {
    public static String DISPLAY_NAME = "Kit Book";
    private static ItemStack item = KitBook.makeItem();;
    private static final int modelNumber = Ascension.generateModelNumber();
    public KitBook() {
        registerItem();
        registerKits();
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    public static ItemStack item() {
        return item;
    }

    private static ItemStack makeItem() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.GOLD).append(TextUtil.rightClickText()));
        meta.setMaxStackSize(1);
        meta.setCustomModelData(modelNumber);
        meta.setEnchantmentGlintOverride(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, Player p) {
        if (isMainHandRightClick(e)) {
            new KitBookGUI(PlayerUtil.getPlayerData(p));
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(item, this);
    }

    private void registerKits() {
        new Ecologist();
        new Hunter();
        new IronTools();
        new Looter();
    }
}