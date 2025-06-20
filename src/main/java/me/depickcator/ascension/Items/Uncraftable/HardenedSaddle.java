package me.depickcator.ascension.Items.Uncraftable;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class HardenedSaddle extends CustomItem {
    private static HardenedSaddle instance;
    private HardenedSaddle() {
        super("Hardened Saddle", "hardened_saddle");
    }

    public static HardenedSaddle getInstance() {
        if (instance == null) {
            instance = new HardenedSaddle();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.YELLOW));
        meta.setEnchantmentGlintOverride(true);
        List<Component> lore = List.of(TextUtil.makeText("Found only in Dungeons",TextUtil.DARK_PURPLE));
        meta.lore(lore);
        item.setItemMeta(meta);
        generateUniqueModelNumber(item);
        return item;
    }
}
