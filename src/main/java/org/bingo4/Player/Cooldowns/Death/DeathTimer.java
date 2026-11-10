package org.bingo4.Player.Cooldowns.Death;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.UseCooldown;
import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameStates;
import org.bingo4.Player.Cooldowns.Cooldowns;
import net.kyori.adventure.key.Key;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DeathTimer extends Cooldowns {
//    private static DeathTimer instance;
    public DeathTimer() {
        super("Death");
    }
    @Override
    public ItemStack makeItem() {
        ItemStack item = new ItemStack(Material.BEDROCK);
        item.setData(DataComponentTypes.USE_COOLDOWN,
                UseCooldown.useCooldown(0.01f)
                        .cooldownGroup(Key.key("cooldown:death")));
        return item;
    }

    @Override
    public void setCooldownTimer(Player p) {
        PlayerData pD = PlayerUtil.getPlayerData(p);
        if (pD == null) {
            setCooldownTimer(p, 10);
            return;
        }
        setCooldownTimer(p, Integer.min(5 + 5 * pD.getPlayerStats().getItemsObtained(), 90));
    }
}
