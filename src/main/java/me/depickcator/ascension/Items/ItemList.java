package me.depickcator.ascension.Items;

import me.depickcator.ascension.Ascension;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemList {
    private final Ascension ab;
    private ArrayList<ItemStack> items;
    private final EasyItems easyItems;
    private final MediumItems mediumItems;
    private final Harditems harditems;
    private final CustomItems customItems;
    private final CombatItems combatItems;
    public ItemList(Ascension ab) {
        this.ab = ab;
        easyItems = new EasyItems();
        mediumItems = new MediumItems();
        harditems = new Harditems();
        customItems = new CustomItems();
        combatItems = new CombatItems();
        items = new ArrayList<>();
        setItems();
    }

    public void setItems() {
        items.addAll(easyItems.getItems());
    }

    private List<ItemStack> grabItemsFromList(List<ItemStack> items, int amount) {
        Random r = new Random();
        List<ItemStack> itemList = new ArrayList<>(items);
        ArrayList<ItemStack> arr = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            int index = r.nextInt(0, itemList.size());
            arr.add(itemList.get(index));
            itemList.remove(index);
        }
        return arr;
    }

    public ArrayList<ItemStack> get25() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.addAll(grabItemsFromList(easyItems.getItems(), 5));
        items.addAll(grabItemsFromList(mediumItems.getItems(), 8));
        items.addAll(grabItemsFromList(harditems.getItems(), 5));
        items.addAll(grabItemsFromList(customItems.getItems(), 7));
//        items.addAll(grabItemsFromList(combatItems.getItems(), 5));
        return items;
    }

    public ArrayList<ItemStack> getItemsForBoard() {
        Random r = new Random();
        ArrayList<ItemStack> items = new ArrayList<>();
        for (ItemStack item : get25()) {
            ItemStack i = item.clone();
            i.setAmount(1);
            int size = items.size();
            if (items.isEmpty()) {
                items.add(i);
            } else {
                items.add(r.nextInt(0, size), i);
            }
        }
        return items;
    }
}
