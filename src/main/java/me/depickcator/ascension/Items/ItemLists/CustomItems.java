package me.depickcator.ascension.Items.ItemLists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Echolocator;
import me.depickcator.ascension.Items.Craftable.Unlocks.GhostItem.Ghost;
import me.depickcator.ascension.Items.Craftable.Unlocks.MasterCompass.MasterCompass;
import me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.PandoraBox;
import me.depickcator.ascension.Items.Craftable.Unlocks.RedLedgerItem.RedLedger;
import me.depickcator.ascension.Items.Craftable.Unlocks.TeamPortalItem.TeamPortal;
import me.depickcator.ascension.Items.Uncraftable.NetherStar.NetherStar;
import me.depickcator.ascension.Items.Uncraftable.RejuvenationBook;
import me.depickcator.ascension.Items.Uncraftable.Skulls.PlayerHead;
import me.depickcator.ascension.Items.Uncraftable.XPTome.XPTome;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;

public class CustomItems {
    private List<ItemStack> items;
    public CustomItems() {
        items = new ArrayList<>();
        setItems();
    }

    private void setItems() {
        //Equipment
        items.addAll(getEquipment());
        items.add(ApprenticeHelmet.getInstance().getResult());
        items.add(MasterCompass.getInstance().getResult());
        items.add(MinerBlessing.getInstance().getResult());
        items.add(DragonSword.getInstance().getResult());

        //Combat
        items.add(PlayerHead.getInstance().getResult());
        items.add(MakeshiftSkull.getInstance().getResult());
        items.add(ShardOfTheFallen.getInstance().getResult());
        items.add(GoldenHead.getInstance().getResult());
        items.add(PandoraBox.getInstance().getResult());
        items.add(NetherStar.getInstance().getResult());

        //Abilities
        items.add(TeamPortal.getInstance().getResult());
        items.add(RedLedger.getInstance().getResult());
        items.add(Echolocator.getInstance().getResult());
        items.add(Ghost.getInstance().getResult());
        items.add(AscensionKey.getInstance().getResult());
        items.add(Resurrection.getInstance().getResult());

        //Tools
        items.add(QuickPick.getInstance().getResult());
        items.add(FlintShovel.getInstance().getResult());
        items.add(PhilosopherPickaxe.getInstance().getResult());
        items.add(VorpalSword.getInstance().getResult());
        items.add(KingsRod.getInstance().getResult());
        items.add(AdvancedQuickPick.getInstance().getResult());

        //Potions
        items.add(PotionOfVelocity.getInstance().getResult());
        items.add(Nectar.getInstance().getResult());
        items.add(Cornucopia.getInstance().getResult());
        items.add(CubeConverter.getInstance().getResult());
        items.add(Panacea.getInstance().getResult());
        items.add(new ItemStack(Material.OMINOUS_BOTTLE));

        //Books
        items.add(BookOfThoth.getInstance().getResult());
        items.add(XPTome.getInstance().getItem());
        items.add(TabletsOfDestiny.getInstance().getResult());
        items.addAll(getLevel1EnchantedBooks());
        items.add(WeaverSilk.getInstance().getResult());
        items.add(RejuvenationBook.getInstance().getResult());
    }

    private List<ItemStack> getLevel1EnchantedBooks() {
        List<ItemStack> items = new ArrayList<>();
        items.add(BookOfProjectileProtection.getInstance().getResult());
        items.add(BookOfProtection.getInstance().getResult());
        items.add(BookOfPower.getInstance().getResult());
        items.add(BookOfSharpness.getInstance().getResult());
        Collections.shuffle(items);
        return new ArrayList<>(List.of(
                items.getFirst()
        ));
    }

    private List<ItemStack> getEquipment() {
        List<ItemStack> items = new ArrayList<>();
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
        Collections.shuffle(items);
        return new ArrayList<>(List.of(
                items.getFirst(),
                items.getLast()
        ));
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
