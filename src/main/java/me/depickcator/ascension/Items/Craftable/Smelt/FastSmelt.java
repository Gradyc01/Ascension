package me.depickcator.ascension.Items.Craftable.Smelt;

import me.depickcator.ascension.Items.Craftable.Craft;

public abstract class FastSmelt extends Craft {
    protected final int smeltTime;
    protected FastSmelt(String key, int smeltTime) {
        super(key);
        this.smeltTime = smeltTime;
        result = initResult();
        recipe = initRecipe();
    }

    public int getSmeltTime() {
        return smeltTime;
    }

}