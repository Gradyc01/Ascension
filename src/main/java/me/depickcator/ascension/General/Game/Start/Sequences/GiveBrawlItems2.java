package me.depickcator.ascension.General.Game.Start.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import me.depickcator.ascension.Items.Craftable.Vanilla.DiamondAxe;
import me.depickcator.ascension.Items.Craftable.Vanilla.DiamondSword;
import me.depickcator.ascension.Items.Craftable.Vanilla.NetheriteAxe;
import me.depickcator.ascension.Items.Craftable.Vanilla.Shield;
import me.depickcator.ascension.Items.Uncraftable.Skulls.CreeperHead;
import me.depickcator.ascension.Items.Uncraftable.Skulls.SkeletonSkull;
import me.depickcator.ascension.Items.Uncraftable.Skulls.ZombieHead;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerSkills;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GiveBrawlItems2 extends GameSequences {
    private final Random r;
    public GiveBrawlItems2() {
        r = new Random();
    }

    @Override
    public void run(GameLauncher game) {
        new BukkitRunnable() {
            List<PlayerData> players = PlayerUtil.getAllPlayingPlayers();
            List<List<Pair<ItemStack, Integer>>> itemLists = new ArrayList<>(List.of(
                    initHelmets(),
                    initChestplate(),
                    initLeggings(),
                    initBoots(),
                    initSword(),
                    initAxe(),
                    initUtility(),
                    initHealing(),
                    initHealing(),
                    initSpecial(),
                    initBow()
            ));
            @Override
            public void run() {
                if (players.isEmpty()) {
                    cancel();
                    game.callback();
                    return;
                }
                PlayerData pD = players.getFirst();
                Player p = pD.getPlayer();
                PlayerSkills pS = pD.getPlayerSkills();
                pS.getCombat().addExp(250);
                pS.getForaging().addExp(1500);
                pS.getMining().addExp(1500);
                p.giveExp(128);

                p.getInventory().clear();

                Collections.shuffle(itemLists);
                List<ItemStack> items = getList();
                items.add(new ItemStack(Material.OAK_PLANKS, 64));
                items.add(new ItemStack(Material.WATER_BUCKET, 1));
                items.add(new ItemStack(Material.ANVIL, 1));
                items.add(new ItemStack(Material.ARROW, 64));
                items.add(new ItemStack(Material.GOLDEN_APPLE, 24));
                items.add(Shield.getInstance().getResult());
                items.add(Shield.getInstance().getResult());
                PlayerUtil.giveItem(p, items);
                players.removeFirst();
            }

            private @NotNull List<ItemStack> getList() {
                List<ItemStack> items = new ArrayList<>();


                int weightRemaining = 55;

                for (List<Pair<ItemStack, Integer>> list : itemLists) {
                    List<Pair<ItemStack, Integer>> selectedItems = new ArrayList<>();
//                    TextUtil.debugText("Weights for this list:");
                    for (Pair<ItemStack, Integer> pair : list) {
                        int weight = pair.getRight();
                        if (weightRemaining >= weight && weight != 0) {
                            int iterations = list.size() - list.indexOf(pair);
                            if (weight < 10 && weight > 0) iterations *= 3;
//                            TextUtil.debugText(iterations + "");
                            for (int i = 0; i < iterations; i++) {
                                selectedItems.add(pair);
                            }
                        }

                    }
                    if (selectedItems.isEmpty()) selectedItems.add(list.getLast());
//                    TextUtil.debugText("Total weight for this list: " + selectedItems.size());
                    Pair<ItemStack, Integer> item = selectedItems.get(r.nextInt(selectedItems.size()));

                    items.add(item.getLeft());
                    weightRemaining -= item.getRight();
                    TextUtil.debugText(item.getRight() + " used                 " + weightRemaining + " Remaining");


                }
                return items;
            }
        }.runTaskTimer(plugin, 10, 10);
    }

    private List<Pair<ItemStack, Integer>> initHelmets() {
        return new ArrayList<>(List.of(
                new ImmutablePair<>(Exodus.getInstance().getResult(), 11),
                new ImmutablePair<>(initArmor(Material.DIAMOND_HELMET, 4), 10),
                new ImmutablePair<>(initArmor(Material.DIAMOND_HELMET, 3), 6),
                new ImmutablePair<>(initArmor(Material.DIAMOND_HELMET, 2), 5),
                new ImmutablePair<>(Tarnhelm.getInstance().getResult(), 2),
                new ImmutablePair<>(initArmor(Material.DIAMOND_HELMET, 1), 0)
        ));
    }
    private List<Pair<ItemStack, Integer>> initChestplate() {
        return new ArrayList<>(List.of(
                new ImmutablePair<>(DragonArmor.getInstance().getResult(), 11),
                new ImmutablePair<>(initArmor(Material.DIAMOND_CHESTPLATE, 3), 7),
                new ImmutablePair<>(initArmor(Material.DIAMOND_CHESTPLATE, 2), 5),
                new ImmutablePair<>(initArmor(Material.DIAMOND_CHESTPLATE, 1), 0)
        ));
    }
    private List<Pair<ItemStack, Integer>> initLeggings() {
        return new ArrayList<>(List.of(
                new ImmutablePair<>(HideOfLeviathan.getInstance().getResult(), 11),
                new ImmutablePair<>(initArmor(Material.DIAMOND_LEGGINGS, 3), 7),
                new ImmutablePair<>(initArmor(Material.DIAMOND_LEGGINGS, 2), 5),
                new ImmutablePair<>(initArmor(Material.DIAMOND_LEGGINGS, 1), 0)
        ));
    }
    private List<Pair<ItemStack, Integer>> initBoots() {
        return new ArrayList<>(List.of(
                new ImmutablePair<>(HermesBoots.getInstance().getResult(), 15),
                new ImmutablePair<>(initArmor(Material.DIAMOND_BOOTS, 4), 11),
                new ImmutablePair<>(SevenLeagueBoots.getInstance().getResult(), 7),
                new ImmutablePair<>(initArmor(Material.DIAMOND_BOOTS, 2), 4),
                new ImmutablePair<>(initArmor(Material.DIAMOND_BOOTS, 1), 0)
        ));
    }
    private List<Pair<ItemStack, Integer>> initSword() {
        return new ArrayList<>(List.of(
                new ImmutablePair<>(initMeleeWeapons(DragonSword.getInstance().getResult(), 3), 13),
                new ImmutablePair<>(initMeleeWeapons(DragonSword.getInstance().getResult(), 2), 12),
                new ImmutablePair<>(initMeleeWeapons(DragonSword.getInstance().getResult(), 1), 11),
                new ImmutablePair<>(initMeleeWeapons(DiamondSword.getInstance().getResult(), 3), 6),
                new ImmutablePair<>(initMeleeWeapons(DiamondSword.getInstance().getResult(), 3), 5),
                new ImmutablePair<>(initMeleeWeapons(DiamondSword.getInstance().getResult(), 2), 4),
                new ImmutablePair<>(initMeleeWeapons(DiamondSword.getInstance().getResult(), 2), 3),
                new ImmutablePair<>(initMeleeWeapons(DiamondSword.getInstance().getResult(), 1), 0)
        ));
    }
    private List<Pair<ItemStack, Integer>> initAxe() {
        return new ArrayList<>(List.of(
                new ImmutablePair<>(initMeleeWeapons(NetheriteAxe.getInstance().getResult(), 2), 15),
                new ImmutablePair<>(initMeleeWeapons(NetheriteAxe.getInstance().getResult(), 1), 12),
                new ImmutablePair<>(initMeleeWeapons(DiamondAxe.getInstance().getResult(), 3), 6),
                new ImmutablePair<>(initMeleeWeapons(DiamondAxe.getInstance().getResult(), 3), 5),
                new ImmutablePair<>(initMeleeWeapons(DiamondAxe.getInstance().getResult(), 2), 4),
                new ImmutablePair<>(initMeleeWeapons(DiamondAxe.getInstance().getResult(), 2), 3),
                new ImmutablePair<>(initMeleeWeapons(DiamondAxe.getInstance().getResult(), 1), 0)
        ));
    }
    private List<Pair<ItemStack, Integer>> initBow() {
        return new ArrayList<>(List.of(
                new ImmutablePair<>(enchantBow(5), 16),
                new ImmutablePair<>(enchantBow(4), 10),
                new ImmutablePair<>(enchantBow(3), 6),
                new ImmutablePair<>(enchantBow(3), 5),
                new ImmutablePair<>(enchantBow(2), 4),
                new ImmutablePair<>(enchantBow(2), 3),
                new ImmutablePair<>(enchantBow(2), 2),
                new ImmutablePair<>(enchantBow(1), 1),
                new ImmutablePair<>(new ItemStack(Material.BOW, 1), 0)
        ));
    }
    private List<Pair<ItemStack, Integer>> initUtility() {
        return new ArrayList<>(List.of(
                new ImmutablePair<>(new ItemStack(Material.ARROW, 64), 15),
                new ImmutablePair<>(new ItemStack(Material.SPECTRAL_ARROW, 12), 11),
                new ImmutablePair<>(new ItemStack(Material.SPECTRAL_ARROW, 8), 9),
                new ImmutablePair<>(new ItemStack(Material.ARROW, 32), 7),
                new ImmutablePair<>(Shield.getInstance().getResult(), 2),
                new ImmutablePair<>(new ItemStack(Material.OAK_PLANKS, 64), 0)
        ));
    }
    private List<Pair<ItemStack, Integer>> initHealing() {
        return new ArrayList<>(List.of(
                new ImmutablePair<>(Panacea.getInstance().getResult(), 13),
                new ImmutablePair<>(Resurrection.getInstance().getResult(), 12),
                new ImmutablePair<>(SkeletonSkull.getInstance().getResult(), 8),
                new ImmutablePair<>(Cornucopia.getInstance().getResult(), 7),
                new ImmutablePair<>(Nectar.getInstance().getResult(), 6),
                new ImmutablePair<>(ZombieHead.getInstance().getResult(), 5),
                new ImmutablePair<>(CreeperHead.getInstance().getResult(), 2),
                new ImmutablePair<>(GoldenHead.getInstance().getResult(), 1),
                new ImmutablePair<>(MakeshiftSkull.getInstance().getResult(), 0)
        ));
    }
    private List<Pair<ItemStack, Integer>> initSpecial() {
        return new ArrayList<>(List.of(
                new ImmutablePair<>(CupidBow.getInstance().getResult(), 10),
                new ImmutablePair<>(MinerBlessing.getInstance().getResult(), 7),
                new ImmutablePair<>(HeliosCurse.getInstance().getResult(), 6),
                new ImmutablePair<>(ApolloGlare.getInstance().getResult(), 5),
                new ImmutablePair<>(KingsRod.getInstance().getResult(), 1),
                new ImmutablePair<>(AdvancedQuickPick.getInstance().getResult(), 0)
        ));
    }

    private ItemStack initMeleeWeapons(ItemStack weapon, int level) {
        ItemStack item = weapon.clone();
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.SHARPNESS, level, true);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack enchantBow(int level) {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.POWER, level, true);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack initArmor(Material material, int level) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION, level, true);
        item.setItemMeta(meta);
        return item;
    }
}
