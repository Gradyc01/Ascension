package me.depickcator.ascension.Items.ItemLists;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;

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

        //Tools
        items.add(QuickPick.getInstance().getResult());
        items.add(FlintShovel.getInstance().getResult());
        items.add(PhilosopherPickaxe.getInstance().getResult());
        items.add(VorpalSword.getInstance().getResult());
        items.add(BookOfProjectileProtection.getInstance().getResult());
        items.add(BookOfProtection.getInstance().getResult());
        items.add(BookOfPower.getInstance().getResult());
        items.add(BookOfSharpness.getInstance().getResult());
        items.add(PotionOfVelocity.getInstance().getResult());
        items.add(BookOfThoth.getInstance().getResult());
        items.add(Cornucopia.getInstance().getResult());
        items.add(Panacea.getInstance().getResult());
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }
}
