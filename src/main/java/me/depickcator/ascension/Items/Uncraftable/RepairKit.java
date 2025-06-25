package me.depickcator.ascension.Items.Uncraftable;

import me.depickcator.ascension.Interfaces.ItemDrop;
import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RepairKit extends CustomItem implements ItemDrop {
    private static RepairKit instance;
    private RepairKit() {
        super("Repair Kit", "repair_kit");
        registerItem();
    }

    public static RepairKit getInstance() {
        if (instance == null) {
            instance = new RepairKit();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.SHEARS);
        ItemMeta meta = item.getItemMeta();
        meta.setEnchantmentGlintOverride(true);
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.WHITE));
        meta.lore(List.of(
                TextUtil.makeText("Apply this to any item to fully repair it", TextUtil.DARK_PURPLE)
        ));
        item.setItemMeta(meta);
        generateUniqueModelNumber(item);
        return item;
    }

    @Override
    public boolean uponApply(InventoryClickEvent e, ItemStack appliedOn, ItemStack applying, PlayerData pD) {
        if (appliedOn.getItemMeta() instanceof Damageable damageable) {
            if (damageable.getDamage() > 0) {
                damageable.setDamage(0);
                appliedOn.setItemMeta(damageable);
                applying.setAmount(0);
                pD.getPlayer().playSound(pD.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 0);
                return true;
            }
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }
}
