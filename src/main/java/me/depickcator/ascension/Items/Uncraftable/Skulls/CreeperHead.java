package me.depickcator.ascension.Items.Uncraftable.Skulls;

import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CreeperHead extends Skulls {
    private static CreeperHead instance;
    private CreeperHead() {
        super("Creeper Head", "creeper_head");
        registerItem();
    }


    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        ItemStack item = e.getItem();
        if (isMainHandRightClick(e)) {
            Player p = e.getPlayer();
            consumedSkull(pD, item);
            p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 12 * 20, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 7 * 20, 1));
            return true;
        }
        return false;
    }


    public static CreeperHead getInstance() {
        if (instance == null) {
            instance = new CreeperHead();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        return buildSkull(Material.CREEPER_HEAD);
    }
}
