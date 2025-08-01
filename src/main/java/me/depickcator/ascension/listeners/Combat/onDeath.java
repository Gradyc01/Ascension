package me.depickcator.ascension.listeners.Combat;

import me.depickcator.ascension.Items.Uncraftable.Skulls.PlayerHead;
import me.depickcator.ascension.Player.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Skills.SkillExpAmount;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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

//        victim.setExp(0);
        victim.setTotalExperience(0);
        ExperienceOrb entity = (ExperienceOrb) victim.getWorld().spawnEntity(victim.getLocation(), EntityType.EXPERIENCE_ORB);
        entity.setExperience((int) victim.getExp() / 2);
        // Remove the metadata after use

        victim.removeMetadata(getDamageSourceKey(), plugin);
        e.setCancelled(true);
    }

    private void playerKillRewards(PlayerDeathEvent e, PlayerData victim) {
        Player v = victim.getPlayer();
        dropHead(e, v);
        ExperienceOrb entity = (ExperienceOrb) v.getWorld().spawnEntity(v.getLocation(), EntityType.EXPERIENCE_ORB);
        entity.setExperience(v.calculateTotalExperiencePoints() / 2);
        v.setExperienceLevelAndProgress(0);
        for (ItemStack item : plugin.getSettingsUI().getSettings().getKillReward(e, victim)) {
            v.getWorld().dropItem(v.getLocation(), item);
        }
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

    private void dropHead(PlayerDeathEvent e, Player victim) {
        Entity entity = e.getDamageSource().getCausingEntity();
        ItemStack item = PlayerHead.getInstance().getResult(victim);
        ItemMeta meta = item.getItemMeta();
        if (entity instanceof Player) {
            Player p = (Player) entity;
            List<Component> lore = new ArrayList<>(List.of(
                    TextUtil.makeText("  Slain by " + p.getName(), TextUtil.RED, false, true)
            ));
            meta.lore(lore);
            item.setItemMeta(meta);
        }

        victim.getWorld().dropItem(victim.getLocation(), item);
    }
}
