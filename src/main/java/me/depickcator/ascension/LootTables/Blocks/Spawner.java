package me.depickcator.ascension.LootTables.Blocks;

import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Skills.SkillExpAmount;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Spawner implements LootTableChanger, BlockLootTable {
    public Spawner() {
        registerItem();
    }

    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public boolean uponEvent(Event e, Player p) {
        BlockBreakEvent event = (BlockBreakEvent) e;
//        event.setDropItems(false);
        if (p.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
            event.setExpToDrop(0);
            Block b = event.getBlock();
            b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.SPAWNER));
        } else {
            PlayerData pD = PlayerUtil.getPlayerData(p);
            pD.getPlayerSkills().getCombat().addExp(SkillExpAmount.COMBAT_LEGENDARY.getExp());
        }
//        p.getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.COBBLESTONE));
        return true;
    }

    @Override
    public void registerItem() {
        addBlock(Material.SPAWNER, this);
    }
}
