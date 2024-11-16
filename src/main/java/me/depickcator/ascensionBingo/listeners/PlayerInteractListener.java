package me.depickcator.ascensionBingo.listeners;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.GameStates;
import me.depickcator.ascensionBingo.General.ItemClick;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import me.depickcator.ascensionBingo.Teams.Team;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
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
//        if (plugin.getGameState().checkState(GameStates.LOBBY)) {
//            e.setCancelled(true);//TODO: Having this on turns off all interactions could be useful in the future
//        }



        ItemClick itemClick = ItemClick.findClickItem(e.getItem());
        if (itemClick != null) {
            itemClick.uponClick(e, p);
            return;
        }

        if (e.getAction().isRightClick() && e.getHand() == EquipmentSlot.HAND && e.getItem().getType() == Material.PLAYER_HEAD) {
            usedPlayerHead(e);
        }
    }

    private void usedPlayerHead(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        Player p = e.getPlayer();
        try {
            assert item != null; // So IDE don't yell at me
            if (item.getItemMeta().getCustomModelData() != 0) {
                return;
            }
            PlayerData pD = PlayerUtil.getPlayerData(p);
            if (pD == null) return;
            Team playerTeam = pD.getPlayerTeam().getTeam();
            e.getItem().setAmount(e.getItem().getAmount() - 1);
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 7 * 20, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 8 * 20, 0));
            p.sendMessage(TextUtil.makeText("You ate a player head which grants you Regeneration II for 7 seconds, Resistance I for 8 seconds", TextUtil.GREEN));
            for (Player player : playerTeam.getOtherTeamMembers(p)) {
                player.sendMessage(TextUtil.makeText(p.getName() + " ate a player head which grants you Regeneration I for 12 seconds", TextUtil.GREEN));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 12 * 20, 0));
            }
        } catch (Exception ignored) {
            plugin.getServer().broadcast(TextUtil.makeText("aa", TextUtil.WHITE));
        }
    }

}
