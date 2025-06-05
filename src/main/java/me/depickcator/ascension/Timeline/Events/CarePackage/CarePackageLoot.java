package me.depickcator.ascension.Timeline.Events.CarePackage;

import me.depickcator.ascension.Interfaces.CustomChestLoot;
import me.depickcator.ascension.Interfaces.CustomChestLootPool;
import me.depickcator.ascension.Interfaces.LootPoolItem;
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
    private CustomChestLootPool netheriteItems;
    private CustomChestLootPool netherItems;

    public CarePackageLoot() {
        super();
        initNetheriteItems();
        initNetherItems();
    }

    private void initNetheriteItems() {
        netheriteItems = new CustomChestLootPool(
                new LootPoolItem(NetheriteAxe.getInstance().getResult()),
                new LootPoolItem(NetheriteSword.getInstance().getResult()),
                new LootPoolItem(Material.NETHERITE_HOE),
                new LootPoolItem(Material.NETHERITE_PICKAXE),
                new LootPoolItem(Material.NETHERITE_SHOVEL),
                new LootPoolItem(Material.NETHERITE_HELMET),
                new LootPoolItem(Material.NETHERITE_CHESTPLATE),
                new LootPoolItem(Material.NETHERITE_LEGGINGS),
                new LootPoolItem(Material.NETHERITE_BOOTS),
                new LootPoolItem(Material.NETHERITE_INGOT)
        );

    }

    private void initNetherItems() {
        netherItems = new CustomChestLootPool(
                new LootPoolItem(Material.BLAZE_ROD, 4),
                new LootPoolItem(Material.MAGMA_CREAM, 3),
                new LootPoolItem(Material.NETHER_WART, 1),
                new LootPoolItem(Material.GHAST_TEAR, 2),
                new LootPoolItem(Material.BONE, 3),
                new LootPoolItem(Material.COAL, 2)
        );
    }

    public Collection<ItemStack> populateLoot(Inventory inv, Random r,  double luck) {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.addAll(netheriteItems.getRandomItemFromList(r, 1));
        items.addAll(netherItems.getRandomItemFromList(r, 3, 2, 3));
        items.add(MakeshiftSkull.getInstance().getResult());
        items.add(NetherStar.getInstance().getResult(2));
        items.add(EnlightenedNugget.getInstance().getResult(2));

        return placeInInventory(inv, r, items);
    }




}
