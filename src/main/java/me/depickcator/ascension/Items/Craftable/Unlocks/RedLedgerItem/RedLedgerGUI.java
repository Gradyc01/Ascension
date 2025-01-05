package me.depickcator.ascension.Items.Craftable.Unlocks.RedLedgerItem;

import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Cooldowns.TeleportSequence;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Teams.TeamUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RedLedgerGUI extends AscensionGUI {
    private HashMap<ItemStack, Team> teams;
    private HashMap<ItemStack, Player> players;
    public RedLedgerGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Red Ledger", TextUtil.AQUA), true);
        renderTeams();
        players = new HashMap<>();
    }

    public RedLedgerGUI(PlayerData playerData, Team team) {
        super(playerData, (char) 6, TextUtil.makeText("Red Ledger", TextUtil.AQUA), true);
        renderTeams();
        renderPlayers(team);
    }

    private void renderTeams() {
        List<Team> teams = TeamUtil.getEveryTeam(true);
        this.teams = new HashMap<>();
        int index = 0;
        for (Team team : teams) {
            Player p = team.getLeader();
            ItemStack item = makeTeamSkull(p, team);
            inventory.setItem(index, item);
            this.teams.put(item, team);
            index++;
        }
    }

    private void renderPlayers(Team team) {
        int index = 39;
        this.players = new HashMap<>();
        for (Player p : team.getTeamMembers()) {
            ItemStack item = makePlayerSkull(p);
            inventory.setItem(index, item);
            this.players.put(item, p);
            index++;
        }
    }

    private ItemStack makeTeamSkull(Player p, Team team) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setPlayerProfile(p.getPlayerProfile());
        meta.displayName(TextUtil.makeText(p.getName() + "'s Team", TextUtil.GOLD));
        List<Component> lore = new ArrayList<>(List.of(TextUtil.makeText("Members: ", TextUtil.DARK_PURPLE)));
        for (Player players : team.getTeamMembers()) {
            lore.add(TextUtil.makeText("  " + players.getName(), TextUtil.GOLD));
        }
        meta.lore(lore);
        meta.setCustomModelData(0x020000);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack makePlayerSkull(Player p) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setPlayerProfile(p.getPlayerProfile());
        meta.displayName(TextUtil.makeText(p.getName(), TextUtil.GOLD));
        meta.setCustomModelData(0x030000);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item != null && ItemClick.isHolding(player, RedLedger.getInstance().getResult())) {
            Team team = teams.get(item);
            if (CombatTimer.getInstance().isOnCooldown(player, false)) {
                event.setCancelled(true);
                return;
            }
            if (team != null) {
                new RedLedgerGUI(playerData, team);
                return;
            }
            Player p = players.get(item);
            if (p != null) {
                PlayerData pD = PlayerUtil.getPlayerData(p);
                if (pD == null || !pD.checkState(PlayerData.STATE_ALIVE)) {
                    TextUtil.errorMessage(player, "This player is currently not available");
                    return;
                }
                event.setCancelled(true);
                player.closeInventory();
                player.getInventory().getItemInMainHand().setAmount(0);
                new TeleportSequence(playerData, p.getLocation(), 20);
                return;
            }
            return;
        }
        TextUtil.errorMessage(player, "You must be holding the item");
        event.setCancelled(true);
        player.closeInventory();

    }
}
