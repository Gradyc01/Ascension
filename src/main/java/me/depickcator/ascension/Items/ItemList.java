package me.depickcator.ascension.Items;

import me.depickcator.ascension.Ascension;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemList {
    private final Ascension ab;
    private ArrayList<ItemStack> items;
    private EasyItems easyItems;
    public ItemList(Ascension ab) {
        this.ab = ab;
        easyItems = new EasyItems();
        items = new ArrayList<>();
        setItems();
    }

    public void setItems() {
        items.addAll(easyItems.getItems());
    }

    public ArrayList<ItemStack> get25() {
        ArrayList<ItemStack> items = new ArrayList<>(this.items);
        ArrayList<ItemStack> arr = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            int index = (int)(Math.random() * items.size());
            arr.add(items.get(index));
            items.remove(index);
        }
        return arr;
    }
}
