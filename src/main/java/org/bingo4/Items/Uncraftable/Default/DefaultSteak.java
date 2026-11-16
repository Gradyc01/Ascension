package org.bingo4.Items.Uncraftable.Default;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bingo4.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class DefaultSteak extends CustomItem {
    private static DefaultSteak instance;
    private DefaultSteak() {
        super("Steak", "default_sword");
    }

    public static DefaultSteak getInstance() {
        if (instance == null) instance = new DefaultSteak();
        return instance;
    }

    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.COOKED_BEEF);
//        PotionEffect regenEffect = new PotionEffect(
//                PotionEffectType.REGENERATION,
//                20 * 30,
//                0
//        );

//        Consumable consumable = Consumable.consumable()
//                .addEffect(ConsumeEffect.applyStatusEffects(List.of(regenEffect), 1.0f))
//                .build();
//
//        item.setData(DataComponentTypes.CONSUMABLE, consumable);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Steak", NamedTextColor.AQUA));
        generateUniqueModelNumber(item);
        return item;
    }


}
