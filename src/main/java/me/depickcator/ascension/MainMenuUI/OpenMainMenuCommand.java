package me.depickcator.ascension.MainMenuUI;

import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OpenMainMenuCommand implements CommandExecutor {
    //    public final static String menuName = "MAIN-MENU";
//    private int GUISize = 6 * 9;
//    private Inventory inventory;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player p = ((Player) commandSender).getPlayer();
//        inventory = Bukkit.createInventory(p,GUISize,TextUtil.makeText("Ascension", TextUtil.AQUA));
        PlayerData pD = PlayerUtil.getPlayerData(p);
        if (CombatTimer.getInstance().isOnCooldown(p) || pD == null) {
            return false;
        }
        if (!pD.checkState(PlayerData.STATE_ALIVE)) {
            TextUtil.errorMessage(p, "You can't use this command in your state!");
            return false;
        }
        new MainMenuGUI(pD);

        return true;
    }
}