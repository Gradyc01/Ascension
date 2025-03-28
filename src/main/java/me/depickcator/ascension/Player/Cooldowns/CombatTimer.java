package me.depickcator.ascension.Player.Cooldowns;

import me.depickcator.ascension.Items.Uncraftable.MainMenu;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CombatTimer extends Cooldowns {
//    private final ItemStack item;
    private static CombatTimer instance;
    private CombatTimer() {
        super();
    }

    @Override
    public ItemStack makeItem() {
        return MainMenu.getInstance().getResult();
    }

    @Override
    public void setCooldownTimer(Player p) {
        if (!p.hasCooldown(makeItem())) {
            p.playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_HURT,20f, 0.8f);
            p.sendMessage(TextUtil.makeText("You are in Combat!", TextUtil.DARK_RED));
        }
        setCooldownTimer(p, 20);
    }

    public static CombatTimer getInstance() {
        if (instance == null) {
            instance = new CombatTimer();
        }
        return instance;
    }


}
