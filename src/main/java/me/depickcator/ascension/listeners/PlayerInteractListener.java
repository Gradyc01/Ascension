package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Interfaces.EntityInteraction;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class PlayerInteractListener implements Listener {
     private final Ascension plugin;

    public PlayerInteractListener() {
         this.plugin = Ascension.getInstance();
    }
    @EventHandler
    public void mainMenuClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (plugin.getGameState().checkState(GameStates.UNLOADED)) return;
        if (e.getItem() == null) return;
        ItemClick itemClick = ItemClick.findClickItem(e.getItem());
        if (itemClick != null) {
            itemClick.uponClick(e, PlayerUtil.getPlayerData(p));
        }

//        if (e.getAction().isRightClick() && e.getHand() == EquipmentSlot.HAND && e.getItem().getType() == Material.PLAYER_HEAD) {
//            usedPlayerHead(e);
//        }
    }

    @EventHandler
    public void interactingWithEntity(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof LivingEntity)) return;
        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        LivingEntity entity = (LivingEntity) e.getRightClicked();
        EntityInteraction entityInteraction = EntityInteraction.getEntityInteraction(entity);
        if (entityInteraction != null) {
            entityInteraction.interact(e);
            return;
        }
    }

//    private void usedPlayerHead(PlayerInteractEvent e) {
//        ItemStack item = e.getItem();
//        Player p = e.getPlayer();
//        // try {
//        //     int a = item.getItemMeta().getCustomModelData();
//        //     return;
//        // } catch (Exception ignored) {
//        // }
//        if (item.getItemMeta().hasCustomModelData()) {
//            return;
//        }
//        PlayerData pD = PlayerUtil.getPlayerData(p);
//        if (pD == null) return;
//        Team playerTeam = pD.getPlayerTeam().getTeam();
//        item.setAmount(item.getAmount() - 1);
//        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 7 * 20, 1));
//        p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 8 * 20, 0));
//        p.sendMessage(TextUtil.makeText("You ate a player head which grants you Regeneration II for 7 seconds, Resistance I for 8 seconds", TextUtil.GREEN));
//        for (Player player : playerTeam.getOtherTeamMembers(p)) {
//            player.sendMessage(TextUtil.makeText(p.getName() + " ate a player head which grants you Regeneration I for 12 seconds", TextUtil.GREEN));
//            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 12 * 20, 0));
//        }
//
//    }

}
