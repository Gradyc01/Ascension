package me.depickcator.ascension.Player.Cooldowns;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeleportCooldown extends Cooldowns{
    private static TeleportCooldown instance;
    private final int MODEL_NUMBER;
    private TeleportCooldown() {
        super();
        MODEL_NUMBER = Ascension.generateModelNumber();
    }
    @Override
    public ItemStack makeItem() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(MODEL_NUMBER);
        meta.displayName(TextUtil.makeText("Teleport Cooldown"));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void setCooldownTimer(Player p) {
        setCooldownTimer(p, 120);
    }

    public static TeleportCooldown getInstance() {
        if (instance == null) {
            instance = new TeleportCooldown();
        }
        return instance;
    }
}
