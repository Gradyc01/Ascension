package me.depickcator.ascension.Player.Cooldowns;

import me.depickcator.ascension.General.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Cooldowns {
    private final ItemStack item;
    private final int ticks = 20;
    protected Cooldowns() {
        item = makeItem();
    }
    public abstract ItemStack makeItem();

    public boolean isOnCooldown(Player p, boolean text) {
        if (p.hasCooldown(item)) {
            if (text) {
                p.sendMessage(TextUtil.makeText("You can't do that yet! (" + Math.round((getCooldownTimer(p)) * 100) / 100.0 + "s)", TextUtil.DARK_RED));
            }
            return true;
        } else {
            return false;
        }
    }
    public boolean isOnCooldown(Player p) {
        return isOnCooldown(p, true);
    }
    public double getCooldownTimer(Player p) {
        return (double) p.getCooldown(item) / ticks;
    }

    public abstract void setCooldownTimer(Player p);
    public void setCooldownTimer(Player p, int seconds) {
        if (!p.hasCooldown(item)) {
            p.sendMessage(TextUtil.makeText("You are in Combat!", TextUtil.DARK_RED));
        }
        p.setCooldown(item, seconds * ticks);
    }
}
