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
            p.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 1, 1));
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
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("  Regeneration III (7 Sec)", TextUtil.DARK_PURPLE),
                TextUtil.makeText("  Instant Health II", TextUtil.DARK_PURPLE)
        ));
        return buildSkull(Material.ZOMBIE_HEAD, TextUtil.makeText("Zombie Head"), lore);
    }
}
