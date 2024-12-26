package me.depickcator.ascension.Items.Uncraftable;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EnlightenedNugget implements ItemClick {
    private static EnlightenedNugget instance;
    private final String DISPLAY_NAME = "Enlightened Nugget";
    private final ItemStack item;
    private EnlightenedNugget() {
        item = initItem();
        registerItem();
    }
    @Override
    public ItemStack getItem() {
        return item;
    }

    private ItemStack initItem() {
        ItemStack item = new ItemStack(Material.IRON_NUGGET);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        meta.lore(new ArrayList<>(List.of(
                TextUtil.makeText(" Become a little", TextUtil.DARK_PURPLE),
                TextUtil.makeText("more enlightened", TextUtil.DARK_PURPLE)
        )));
        meta.setEnchantmentGlintOverride(true);
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.WHITE).append(TextUtil.rightClickText()));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        Player p = pD.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        int amount = item.getAmount();
        item.setAmount(0);
        pD.getPlayerTeam().getTeam().getTeamStats().addGameScore(amount);
        return true;
    }

    @Override
    public void registerItem() {
        addItem(item, this);
    }

    public static EnlightenedNugget getInstance() {
        if (instance == null) {
            instance = new EnlightenedNugget();
        }
        return instance;
    }
}
