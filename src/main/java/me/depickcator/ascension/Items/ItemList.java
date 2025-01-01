package me.depickcator.ascension.Items;

import me.depickcator.ascension.Items.ItemLists.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ItemList {
    private ArrayList<ItemStack> items;
    private final EasyItems easyItems;
    private final MediumItems mediumItems;
    private final Harditems harditems;
    private final CustomItems customItems;
    private final CombatItems combatItems;
    public ItemList() {
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

    public List<ItemStack> grabItemsFromList(List<ItemStack> items, int amount) {
        Random r = new Random();
        List<ItemStack> itemList = new ArrayList<>(items);
        ArrayList<ItemStack> arr = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            int index = r.nextInt(0, itemList.size());
            ItemStack item = itemList.get(index).clone();
            item.setAmount(1);
            arr.add(item);
            itemList.remove(index);
        }
        return arr;
    }

    public ArrayList<ItemStack> get25(int easyItems, int mediumItems, int hardItems, int customItems) {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.addAll(grabItemsFromList(this.easyItems.getItems(), easyItems));
        items.addAll(grabItemsFromList(this.mediumItems.getItems(), mediumItems));
        items.addAll(grabItemsFromList(this.harditems.getItems(), hardItems));
        items.addAll(grabItemsFromList(this.customItems.getItems(), customItems));
//        items.addAll(grabItemsFromList(combatItems.getItems(), 5));
        return items;
    }

    public ArrayList<ItemStack> getItemsForBoard(List<Integer> itemDistribution) {
        if (itemDistribution.size() != 4) {
            throw new IllegalArgumentException("Invalid item distribution");
        }
        ArrayList<ItemStack> items = new ArrayList<>(get25(
                itemDistribution.get(0),
                itemDistribution.get(1),
                itemDistribution.get(2),
                itemDistribution.get(3)));
//        if (items.size() != 25) {
//            throw new IllegalArgumentException("Not 25 items");
//        }
        for (int i = items.size(); i < 25; i++) {
            items.add(new ItemStack(Material.PLAYER_HEAD));
        }
        Collections.shuffle(items);
        return items;
    }

    public EasyItems getEasyItems() {
        return easyItems;
    }

    public MediumItems getMediumItems() {
        return mediumItems;
    }

    public Harditems getHarditems() {
        return harditems;
    }

    public CustomItems getCustomItems() {
        return customItems;
    }

    public CombatItems getCombatItems() {
        return combatItems;
    }
}
