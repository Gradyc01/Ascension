package me.depickcator.ascensionBingo.Player;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.mainMenu.GiveMainMenuItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import me.depickcator.ascensionBingo.Teams.Team;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class PlayerData {
    private Player player;
    private AscensionBingo plugin;

    //Unlocks
    private PlayerUnlocks playerUnlocks;
    //TEAMS
    private Boolean pendingTeamInvite;
    private PlayerData inviteFromWho;
    private Team team;

    public PlayerData(Player player, AscensionBingo plugin) {
        this.player = player;
        this.plugin = plugin;
        playerUnlocks = new PlayerUnlocks(this.plugin, this.player);
        pendingTeamInvite = false;
        team = null;
    }

    public void resetToLobby() {
        clearInventoryAndEffects();
        addLobbyPotionEffects();
        getMainMenuItem();
        Location loc = new Location(plugin.getWorld(), AscensionBingo.getSpawn().getX(), AscensionBingo.getSpawn().getY(), AscensionBingo.getSpawn().getZ());
        loc.setY(loc.getBlockY() + 104);
        player.teleport(loc);
    }

    public void resetBeforeStartGame() {
        clearInventoryAndEffects();
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 128, false, false));
        getMainMenuItem();
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_JUMP_STRENGTH)).setBaseValue(0);
        player.setExperienceLevelAndProgress(0);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement revoke @s everything");
    }

    public void resetAfterStartGame() {
        clearInventoryAndEffects();
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120 * 20, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 60 * 20, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 30 * 60 * 20, 4, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 60 * 20, 9, false, false));
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_JUMP_STRENGTH)).setBaseValue(0.41999998688697815);
        getMainMenuItem();
        giveStartingFood();
    }

    private void giveStartingFood() {
        ItemStack food = new ItemStack(Material.COOKED_BEEF, 64);
        ItemMeta meta = food.getItemMeta();
        meta.addEnchant(Enchantment.INFINITY, 1, true);
        food.setItemMeta(meta);
        player.getInventory().addItem(food);
    }

    private void clearInventoryAndEffects() {
        player.getInventory().clear();
        player.clearActivePotionEffects();
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20);
    }

    private void getMainMenuItem() {
        player.getInventory().setItem(8, GiveMainMenuItem.getMenuItem());
    }

    private void addLobbyPotionEffects() {
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, Integer.MAX_VALUE, 4, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0, false, false));
    }

    public void sendInvite(Player p) {

        team = createOrGetTeam();
        PlayerData playerData = PlayerUtil.getPlayerData(p);
        if (playerData == null) {
            throw new NullPointerException("PlayerData is null!");
        }
        if (playerData.getPendingTeamInvite()) {
            player.sendMessage(p.getName() + " already has a team invite from somewhere else");
            return;
        }
        playerData.setInviteFromWho(this);
        playerData.setPendingTeamInvite(true);
        p.sendMessage("You have an invite!");
    }

    public void acceptInvite() {
        if (team != null) {
            player.sendMessage("Already on a team must leave before joining another team!");
            return;
        }
        if (getPendingTeamInvite()) {
            PlayerData sender = getInviteFromWho();
            pendingTeamInvite = false;
            inviteFromWho = null;
            try {
                sender.getTeam().addPlayer(player);
            } catch (NullPointerException e) {
                player.sendMessage("Party no longer exists");
                pendingTeamInvite = false;
                inviteFromWho = null;
            }

        }
    }

    public void rejectInvite() {
        if (getPendingTeamInvite()) {
            PlayerData sender = getInviteFromWho();
            sender.getTeam().announceToAllTeamMembers(player.getName() + " has rejected your party invite");
            pendingTeamInvite = false;
            inviteFromWho = null;
        } else {
            player.sendMessage("You do not have an invite from anyone");
        }
    }

    public void leaveTeam() {
        if (team == null) {
            player.sendMessage("You must be on a team to be able to leave the team");
            return;
        }
        team.removePlayer(player);
    }

    public Team createOrGetTeam() {
        if (team != null) {
            return team;
        }
        team = new Team(plugin, player);
        return team;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team t) {
        team = t;
    }

    public Boolean getPendingTeamInvite() {
        return pendingTeamInvite;
    }

    public void setPendingTeamInvite(Boolean teamInvite) {
        pendingTeamInvite = teamInvite;
    }

    public PlayerData getInviteFromWho() {
        return inviteFromWho;
    }

    public void setInviteFromWho(PlayerData inviteFromWho) {
        this.inviteFromWho = inviteFromWho;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerUnlocks getPlayerUnlocks() {
        return playerUnlocks;
    }
}
