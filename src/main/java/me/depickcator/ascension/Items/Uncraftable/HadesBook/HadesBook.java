package me.depickcator.ascension.Items.Uncraftable.HadesBook;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.ItemClick;
import me.depickcator.ascension.General.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HadesBook implements ItemClick {
    private static HadesBook instance;
    private final String DISPLAY_NAME = "Hades' Book";
    private final ItemStack item;
    private HadesBook() {
        item = initItem();
        registerItem();
    }
    @Override
    public ItemStack getItem() {
        return item;
    }

    private ItemStack initItem() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.GOLD, true, false).append(TextUtil.rightClickText()));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, Player p) {
        if (!p.hasCooldown(item)) {
            new HadesBookGUI(p);
            p.setCooldown(item, 3 * 20);
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(item, this);
    }

    public static HadesBook getInstance() {
        if (instance == null) {
            instance = new HadesBook();
        }
        return instance;
    }
}
