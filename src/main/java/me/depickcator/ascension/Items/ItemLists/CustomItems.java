package me.depickcator.ascension.Items.ItemLists;

import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Echolocator;
import me.depickcator.ascension.Items.Craftable.Unlocks.GhostItem.Ghost;
import me.depickcator.ascension.Items.Craftable.Unlocks.MasterCompass.MasterCompass;
import me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.PandoraBox;
import me.depickcator.ascension.Items.Craftable.Unlocks.RedLedgerItem.RedLedger;
import me.depickcator.ascension.Items.Craftable.Unlocks.TeamPortalItem.TeamPortal;
import me.depickcator.ascension.Items.Uncraftable.NetherStar.NetherStar;
import me.depickcator.ascension.Items.Uncraftable.RejuvenationBook;
import me.depickcator.ascension.Items.Uncraftable.RepairKit;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Items.Uncraftable.Skulls.PlayerHead;
import me.depickcator.ascension.Items.Uncraftable.XPTome.XPTome;
import org.bukkit.entity.Pose;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CustomItems extends ItemLists{

    @Override
    protected void initItemList() {
        addItems(getRandomItemInList(getEquipment(), 2));
        addItems(getRandomItemInList(getLevel1EnchantedBooks(), 1));
        addItems(parseCustomItems(
                //Equipment
                ApprenticeHelmet.getInstance(),
                MasterCompass.getInstance(),
                MinerBlessing.getInstance(),
                DragonSword.getInstance(),
                LeapingAxe.getInstance(),
                //Combat
                PlayerHead.getInstance(),
                MakeshiftSkull.getInstance(),
                ShardOfTheFallen.getInstance(),
                GoldenHead.getInstance(),
                PandoraBox.getInstance(),
                NetherStar.getInstance(),
                //Abilities
                TeamPortal.getInstance(),
                RedLedger.getInstance(),
                Echolocator.getInstance(),
                Ghost.getInstance(),
                AscensionKey.getInstance(),
                Resurrection.getInstance(),
                //Tools
                RepairKit.getInstance(),
                QuickPick.getInstance(),
                LumberjackAxe.getInstance(),
                FlintShovel.getInstance(),
                PhilosopherPickaxe.getInstance(),
                VorpalSword.getInstance(),
                KingsRod.getInstance(),
                AdvancedQuickPick.getInstance(),
                //Potions
                PotionOfVelocity.getInstance(),
                Nectar.getInstance(),
                Cornucopia.getInstance(),
                CubeConverter.getInstance(),
                Panacea.getInstance(),
                Dread.getInstance(),
                //Books
                BookOfThoth.getInstance(),
                TabletsOfDestiny.getInstance(),
                WeaverSilk.getInstance(),
                RejuvenationBook.getInstance()
                ));
    }

    private List<ItemStack> getLevel1EnchantedBooks() {
        List<ItemStack> items = new ArrayList<>();
        items.add(BookOfProjectileProtection.getInstance().getResult());
        items.add(BookOfProtection.getInstance().getResult());
        items.add(BookOfPower.getInstance().getResult());
        items.add(BookOfSharpness.getInstance().getResult());
        return items;
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
        items.add(Poseidon.getInstance().getResult());
        return items;
    }
}
