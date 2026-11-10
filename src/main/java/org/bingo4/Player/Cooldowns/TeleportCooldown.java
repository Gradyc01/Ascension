package org.bingo4.Player.Cooldowns;

import org.bingo4.Bingo4;
import org.bingo4.Utility.TextUtil;
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
        meta.setCustomModelData(Bingo4.getInstance().generateModelNumber());
        meta.displayName(TextUtil.makeText("Teleport Cooldown"));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void setCooldownTimer(Player p) {
        setCooldownTimer(p, 120, Material.ENDER_PEARL);
    }

    @Override
    public void setCooldownTimer(Player p, int seconds) {
        setCooldownTimer(p, seconds, Material.ENDER_PEARL);
    }

    public static TeleportCooldown getInstance() {
        if (instance == null) {
            instance = new TeleportCooldown();
        }
        return instance;
    }
}
