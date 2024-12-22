package me.depickcator.ascension.Items.Craftable.Unlocks.TeamPortalItem;

import me.depickcator.ascension.General.ItemClick;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Cooldowns.TeleportCooldown;
import me.depickcator.ascension.Player.Cooldowns.TeleportSequence;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Teams.Team;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.List;

public class TeamPortalGUI extends AscensionGUI {

    private HashMap<ItemStack, Player> skullMap;
    public TeamPortalGUI(PlayerData playerData) {
        super(playerData, (char) 1, TextUtil.makeText("Team Portal", TextUtil.AQUA), false);
        setPlayerTeam();
    }

    private void setPlayerTeam() {
        Team team = playerData.getPlayerTeam().getTeam();
        skullMap = new HashMap<>();
        List<Player> players = team.getOtherTeamMembers(player);
        int i = 0;
        for (Player p : players) {
            ItemStack skull = makeSkull(p);
            inventory.setItem(i, skull);
            i++;
            skullMap.put(skull, p);
        }
    }

    private ItemStack makeSkull(Player p) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setPlayerProfile(p.getPlayerProfile());
        meta.displayName(TextUtil.makeText(p.getName(), TextUtil.GOLD));
//        meta.lore();
        meta.setCustomModelData(0x020000);
        item.setItemMeta(meta);
        return item;
    }


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (event.isLeftClick() && item != null && ItemClick.compareItems(player.getInventory().getItemInMainHand(), TeamPortal.getInstance().getItem())) {
            Player teammate = skullMap.get(item);
            if (teammate != null && !CombatTimer.getInstance().isOnCooldown(player, false)) {
//                TextUtil.debugText(teammate.getName() + " found teleport sequence should initiate");
                TeleportCooldown.getInstance().setCooldownTimer(player, 120);
                new TeleportSequence(playerData, teammate.getLocation(), 15);
            }
        }
        event.setCancelled(true);
        player.closeInventory();

    }
}

