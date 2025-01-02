package me.depickcator.ascension.Teams;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TeamBackpack {

    private final Inventory inventory;
    private final Team team;
    public TeamBackpack(Team team) {
        this.team = team;
        inventory = Bukkit.createInventory(this.team.getLeader(),
                3 * 9,
                TextUtil.makeText(team.getLeader().getName() + "'s Team Backpack", TextUtil.AQUA));
    }

    public boolean openInventory(PlayerData playerData) {
        Player p = playerData.getPlayer();
        if (Ascension.getInstance().getGameState().checkState(GameStates.GAME_BEFORE_GRACE)) {
            if (CombatTimer.getInstance().isOnCooldown(p)) return false;
            p.openInventory(inventory);
            p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 10f, 1f);
            TextUtil.broadcastMessage(
                    TextUtil.makeText(p.getName() + " opened the team backpack", TextUtil.AQUA),
                    team.getOtherTeamMembers(p));
            return true;
        }
        TextUtil.errorMessage(p, "You can not use the team backpack at this moment");
        return false;
    }
}
