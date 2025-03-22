package me.depickcator.ascension.Items.Uncraftable.Skulls;

import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerHead extends Skulls {
    private static PlayerHead instance;
    private PlayerHead() {
        super("Player Head", "player_head");
        registerItem();
    }


    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        ItemStack item = e.getItem();
        if (isMainHandRightClick(e)) {
            consumedSkull(pD, item);
            giveSkullTeamEffects(pD);
            consumeMessage(pD,
                    "Regeneration II for 7 seconds, Resistance I for 8 seconds",
                    "Regeneration I for 12 seconds");
            return true;
        }
        return false;
    }


    public static PlayerHead getInstance() {
        if (instance == null) {
            instance = new PlayerHead();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        return buildSkull(Material.PLAYER_HEAD);
    }

    public ItemStack getResult(Player p) {
        ItemStack item = result.clone();
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(p);
        item.setItemMeta(meta);
        return item;
    }
}
