package me.depickcator.ascension.Settings.BuildCustom;

import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ChoiceButton extends Button {
    private int numChoices;
    private final HashMap<String, Material> choiceTitles;
    private String currentSelected;

    public ChoiceButton(Material material, String title, String initialSelection) {
        super(material, title);
        numChoices = 0;
        choiceTitles = new HashMap<>();
        currentSelected = initialSelection;
        updateVisual();
    }

    public abstract boolean build(BuildCustom custom);

    protected void addChoice(String choiceTitle, Material material) {
        numChoices++;
        choiceTitles.put(choiceTitle, material);
    };

    public void updateVisual() {
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(getCurrentSelection(), TextUtil.DARK_GREEN)
        ));
        ItemMeta meta = getItem().getItemMeta();
        meta.lore(lore);
        getItem().setItemMeta(meta);
    }

    public HashMap<String, Material> getChoiceTitles() {
        return choiceTitles;
    }

    public String getCurrentSelection() {
        return currentSelected;
    }

    public void setCurrentSelection(String newSelection) {
        currentSelected = newSelection;
        updateVisual();
    }

    public int getNumChoices() {
        return numChoices;
    }


}
