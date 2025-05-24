package me.depickcator.ascension.Items.Uncraftable;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EnderPearl extends CustomItem implements ItemClick {
    private static EnderPearl instance;
    private EnderPearl() {
        super("Ender Pearl", "ender_pearl");
        registerItem();
    }
    @Override
    public ItemStack getItem() {
        return getResult();
    }


    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (!Ascension.getInstance().getGameState().canTeleport(pD.getPlayer())) {
            e.setCancelled(true);
            return false;
        }
        return true;
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }

    public static EnderPearl getInstance() {
        if (instance == null) {
            instance = new EnderPearl();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.ENDER_PEARL);
    }
}
