package me.depickcator.ascension.Timeline.Events.Scavenger;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.SoundUtil;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.PlayerData;
import me.depickcator.ascension.Player.PlayerUtil;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Teams.TeamStats;
import me.depickcator.ascension.mainMenu.Command.Commands.Commands;
import me.depickcator.ascension.mainMenu.Command.Commands.FoodDrops;
import me.depickcator.ascension.mainMenu.Command.Commands.NightVision;
import me.depickcator.ascension.mainMenu.Command.Commands.SendCoords;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScavengerGUI implements AscensionGUI {
    public final static String menuName = "Scavenger-GUI";

    private final int GUISize = 6 * 9;
    private final Inventory inventory;
    private final Player player;
    private final PlayerData playerData;
    private final Ascension plugin;
    private final Scavenger scavenger;
    private final boolean canSubmitItems;
    public ScavengerGUI(Scavenger scavenger, Player player, boolean canSubmitItems) {
        this.plugin = Ascension.getInstance();
        this.player = player;
        this.playerData = PlayerUtil.getPlayerData(player);
        this.scavenger = scavenger;
        this.canSubmitItems = canSubmitItems;


        inventory = Bukkit.createInventory(player, GUISize, TextUtil.makeText("Scavenger", TextUtil.AQUA));
        player.openInventory(inventory);
        setItemBackground(inventory, GUISize);
        setTrades();
        addTradePanes();
        addClaimItems();
        playerHeadButton(inventory, 49, player);

        if (!canSubmitItems) {
            inventory.setItem(48, goBackItem());
        }


        Pair<Inventory, AscensionGUI> pair2 = new MutablePair<>(inventory,this);
        Ascension.guiMap.put(player.getUniqueId(), pair2);
    }

    private void setTrades() {
        List<Pair<ItemStack, ItemStack>> trades = scavenger.getScavengerTrades().getTrades();
        int index = 1;
        for (Pair<ItemStack, ItemStack> trade : trades) {
            ItemStack input = trade.getLeft();
            ItemStack output = trade.getRight();
            inventory.setItem(index, input);
            inventory.setItem(index + 6, output);
            index+=9;
        }
    }

    private void addTradePanes() {
        TeamStats teamStats = playerData.getPlayerTeam().getTeam().getTeamStats();
        ArrayList<Boolean> score = teamStats.getScavengerScore();
        for (int i = 0; i < 5; i++) {
            ItemStack item;
            if (score.get(i)) {
                item = completedItem();
            } else {
                item = incompleteItem();
            }
            for (int j = 2 + i * 9; j < 7 + i * 9; j++) {
                inventory.setItem(j, item);
            }
        }
    }

    private void addClaimItems() {
        for (int i = 8; i < 45; i+=9) {
            inventory.setItem(i, claimItem());
        }
    }

    private ItemStack completedItem() {
        ItemStack item = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Completed", TextUtil.DARK_GREEN));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack incompleteItem() {
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("", TextUtil.DARK_RED));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack claimItem() {
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Complete Trade", TextUtil.DARK_GREEN));
        meta.setEnchantmentGlintOverride(true);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public String getGUIName() {
        return menuName;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event, Player p) {
        if (event.getCurrentItem() == null || !event.isLeftClick()) {
            event.setCancelled(true);
            return;
        }

        if (canSubmitItems && event.getCurrentItem().equals(claimItem())) {
            int tradeNumber = (event.getSlot() - 8) / 9;
            ScavengerTrades scavengerTrades = scavenger.getScavengerTrades();
            List<Pair<ItemStack, ItemStack>> trades = scavengerTrades.getTrades();
            Pair<ItemStack, ItemStack> trade = trades.get(tradeNumber);
            if (scavengerTrades.tradeItem(trade.getLeft(), playerData)) {
                successfulTrade(trade, tradeNumber);
                new ScavengerGUI(scavenger, player, true);
            } else {
                TextUtil.errorMessage(player, "You currently do not have the item required or your team has already completed the trade");
            }
        }

        if (event.getCurrentItem().equals(goBackItem()) && !canSubmitItems) {
            p.performCommand("open-main-menu");
        }
    }

    private void successfulTrade(Pair<ItemStack, ItemStack> trade, int tradeNumber) {
        Team team = playerData.getPlayerTeam().getTeam();
        SoundUtil.playHighPitchPling(player);
        TeamStats teamStats = team.getTeamStats();
        ArrayList<Boolean> scavScore = teamStats.getScavengerScore();
        scavScore.set(tradeNumber, true);
        successfulTradeText(scavScore, trade, team);
    }

    private void successfulTradeText(ArrayList<Boolean> scavScore, Pair<ItemStack, ItemStack> trade, Team team) {
        Component name = TextUtil.makeText(player.getName() + " has completed a trade ", TextUtil.DARK_GREEN);
        Component arrow = TextUtil.makeText(" --> ", TextUtil.YELLOW);
        int i = 0;
        for (Boolean b : scavScore) {
            if (b) i++;
        }
        Component num = TextUtil.makeText(" (" + i + "/5)", TextUtil.BLUE);
        TextUtil.broadcastMessage(name);
        TextUtil.broadcastMessage(trade.getLeft().displayName().append(arrow.append(trade.getRight().displayName().append(num))), team.getTeamMembers());
    }

}
