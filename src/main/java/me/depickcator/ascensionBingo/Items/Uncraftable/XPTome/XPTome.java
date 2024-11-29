package me.depickcator.ascensionBingo.Items.Uncraftable.XPTome;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.ItemClick;
import me.depickcator.ascensionBingo.General.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class XPTome implements ItemClick {
    public static String DISPLAY_NAME = "XP Tome";
    private final ItemStack item;
    private final AscensionBingo plugin;
    private static final int modelNumber = AscensionBingo.generateModelNumber();
    public XPTome(AscensionBingo plugin) {
        this.plugin = plugin;
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
