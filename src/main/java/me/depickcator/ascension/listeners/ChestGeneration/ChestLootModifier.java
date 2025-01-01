package me.depickcator.ascension.listeners.ChestGeneration;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Skills.SkillExpAmount;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.listeners.ChestGeneration.Tables.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
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
        if (table == null) {
            TextUtil.debugText("Table is not found within the Dictionary");
            playerData.getPlayerSkills().getForaging().addExp(SkillExpAmount.FORAGING_RARE.getExp());
            return;
        }
        table.addLootToTable(playerData, event.getLoot());

    }
}
