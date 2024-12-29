package me.depickcator.ascension.listeners.Combat;

import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class onElytraFlight extends PlayerCombat {
    @EventHandler
    public void onPlayerElytra(EntityToggleGlideEvent event) {
        if (event.getEntity() instanceof Player && plugin.getGameState().inGame()) {
            Player p = (Player) event.getEntity();
//            PlayerData pD = PlayerUtil.getPlayerData(p);
            if (CombatTimer.getInstance().isOnCooldown(p)) {
                TextUtil.debugText("Set Gliding false and cancelled");
                p.setGliding(false);
                event.setCancelled(true);
            }
        }
    }
}
