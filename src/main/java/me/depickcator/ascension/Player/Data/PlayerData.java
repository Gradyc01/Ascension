package me.depickcator.ascension.Player.Data;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Uncraftable.KitBook;
import me.depickcator.ascension.Items.Uncraftable.MainMenu;
import me.depickcator.ascension.Items.Uncraftable.TeammateTracker;
import me.depickcator.ascension.Lobby.Lobby;
import me.depickcator.ascension.Player.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerData {
    private Player player;
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
    private final PlayerInventoryTracker playerInventoryTracker;
    private final PlayerBackpack playerBackpack;

    private final List<PlayerDataObservers> observers;

    private int playerState;

    /*Initializes a Player by associating a playerData with a player*/
    public PlayerData(Player player) {
        this.player = player;
        this.plugin = Ascension.getInstance();
        playerUnlocks = new PlayerUnlocks(this);
        playerTeam = new PlayerTeam(this.plugin, this);
        playerSkills = new PlayerSkills(this);
        playerStats = new PlayerStats(this);
        playerScoreboard = new PlayerScoreboard(this);
        playerInventoryTracker = new PlayerInventoryTracker(this);
        playerBackpack = new PlayerBackpack(this);
        initPlayerState();

        observers = new ArrayList<>(List.of(
                playerUnlocks,
                playerTeam,
                playerSkills,
                playerStats,
                playerScoreboard,
                playerInventoryTracker,
                playerBackpack
        ));
    }

    private void initPlayerState() {
        if (plugin.getGameState().inGame()) {
            playerState = STATE_SPECTATING;
        } else {
            playerState = STATE_ALIVE;
        }
        PlayerUtil.changePlayerVisibility(this);
    }

    /*Re-initializes the Player after they re-login or obtained a new player instance*/
    public void reInitPlayer(Player player) {
        this.player = player;
        for (PlayerDataObservers observer : observers) {
            observer.updatePlayer();
        }
        setPlayerState(STATE_DEAD);
        PlayerDeath.getInstance().setRespawningLater(this);
    }

    /*Resets the Player back to its lobby state*/
    public void resetToLobby() {
        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(20);
        freezePlayer(false);
        clearInventoryAndEffects();
        addLobbyPotionEffects();
        getMainMenuItem();

        plugin.getLobby().resetToLobby(this);

        Location loc = plugin.getLobby().getSpawn().clone();
        loc.setY(loc.getY() + 102);
//        Location loc = new Location(plugin.getSpawnWorld(), Ascension.getSpawn().getX(), Ascension.getSpawn().getY(), Ascension.getSpawn().getZ());
//        loc.setY(loc.getBlockY() + 104);
        player.teleport(loc);
        player.setGameMode(GameMode.SURVIVAL);
    }

    /*Resets the Player so that it can begin a new Game*/
    public void resetBeforeStartGame() {
        PlayerInventory inv = player.getInventory();
        inv.clear();
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, PotionEffect.INFINITE_DURATION, 0, false, false));
        getMainMenuItem();
        inv.setItem(6, TeammateTracker.getInstance().getResult());
        inv.setItem(7, KitBook.getInstance().getResult());
        player.setExperienceLevelAndProgress(0);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement revoke " + player.getName() + " everything");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "recipe give " + player.getName() + " *");
        giveStartingFood();
    }

    /*Gives the player the effects and movement it should have after the game countdown
    * Takes in gracePeriodDuration as input depending on how long Grace Period is*/
    public void resetAfterStartGame(int gracePeriodDuration) {
        PlayerUtil.clearEffects(this);
        addPlayerPotionEffect(PotionEffectType.SPEED, Math.min(gracePeriodDuration, 3), 1);
        addPlayerPotionEffect(PotionEffectType.HASTE, Math.min(gracePeriodDuration, 2), 1);
        addPlayerPotionEffect(PotionEffectType.HASTE, Math.min(gracePeriodDuration, 1), 2);
        addPlayerPotionEffect(PotionEffectType.ABSORPTION, gracePeriodDuration, 4);
        addPlayerPotionEffect(PotionEffectType.RESISTANCE, Math.min(gracePeriodDuration + 0.25, 1), 4);
        addPlayerPotionEffect(PotionEffectType.REGENERATION, 0.05, 9);
//        Objects.requireNonNull(player.getAttribute(Attribute.JUMP_STRENGTH)).setBaseValue(0.41999998688697815);
    }

    /*Freezes the player in place and doesn't allow them to move if 'freeze' is true else unfreezes them if 'freeze' is false*/
    public void freezePlayer(boolean freeze) {
        if (!freeze) {
            player.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.1);
            player.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0.41999998688697815);
        } else {
            player.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0);
            player.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0);
        }
    }


    private void addPlayerPotionEffect(PotionEffectType effect, double minutes, int amplifier) {
        player.addPotionEffect(new PotionEffect(effect, (int) (minutes * 60 * 20), amplifier, false, false));
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
        PlayerUtil.clearEffects(this);
        Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(20);
    }
    private void getMainMenuItem() {
        player.getInventory().setItem(8, MainMenu.getInstance().getResult());
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

    public PlayerInventoryTracker getPlayerInventoryTracker() {
        return playerInventoryTracker;
    }

    public PlayerBackpack getPlayerBackpack() {
        return playerBackpack;
    }

    public int getPlayerState() {
        return playerState;
    }

    /*Sets the player to Player State playerState*/
    public void setPlayerState(int playerState) {
        this.playerState = playerState;
        TextUtil.debugText(player.getName() + "is now State: " + playerState);
    }

    public boolean checkState(int state) {
        return playerState == state;
    }
}
