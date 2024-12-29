package me.depickcator.ascension.listeners.Combat;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Interfaces.ItemComparison;
import me.depickcator.ascension.Items.Craftable.Vanilla.*;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PlayerCombat extends ItemComparison implements Listener {
    protected final Ascension plugin;
    private final String damageSourceKey = "lastDamageSource";
    private final String PLAYER_DAMAGE = "PLAYER_DAMAGE";
    private HashMap<Material, Double> weaponDamageValues;
    private Set<EntityType> undead;
    private Set<EntityType> arthropods;
    public PlayerCombat() {
        this.plugin = Ascension.getInstance();
        initDamageValues();
    }

    public double getDamageValue(Material material) {
        return weaponDamageValues.getOrDefault(material, 1.0);
    }

    private void initDamageValues() {
        weaponDamageValues = new HashMap<>();
        weaponDamageValues.put(Material.WOODEN_AXE, WoodenAxe.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.STONE_AXE, StoneAxe.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.IRON_AXE, IronAxe.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.DIAMOND_AXE, DiamondAxe.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.NETHERITE_AXE, NetheriteAxe.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.GOLDEN_AXE, (double) 9);
        weaponDamageValues.put(Material.WOODEN_SWORD, WoodenSword.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.STONE_SWORD, StoneSword.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.IRON_SWORD, IronSword.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.DIAMOND_SWORD, DiamondSword.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.NETHERITE_SWORD, NetheriteSword.getInstance().getAttackDamage());
        weaponDamageValues.put(Material.GOLDEN_SWORD, (double) 4);
    }

    public Set<EntityType> getArthropods() {
        if (this.arthropods == null) {
            arthropods = new HashSet<>(Set.of(
                    EntityType.SPIDER,
                    EntityType.CAVE_SPIDER
            ));
        }
        return this.arthropods;
    }

    public Set<EntityType> getUndead() {
        if (this.undead == null) {
            undead = new HashSet<>(Set.of(
                    EntityType.ZOMBIE,
                    EntityType.ZOMBIE_VILLAGER,
                    EntityType.DROWNED,
                    EntityType.SKELETON,
                    EntityType.WITHER_SKELETON,
                    EntityType.STRAY,
                    EntityType.HUSK,
                    EntityType.ZOMBIFIED_PIGLIN,
                    EntityType.PHANTOM,
                    EntityType.WITHER
            ));
        }
        return this.undead;
    }

    public String getPLAYER_DAMAGE() {
        return PLAYER_DAMAGE;
    }

    public String getDamageSourceKey() {
        return damageSourceKey;
    }

    protected void addFinalAscensionTimer(PlayerData damager, int time) {
        if (plugin.getGameState().checkState(GameStates.GAME_FINAL_ASCENSION)) {
            damager.getPlayerTeam().getTeam().getTeamStats().addFinalAscensionTimer(time);
        }
    }
}
