package me.depickcator.ascension.Items.ItemLists;

import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CustomItems {
    private ArrayList<ItemStack> items;
    public CustomItems() {
        items = new ArrayList<>();
        setItems();
    }

    private void setItems() {
        items.add(new ItemStack(Material.OMINOUS_BOTTLE));
        items.add(new ItemStack(Material.TOTEM_OF_UNDYING));
        items.add(KingsRod.getInstance().getResult());
        items.add(Nectar.getInstance().getResult());
        items.add(WeaverSilk.getInstance().getResult());
        items.add(DragonArmor.getInstance().getResult());
        items.add(HideOfLeviathan.getInstance().getResult());
        items.add(SevenLeagueBoots.getInstance().getResult());
        items.add(Tarnhelm.getInstance().getResult());

        //Combat
        items.add(new ItemStack(Material.PLAYER_HEAD));
        items.add(new ItemStack(Material.NETHER_STAR));
        items.add(ShardOfTheFallen.result());
        items.add(GoldenHead.getInstance().getResult());
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }
}
