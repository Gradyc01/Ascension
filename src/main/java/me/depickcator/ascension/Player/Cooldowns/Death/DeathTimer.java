package me.depickcator.ascension.Player.Cooldowns.Death;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.UseCooldown;
import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Cooldowns.Cooldowns;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        int seconds = switch (Ascension.getInstance().getGameState().getCurrentState()) {
            case GameStates.GAME_BEFORE_GRACE -> 60;
            case GameStates.GAME_ASCENSION -> 20;
            default -> 30;
        };
        setCooldownTimer(p, seconds);
    }
}
