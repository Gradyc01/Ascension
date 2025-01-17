package me.depickcator.ascension.listeners.Combat;

import me.depickcator.ascension.Player.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Skills.SkillExpAmount;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class onDeath extends PlayerCombat {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        PlayerData victimData = PlayerUtil.getPlayerData(victim);
        String cause = "Unknown";

        PlayerDeath.getInstance().playerDied(victimData);
        victimData.getPlayerStats().addDeaths(1);

        // Check the cause of damage when the player dies
        if (victim.hasMetadata(getDamageSourceKey())) {
            cause = victim.getMetadata(getDamageSourceKey()).getFirst().asString();
        }
        if (cause.equals(getPLAYER_DAMAGE())) {
            playerKillRewards(e, victimData);
            increaseKillCount(e);
        }

        plugin.getServer().broadcast(e.deathMessage().color(TextUtil.DARK_RED));
        plugin.getLogger().info(victim.getName() + " died due to: " + cause);

        // Remove the metadata after use

        victim.removeMetadata(getDamageSourceKey(), plugin);
        e.setCancelled(true);
    }

    private void playerKillRewards(PlayerDeathEvent e, PlayerData victim) {
        Player v = victim.getPlayer();
        dropHead(v);
        for (ItemStack item : plugin.getSettingsUI().getSettings().getKillReward(e, victim)) {
            v.getWorld().dropItem(v.getLocation(), item);
        }
//        ItemStack shards = ShardOfTheFallen.result().clone();
//        shards.setAmount(12);

//        v.getWorld().dropItem(v.getLocation(), shards);
    }

    private void increaseKillCount(PlayerDeathEvent e) {
        Entity entity = e.getDamageSource().getCausingEntity();
        if (entity instanceof Player) {
            PlayerData killer = PlayerUtil.getPlayerData((Player) entity);
            killer.getPlayerStats().addKill();
            addFinalAscensionTimer(killer, 60);
            //TODO: Remove this later
            killer.getPlayerSkills().getCombat().addExp(SkillExpAmount.COMBAT_LEGENDARY.getExp());
        }
    }

    private void dropHead(Player victim) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(victim);
        skullMeta.setMaxStackSize(1);
        skull.setItemMeta(skullMeta);
        victim.getWorld().dropItem(victim.getLocation(), skull);
    }
}
