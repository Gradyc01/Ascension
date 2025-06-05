package me.depickcator.ascension.Lobby.NPCs;

import me.depickcator.ascension.Kits.KitBookGUI;
import me.depickcator.ascension.Player.Cooldowns.EntityInteractionCooldown;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;

public class ParkourNPC extends LobbyNPCs {
    public ParkourNPC(double x, double y, double z, Pair<Integer, Integer> rotation) {
        super(x, y, z, rotation,
                EntityType.ALLAY, TextUtil.makeText("Redeem Space Helmet", TextUtil.GOLD, true, false));
    }

    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        EntityInteractionCooldown cooldown = EntityInteractionCooldown.getInstance();
        if (cooldown.isOnCooldown(p)) return false;
        p.getInventory().setHelmet(initHelmet());
        EntityInteractionCooldown.getInstance().setCooldownTimer(p);
        SoundUtil.playHighPitchPling(p);
        p.sendMessage(TextUtil.makeText("Congrats on the new Helmet!", TextUtil.GREEN));
        return true;
    }

    private ItemStack initHelmet() {
        Random r = new Random();
        List<Material> glassColors = List.of(
                Material.GLASS,
                Material.WHITE_STAINED_GLASS,
                Material.GRAY_STAINED_GLASS,
                Material.LIGHT_GRAY_STAINED_GLASS,
                Material.BLACK_STAINED_GLASS,
                Material.BROWN_STAINED_GLASS,
                Material.RED_STAINED_GLASS,
                Material.ORANGE_STAINED_GLASS,
                Material.YELLOW_STAINED_GLASS,
                Material.LIME_STAINED_GLASS,
                Material.GREEN_STAINED_GLASS,
                Material.CYAN_STAINED_GLASS,
                Material.LIGHT_BLUE_STAINED_GLASS,
                Material.BLUE_STAINED_GLASS,
                Material.PURPLE_STAINED_GLASS,
                Material.MAGENTA_STAINED_GLASS,
                Material.PINK_STAINED_GLASS
        );
        ItemStack item = new ItemStack(glassColors.get(r.nextInt(glassColors.size())));
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Space Helmet", TextUtil.DARK_GRAY));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        item.setItemMeta(meta);
        return item;
    }
}
