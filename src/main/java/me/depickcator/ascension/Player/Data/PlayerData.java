package me.depickcator.ascension.Player.Data;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Uncraftable.KitBook;
import me.depickcator.ascension.Items.Uncraftable.MainMenu;
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

    private final List<PlayerDataObservers> observers;

    private int playerState;

    //Stats
    public PlayerData(Player player) {
        this.player = player;
        this.plugin = Ascension.getInstance();
        playerUnlocks = new PlayerUnlocks(this);
        playerTeam = new PlayerTeam(this.plugin, this);
        playerSkills = new PlayerSkills(this);
        playerStats = new PlayerStats(this);
        playerScoreboard = new PlayerScoreboard(this);
        initPlayerState();

        observers = new ArrayList<>(List.of(
                playerUnlocks,
                playerTeam,
                playerSkills,
                playerStats,
                playerScoreboard
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

    public void reInitPlayer(Player player) {
        this.player = player;
        for (PlayerDataObservers observer : observers) {
            observer.updatePlayer();
        }
        setPlayerState(STATE_DEAD);
        PlayerDeath.getInstance().setRespawningLater(this);
    }

    public void resetToLobby() {
        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(20);
        clearInventoryAndEffects();
        addLobbyPotionEffects();
        getMainMenuItem();

        Lobby lobby = plugin.getLobby();
        lobby.getEventBoard().showDefaultBoard(this);
        lobby.getBoardDisplay().displayBoard(player, player);

        Location loc = new Location(plugin.getWorld(), Ascension.getSpawn().getX(), Ascension.getSpawn().getY(), Ascension.getSpawn().getZ());
        loc.setY(loc.getBlockY() + 104);
        player.teleport(loc);
        player.setGameMode(GameMode.SURVIVAL);
    }
    public void resetBeforeStartGame() {
//        clearInventoryAndEffects();
        player.getInventory().clear();
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, PotionEffect.INFINITE_DURATION, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, PotionEffect.INFINITE_DURATION, 128, false, false));
        getMainMenuItem();
        getKitBook();
//        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_JUMP_STRENGTH)).setBaseValue(0);
        player.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0);
        player.setExperienceLevelAndProgress(0);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement revoke " + player.getName() + " everything");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "recipe give " + player.getName() + " *");
        giveStartingFood();
    }
    public void resetAfterStartGame(int gracePeriodDuration) {
        PlayerUtil.clearEffects(this);
//        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Math.max(gracePeriodDuration, 3) * 60 * 20, 1));
//        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 60 * 20, 2));
//        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, gracePeriodDuration * 60 * 20, 4, false, false));
//        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 60 * 20, 9, false, false));
//        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2 * 20, 9, false, false));
        addPlayerPotionEffect(player, PotionEffectType.SPEED, Math.min(gracePeriodDuration, 3), 1);
        addPlayerPotionEffect(player, PotionEffectType.HASTE, Math.min(gracePeriodDuration, 1), 2);
        addPlayerPotionEffect(player, PotionEffectType.ABSORPTION, gracePeriodDuration, 4);
        addPlayerPotionEffect(player, PotionEffectType.ABSORPTION, Math.min(gracePeriodDuration, 1), 9);
        addPlayerPotionEffect(player, PotionEffectType.REGENERATION, 0.05, 9);
        Objects.requireNonNull(player.getAttribute(Attribute.JUMP_STRENGTH)).setBaseValue(0.41999998688697815);
    }

    private void addPlayerPotionEffect(Player p, PotionEffectType effect, double minutes, int amplifier) {
        p.addPotionEffect(new PotionEffect(effect, (int) (minutes * 60 * 20), amplifier, false, false));
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
    private void getKitBook() {
        player.getInventory().setItem(7, KitBook.getInstance().getResult());
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
        TextUtil.debugText(player.getName() + "is now State: " + playerState);
    }

    public boolean checkState(int state) {
        return playerState == state;
    }
}
