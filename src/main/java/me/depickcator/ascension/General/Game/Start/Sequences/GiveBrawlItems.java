package me.depickcator.ascension.General.Game.Start.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import me.depickcator.ascension.Items.Craftable.Vanilla.DiamondAxe;
import me.depickcator.ascension.Items.Craftable.Vanilla.DiamondSword;
import me.depickcator.ascension.Items.Craftable.Vanilla.Shield;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerSkills;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class GiveBrawlItems extends GameSequences {
    public GiveBrawlItems() {

    }

    @Override
    public void run(GameLauncher game) {
        new BukkitRunnable() {
            List<PlayerData> players = PlayerUtil.getAllPlayingPlayers();

            ItemStack sword = enchantedMeleeWeapons(DiamondAxe.getInstance().getResult());
            ItemStack axe = enchantedMeleeWeapons(DiamondSword.getInstance().getResult());
            ItemStack bow = initBow();
            ItemStack chestplate = initArmor(Material.DIAMOND_CHESTPLATE);
            ItemStack boots = initArmor(Material.DIAMOND_BOOTS);

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

                int r = new Random().nextInt(0, 3);
                ItemStack crossbow = r == 0 ? HeliosCurse.getInstance().getResult() : r == 1 ?
                        CupidBow.getInstance().getResult() : ApolloGlare.getInstance().getResult();

                PlayerUtil.giveItem(p,
                        sword,
                        axe,
                        QuickPick.getInstance().getResult(),
                        new ItemStack(Material.OAK_PLANKS, 64),
                        new ItemStack(Material.GOLDEN_APPLE, 32),
                        bow,
                        new ItemStack(Material.ARROW, 64),
                        crossbow,
                        Tarnhelm.getInstance().getResult(),
                        chestplate,
                        HideOfLeviathan.getInstance().getResult(),
                        boots,
                        new ItemStack(Material.WATER_BUCKET),
                        new ItemStack(Material.ANVIL),
                        new ItemStack(Material.GOLD_INGOT, 64),
                        Shield.getInstance().getResult(),
                        Shield.getInstance().getResult(),
                        Shield.getInstance().getResult(),
                        Resurrection.getInstance().getResult(),
                        Nectar.getInstance().getResult(),
                        new ItemStack(Material.OAK_PLANKS, 64),
                        new ItemStack(Material.ARROW, 64)

                );



                players.removeFirst();
            }
        }.runTaskTimer(plugin, 10, 10);
    }

    private ItemStack enchantedMeleeWeapons(ItemStack weapon) {
        ItemStack item = weapon.clone();
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.SHARPNESS, 3, true);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack initBow() {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.POWER, 3, true);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack initArmor(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION, 2, true);
        item.setItemMeta(meta);
        return item;
    }
}
