package me.depickcator.ascension.Items.Uncraftable.XPTome;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.ItemClick;
import me.depickcator.ascension.General.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class XPTome implements ItemClick {
    public static String DISPLAY_NAME = "XP Tome";
    private final ItemStack item;
    private final Ascension plugin;
    private static final int modelNumber = Ascension.generateModelNumber();
    public XPTome() {
        this.plugin = Ascension.getInstance();
        this.item = result();
        registerItem();
    }
    public static ItemStack result() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(modelNumber);
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.DARK_GREEN).append(TextUtil.rightClickText()));
        meta.setEnchantmentGlintOverride(true);
        meta.setMaxStackSize(1);
        item.setItemMeta(meta);
        return item;
    }


    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, Player p) {
        if (isMainHandRightClick(e)) {
//            e.getItem().setAmount(0);
            new XPTomeGUI(plugin, p);
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(XPTome.result(), this);
    }
}