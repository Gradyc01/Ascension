package org.bingo4.Player.Cooldowns;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.UseCooldown;
import net.kyori.adventure.key.Key;
import org.bingo4.Bingo4;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AnchorPointCooldown extends Cooldowns {
    public static Key KEY = Key.key(Bingo4.getInstance().getName(), "anchor_point_creation");
    private static AnchorPointCooldown instance;
    protected AnchorPointCooldown() {
        super("Anchor Point");
    }

    @Override
    public ItemStack makeItem() {
        ItemStack item = new ItemStack(Material.ENDER_CHEST);
        ItemMeta meta = item.getItemMeta();
        meta.lore(List.of(
                TextUtil.makeText("Create a rift in the world allowing one & friends", TextUtil.DARK_PURPLE),
                TextUtil.makeText("to travel through a warped reality", TextUtil.DARK_PURPLE)
        ));
        meta.customName(TextUtil.makeText("Build Void Box", TextUtil.DARK_PURPLE));
        item.setItemMeta(meta);
        item.setData(DataComponentTypes.USE_COOLDOWN, UseCooldown.useCooldown(1).cooldownGroup(KEY).build());
        return item;
    }

    @Override
    public void setCooldownTimer(Player p) {
        PlayerData pD = PlayerUtil.getPlayerData(p);
        if (pD == null) {
            TextUtil.debugText("[Anchor Point Cooldown] ERROR: Player data " + p.getName() + " invalid");
            return;
        }
        p.setCooldown(KEY, 10 * 20);
    }

    public static AnchorPointCooldown getInstance() {
        if (instance == null) {
            instance = new AnchorPointCooldown();
        }
        return instance;
    }
}
