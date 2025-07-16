package me.depickcator.ascension.Effects;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

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
        initialTextAnimation();
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
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 100, 1);
                }
                if (timer <= time - 4) {
                    displayTitle(time - timer);
                }
                timer--;
            }
        }.runTaskTimer(Ascension.getInstance(), 0, 20);
    }

    private void initialTextAnimation() {
        List<String> strings = List.of(".", "..", "...");

        new BukkitRunnable() {
            int timer = 10;
            @Override
            public void run() {
                if (timer > 0) {
                    String str = strings.get(timer%3);
                    Title title = TextUtil.makeTitle(TextUtil.makeText("Initializing" + str, TextUtil.GRAY),
                            0, 1, 1);
                    player.showTitle(title);
                } else {
                    cancel();
                    return;
                }
                timer--;
            }
        }.runTaskTimer(Ascension.getInstance(), 0, 4);
    }

    private void displayTitle(int timer) {
        Component text = TextUtil.makeText("[", TextUtil.WHITE);
        Component endText = TextUtil.makeText("]", TextUtil.WHITE);
        Component red = TextUtil.makeText(":", TextUtil.RED);
        Component green = TextUtil.makeText(":", TextUtil.GREEN);
        int score = Math.round(25 * ((float) timer / (float) time));
        for (int i = 0; i < 25; i++) {
            if (i < score) text = text.append(green);
            else text = text.append(red);
        }
        Title title = TextUtil.makeTitle(TextUtil.makeText(""),text.append(endText), 0, 1.3, 0);
        player.showTitle(title);
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
