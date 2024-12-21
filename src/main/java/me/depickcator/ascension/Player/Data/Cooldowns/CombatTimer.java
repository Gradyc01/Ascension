package me.depickcator.ascension.Player.Data.Cooldowns;

import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.MainMenu.GiveMainMenuItem;
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
        return GiveMainMenuItem.getMenuItem();
    }

    @Override
    public void setCooldownTimer(Player p) {
        if (!p.hasCooldown(makeItem())) {
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
