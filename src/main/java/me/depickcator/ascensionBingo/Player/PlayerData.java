package me.depickcator.ascensionBingo.Player;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.mainMenu.GiveMainMenuItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class PlayerData {
    private final Player player;
    private final AscensionBingo plugin;

    //Extra Player Data
    private final PlayerUnlocks playerUnlocks;
    private final PlayerScoreboard playerScoreboard;
    private final PlayerTeam playerTeam;
    private final PlayerSkills playerSkills;
    private final PlayerStats playerStats;

    //Stats
    private int playerKills;

    public PlayerData(Player player, AscensionBingo plugin) {
        this.player = player;
        this.plugin = plugin;
        playerUnlocks = new PlayerUnlocks(this.plugin, this);
        playerTeam = new PlayerTeam(this.plugin, this);
        playerSkills = new PlayerSkills(this, plugin);
        playerStats = new PlayerStats(this.plugin, this);
        playerScoreboard = new PlayerScoreboard(this.plugin, this);
        playerKills = 0;
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
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2 * 20, 9, false, false));
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

    public PlayerTeam getPlayerTeam() {
        return playerTeam;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerUnlocks getPlayerUnlocks() {
        return playerUnlocks;
    }

    public PlayerSkills getPlayerSkills() {
        return playerSkills;
    }

    public PlayerScoreboard getPlayerScoreboard() {
        return playerScoreboard;
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }
}
