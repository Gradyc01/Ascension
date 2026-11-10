package org.bingo4.Items.Uncraftable;

import org.bingo4.Bingo4;
import org.bingo4.Interfaces.ItemClick;
import org.bingo4.Items.Craftable.Unlocks.MasterCompass.CompassAbilities;
import org.bingo4.Items.CustomItem;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.ArrayList;
import java.util.List;

public class TeammateTracker extends CustomItem implements ItemClick {
    private static TeammateTracker instance;

    private TeammateTracker() {
        super("Teammate Tracker", "teammate_tracker");
        registerItem();
    }

    public static TeammateTracker getInstance() {
        if (instance == null) {
            instance = new TeammateTracker();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.COMPASS, 1);
        CompassMeta meta = (CompassMeta) item.getItemMeta();
        meta.setCustomModelData(Bingo4.getInstance().generateModelNumber());
        meta.setLodestoneTracked(false);
        meta.setLodestone(Bingo4.getSpawn());
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.GREEN).append(TextUtil.clickText()));
        meta.setEnchantmentGlintOverride(false);
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Be able to locate your teammates", TextUtil.DARK_PURPLE)
        ));
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public ItemStack getItem() {
        return getResult();
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        Action a = e.getAction();
        CompassAbilities compassAbilities = new CompassAbilities(e.getItem(), 0);
        if (a.isLeftClick()) {
            Player p = findNextPlayer(compassAbilities.getTrackedPlayer(), pD);
            if (p != null) compassAbilities.trackPlayer(pD.getPlayer(), p);
        } else if (a.isRightClick()) {
            compassAbilities.track(pD.getPlayer());
        }
        return true;
    }

    private Player findNextPlayer(Player currentTarget, PlayerData playerData) {
        Player player = playerData.getPlayer();
//        Player currentTarget = compassAbilities.getTrackedPlayer();
        List<Player> teamMembers = playerData.getPlayerTeam().getTeam().getOtherTeamMembers(player);
        if (teamMembers.isEmpty() || (teamMembers.size() == 1 && currentTarget != null)) {
            player.sendMessage(TextUtil.makeText("There is no one to track", TextUtil.RED));
            return null;
        }
        if (currentTarget == null) {
            return teamMembers.getFirst();
        }
        int index = teamMembers.indexOf(currentTarget);
        int newIndex = index + 1 >= teamMembers.size() ? 0 : index + 1;
        return teamMembers.get(newIndex);
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }
}
