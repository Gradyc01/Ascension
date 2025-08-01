package me.depickcator.ascension.Timeline.Events.Scavenger;

import me.depickcator.ascension.Items.ItemLists.ScavengerLists.Input_Standard;
import me.depickcator.ascension.Items.ItemLists.ScavengerLists.Output_Standard;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Utility.ItemComparison;
import me.depickcator.ascension.Items.Uncraftable.EnlightenedNugget;
import me.depickcator.ascension.Items.Uncraftable.HadesBook.HadesBook;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ScavengerTrades {
    /*Input on the Left Output on the Right*/
    private List<Pair<ItemStack, ItemStack>> trades;
    private final List<ItemStack> input;
    private final List<ItemStack> output;
    public ScavengerTrades() {
        input = new ArrayList<>(new Input_Standard().getItems(5));
        output = new ArrayList<>(new Output_Standard().getItems(5));
    }

    public ScavengerTrades(List<ItemStack> input, List<ItemStack> output) {
        this.input = input;
        this.output = output;
    }

    public List<Pair<ItemStack, ItemStack>> generateTrades() {
        List<Pair<ItemStack, ItemStack>> trades = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            Pair<ItemStack, ItemStack> pair = Pair.of(input.get(i), output.get(i));
            trades.add(pair);
        }
        this.trades = trades;
        return trades;
    }

    public boolean tradeItem(ItemStack input, PlayerData playerData) {
        Player p = playerData.getPlayer();
        ItemStack output = doesTradeExist(input);
        ItemStack playerItemStack = doesPlayerHaveInput(input, p);
        int tradeNumber = trades.indexOf(Pair.of(input, output));
        boolean alreadyTraded = playerData.getPlayerTeam().getTeam().getTeamStats().getScavengerScore().get(tradeNumber);
        if (output == null || playerItemStack == null || alreadyTraded) return false;
        playerItemStack.setAmount(playerItemStack.getAmount() - 1);
//        p.getWorld().dropItem(p.getLocation(), output);
        PlayerUtil.giveItem(p, output);

        checkIfCompleted(playerData);

        return true;
    }

    private ItemStack doesPlayerHaveInput(ItemStack input, Player player) {
        PlayerInventory inventory = player.getInventory();
        ArrayList<ItemStack> armor = new ArrayList<>(
                Arrays.stream(inventory.getArmorContents())
                        .filter(Objects::nonNull) // Filter out null values
                        .toList());
        for (ItemStack item: inventory.getContents()) {
            if (armor.contains(item) || item == null) continue;
            if (ItemComparison.equalItems(item, input)) return item;
        }
        return null;
    }

    //Attempts to find if the trade exists
    //Returns the output of the other trade or null if not found
    private ItemStack doesTradeExist(ItemStack input) {
        for (Pair<ItemStack, ItemStack> pair : trades) {
            if (ItemComparison.equalItems(pair.getLeft(), input)) {
                return pair.getRight();
            }
        }
        return null;
    }

    private void checkIfCompleted(PlayerData playerData) {
        Player p = playerData.getPlayer();
        int i = 0;
        for (boolean b : playerData.getPlayerTeam().getTeam().getTeamStats().getScavengerScore()) {
            if (b) {
                i++;
            }
        }
        if (i + 1 == 3) {
            ItemStack item = EnlightenedNugget.getInstance().getItem().clone();
            item.setAmount(3);
            PlayerUtil.giveItem(p, item);
            PlayerUtil.giveItem(p, HadesBook.getInstance().getItem());
            sendCompletionText(playerData);
        }
    }

    private void sendCompletionText(PlayerData playerData) {
        List<Player> players = playerData.getPlayerTeam().getTeam().getTeamMembers();
        TextUtil.broadcastMessage(TextUtil.topBorder(TextUtil.BLUE), players);
        TextUtil.broadcastMessage(TextUtil.makeText("       SCAVENGER COMPLETED", TextUtil.GOLD, true, false), players);
//        TextUtil.broadcastMessage(TextUtil.makeText("\ ", TextUtil.GOLD), players);
        TextUtil.broadcastMessage(TextUtil.bottomBorder(TextUtil.BLUE), players);
        SoundUtil.broadcastSound(Sound.ENTITY_PLAYER_LEVELUP, 1, 0, players);
    }

    public List<Pair<ItemStack, ItemStack>> getTrades() {
        return trades;
    }


}
