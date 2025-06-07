package me.depickcator.ascension.Effects;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class NatureWrath {
    private final PlayerData playerData;
    private final Player player;
    private final int seconds;
    private final World world;
    private final PlayerData attackerData;
    public NatureWrath(PlayerData playerData, PlayerData attacker, int seconds) {
        this.playerData = playerData;
        player = this.playerData.getPlayer();
        world = this.player.getWorld();
        attackerData = attacker;
        this.seconds = seconds;
        start();
    }



    private void start() {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, seconds * 20, 9, false, false));
//        player.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0);
        player.playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1.0f, 2.0f);
        player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_RETURN, 100, 2);
//        player.sendMessage(TextUtil.makeText("You travel through the void...", TextUtil.DARK_GRAY));
        loop();
    }

    private void loop() {
        Component text = TextUtil.makeText("SMITED", TextUtil.GRAY);
        DamageSource source = DamageSource.builder(DamageType.LIGHTNING_BOLT).build();
        player.showTitle(TextUtil.makeTitle(text, 0, seconds - 2, 2));
        new BukkitRunnable() {
            int timer = seconds;
            public void run() {
                if (!playerData.checkState(PlayerData.STATE_ALIVE)) {
                    canceled();
                    cancel();
                    return;
                }
                if (timer <= 0) {
                    stop();
                    cancel();
                    return;
                }
                player.sendMessage(text);
                Location loc = player.getLocation();
                player.playSound(loc, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1.0f, 2.0f);
                player.setHealth(Double.max(0.1, player.getHealth() - 2.5));
                world.strikeLightningEffect(loc);
                world.playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0F, 1.0F);
                world.playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.0F, 0.0F);
//                world.spawnEntity(player.getLocation(), EntityType.LIGHTNING_BOLT);
                player.damage(1, source);

                timer--;
            }
        }.runTaskTimer(Ascension.getInstance(), 0, 20);
    }

    private void stop() {

    }

    private void canceled() {
    }
}
