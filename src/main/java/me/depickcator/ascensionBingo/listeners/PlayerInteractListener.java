package me.depickcator.ascensionBingo.listeners;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.GameStates;
import me.depickcator.ascensionBingo.General.ItemClick;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import me.depickcator.ascensionBingo.Teams.Team;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class PlayerInteractListener implements Listener {
    private AscensionBingo plugin;

    public PlayerInteractListener(AscensionBingo plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void mainMenuClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getItem() == null) return;
        if (plugin.getGameState().checkState(GameStates.LOBBY)) {
//            e.setCancelled(true);//TODO: Having this on turns off all interactions could be useful in the future
        }

        if (e.getAction().isRightClick() &&
                e.getHand() == EquipmentSlot.HAND &&
                e.getItem().getType() == Material.PLAYER_HEAD &&
                e.getItem().getItemMeta().getMaxStackSize() == 1) {
            PlayerData pD = PlayerUtil.getPlayerData(p);
            if (pD == null) return;
            Team playerTeam = pD.getTeam();
            e.getItem().setAmount(e.getItem().getAmount() - 1);
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 7 * 20, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 8 * 20, 0));
            for (Player player : playerTeam.getTeamMembers()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 0));
            }
        }

        ItemClick itemClick = ItemClick.findClickItem(e.getItem());
        if (itemClick != null) {
            itemClick.uponClick(e, p);
        }
    }


}
