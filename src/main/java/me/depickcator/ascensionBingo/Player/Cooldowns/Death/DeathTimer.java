package me.depickcator.ascensionBingo.Player.Cooldowns.Death;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Player.Cooldowns.Cooldowns;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DeathTimer extends Cooldowns {
//    private static DeathTimer instance;
    private final int MODEL_NUMBER;
    public DeathTimer() {
        super();
        MODEL_NUMBER = AscensionBingo.generateModelNumber();
    }
    @Override
    public ItemStack makeItem() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(MODEL_NUMBER);
        meta.displayName(TextUtil.makeText("Death Timer"));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void setCooldownTimer(Player p) {
        setCooldownTimer(p, 60);
    }

//    public static DeathTimer getInstance() {
//        if (instance == null) {
//            instance = new DeathTimer();
//        }
//        return instance;
//    }
}
