package me.depickcator.ascension.Items.Craftable.Unlocks.MasterCompass;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Cooldowns.Cooldowns;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class CompassAbilities extends Cooldowns {
    private final ItemStack compass;
    private final NamespacedKey tracked_player;
    private final Component trackingText;
    private final Material fuelMaterial;
    private final int fuelAmount;

    public CompassAbilities(ItemStack compass, Material fuelMaterial, int fuelAmount) {
        this.compass = compass;
        trackingText = TextUtil.makeText("Tracking Player: ", TextUtil.DARK_PURPLE);
        tracked_player = new NamespacedKey(Ascension.getInstance(), "tracked_player");
        this.fuelMaterial = fuelMaterial;
        this.fuelAmount = fuelAmount;
    }

    public CompassAbilities(ItemStack compass, int fuelAmount) {
        this(compass, Material.REDSTONE, fuelAmount);
    }

    public CompassAbilities(ItemStack compass) {
        this(compass, Material.REDSTONE, 2);
    }

    public Player getTrackedPlayer() {
        ItemMeta meta = compass.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        String UUID = container.get(tracked_player, PersistentDataType.STRING);
        if (UUID == null) return null;
        return Bukkit.getPlayer(java.util.UUID.fromString(UUID));
    }

    //Tracks player p
    public void trackPlayer(Player tracker, Player target) {
        CompassMeta meta = (CompassMeta) compass.getItemMeta();
//        if (meta.lore() == null || meta.lore().isEmpty()) initLore();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(tracked_player, PersistentDataType.STRING, target.getUniqueId().toString());
        compass.setItemMeta(setLore(meta, target));
        tracker.sendMessage(TextUtil.makeText("Now Tracking: ", TextUtil.DARK_GREEN).append(TextUtil.makeText(target.getName(), TextUtil.GOLD)));
        SoundUtil.playHighPitchPling(tracker);
    }

    //Tracks nearest player for PlayerData playerData
    public boolean trackNearestPlayer(PlayerData playerData) {
        Player player = playerData.getPlayer();
        PlayerData nearest = null;
        int distance = Integer.MAX_VALUE;
        for (Player p : player.getLocation().getNearbyPlayers(1000)) {
            PlayerData pD = PlayerUtil.getPlayerData(p);
            if (pD.getPlayerTeam().getTeam().equals(playerData.getPlayerTeam().getTeam())) continue;
            if (!p.getLocation().getWorld().equals(player.getLocation().getWorld())) continue;
            int dist = (int) p.getLocation().distanceSquared(player.getLocation());;
            if (dist < distance) {
                nearest = pD;
                distance = dist;
            }
        }

        if (nearest == null) {
            TextUtil.errorMessage(player, "There seems to be no one near your location");
            return false;
        }
        trackPlayer(player, nearest.getPlayer());
        return true;
    }

    public boolean track(Player tracker) {
        CompassMeta meta = (CompassMeta) compass.getItemMeta();

        Player p = getTrackedPlayer();
        if (p == null) {
            sendFailedMessage(tracker, "There is no player current set to be tracked");
            return false;
        }

        if (!playerIsTrackable(tracker, p) || !hasFuel(tracker) || isOnCooldown(tracker)) return false;

        removeFuel(tracker);
        setCooldownTimer(tracker);
        sendDistanceMessage(tracker, p);

        meta.setLodestone(p.getLocation());
        compass.setItemMeta(meta);
        return true;
    }

    private void sendDistanceMessage(Player tracker, Player p) {
        int distance = (int) tracker.getLocation().distance(p.getLocation());
        Component distanceText = TextUtil.makeText(distance + "", TextUtil.GOLD);
        Component text = TextUtil.makeText(p.getName() + " is ", TextUtil.DARK_GREEN)
                .append(distanceText)
                .append(TextUtil.makeText( " blocks away", TextUtil.DARK_GREEN));
        tracker.playSound(tracker.getLocation(), Sound.UI_BUTTON_CLICK, 1.9f, 1.9f);
        TextUtil.sendActionBar(tracker, text, 20, Ascension.getInstance());
    }

    private void removeFuel(Player tracker) {
        PlayerInventory inv = tracker.getInventory();
        int fuelRequirementRemaining = fuelAmount;
        for (ItemStack item : inv.getContents()) {
            if (item == null) continue;
            if (item.getType() == fuelMaterial) {
                if (fuelRequirementRemaining <= item.getAmount()) {
                    item.setAmount(item.getAmount() - fuelRequirementRemaining);
                    return;
                } else {
                    fuelRequirementRemaining -= item.getAmount();
                    item.setAmount(0);
                }
            }
        }
    }

    private boolean hasFuel(Player tracker) {
        PlayerInventory inv = tracker.getInventory();
        if (inv.contains(fuelMaterial, fuelAmount)) {
            return true;
        } else {
            sendFailedMessage(tracker, "You do not have enough fuel");
            return false;
        }
    }

    private boolean playerIsTrackable(Player tracker, Player target) {
        PlayerData pD = PlayerUtil.getPlayerData(target);
        if (pD.getPlayerState() != PlayerData.STATE_ALIVE) {
            sendFailedMessage(tracker, target.getName() + " can not be tracked at the current moment");
            return false;
        }

        if (!target.getLocation().getWorld().equals(tracker.getLocation().getWorld())) {
            sendFailedMessage(tracker, target.getName() + " is in a different dimension");
            return false;
        }
        return true;
    }

    private void sendFailedMessage(Player p, String text) {
        TextUtil.sendActionBar(p, TextUtil.makeText(text, TextUtil.RED), 20, Ascension.getInstance());
        SoundUtil.playErrorSoundEffect(p);
    }

    private CompassMeta setLore(CompassMeta meta, Player p) {
        List<Component> lore = new ArrayList<>(List.of(
                trackingText.append(TextUtil.makeText(p.getName(), TextUtil.GOLD))
        ));
        meta.lore(lore);
        return meta;
    }

    public ItemStack getCompass() {
        return compass;
    }

    @Override
    public ItemStack makeItem() {
        return new ItemStack(Material.COMPASS);
    }

    @Override
    public void setCooldownTimer(Player p) {
        if (CombatTimer.getInstance().isOnCooldown(p, false)) {
            setCooldownTimer(p, 15);
        }
        setCooldownTimer(p, 5);
    }
}
