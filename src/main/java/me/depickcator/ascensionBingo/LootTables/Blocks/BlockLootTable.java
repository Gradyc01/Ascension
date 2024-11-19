package me.depickcator.ascensionBingo.LootTables.Blocks;

import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;


public interface BlockLootTable {
//    TreeSet<Location> placedLocations = new TreeSet<>();
default void giveMiningExp(Player p, int amount) throws Exception {
    PlayerData pD = PlayerUtil.getPlayerData(p);
    if (pD == null) throw new Exception("No PlayerData");
    pD.getPlayerSkills().getMining().addExp(amount);
}

    default BlockBreakEvent getBreakBlockEvent(Event event) throws Exception {
        if (!(event instanceof BlockBreakEvent)) throw new Exception("Incorrect type");
        return (BlockBreakEvent) event;
    }

    default boolean toolContainsSilkTouch(BlockBreakEvent event){
        return event.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH);
    }

    default boolean eligibleForMiningExp(Event e, Player p) {
        try {
            BlockBreakEvent event = getBreakBlockEvent(e);
            if (toolContainsSilkTouch(event)) {
                return false;
            }
            PlayerData playerData = PlayerUtil.getPlayerData(p);
            return playerData != null;
        } catch (Exception ignored) {
            return false;
        }
    }
}
