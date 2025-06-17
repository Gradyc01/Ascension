package me.depickcator.ascension.MainMenuUI.Settings.Settings;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerStats;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVision extends SettingButton {
    public NightVision() {
        super("Night Vision", "Have permanent night vision", PlayerStats.nightVisionKey, Material.GOLDEN_CARROT);
    }

    @Override
    public boolean onClick(PlayerData pD, boolean newValue) {
        Player p = pD.getPlayer();
        if (newValue) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 0, false, false));
        } else {
            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
        return true;
    }
}
