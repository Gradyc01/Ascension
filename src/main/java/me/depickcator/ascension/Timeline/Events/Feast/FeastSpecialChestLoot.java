package me.depickcator.ascension.Timeline.Events.Feast;

import me.depickcator.ascension.Interfaces.CustomChestLoot;
import me.depickcator.ascension.Items.Uncraftable.HadesBook.HadesBook;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class FeastSpecialChestLoot extends CustomChestLoot {
    private static FeastSpecialChestLoot instance;
    @Override
    public Collection<ItemStack> populateLoot(Inventory inv, Random r, double luck) {
//        return List.of();
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.NETHER_STAR, 2));
        items.add(HadesBook.getInstance().getItem());

        return placeInInventory(inv, r, items);
    }



    public static FeastSpecialChestLoot getInstance() {
        if (instance == null) instance = new FeastSpecialChestLoot();
        return instance;
    }
}
