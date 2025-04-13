package me.depickcator.ascension.Player.Cooldowns;

import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Cooldowns {
    private final ItemStack item;
    private final int ticks = 20;
    protected Cooldowns() {
        item = makeItem();
    }
    /*Makes the item that will represent the cooldown*/
    public abstract ItemStack makeItem();

    /*Checks if Player p is on Cooldown
    * Returns true if Player p is on Cooldown
    * Also shows the text to Player p if text is true*/
    public boolean isOnCooldown(Player p, boolean text) {
        if (p.hasCooldown(item)) {
            if (text) {
                TextUtil.errorMessage(p, "You can't do that yet! (" + Math.round((getCooldownTimer(p)) * 100) / 100.0 + "s)");
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

    /*Sets the cooldown timer for Player p*/
    public abstract void setCooldownTimer(Player p);
    public void setCooldownTimer(Player p, int seconds) {

        p.setCooldown(item, seconds * ticks);
    }
}
