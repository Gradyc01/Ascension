package me.depickcator.ascension.Items.ItemLists;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.LocationChecker.LocationCheck;
import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class ItemLists {
    private final List<ItemStack> items;
    private final Random random;
    private final LocationCheck locationCheck;
    public ItemLists() {
        this.items = new ArrayList<>();
        this.random = new Random();
        locationCheck = Ascension.getInstance().getLocationCheck();
        initItemList();
    }

    protected abstract void initItemList();

    /*Adds num random items to the items from itemList and returns the result
    * num must be > 0 && itemList.size() > num*/
    public List<ItemStack> getRandomItemInList(List<ItemStack> itemList, int num) {
        List<ItemStack> modifiableItemList = new ArrayList<>(itemList);
        List<ItemStack> result = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            ItemStack item = modifiableItemList.get(random.nextInt(modifiableItemList.size()));
            result.add(item);
            modifiableItemList.remove(item);
        }
        return result;
    }

    public ItemStack getRandomItemInList(List<ItemStack> itemList) {
        return getRandomItemInList(itemList, 1).getFirst();
    }

    protected List<ItemStack> addItems(List<ItemStack> items, Biome... biome) {
        if (biome == null || locationCheck.isALocation(biome)) {
            this.items.addAll(items);
        }
        return this.items;
    }

    protected List<ItemStack> addItems(List<ItemStack> items, Structure... structure) {
        if (structure == null || locationCheck.isALocation(structure)) {
            this.items.addAll(items);
        }
        return this.items;
    }

    protected List<ItemStack> addItems(List<ItemStack> items) {
        this.items.addAll(items);
        return this.items;
    }

    protected List<ItemStack> parseMaterials(Material... materials) {
        List<ItemStack> result = new ArrayList<>();
        for (Material material : materials) {
            result.add(new ItemStack(material));
        }
        return result;
    }

    protected List<ItemStack> parseCustomItems(CustomItem... customItems) {
        List<ItemStack> result = new ArrayList<>();
        for (CustomItem customItem : customItems) {
            result.add(customItem.getResult());
        }
        return result;
    }


    public List<ItemStack> getItems() {
        return items;
    }

    public List<ItemStack> getItems(int amount) {
//        List<ItemStack> startingItems = new ArrayList<>(items);
//        List<ItemStack> result = new ArrayList<>();
//        for (int i = 0; i < amount; i++) {
//            ItemStack item = startingItems.get(random.nextInt(startingItems.size()));
//            result.add(item);
//            startingItems.remove(item);
//        }
//        return result;
        return getRandomItemInList(items, amount);
    }
}
