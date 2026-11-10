package org.bingo4.Items.Uncraftable;

import org.bingo4.Interfaces.ItemClick;
import org.bingo4.Items.CustomItem;
import org.bingo4.MainMenuUI.MainMenuGUI;
import org.bingo4.Player.Cooldowns.CombatTimer;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

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
        itemMeta.lore(List.of(
                TextUtil.makeText("Runs /open-main-menu", TextUtil.DARK_PURPLE)
        ));
        itemMeta.setMaxStackSize(1);
        itemMeta.setEnchantmentGlintOverride(true);
        item.setItemMeta(itemMeta);
        addCooldownGroup(item);
        generateUniqueModelNumber(item);
        return item;
    }

    @Override
    public ItemStack getItem() {
        return getResult();
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (isMainHandRightClick(e) && !CombatTimer.getInstance().isOnCooldown(pD.getPlayer())) {
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
