package me.depickcator.ascension.Items.Craftable.Vanilla;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BlocksAttacks;
import me.depickcator.ascension.Items.Craftable.Craft;
import net.kyori.adventure.key.Key;
import org.bukkit.inventory.ItemStack;

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

    /*Adds Sword Effects*/
    protected ItemStack addSwordEffects(ItemStack item) {
//        item.setData(DataComponentTypes.BLOCKS_ATTACKS, BlocksAttacks.blocksAttacks().blockDelaySeconds(0));
//        item.setData(DataComponentTypes.BLOCKS_ATTACKS, BlocksAttacks.blocksAttacks().blockSound(Key.key("entity.player.attack.nodamage")));
        return item;
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }
}
