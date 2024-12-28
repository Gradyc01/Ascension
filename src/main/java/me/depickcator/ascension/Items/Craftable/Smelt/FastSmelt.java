package me.depickcator.ascension.Items.Craftable.Smelt;

import me.depickcator.ascension.Items.Craftable.Craft;

public abstract class FastSmelt extends Craft {
    protected final int smeltTime;
    protected FastSmelt(String key, int smeltTime) {
        super(key);
        this.smeltTime = smeltTime;
    }

    protected FastSmelt(String key) {
        super(key);
        this.smeltTime = 200;
    }

    public int getSmeltTime() {
        return smeltTime;
    }

}