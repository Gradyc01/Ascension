package me.depickcator.ascension.Timeline.Events.Scavenger;

import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Teams.TeamStats;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ScavengerGUI extends AscensionMenuGUI {
    public final static String menuName = "Scavenger-GUI";

    private final Scavenger scavenger;
    private final boolean canSubmitItems;
    public ScavengerGUI(Scavenger scavenger, PlayerData playerData, boolean canSubmitItems) {
        super(playerData, (char) 6, TextUtil.makeText("Scavenger", TextUtil.AQUA), true);
        this.scavenger = scavenger;
        this.canSubmitItems = canSubmitItems;

        setTrades();
        addTradePanes();
        playerHeadButton(49);

        if (!canSubmitItems) {
            inventory.setItem(48, goBackItem());
        } else {
            addClaimItems();
        }

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
        List<Boolean> score = teamStats.getScavengerScore();
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
    public void interactWithGUIButtons(InventoryClickEvent event) {
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
                new ScavengerGUI(scavenger, playerData, true);
            } else {
                TextUtil.errorMessage(player, "You currently do not have the item required or your team has already completed the trade");
            }
        }

        if (event.getCurrentItem().equals(goBackItem()) && !canSubmitItems) {
            player.performCommand("open-main-menu");
        }
    }

    private void successfulTrade(Pair<ItemStack, ItemStack> trade, int tradeNumber) {
        Team team = playerData.getPlayerTeam().getTeam();
        SoundUtil.playHighPitchPling(player);
        TeamStats teamStats = team.getTeamStats();
        List<Boolean> scavScore = teamStats.getScavengerScore();
        scavScore.set(tradeNumber, true);
        successfulTradeText(scavScore, trade, team);
    }

    private void successfulTradeText(List<Boolean> scavScore, Pair<ItemStack, ItemStack> trade, Team team) {
        Component name = TextUtil.makeText(player.getName() + " has completed a trade ", TextUtil.DARK_GREEN);
        Component arrow = TextUtil.makeText(" --> ", TextUtil.YELLOW);
        int i = 0;
        for (Boolean b : scavScore) {
            if (b) i++;
        }
        Component num = TextUtil.makeText(" (" + i + "/5)", TextUtil.BLUE);
        TextUtil.broadcastMessage(name, team.getTeamMembers());
        TextUtil.broadcastMessage(trade.getLeft().displayName().append(arrow.append(trade.getRight().displayName().append(num))), team.getTeamMembers());
    }

}
