package me.depickcator.ascension.Items;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

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
        items.add(KingsRod.result());
        items.add(Nectar.result());
        items.add(KingsRod.result());
        items.add(WeaverSilk.result());
        items.add(DragonArmor.result());
        items.add(HideOfLeviathan.result());
        items.add(SevenLeagueBoots.result());
        items.add(Tarnhelm.result());
        items.add(ExplosivePropulsion.result());

        //Combat
        items.add(new ItemStack(Material.PLAYER_HEAD));
        items.add(new ItemStack(Material.NETHER_STAR));
        items.add(ShardOfTheFallen.result());
        items.add(new ItemStack(Material.MACE));//TODO: Maybe Change Later to Hard
        items.add(new ItemStack(Material.TRIDENT)); //TODO: Maybe Change Later to Hard
        items.add(GoldenHead.result());
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }
}
