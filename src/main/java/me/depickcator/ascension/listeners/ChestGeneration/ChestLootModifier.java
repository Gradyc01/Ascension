package me.depickcator.ascension.listeners.ChestGeneration;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Items.Uncraftable.NetherStar.NetherStar;
import me.depickcator.ascension.Skills.SkillExpAmount;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.listeners.ChestGeneration.Tables.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Vault;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseLootEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ChestLootModifier implements Listener {
    private HashMap<String, ChestLootTable> tables;
    public ChestLootModifier() {
        initChestLootTables();
    }

    private void initChestLootTables() {
        tables = new HashMap<>();
        addLootTable(new Dungeon());
        addLootTable(new Mineshaft());
        addLootTable(new OminousTrialsLoot());
        addLootTable(new PillagerOutpost());
        addLootTable(new BastionBridge());
        addLootTable(new BastionTreasure());
        addLootTable(new DesertPyramid());
        addLootTable(new CommonTrialsLoot());
    }

    private void addLootTable(ChestLootTable table) {
        tables.put(table.getLootTableName(), table);
    }

    @EventHandler
    public void lootTableGenerate(LootGenerateEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (Ascension.getInstance().getGameState().checkState(GameStates.UNLOADED)) return;

        Player player = (Player) event.getEntity();
        PlayerData playerData = PlayerUtil.getPlayerData(player);
        ChestLootTable table = tables.get(event.getLootTable().getKey().toString());
        event.getLoot().add(NetherStar.getInstance().getResult());
        if (table == null) {
            TextUtil.debugText("Table is not found within the Dictionary");
            playerData.getPlayerSkills().getForaging().addExp(SkillExpAmount.FORAGING_RARE.getExp());
            return;
        }
        table.addLootToTable(playerData, event.getLoot());
    }

    @EventHandler
    public void lootTableDispensed(BlockDispenseLootEvent event) {
        if (Ascension.getInstance().getGameState().checkState(GameStates.UNLOADED)) return;

        Player player = event.getPlayer();
        PlayerData playerData = player == null ? null : PlayerUtil.getPlayerData(player);
        if (event.getBlock().getType() == Material.VAULT && playerData != null) {
            modifyVaultLootTables(event, playerData);
        } else if (event.getBlock().getType() == Material.TRIAL_SPAWNER) {
            Block b = event.getBlock();
            b.getLocation().getWorld().dropItem(b.getLocation().add(0, 2, 0), new ItemStack(Material.TRIAL_KEY));
        }

    }

    private void modifyVaultLootTables(BlockDispenseLootEvent event, PlayerData playerData) {
        Vault vault = (Vault) event.getBlock().getState();
        ChestLootTable table = tables.get(vault.getLootTable().getKey().toString());
        event.getDispensedLoot().add(NetherStar.getInstance().getResult());
        TextUtil.debugText("Attempting to Generate Loot");
        if (table == null) {
            TextUtil.debugText("Table " + vault.getLootTable().getKey() +  " is not found within the Dictionary");
            playerData.getPlayerSkills().getForaging().addExp(SkillExpAmount.FORAGING_RARE.getExp());
            return;
        }
        TextUtil.debugText("Table found");
        table.addLootToTable(playerData, event.getDispensedLoot());
    }
}
