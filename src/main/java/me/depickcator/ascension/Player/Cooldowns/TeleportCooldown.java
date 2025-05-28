package me.depickcator.ascension.Player.Cooldowns;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeleportCooldown extends Cooldowns{
    private static TeleportCooldown instance;
    private TeleportCooldown() {
        super("Teleport");
    }
    @Override
    public ItemStack makeItem() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        meta.displayName(TextUtil.makeText("Teleport Cooldown"));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void setCooldownTimer(Player p) {
        setCooldownTimer(p, 120, Material.ENDER_PEARL);
    }

    public static TeleportCooldown getInstance() {
        if (instance == null) {
            instance = new TeleportCooldown();
        }
        return instance;
    }
}
