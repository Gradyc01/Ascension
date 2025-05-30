package me.depickcator.ascension.Kits.Kits;

import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Echolocator;
import me.depickcator.ascension.Items.Craftable.Vanilla.Shield;
import me.depickcator.ascension.Items.Uncraftable.Anduril;
import me.depickcator.ascension.Items.Uncraftable.NetherStar.NetherStar;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Scout extends Kit {
    public Scout() {
        super("Scout");
    }

    @Override
    public List<ItemStack> initKitItems() {
        return new ArrayList<>(List.of(
                Anduril.getInstance().getResult(),
                NetherStar.getInstance().getResult(2),
                Echolocator.getInstance().getResult(),
                Shield.getInstance().getResult()
        ));
    }

    @Override
    public ItemStack getMascot() {
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.AQUA));
        item.setItemMeta(meta);
        return item;
    }
}
