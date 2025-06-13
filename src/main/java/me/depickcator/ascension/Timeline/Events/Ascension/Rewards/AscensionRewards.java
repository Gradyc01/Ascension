package me.depickcator.ascension.Timeline.Events.Ascension.Rewards;

import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import me.depickcator.ascension.Items.Uncraftable.AscensionRewardBundle;
import me.depickcator.ascension.Items.Uncraftable.NetherStar.NetherStar;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AscensionRewards {
    private final Map<ItemStack, Integer> teamBasedItemNames;
    private final Map<ItemStack, Integer> playerBasedItemNames;
    private final List<List<ItemStack>> teamBasedItems; // Items only given to the team leader
    private final List<List<ItemStack>> playerBasedItems; // Items given to all team members
    private int gameScore;
    private final Team team;
    public AscensionRewards(Team team) {
        this.team = team;
        teamBasedItems = new ArrayList<>();
        playerBasedItems = new ArrayList<>();
        teamBasedItemNames = new HashMap<>();
        playerBasedItemNames = new HashMap<>();
    }

    public void addRewardTier(int tier) {
        switch (tier) {
            case 0: addTier0Rewards(); break;
            case 1: addTier1Rewards(); break;
            case 2: addTier2Rewards(); break;
            case 3: addTier3Rewards(); break;
            case 4: addTier4Rewards(); break;
        }
        if (tier != 0) sendRewardAchievedText(tier);
    }

    public void giveRewards() {
        giveGameScore(team);
        ItemStack item = AscensionRewardBundle.getInstance().getResult(this);
        if (playerBasedItemNames.isEmpty()) return;
        sendRewardText();
        for (Player p : team.getTeamMembers()) {
            PlayerUtil.giveItem(p, item);
        }
    }

    private void addRewards(Map<ItemStack, Integer> names, List<List<ItemStack>> rewardItems, ItemStack... items) {
        List<ItemStack> newItems = new ArrayList<>();
        for (ItemStack item : items) {
            newItems.add(item);
            ItemStack itemStack = item.clone();
            itemStack.setAmount(1);
            int amount = names.getOrDefault(itemStack, 0);
            names.put(itemStack, amount + item.getAmount());
        }
        rewardItems.add(newItems);
    }

    public void giveItems(PlayerData pD) {
//        for (Player p : team.getTeamMembers()) {
//
//        }
        Player p = pD.getPlayer();
        for (List<ItemStack> items : playerBasedItems) {
            PlayerUtil.giveItem(p, items);
        }
        if (team.getLeader().equals(p)) {
            for (List<ItemStack> items : teamBasedItems) {
                PlayerUtil.giveItem(p, items);
            }
        }

    }

    private void giveGameScore(Team team) {
        if (gameScore == 0) return;
        team.getTeamStats().addGameScore(gameScore);
    }

    private void sendRewardAchievedText(int tier) {
        String level = TextUtil.toRomanNumeral(tier);
        Title title = TextUtil.makeTitle(
                TextUtil.makeText("Tier " + level + " rewards achieved!", TextUtil.YELLOW),
                0.5, 3, 0.5);
        Audience audience = Audience.audience(team.getTeamMembers());
        audience.showTitle(title);
        audience.forEachAudience(player -> {
            Player p = (Player) player;
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0f, 0.0f);
        });
    }

    private void sendRewardText() {
        Component message = TextUtil.topBorder(TextUtil.DARK_GRAY)
                .append(TextUtil.makeText("Through this Ascension attempt you earned yourself:"
                        , TextUtil.WHITE));
        for (Map.Entry<ItemStack, Integer> entry : playerBasedItemNames.entrySet()) {
            message = message.append(TextUtil.makeText("\n     ")
                    .append(entry.getKey().displayName())
                    .append(TextUtil.makeText("  x" + entry.getValue())));
        }
        message = message.append(TextUtil.bottomBorder(TextUtil.DARK_GRAY));
        Audience audience = Audience.audience(team.getTeamMembers());
        audience.sendMessage(message);

    }

    private void addTier0Rewards() {
        addRewards(playerBasedItemNames, playerBasedItems,
                new ItemStack(Material.GOLDEN_APPLE, 3),
                new ItemStack(Material.DIAMOND, 5),
                new ItemStack(Material.EMERALD, 4),
                NetherStar.getInstance().getResult(1),
                ShardOfTheFallen.getInstance().getResult(3));
        gameScore += 1;
    }

    private void addTier1Rewards() {
        addRewards(playerBasedItemNames, playerBasedItems,
                new ItemStack(Material.GOLDEN_APPLE, 2),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.DIAMOND, 5),
                new ItemStack(Material.EMERALD, 4),
                NetherStar.getInstance().getResult(1),
                ShardOfTheFallen.getInstance().getResult(2));
        addRewards(teamBasedItemNames, teamBasedItems,
                MakeshiftSkull.getInstance().getResult());
        gameScore += 2;
    }

    private void addTier2Rewards() {
        addRewards(playerBasedItemNames, playerBasedItems,
                new ItemStack(Material.GOLDEN_APPLE, 2),
                new ItemStack(Material.DIAMOND, 4),
                new ItemStack(Material.ARROW, 32),
                NetherStar.getInstance().getResult(2),
                ShardOfTheFallen.getInstance().getResult(4));
        addRewards(teamBasedItemNames, teamBasedItems,
                MakeshiftSkull.getInstance().getResult(),
                NotchApple.getInstance().getResult());
        gameScore += 2;
    }

    private void addTier3Rewards() {
        addRewards(playerBasedItemNames, playerBasedItems,
                new ItemStack(Material.GOLDEN_APPLE, 3),
                Nectar.getInstance().getResult(),
                new ItemStack(Material.ARROW, 32),
                new ItemStack(Material.EMERALD, 3),
                NetherStar.getInstance().getResult(2),
                MakeshiftSkull.getInstance().getResult());
        addRewards(teamBasedItemNames, teamBasedItems,
                GoldenHead.getInstance().getResult(),
                NotchApple.getInstance().getResult());
        gameScore += 2;
    }

    private void addTier4Rewards() {
        addRewards(playerBasedItemNames, playerBasedItems,
                Panacea.getInstance().getResult(),
                Cornucopia.getInstance().getResult(),
                new ItemStack(Material.DIAMOND, 4),
                new ItemStack(Material.EMERALD, 3),
                NetherStar.getInstance().getResult(2),
                GoldenHead.getInstance().getResult());
        addRewards(teamBasedItemNames, teamBasedItems,
                Resurrection.getInstance().getResult());
        gameScore += 2;
    }


}
