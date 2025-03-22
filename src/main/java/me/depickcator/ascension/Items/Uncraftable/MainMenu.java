package me.depickcator.ascension.Items.Uncraftable;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.MainMenuUI.MainMenuGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenu extends CustomItem implements ItemClick {
    private static MainMenu instance;

    private MainMenu() {
        super("View Main Menu", "main_menu");
        registerItem();
    }

    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.AQUA).append(TextUtil.rightClickText()));
        itemMeta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        itemMeta.setMaxStackSize(1);
        itemMeta.setEnchantmentGlintOverride(true);
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public ItemStack getItem() {
        return getResult();
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (isMainHandRightClick(e)) {
            new MainMenuGUI(pD);
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }
}
