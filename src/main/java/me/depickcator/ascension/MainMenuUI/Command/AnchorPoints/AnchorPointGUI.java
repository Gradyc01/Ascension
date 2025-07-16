package me.depickcator.ascension.MainMenuUI.Command.AnchorPoints;

import me.depickcator.ascension.Effects.TeleportSequence;
import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.MainMenuUI.Command.CommandGUI;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Cooldowns.TeleportCooldown;
import me.depickcator.ascension.Player.Data.PlayerAnchors;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnchorPointGUI extends AscensionMenuGUI {
    private final Map<ItemStack, AnchorPoint> anchorPoints;
    private final ItemStack createAnchorPointItem;
    private final PlayerAnchors playerAnchors;
    public AnchorPointGUI(PlayerData playerData) {
        super(playerData, (char) 3, TextUtil.makeText("Anchor Points", TextUtil.AQUA), true);
        this.anchorPoints = new HashMap<>();
        playerAnchors = playerData.getPlayerAnchors();
        initAnchorPoints();
        playerHeadButton(22);
        inventory.setItem(21, goBackItem());
        createAnchorPointItem = initAnchorPointItem();
    }

    private ItemStack initAnchorPointItem() {
        ItemStack item = new ItemStack(Material.GREEN_WOOL);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Create New Anchor Point", TextUtil.GREEN));
        meta.lore(List.of(TextUtil.makeText("Cost: [500 Souls]", TextUtil.GOLD)));
        item.setItemMeta(meta);
        inventory.setItem(16, item);
        return item;
    }

    private void initAnchorPoints() {
//        int index = 11;
//        for (AnchorPoint anchorPoint : playerAnchors.getAnchorPoints()) {
//            initAnchorPoint(anchorPoint, index++);
//        }
        int startingIndex = 11;
        ItemStack item = initExplainerItem(Material.RED_STAINED_GLASS_PANE, List.of(), TextUtil.makeText("Unset", TextUtil.DARK_RED));
        List<AnchorPoint> anchorPoints = playerAnchors.getAnchorPoints();
        for (int i = startingIndex; i < startingIndex + 4; i++) {
            try {
                AnchorPoint anchorPoint = anchorPoints.get(i - startingIndex);
                initAnchorPoint(anchorPoint, i);
            } catch (IndexOutOfBoundsException ignored) {
                inventory.setItem(i, item);
            }
        }
    }

    private void initAnchorPoint(AnchorPoint anchorPoint, int index) {
        ItemStack item = new ItemStack(anchorPoint.getMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(anchorPoint.getName(), TextUtil.GOLD));
        List<Component> lore = List.of(
                TextUtil.makeText("Located at: ", TextUtil.DARK_GREEN)
                        .append(TextUtil.makeText(anchorPoint.getCoordinateString(), TextUtil.AQUA)),
                TextUtil.makeText("[Left Click]", TextUtil.DARK_GREEN).append(TextUtil.makeText(" Teleport", TextUtil.YELLOW)),
                TextUtil.makeText("[Right Click]", TextUtil.DARK_GREEN).append(TextUtil.makeText(" Delete", TextUtil.RED))
        );
        meta.lore(lore);
        item.setItemMeta(meta);
        anchorPoints.put(item, anchorPoint);
        inventory.setItem(index, item);
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item.equals(createAnchorPointItem)) {
            if (playerAnchors.createAnchorPoint(playerData)) {
                new AnchorPointGUI(playerData);
            }
        } else if (anchorPoints.containsKey(item)) {
            AnchorPoint anchorPoint = anchorPoints.get(item);
            if (event.isLeftClick()) {
                anchorPoint.teleport(playerData);
            } else if (event.isRightClick()) {
                if (playerAnchors.removeAnchorPoint(anchorPoint)) {
                    new AnchorPointGUI(playerData);
                    player.sendMessage(TextUtil.makeText("[Anchor Point] ", TextUtil.BLUE)
                            .append(TextUtil.makeText("Anchor Removed Successfully", TextUtil.DARK_GREEN)));
                    SoundUtil.playHighPitchPling(player);
                }
            }
        } else if (item.equals(goBackItem())) {
            new CommandGUI(playerData);
        }
    }
}
