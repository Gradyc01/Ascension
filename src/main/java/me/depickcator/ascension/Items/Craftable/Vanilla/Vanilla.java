package me.depickcator.ascension.Items.Craftable.Vanilla;

import me.depickcator.ascension.General.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public interface Vanilla {
    void removeVanillaRecipe();
    static ItemMeta addModifiers(ItemMeta meta, double attackDamage, double attackSpeed, String KEY) {
        meta.addAttributeModifier(Attribute.ATTACK_DAMAGE, Vanilla.makeAttackDamageModifier(KEY, attackDamage));
        meta.addAttributeModifier(Attribute.ATTACK_SPEED, Vanilla.makeAttackSpeedModifier(KEY, attackSpeed));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.lore(Vanilla.makeFakeLore(attackDamage, attackSpeed));
        return meta;
    }
    static AttributeModifier makeAttackSpeedModifier(String key, double num) {
        return new AttributeModifier(
                NamespacedKey.minecraft(key),
                num,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.MAINHAND);
    }
    static AttributeModifier makeAttackDamageModifier(String key, double num) {
        return  new AttributeModifier(
                NamespacedKey.minecraft(key),
                num,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.MAINHAND);
    }
    static List<Component> makeFakeLore(double attackDamage, double attackSpeed) {
        List<Component> lore = new ArrayList<>();
        lore.add(TextUtil.makeText("", TextUtil.GRAY));
        lore.add(TextUtil.makeText("When in Main Hand:", TextUtil.GRAY));
        lore.add(TextUtil.makeText(" " + ((int) attackDamage) + " Attack Damage", TextUtil.DARK_GREEN));
        lore.add(TextUtil.makeText(" " + Math.round((4 + attackSpeed) * 10) / 10.0 + " Attack Speed", TextUtil.DARK_GREEN));
        return lore;
    }
}
