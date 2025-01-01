package me.depickcator.ascension.MainMenuUI.Command.Commands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Cooldowns.TeleportCooldown;
import me.depickcator.ascension.Player.Cooldowns.TeleportSequence;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class SpawnTravel implements Commands {
    public final static String NAME = "spawn_travel";
    private final ItemStack item;
    private final Ascension plugin;
    public SpawnTravel() {
        item = makeButton();
        this.plugin = Ascension.getInstance();
    }
    @Override
    public void uponEvent(InventoryClickEvent event, PlayerData playerData) {
        Player p = playerData.getPlayer();
        if (!plugin.getGameState().canTeleport(p) ||
                TeleportCooldown.getInstance().isOnCooldown(p)) {
//            TextUtil.errorMessage(p, "You can't currently use this command");
            return;
        }
        Location spawn = Ascension.getSpawn();
        Random rand = new Random();
        int xM = rand.nextInt(2) == 1 ? 1 : -1;
        int zM = rand.nextInt(2) == 1 ? 1 : -1;
        int x = spawn.getBlockX() + rand.nextInt(35, 500) * xM;
        int z = spawn.getBlockZ() + rand.nextInt(35, 500) * zM;
        Location newLoc = plugin.getWorld().getHighestBlockAt(x, z).getLocation();
        TeleportCooldown.getInstance().setCooldownTimer(playerData.getPlayer());
        new TeleportSequence(playerData, newLoc.add(0, 1, 0), 15);

    }

    @Override
    public ItemStack getButton() {
        return item;
    }

    private ItemStack makeButton() {
        ItemStack item = new ItemStack(Material.FILLED_MAP);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(TextUtil.makeText("Spawn Travel", TextUtil.AQUA));
        item.setItemMeta(itemMeta);
        return item;
    }
}
