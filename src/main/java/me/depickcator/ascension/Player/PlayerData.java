package me.depickcator.ascension.Player;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Uncraftable.KitBook;
import me.depickcator.ascension.mainMenu.GiveMainMenuItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
    private final Ascension plugin;
    public final static int STATE_ALIVE = 1;
    public final static int STATE_DEAD = 2;
    public final static int STATE_SPECTATING = 3;

    //Extra Player Data
    private final PlayerUnlocks playerUnlocks;
    private final PlayerScoreboard playerScoreboard;
    private final PlayerTeam playerTeam;
    private final PlayerSkills playerSkills;
    private final PlayerStats playerStats;

    private int playerState;

    //Stats
    public PlayerData(Player player) {
        this.player = player;
        this.plugin = Ascension.getInstance();
        playerUnlocks = new PlayerUnlocks(this);
        playerTeam = new PlayerTeam(this.plugin, this);
        playerSkills = new PlayerSkills(this);
        playerStats = new PlayerStats(this);
        playerScoreboard = new PlayerScoreboard(this.plugin, this);
        initPlayerState();
    }

    private void initPlayerState() {
        if (plugin.getGameState().inGame()) {
            playerState = STATE_SPECTATING;
        } else {
            playerState = STATE_ALIVE;
        }
    }

    public void resetToLobby() {
        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(20);
        clearInventoryAndEffects();
        addLobbyPotionEffects();
        getMainMenuItem();
        Location loc = new Location(plugin.getWorld(), Ascension.getSpawn().getX(), Ascension.getSpawn().getY(), Ascension.getSpawn().getZ());
        loc.setY(loc.getBlockY() + 104);
        player.teleport(loc);
        player.setGameMode(GameMode.SURVIVAL);
    }
    public void resetBeforeStartGame() {
        clearInventoryAndEffects();
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, PotionEffect.INFINITE_DURATION, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, PotionEffect.INFINITE_DURATION, 128, false, false));
        getMainMenuItem();
        getKitBook();
//        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_JUMP_STRENGTH)).setBaseValue(0);
        player.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0);
        player.setExperienceLevelAndProgress(0);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement revoke " + player.getName() + " everything");
    }
    public void resetAfterStartGame() {
        player.clearActivePotionEffects();
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120 * 20, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 60 * 20, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 30 * 60 * 20, 4, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 60 * 20, 9, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2 * 20, 9, false, false));
        Objects.requireNonNull(player.getAttribute(Attribute.JUMP_STRENGTH)).setBaseValue(0.41999998688697815);
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
        Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(20);
    }
    private void getMainMenuItem() {
        player.getInventory().setItem(8, GiveMainMenuItem.getMenuItem());
    }
    private void getKitBook() {
        player.getInventory().setItem(7, KitBook.item());
    }
    private void addLobbyPotionEffects() {
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, PotionEffect.INFINITE_DURATION, 4, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, PotionEffect.INFINITE_DURATION, 0, false, false));
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

    public int getPlayerState() {
        return playerState;
    }

    public void setPlayerState(int playerState) {
        this.playerState = playerState;
    }

    public boolean checkState(int state) {
        return playerState == state;
    }
}
