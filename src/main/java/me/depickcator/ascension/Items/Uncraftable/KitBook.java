package me.depickcator.ascension.Items.Uncraftable;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.Kits.Kits.*;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Kits.KitBookGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitBook extends CustomItem implements ItemClick {
    private static KitBook instance;
    private final List<Kit2> kits;
    private KitBook() {
        super("Kit Book", "kit_book");
        registerItem();
        kits = registerKits();
    }

    @Override
    public ItemStack getItem() {
        return getResult();
    }

    public static KitBook getInstance() {
        if (instance == null) {
            instance = new KitBook();
        }
        return instance;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (isMainHandRightClick(e)) {
            new KitBookGUI(pD);
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }

    private List<Kit2> registerKits() {
        return new ArrayList<>(List.of(
                new Ecologist(),
                new Hunter(),
                new IronTools(),
                new Looter()
        ));
    }

    public List<Kit2> getKits() {
        return kits;
    }

    public Kit2 getKit(ItemStack item) {
        for (Kit2 k : kits) {
            if (k.getMascot().equals(item)) {
                return k;
            }
        }
        return null;
    }


    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.GOLD).append(TextUtil.rightClickText()));
        meta.setMaxStackSize(1);
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        meta.setEnchantmentGlintOverride(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }
}
