package me.depickcator.ascension.Items.Uncraftable.Skulls;

import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class SkeletonSkull extends Skulls {
    private static SkeletonSkull instance;
    private SkeletonSkull() {
        super("Skeleton Skull", "skeleton_skull");
        registerItem();
    }


    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        ItemStack item = e.getItem();
        if (isMainHandRightClick(e)) {
            Player p = e.getPlayer();
            consumedSkull(pD, item);
            p.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 10 * 20, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 5 * 20, 1));
            return true;
        }
        return false;
    }


    public static SkeletonSkull getInstance() {
        if (instance == null) {
            instance = new SkeletonSkull();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        return buildSkull(Material.SKELETON_SKULL);
    }
}
