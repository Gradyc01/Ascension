package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.KingsRod;
import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.ForageBlocks;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Skills.SkillExpAmount;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import java.util.List;

public class LootTableGeneration implements Listener {
    private final Ascension plugin;

    public LootTableGeneration() {
        this.plugin = Ascension.getInstance();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (plugin.getGameState().canNotBuild() || event.getBlock().hasMetadata("UNBREAKABLE")) {
            event.setCancelled(true);
            return;
        }
        Player p = event.getPlayer();
        Block b = event.getBlock();
        LootTableChanger lootTableChanger = LootTableChanger.findItem(p.getInventory().getItemInMainHand());
        if (lootTableChanger != null) {
            lootTableChanger.uponEvent(event, p);
            return;
        }
        lootTableChanger = LootTableChanger.findBlock(b);
        if (lootTableChanger != null) {
            lootTableChanger.uponEvent(event, p);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block b = event.getBlock();
        LootTableChanger lootTableChanger = LootTableChanger.findBlock(b);
        if (lootTableChanger != null) {
            if (lootTableChanger instanceof ForageBlocks) {
                ForageBlocks forageBlocks = (ForageBlocks) lootTableChanger;
                forageBlocks.onPlacedForagingBlock(event);
            }
        }
    }

    @EventHandler
    public void onBlockExplode(EntityExplodeEvent event) {
        List<Block> explodedBlocks = event.blockList();
        for (Block b: explodedBlocks) {
            if (b.hasMetadata("UNBREAKABLE")) {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity e = event.getDamageSource().getCausingEntity();
        if (e == null || e.getType() != EntityType.PLAYER) {
            return;
        }
        Player p = (Player) e;
        LootTableChanger lootTableChanger = LootTableChanger.findEntity(event.getEntity());
        if (lootTableChanger != null) {
            lootTableChanger.uponEvent(event, p);
        }
    }

    @EventHandler
    public void onFishing(PlayerFishEvent e) {
        Player p = e.getPlayer();
        if (e.getState().equals(PlayerFishEvent.State.FISHING)) {
            e.getHook().setVelocity(p.getEyeLocation().getDirection().normalize().multiply(1.49));
        } else if (e.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY)) {
            if (e.getCaught() instanceof Player) {
                e.getHook().setHookedEntity(null);
            }
        } else if (e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            PlayerInventory inv = p.getInventory();
            if (inv.getItemInMainHand().getType() == Material.FISHING_ROD || inv.getItemInOffHand().getType() == Material.FISHING_ROD) {
                PlayerUtil.getPlayerData(p).getPlayerSkills().getForaging().addExp(SkillExpAmount.FORAGING_UNCOMMON.getExp());
                if (inv.getItemInMainHand().getItemMeta().getCustomModelData() == KingsRod.getInstance().getResult().getItemMeta().getCustomModelData()) {
                    plugin.getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.GOLD_NUGGET, 6));
                }
            }
        }
    }

    @EventHandler
    public void onRodLand(ProjectileHitEvent e) {
        if (e.getEntity().getShooter() == e.getHitEntity() || e.getHitEntity() == null) {
            return;
        } else {
            if (e.getEntityType() != EntityType.FISHING_BOBBER) {
                return;
            }

            FishHook hook = (FishHook) e.getEntity();
            Player hookShooter = (Player) hook.getShooter();
            LivingEntity hitEntity = (LivingEntity) e.getHitEntity();

            double kx = hook.getLocation().getDirection().getX() / 2.5;
            double kz = hook.getLocation().getDirection().getZ() / 2.5;
            kx = kx - kx * 2;


            if (hitEntity.getNoDamageTicks() >= 8) {
                return;
            } else if (hitEntity.getNoDamageTicks() < 8 && hitEntity.getLocation().getWorld().getBlockAt(hitEntity.getLocation()).getType().toString() != "AIR") {
                hitEntity.setNoDamageTicks(0);
            }

            hitEntity.damage(0.001, hookShooter);
            double upVel = 0.372;
            if (!hitEntity.isOnGround()) {
                upVel = 0;
            }

            hitEntity.setVelocity(new Vector(kx, upVel, kz));

//            hitEntity.setNoDamageTicks(7);
        }
    }
}
