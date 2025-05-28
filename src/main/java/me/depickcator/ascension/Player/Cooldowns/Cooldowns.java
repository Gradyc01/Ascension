package me.depickcator.ascension.Player.Cooldowns;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.common.icon.ItemStackIcon;
import com.lunarclient.apollo.module.cooldown.Cooldown;
import com.lunarclient.apollo.module.cooldown.CooldownModule;
import com.lunarclient.apollo.player.ApolloPlayer;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.Optional;

public abstract class Cooldowns {
    private final ItemStack item;
    private final int ticks = 20;
    private final String name;

    private final CooldownModule cooldownModule;
    protected Cooldowns(String name) {
        this.name = name;
        item = makeItem();
        cooldownModule = Apollo.getModuleManager().getModule(CooldownModule.class);
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

    /*Displays the cooldown on Lunar for player viewer
     * itemName: an item stack icon must be CAPITAL LETTERS matching mc itemstack
     * cooldownTime: in seconds*/
    private void displayCooldownLunar(Player viewer, ItemStackIcon icon, int cooldownTime) {
        Optional<ApolloPlayer> apolloPlayerOpt = Apollo.getPlayerManager().getPlayer(viewer.getUniqueId());
        apolloPlayerOpt.ifPresent(apolloPlayer -> {
            this.cooldownModule.displayCooldown(apolloPlayer, Cooldown.builder()
                    .name(name + "-cooldown")
                    .duration(Duration.ofSeconds(cooldownTime))
                    .icon(icon)
                    .build()
            );
        });
    }

    /*Sets the cooldown timer for Player p*/
    public abstract void setCooldownTimer(Player p);
    public void setCooldownTimer(Player p, int seconds) {
        p.setCooldown(item, seconds * ticks);
    }
    /*Sets the cooldown on for player p
     * itemName: an item stack icon must be CAPITAL LETTERS matching mc itemstack */
    public void setCooldownTimer(Player p, int seconds, Material itemStackName) {
        p.setCooldown(item, seconds * ticks);
        displayCooldownLunar(
                p,
                ItemStackIcon.builder().itemName(itemStackName.toString().toUpperCase()).build(),
                seconds);
    }
}
