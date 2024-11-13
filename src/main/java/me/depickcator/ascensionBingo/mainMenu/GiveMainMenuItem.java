package me.depickcator.ascensionBingo.mainMenu;

import me.depickcator.ascensionBingo.General.ItemClick;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class GiveMainMenuItem implements CommandExecutor, ItemClick {

    public GiveMainMenuItem(PluginManager manager, Plugin plugin) {
        registerItem();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player p = returnPlayer(commandSender);
        if (p == null) return false;

        ItemStack item = makeItem();
        p.getInventory().setItem(8, item);
        return true;
    }

    private static ItemStack makeItem() {
        ItemStack item =new ItemStack(Material.BOOK);
        ItemMeta itemMeta = item.getItemMeta();
        Component title = Component.text("View Main Menu").color(TextColor.color(0,255,255));
        title = title.decoration(TextDecoration.ITALIC, false);
        title = title.append(Component.text(" [Right Click]").color(TextColor.color(185,185,185)));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.displayName(title);
        itemMeta.setCustomModelData(1);
        itemMeta.setMaxStackSize(1);
        itemMeta.setEnchantmentGlintOverride(true);
        item.setItemMeta(itemMeta);
        return item;
    }

    private Player returnPlayer(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            return null;
        }
        return ((Player) commandSender).getPlayer();
    }

    @Override
    public ItemStack getItem() {
        return makeItem();
    }

    public static ItemStack getMenuItem() {
        return makeItem();
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, Player p) {
        if (!isMainHandRightClick(e)) return false;
        p.performCommand("open-main-menu");
        return true;
    }

    @Override
    public void registerItem() {
        addItem(makeItem(), this);
    }


}
