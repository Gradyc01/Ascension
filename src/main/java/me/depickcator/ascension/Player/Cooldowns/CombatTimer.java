package me.depickcator.ascension.Player.Cooldowns;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Uncraftable.MainMenu;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class CombatTimer extends Cooldowns {
//    private final ItemStack item;
    private static CombatTimer instance;
    private final int cooldownTime; // In Seconds
    private CombatTimer() {
        super("Combat");
        cooldownTime = 20;
    }

    @Override
    public ItemStack makeItem() {
        return MainMenu.getInstance().getResult();
    }

    @Override
    public void setCooldownTimer(Player p) {
        if (!p.hasCooldown(makeItem())) {
            p.playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_HURT,20f, 0.8f);
            Component text = TextUtil.makeText("You are in Combat!", TextUtil.DARK_RED);
            p.sendMessage(text);
            TextUtil.sendActionBar(p, text, 60, Ascension.getInstance());
        }
        setCooldownTimer(p, cooldownTime, Material.IRON_SWORD);
    }



    public static CombatTimer getInstance() {
        if (instance == null) {
            instance = new CombatTimer();
        }
        return instance;
    }


}
