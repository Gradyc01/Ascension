package org.bingo4.MainMenuUI.Command.AnchorPoints;

import org.bingo4.Bingo4;
import org.bingo4.Effects.TeleportSequence;
import org.bingo4.Player.Cooldowns.CombatTimer;
import org.bingo4.Player.Cooldowns.TeleportCooldown;
import org.bingo4.Player.Data.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnchorPoint {
    private final String name;
    private final Location location;
    private final Material material;
    public AnchorPoint(Location location) {
        this.location = location;
        material = generateMaterial();
        this.name = generateName(location);
    }

    private String generateName(Location location) {
        List<String> strings = new ArrayList<>(List.of(
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1",  "2", "3", "4",
                "5", "6", "7", "8", "9", "0"));
        String str = location.getWorld().getBiome(location).getKey().getKey() + " - ";
        Random random = new Random();
        str += strings.get(random.nextInt(strings.size()));
        str += strings.get(random.nextInt(strings.size()));
        return str.toUpperCase();
    }

    private Material generateMaterial() {
        List<Material> materials = List.of(Material.GRASS_BLOCK, Material.GRAVEL, Material.WATER_BUCKET,
                Material.APPLE, Material.LAVA_BUCKET, Material.ANDESITE, Material.STONE, Material.CACTUS,
                Material.BONE, Material.FURNACE, Material.CRAFTING_TABLE);
        Random random = new Random();
        return materials.get(random.nextInt(materials.size()));
    }

    public boolean teleport(PlayerData playerData) {
        Player player = playerData.getPlayer();
        if (!Bingo4.getInstance().getGameState().canTeleport(player)) return false;
        if (TeleportCooldown.getInstance().isOnCooldown(player)) return false;
        if (CombatTimer.getInstance().isOnCooldown(player)) return false;
        TeleportCooldown.getInstance().setCooldownTimer(player, 45);
        new TeleportSequence(playerData, location, 30);
        return true;
    }

    public String getName() {
        return name;
    }

    public String getCoordinateString() {
        return "(" + location.getBlockX() + ", " +
                location.getBlockY() + ", " + location.getBlockZ() + ")";
    }

    public Material getMaterial() {
        return material;
    }
}
