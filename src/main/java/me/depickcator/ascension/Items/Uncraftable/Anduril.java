package me.depickcator.ascension.Items.Uncraftable;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Vanilla.IronSword;
import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Anduril extends CustomItem {
    private static Anduril instance;
    private Anduril() {
        super("Andūril", "anduril");
    }

    public static Anduril getInstance() {
        if (instance == null) {
            instance = new Anduril();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = IronSword.getInstance().getResult();
        ItemMeta meta = item.getItemMeta();

        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.AQUA));
        List<Component> lore = new ArrayList<>(meta.lore());
        lore.addAll(List.of(
                TextUtil.makeText(" +20% Movement Speed", TextUtil.BLUE)
//                TextUtil.makeText(" +4 Armor", TextUtil.BLUE)
        ));
        meta.lore(lore);

        NamespacedKey key = new NamespacedKey(Ascension.getInstance(), getKey());
        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, new AttributeModifier(key, 0.20, AttributeModifier.Operation.ADD_SCALAR));
//        meta.addAttributeModifier(Attribute.ARMOR, new AttributeModifier(key, 4, AttributeModifier.Operation.ADD_NUMBER));
        meta.addEnchant(Enchantment.SHARPNESS, 3, true);
        item.setItemMeta(meta);
        return item;
    }
}
