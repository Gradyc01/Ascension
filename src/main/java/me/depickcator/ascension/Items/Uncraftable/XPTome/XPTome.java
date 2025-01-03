package me.depickcator.ascension.Items.Uncraftable.XPTome;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class XPTome implements ItemClick {
    private static XPTome instance;
    public static String DISPLAY_NAME = "XP Tome";
    private final ItemStack item;
    // private final Ascension plugin;
    private XPTome() {
        // this.plugin = Ascension.getInstance();
        this.item = initResult();
        registerItem();
    }
    private ItemStack initResult() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
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
        return item;
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
        addItem(item, this);
    }

    public static XPTome getInstance() {
        if (instance == null) {
            instance = new XPTome();
        }
        return instance;
    }
}
