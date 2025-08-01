package me.depickcator.ascension.Items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.UseCooldown;
import me.depickcator.ascension.Ascension;
import net.kyori.adventure.key.Key;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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

//    protected CustomModelDataComponent generateUniqueModelNumber(CustomModelDataComponent model) {
//        model.setFloats(new ArrayList<>(List.of((float) Ascension.getInstance().generateModelNumber())));
//        return model;
//    }

    protected void addCooldownGroup(ItemStack item, float seconds) {
        item.setData(DataComponentTypes.USE_COOLDOWN,
                UseCooldown.useCooldown(seconds)
                        .cooldownGroup(Key.key(Ascension.getInstance().getName().toLowerCase()+ ":" + getKey())));
    }

    protected void addCooldownGroup(ItemStack item) {
        addCooldownGroup(item, 0.01f);
    }

    /*Generates a unique model number for ItemStack item*/
    protected void generateUniqueModelNumber(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setFloats(new ArrayList<>(List.of((float) Ascension.getInstance().generateModelNumber())));
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);
    }

    /*Sets a model number for ItemStack item*/
    protected void setModelNumber(ItemStack item, int modelNumber) {
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setFloats(new ArrayList<>(List.of((float) modelNumber)));
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);
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
