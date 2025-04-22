package me.depickcator.ascension.Items.Uncraftable.Skulls;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;


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
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("  Strength II (5 Sec) ", TextUtil.DARK_PURPLE)
        ));
        return buildSkull(Material.SKELETON_SKULL, TextUtil.makeText("Skeleton Skull"), lore);
    }
}
