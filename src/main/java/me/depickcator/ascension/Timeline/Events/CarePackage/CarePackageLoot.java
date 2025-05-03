package me.depickcator.ascension.Timeline.Events.CarePackage;

import me.depickcator.ascension.Interfaces.CustomChestLoot;
import me.depickcator.ascension.Items.Craftable.Unlocks.MakeshiftSkull;
import me.depickcator.ascension.Items.Craftable.Vanilla.NetheriteAxe;
import me.depickcator.ascension.Items.Craftable.Vanilla.NetheriteSword;
import me.depickcator.ascension.Items.Uncraftable.EnlightenedNugget;
import me.depickcator.ascension.Items.Uncraftable.NetherStar.NetherStar;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;


public class CarePackageLoot extends CustomChestLoot {
    private ArrayList<ItemStack> netheriteItems;
    private ArrayList<ItemStack> netherItems;

    public CarePackageLoot() {
        super();
        initNetheriteItems();
        initNetherItems();
    }

    private void initNetheriteItems() {

        netheriteItems = new ArrayList<>(List.of(
                NetheriteAxe.getInstance().getResult(),
                NetheriteSword.getInstance().getResult(),
                new ItemStack(Material.NETHERITE_HOE),
                new ItemStack(Material.NETHERITE_PICKAXE),
                new ItemStack(Material.NETHERITE_SHOVEL),
                new ItemStack(Material.NETHERITE_HELMET),
                new ItemStack(Material.NETHERITE_CHESTPLATE),
                new ItemStack(Material.NETHERITE_LEGGINGS),
                new ItemStack(Material.NETHERITE_BOOTS),
                new ItemStack(Material.NETHERITE_INGOT)
        ));

    }

    private void initNetherItems() {
        netherItems = new ArrayList<>(List.of(
                new ItemStack(Material.BLAZE_ROD),
                new ItemStack(Material.MAGMA_CREAM),
                new ItemStack(Material.NETHER_WART),
                new ItemStack(Material.GHAST_TEAR),
                new ItemStack(Material.BONE),
                new ItemStack(Material.COAL)
        ));
    }

    public Collection<ItemStack> populateLoot(Inventory inv, Random r,  double luck) {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.addAll(getRandomItemFromList(netheriteItems, r, 1));
        items.addAll(getRandomItemFromList(netherItems, r, 3, 2, 3));
        items.add(MakeshiftSkull.getInstance().getResult());
        items.add(NetherStar.getInstance().getResult(2));
        items.add(EnlightenedNugget.getInstance().getResult());
        items.add(EnlightenedNugget.getInstance().getResult());

        return placeInInventory(inv, r, items);
    }




}
