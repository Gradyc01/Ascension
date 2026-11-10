package org.bingo4.Items.Uncraftable.NetherStar;

import org.bingo4.Interfaces.ItemClick;
import org.bingo4.Items.CustomItem;
import org.bingo4.Player.Cooldowns.CombatTimer;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class NetherStar extends CustomItem implements ItemClick {
    private static NetherStar instance;

    private NetherStar() {
        super("Nether Star", "nether_star");
        registerItem();
    }

    public static NetherStar getInstance() {
        if (instance == null) {
            instance = new NetherStar();
        }
        return instance;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (CombatTimer.getInstance().isOnCooldown(pD.getPlayer())) return false;
        if (isMainHandRightClick(e)) {
            new NetherStarGUI(pD, e.getItem());
            return true;
        }
        return false;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.AQUA).append(TextUtil.rightClickText()));
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Rift I", TextUtil.GRAY),
                TextUtil.makeText(""),
                TextUtil.makeText("Can be exchanged for raw materials with", TextUtil.DARK_PURPLE),
                TextUtil.makeText("more quantity unlocking more trades", TextUtil.DARK_PURPLE)
        ));
        meta.lore(lore);
        meta.setMaxStackSize(4);
        item.setItemMeta(meta);
        generateUniqueModelNumber(item);
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
