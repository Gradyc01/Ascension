package me.depickcator.ascension.Items.Uncraftable.Skulls;

import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ZombieHead extends Skulls {
    private static ZombieHead instance;
    private ZombieHead() {
        super("Zombie Head", "zombie_head");
        registerItem();
    }


    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        ItemStack item = e.getItem();
        if (isMainHandRightClick(e)) {
            Player p = e.getPlayer();
            consumedSkull(pD, item);
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 7 * 20, 2));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 20, 1));
            return true;
        }
        return false;
    }


    public static ZombieHead getInstance() {
        if (instance == null) {
            instance = new ZombieHead();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        return buildSkull(Material.ZOMBIE_HEAD);
    }
}
