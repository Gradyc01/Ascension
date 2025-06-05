package me.depickcator.ascension.Items.Uncraftable;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EnlightenedNugget extends CustomItem implements ItemClick {
    private static EnlightenedNugget instance;
    private EnlightenedNugget() {
        super("Enlightened Nugget", "enlightened_nugget");
        registerItem();
    }
    @Override
    public ItemStack getItem() {
        return getResult();
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
        addItem(getResult(), this);
    }

    public static EnlightenedNugget getInstance() {
        if (instance == null) {
            instance = new EnlightenedNugget();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.IRON_NUGGET);
        ItemMeta meta = item.getItemMeta();
        meta.lore(new ArrayList<>(List.of(
                TextUtil.makeText("Enlightened I", TextUtil.GRAY),
                TextUtil.makeText("", TextUtil.GRAY),
                TextUtil.makeText(" Become a little more enlightened", TextUtil.DARK_PURPLE)
        )));
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        meta.setEnchantmentGlintOverride(true);
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.DARK_GRAY).append(TextUtil.rightClickText()));
        item.setItemMeta(meta);
        generateUniqueModelNumber(item);
        return item;
    }
}
