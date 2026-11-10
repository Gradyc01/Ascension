package org.bingo4.Kits.Kits;

import org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.Echolocator;
import org.bingo4.Items.Craftable.Vanilla.Shield;
import org.bingo4.Items.Uncraftable.Anduril;
import org.bingo4.Items.Uncraftable.NetherStar.NetherStar;
import org.bingo4.Utility.TextUtil;
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
