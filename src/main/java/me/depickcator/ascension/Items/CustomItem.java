package me.depickcator.ascension.Items;

import me.depickcator.ascension.Ascension;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomItem {
    protected final String DISPLAY_NAME;
    protected final String KEY;
    protected ItemStack result;
    public CustomItem(String displayName, String key) {
        this.DISPLAY_NAME = displayName;
        this.KEY = key;
        result = initResult();
    }

    public CustomItem(String displayName, String key, boolean dontGenerateResult) {
        this.DISPLAY_NAME = displayName;
        this.KEY = key;
    }

    /*Initialize the Custom Item Result and Returns the ItemStack*/
    protected abstract ItemStack initResult();

    protected CustomModelDataComponent generateUniqueModelNumber(CustomModelDataComponent model) {
        model.setFloats(new ArrayList<>(List.of((float) Ascension.getInstance().generateModelNumber())));
        return model;
    }

    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    public String getKey() {
        return KEY;
    }

    public ItemStack getResult() {
        return result.clone();
    }

    public ItemStack getResult(int amount) {
        ItemStack item = result.clone();
        item.setAmount(amount);
        return item;
    }
}
