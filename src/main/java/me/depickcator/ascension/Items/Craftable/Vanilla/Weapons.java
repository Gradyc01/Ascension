package me.depickcator.ascension.Items.Craftable.Vanilla;

import me.depickcator.ascension.Items.Craftable.Craft;

public abstract class Weapons extends Craft {
    private final double attackDamage;
    private final double attackSpeed;
    protected Weapons(String displayName, String key, double attackDamage, double attackSpeed) {
        super(displayName, key);
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        result = initResult();
        recipe = initRecipe();
    }

    protected Weapons(int cost, int maxCrafts, String displayName, String key, double attackDamage, double attackSpeed) {
        super(cost, maxCrafts, displayName, key, true);
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        result = initResult();
        recipe = initRecipe();
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }
}
