package me.depickcator.ascension.Items.ItemLists;

import java.util.ArrayList;

import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Echolocator;
import me.depickcator.ascension.Items.Craftable.Unlocks.GhostItem.Ghost;
import me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.PandoraBox;
import me.depickcator.ascension.Items.Craftable.Unlocks.RedLedgerItem.RedLedger;
import me.depickcator.ascension.Items.Craftable.Unlocks.TeamPortalItem.TeamPortal;
import me.depickcator.ascension.Items.Uncraftable.XPTome.XPTome;
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

        //Equipment
        items.add(DragonArmor.getInstance().getResult());
        items.add(HideOfLeviathan.getInstance().getResult());
        items.add(SevenLeagueBoots.getInstance().getResult());
        items.add(Tarnhelm.getInstance().getResult());
        items.add(HermesBoots.getInstance().getResult());
        items.add(Exodus.getInstance().getResult());
        items.add(WingsOfIcarus.getInstance().getResult());
        items.add(ApolloGlare.getInstance().getResult());
        items.add(HeliosCurse.getInstance().getResult());
        items.add(CupidBow.getInstance().getResult());

        //Combat
        items.add(new ItemStack(Material.PLAYER_HEAD));
        items.add(new ItemStack(Material.NETHER_STAR));
        items.add(ShardOfTheFallen.result());
        items.add(GoldenHead.getInstance().getResult());
        items.add(PandoraBox.getInstance().getResult());


        //Abilities
        items.add(TeamPortal.getInstance().getResult());
        items.add(RedLedger.getInstance().getResult());
        items.add(Echolocator.getInstance().getResult());
        items.add(Ghost.getInstance().getResult());
        items.add(AscensionKey.getInstance().getResult());



        //Tools
        items.add(QuickPick.getInstance().getResult());
        items.add(FlintShovel.getInstance().getResult());
        items.add(PhilosopherPickaxe.getInstance().getResult());
        items.add(VorpalSword.getInstance().getResult());
        items.add(PotionOfVelocity.getInstance().getResult());
        items.add(Cornucopia.getInstance().getResult());
        items.add(CubeConverter.getInstance().getResult());
        items.add(Panacea.getInstance().getResult());


        //Books
        items.add(BookOfThoth.getInstance().getResult());
        items.add(XPTome.getInstance().getItem());
        items.add(TabletsOfDestiny.getInstance().getResult());
        items.add(BookOfProjectileProtection.getInstance().getResult());
        items.add(BookOfProtection.getInstance().getResult());
        items.add(BookOfPower.getInstance().getResult());
        items.add(BookOfSharpness.getInstance().getResult());
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }
}
