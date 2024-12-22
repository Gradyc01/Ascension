package me.depickcator.ascension.Player.Cooldowns;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ScanBoardCooldown extends Cooldowns{
    private static ScanBoardCooldown instance;
    public ScanBoardCooldown() {
        super();
    }

    @Override
    public ItemStack makeItem() {
        return new ItemStack(Material.REINFORCED_DEEPSLATE);
    }

    @Override
    public void setCooldownTimer(Player p) {
        setCooldownTimer(p, 60);
    }

    public static ScanBoardCooldown getInstance() {
        if (instance == null) {
            instance = new ScanBoardCooldown();
        }
        return instance;
    }
}
