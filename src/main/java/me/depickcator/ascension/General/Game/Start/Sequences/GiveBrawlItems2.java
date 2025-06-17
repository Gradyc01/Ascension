package me.depickcator.ascension.General.Game.Start.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import me.depickcator.ascension.Items.Craftable.Vanilla.*;
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
    private final List<List<ItemStack>> allKits;
    private final List<ItemStack> healing;
    public GiveBrawlItems2() {
        r = new Random();
        allKits = List.of(kit1(), kit2(), kit3(), kit4(), kit5(),
                kit6(), kit7(), kit8(), kit9(), kit10(), kit11(), kit12()
        );
        healing = initHealing();
    }

    @Override
    public void run(GameLauncher game) {
        new BukkitRunnable() {
            List<PlayerData> players = PlayerUtil.getAllPlayingPlayers();
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
                p.giveExpLevels(100);

                p.getInventory().clear();

                List<ItemStack> items = getList();
                items.add(new ItemStack(Material.OAK_PLANKS, 64));
                items.add(new ItemStack(Material.WATER_BUCKET, 1));
                items.add(new ItemStack(Material.LAVA_BUCKET, 1));
                items.add(new ItemStack(Material.ANVIL, 1));
                items.add(new ItemStack(Material.ARROW, 64));
                items.add(new ItemStack(Material.ARROW, 32));
                items.add(new ItemStack(Material.GOLDEN_APPLE, 8));
                items.add(Shield.getInstance().getResult());
                items.add(Shield.getInstance().getResult());
                PlayerUtil.giveItem(p, items);
                players.removeFirst();
            }

        }.runTaskTimer(plugin, 10, 10);
    }

    private List<ItemStack> getList() {
        List<ItemStack> items = new ArrayList<>(allKits.get(r.nextInt(allKits.size())));
        items.add(healing.get(r.nextInt(healing.size())));
        return items;
    }

    private List<ItemStack> kit1() {
        return new ArrayList<>(List.of(
                Exodus.getInstance().getResult(),
                initArmor(Material.DIAMOND_CHESTPLATE, 2),
                initArmor(Material.DIAMOND_LEGGINGS, 2),
                initArmor(Material.DIAMOND_BOOTS, 2),
                initMeleeWeapons(DiamondSword.getInstance().getResult(), 3),
                initMeleeWeapons(DiamondAxe.getInstance().getResult(), 3),
                enchantBow(3),
                Nectar.getInstance().getResult(),
                MinerBlessing.getInstance().getResult()
        ));
    }
    private List<ItemStack> kit2() {
        return new ArrayList<>(List.of(
                initArmor(Material.DIAMOND_HELMET, 2),
                DragonArmor.getInstance().getResult(),
                initArmor(Material.DIAMOND_LEGGINGS, 2),
                initArmor(Material.DIAMOND_BOOTS, 2),
                initMeleeWeapons(DiamondSword.getInstance().getResult(), 3),
                initMeleeWeapons(DiamondAxe.getInstance().getResult(), 3),
                enchantBow(3),
                ZombieHead.getInstance().getResult(),
                ApolloGlare.getInstance().getResult()
        ));
    }
    private List<ItemStack> kit3() {
        return new ArrayList<>(List.of(
                initArmor(Material.DIAMOND_HELMET, 2),
                initArmor(Material.DIAMOND_CHESTPLATE, 2),
                HideOfLeviathan.getInstance().getResult(),
                initArmor(Material.DIAMOND_BOOTS, 2),
                initMeleeWeapons(DiamondSword.getInstance().getResult(), 3),
                initMeleeWeapons(DiamondAxe.getInstance().getResult(), 3),
                enchantBow(3),
                SkeletonSkull.getInstance().getResult(),
                HeliosCurse.getInstance().getResult()
        ));
    }
    private List<ItemStack> kit4() {
        return new ArrayList<>(List.of(
                initArmor(Material.DIAMOND_HELMET, 2),
                initArmor(Material.DIAMOND_CHESTPLATE, 3),
                initArmor(Material.DIAMOND_LEGGINGS, 2),
                SevenLeagueBoots.getInstance().getResult(),
                initMeleeWeapons(DragonSword.getInstance().getResult(), 3),
                initMeleeWeapons(DiamondAxe.getInstance().getResult(), 3),
                enchantBow(3),
                CreeperHead.getInstance().getResult(),
                CupidBow.getInstance().getResult()
        ));
    }
    private List<ItemStack> kit5() {
        return new ArrayList<>(List.of(
                Tarnhelm.getInstance().getResult(),
                initArmor(Material.DIAMOND_CHESTPLATE, 3),
                initArmor(Material.DIAMOND_LEGGINGS, 3),
                HermesBoots.getInstance().getResult(),
                initMeleeWeapons(DragonSword.getInstance().getResult(), 3),
                initMeleeWeapons(DiamondAxe.getInstance().getResult(), 3),
                enchantBow(3),
                GoldenHead.getInstance().getResult(),
                ApolloGlare.getInstance().getResult()
        ));
    }
    private List<ItemStack> kit6() {
        return new ArrayList<>(List.of(
                Tarnhelm.getInstance().getResult(),
                initArmor(Material.DIAMOND_CHESTPLATE, 3),
                initArmor(Material.DIAMOND_LEGGINGS, 3),
                SevenLeagueBoots.getInstance().getResult(),
                initMeleeWeapons(DiamondSword.getInstance().getResult(), 3),
                initMeleeWeapons(LeapingAxe.getInstance().getResult(), 3),
                enchantBow(3),
                GoldenHead.getInstance().getResult(),
                MinerBlessing.getInstance().getResult()
        ));
    }
    private List<ItemStack> kit7() {
        return new ArrayList<>(List.of(
                initArmor(Material.DIAMOND_HELMET, 2),
                initArmor(Material.DIAMOND_CHESTPLATE, 2),
                initArmor(Material.DIAMOND_LEGGINGS, 2),
                SevenLeagueBoots.getInstance().getResult(),
                initMeleeWeapons(DiamondSword.getInstance().getResult(), 3),
                initMeleeWeapons(DiamondAxe.getInstance().getResult(), 3),
                enchantBow(3),
                MakeshiftSkull.getInstance().getResult(),
                Mace.getInstance().getResult()
        ));
    }
    private List<ItemStack> kit8() {
        return new ArrayList<>(List.of(
                Tarnhelm.getInstance().getResult(),
                DragonArmor.getInstance().getResult(),
                initArmor(Material.DIAMOND_LEGGINGS, 2),
                initArmor(Material.DIAMOND_BOOTS, 2),
                initMeleeWeapons(DiamondSword.getInstance().getResult(), 3),
                initMeleeWeapons(DiamondAxe.getInstance().getResult(), 3),
                enchantBow(3),
                Cornucopia.getInstance().getResult(),
                Poseidon.getInstance().getResult()
        ));
    }
    private List<ItemStack> kit9() {
        return new ArrayList<>(List.of(
                Tarnhelm.getInstance().getResult(),
                DragonArmor.getInstance().getResult(),
                initArmor(Material.DIAMOND_LEGGINGS, 2),
                initArmor(Material.DIAMOND_BOOTS, 2),
                initMeleeWeapons(DiamondSword.getInstance().getResult(), 3),
                initMeleeWeapons(DiamondAxe.getInstance().getResult(), 3),
                enchantBow(3),
                SkeletonSkull.getInstance().getResult(),
                Poseidon.getInstance().getResult()
        ));
    }
    private List<ItemStack> kit10() {
        return new ArrayList<>(List.of(
                Tarnhelm.getInstance().getResult(),
                initArmor(Material.DIAMOND_CHESTPLATE, 2),
                HideOfLeviathan.getInstance().getResult(),
                initArmor(Material.DIAMOND_BOOTS, 2),
                initMeleeWeapons(DragonSword.getInstance().getResult(), 3),
                initMeleeWeapons(DiamondAxe.getInstance().getResult(), 3),
                enchantBow(3),
                Panacea.getInstance().getResult(),
                HeliosCurse.getInstance().getResult()
        ));
    }
    private List<ItemStack> kit11() {
        return new ArrayList<>(List.of(
                initArmor(Material.DIAMOND_HELMET, 1),
                initArmor(Material.DIAMOND_CHESTPLATE, 1),
                HideOfLeviathan.getInstance().getResult(),
                SevenLeagueBoots.getInstance().getResult(),
                initMeleeWeapons(DiamondSword.getInstance().getResult(), 3),
                initMeleeWeapons(LeapingAxe.getInstance().getResult(), 3),
                enchantBow(3),
                Resurrection.getInstance().getResult(),
                CupidBow.getInstance().getResult()
        ));
    }
    private List<ItemStack> kit12() {
        return new ArrayList<>(List.of(
                Tarnhelm.getInstance().getResult(),
                DragonArmor.getInstance().getResult(),
                initArmor(Material.DIAMOND_LEGGINGS, 1),
                SevenLeagueBoots.getInstance().getResult(),
                initMeleeWeapons(DiamondSword.getInstance().getResult(), 3),
                initMeleeWeapons(DiamondAxe.getInstance().getResult(), 3),
                enchantBow(4),
                Nectar.getInstance().getResult(),
                HeliosCurse.getInstance().getResult()
        ));
    }

    private List<ItemStack> initHealing() {
        return new ArrayList<>(List.of(
                Panacea.getInstance().getResult(),
                Resurrection.getInstance().getResult(),
                SkeletonSkull.getInstance().getResult(),
                Cornucopia.getInstance().getResult(),
                Nectar.getInstance().getResult(),
                ZombieHead.getInstance().getResult(),
                CreeperHead.getInstance().getResult(),
                GoldenHead.getInstance().getResult(),
                MakeshiftSkull.getInstance().getResult(),
                new ItemStack(Material.GOLDEN_APPLE, 2),
                new ItemStack(Material.GOLDEN_APPLE, 2),
                new ItemStack(Material.GOLDEN_APPLE, 4),
                new ItemStack(Material.GOLDEN_APPLE, 4),
                new ItemStack(Material.GOLDEN_APPLE, 4),
                new ItemStack(Material.GOLDEN_APPLE, 4),
                new ItemStack(Material.GOLDEN_APPLE, 6),
                new ItemStack(Material.GOLDEN_APPLE, 6)
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
