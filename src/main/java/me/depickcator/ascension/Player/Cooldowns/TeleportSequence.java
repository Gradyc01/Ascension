package me.depickcator.ascension.Player.Cooldowns;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportSequence {
    private final PlayerData playerData;
    private final Player player;
    private final int time;
    private final Location location;
    public TeleportSequence(PlayerData playerData, Location location, int time) {
        this.playerData = playerData;
        this.time = time;
        player = this.playerData.getPlayer();
        this.location = location;
        start();
    }

    private void start() {
        player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, (time + 5) * 20, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, (time + 1) * 20, 127, false, false));
        player.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0);
        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 100, 0);
        player.sendMessage(TextUtil.makeText("You travel through the void...", TextUtil.DARK_GRAY));
        loop();
    }

    private void loop() {
        new BukkitRunnable() {
            int timer = time;
            public void run() {
                if (CombatTimer.getInstance().isOnCooldown(player, false) || !Ascension.getInstance().getGameState().canTeleport(player)) {
                    canceled();
                    cancel();
                    return;
                }
                if (timer <= 0) {
                    stop();
                    cancel();
                    return;
                }
                if (timer <= 5) {
                    player.sendMessage(TextUtil.makeText(timer + "", TextUtil.GRAY));
//                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
                }
                timer--;
            }
        }.runTaskTimer(Ascension.getInstance(), 0, 20);
    }

    private void stop() {
        player.teleport(location);
        player.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0.41999998688697815);
        player.getWorld().playSound(location, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 3, 1);
    }

    private void canceled() {
        player.removePotionEffect(PotionEffectType.SLOWNESS);
        player.removePotionEffect(PotionEffectType.DARKNESS);
        player.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0.41999998688697815);
        player.getWorld().playSound(location, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 3, 2);
        player.showTitle(TextUtil.makeTitle(TextUtil.makeText("CANCELLED", TextUtil.RED), 0, 1, 1));
    }
}
