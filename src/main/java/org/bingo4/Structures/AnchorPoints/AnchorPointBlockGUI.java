package org.bingo4.Structures.AnchorPoints;

import org.bingo4.Interfaces.AscensionBlockGUI;
import org.bingo4.Interfaces.AscensionBlockMenuGUI;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Teams.Team;
import org.bingo4.Teams.TeamAnchors;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Lidded;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AnchorPointBlockGUI extends AscensionBlockMenuGUI {
//    private final ItemStack createAnchorPointItem;
    private final TeamAnchors teamAnchors;
    private final AnchorPoint anchorPoint;
    private final Team team;
    private final ItemStack openItem;
    private final ItemStack enterItem;
    private final ItemStack breakItem;
    private PlayerData currentUser = null;
    public AnchorPointBlockGUI(PlayerData pD, Block block, AnchorPoint anchorPoint) {
        super(pD, block, 3, TextUtil.makeText(pD.getPlayer().getName() + "'s Void Box", TextUtil.DARK_PURPLE), true);
        teamAnchors = playerData.getPlayerTeam().getTeam().getTeamAnchors();
        this.team = playerData.getPlayerTeam().getTeam();
        this.anchorPoint = anchorPoint;
        openItem = createOpenItem();
        enterItem = createEnterItem();
        breakItem = createBreakItem();
        AscensionBlockGUI.registerGUI(this);
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (item.equals(openItem)) {
            new CreateAnchorPointAnimation(currentUser.getPlayerTeam().getTeam(), anchorPoint);
        } else if (item.equals(breakItem)) {
            anchorPoint.destroyAnchor(currentUser);
            AscensionBlockGUI.removeGUI(this);
        } else if (item.equals(enterItem)) {
            anchorPoint.enterPortal(currentUser);
        }
    }

    @Override
    public boolean interactWithBlock(PlayerData playerData, Block block, PlayerInteractEvent event) {
        event.setCancelled(false);
        if (currentUser != null) {
            playerData.getPlayer().sendMessage(TextUtil.makeText("Already opened by someone else!", TextUtil.RED));
            SoundUtil.playErrorSoundEffect(playerData.getPlayer());
            return false;
        }
        initializeInventory(playerData);
        open(playerData.getPlayer());
        currentUser = playerData;
        return false;
    }

    @Override
    public void open(Player p) {
        super.open(p);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 4F, 0);
        if (getBlock().getState() instanceof Lidded lidded) {
            lidded.open();
        }
    }

    @Override
    public boolean runWhenCloseGUI(InventoryCloseEvent event) {
        currentUser = null;
        if (getBlock().getState() instanceof Lidded lidded) {
            lidded.close();
        }
        return super.runWhenCloseGUI(event);
    }

    private void initializeInventory(PlayerData opener) {
        setBackground();
        if (opener.getPlayerTeam().getTeam().equals(team)) {
            if (anchorPoint.isState(AnchorPoint.OPEN)) {
                inventory.setItem(13, enterItem);
                inventory.setItem(26, breakItem);
            } else if (anchorPoint.isState(AnchorPoint.CLOSED)) {
                inventory.setItem(11, openItem);
                inventory.setItem(15, breakItem);
            }
        } else {
            inventory.setItem(26, breakItem);
        }
    }

    private ItemStack createOpenItem() {
        return initExplainerItem(Material.END_PORTAL_FRAME,
                List.of(),
                TextUtil.makeText("Open Void Box", TextUtil.DARK_PURPLE));
    }

    private ItemStack createEnterItem() {
        return initExplainerItem(Material.ENDER_PEARL,
                List.of(),
                TextUtil.makeText("Enter Void Box", TextUtil.DARK_PURPLE));
    }

    private ItemStack createBreakItem() {
        return initExplainerItem(Material.RED_STAINED_GLASS,
                List.of(),
                TextUtil.makeText("Break Void Box", TextUtil.DARK_PURPLE));
    }
}
