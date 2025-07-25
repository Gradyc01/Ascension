package me.depickcator.ascension.Player.Data;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.BackpackGUI;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerBackpack implements PlayerDataObservers{
    private final PlayerData playerData;
    private Player player;
    private final Ascension plugin;
    private BackpackGUI backpackGUI;
    private int backpackLevel;
    public PlayerBackpack(PlayerData playerData) {
        plugin = Ascension.getInstance();
        this.playerData = playerData;
        this.player = playerData.getPlayer();
        this.backpackLevel = 0;
        this.backpackGUI = new BackpackGUI(playerData, backpackLevel);
    }

    @Override
    public void updatePlayer() {
        player = playerData.getPlayer();
    }

    public void openBackpack() {
        if (!plugin.getGameState().inGame() ) {
            errorMessage("You are not currently in a game!");
        } else if (CombatTimer.getInstance().isOnCooldown(player)) {//Does Nothing on purpose
        } else {
            backpackGUI.open(player);
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 1.0f, 1.0f);
        }
    }

    public void upgradeBackpack() {
        backpackLevel++;
        backpackGUI = new BackpackGUI(playerData, backpackLevel, backpackGUI.getInventory());
    }

    private void errorMessage(String message) {
        TextUtil.errorMessage(player, message);
    }
}
