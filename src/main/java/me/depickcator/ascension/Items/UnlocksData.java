package me.depickcator.ascension.Items;

import java.util.ArrayList;
import java.util.List;

import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Echolocator;
import me.depickcator.ascension.Items.Craftable.Unlocks.GhostItem.Ghost;
import me.depickcator.ascension.Items.Craftable.Unlocks.MasterCompass.MasterCompass;
import me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.PandoraBox;
import me.depickcator.ascension.Items.Craftable.Unlocks.RedLedgerItem.RedLedger;
import me.depickcator.ascension.Items.Craftable.Unlocks.TeamPortalItem.TeamPortal;
import org.apache.commons.lang3.tuple.Pair;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import me.depickcator.ascension.Items.Craftable.Vanilla.*;
import me.depickcator.ascension.Items.Uncraftable.KitBook;
import me.depickcator.ascension.Items.Uncraftable.XPTome.XPTome;

public class UnlocksData {
    private ArrayList<Craft> tier1Unlocks;
    private ArrayList<Craft> tier2Unlocks;
    private ArrayList<Craft> tier3Unlocks;
    private ArrayList<Craft> tier4Unlocks;
    private ArrayList<Craft> tier5Unlocks;
    public static int COST_75 = 75;
    public static int COST_100 = 100;
    public static int COST_125 = 125;
    public static int COST_150 = 150;
    public static int COST_175 = 175;
    public static int COST_200 = 200;
    public static int COST_225 = 225;
    public static int COST_250 = 250;
    public static int COST_275 = 275;
    public static int COST_300 = 300;
    public static int COST_325 = 325;
    public static int COST_350 = 350;
    public static int COST_375 = 375;
    public static int COST_400 = 400;
    public static int COST_425 = 425;
    public static int COST_450 = 450;
    public static int COST_475 = 475;
    public static int COST_500 = 500;

    /*Initializes all Unlocks and Custom Items */
    public UnlocksData() {
        Tier1Unlocks();
        Tier2Unlocks();
        Tier3Unlocks();
        Tier4Unlocks();
        Tier5Unlocks();
        Uncraftable();
        Vanilla();
    }


    private void Tier1Unlocks() {
        tier1Unlocks = new ArrayList<>(List.of(
                IronPack.getInstance(),
                GoldPack.getInstance(),
                QuickPick.getInstance(),
                SugarRush.getInstance(),
                LeatherEconomy.getInstance(),
                ArrowEconomy.getInstance(),
                EveTemptation.getInstance(),
                Backpack.getInstance(),
                FlintShovel.getInstance(),
                QuickBow.getInstance(),
                VorpalSword.getInstance(),
                DustOfLight.getInstance()
        ));
    }

    private void Tier2Unlocks() {
        tier2Unlocks = new ArrayList<>(List.of(
                DiamondPack.getInstance(),
                FlamingArtifact.getInstance(),
                PhilosopherPickaxe.getInstance(),
                LightAnvil.getInstance(),
                LightEnchantingTable.getInstance(),
                EnlighteningPack.getInstance(),
                LightApple.getInstance(),
                GoldenHead.getInstance(),
                ApprenticeHelmet.getInstance(),
                Obsidian.getInstance(),
                BookOfProjectileProtection.getInstance(),
                BookOfProtection.getInstance(),
                BookOfPower.getInstance(),
                BookOfSharpness.getInstance()
        ));
    }

    private void Tier3Unlocks() {
        tier3Unlocks = new ArrayList<>(List.of(
                Tarnhelm.getInstance(),
                KingsRod.getInstance(),
                NotchApple.getInstance(),
                AncientArtifact.getInstance(),
                SoulArtifact.getInstance(),
                MakeshiftStar.getInstance(),
                Nectar.getInstance(),
                PotionOfVelocity.getInstance(),
                BookOfThoth.getInstance(),
                DragonSword.getInstance(),
                Cornucopia.getInstance(),
                PandoraBox.getInstance(),
                MasterCompass.getInstance()
        ));
    }

    private void Tier4Unlocks() {
        tier4Unlocks = new ArrayList<>(List.of(
                DragonArmor.getInstance(),
                HideOfLeviathan.getInstance(),
                SevenLeagueBoots.getInstance(),
                Dread.getInstance(),
                CubeConverter.getInstance(),
                ExplosivePropulsion.getInstance(),
                TeamPortal.getInstance(),
                WeaverSilk.getInstance(),
                RedLedger.getInstance(),
                CrimsonArtifact.getInstance(),
                Echolocator.getInstance(),
                Ghost.getInstance()
        ));
    }

    private void Tier5Unlocks() {
        tier5Unlocks = new ArrayList<>(List.of(
                AscensionKey.getInstance(),
                Resurrection.getInstance(),
                CupidBow.getInstance(),
                ApolloGlare.getInstance(),
                HeliosCurse.getInstance(),
                HermesBoots.getInstance(),
                Exodus.getInstance(),
                WingsOfIcarus.getInstance(),
                Panacea.getInstance(),
                TabletsOfDestiny.getInstance(),
                Daredevil.getInstance()
        ));
    }

    private void Uncraftable() {
        XPTome.getInstance();
        KitBook.getInstance();
    }

    private void Vanilla() {
        WoodenSword.getInstance();
        StoneSword.getInstance();
        IronSword.getInstance();
        DiamondSword.getInstance();
        NetheriteSword.getInstance();
        WoodenAxe.getInstance();
        StoneAxe.getInstance();
        IronAxe.getInstance();
        DiamondAxe.getInstance();
        NetheriteAxe.getInstance();
        Shield.getInstance();
    }

    /*Returns the Craft and the Tier it is in for Unlock with displayName 'displayName'*/
    public Pair<Craft, Integer> findUnlock(String displayName) {
        ArrayList<ArrayList<Craft>> arr = Ascension.getInstance().getUnlocksData().getAllUnlocks();
        for (int i = 0; i < arr.size(); i++) {
            for (Craft c : arr.get(i)) {
                if(c.getDisplayName().equals(displayName)) {
                    return Pair.of(c, i + 1);
                }
            }
        }
        return null;
    }

    public ArrayList<Craft> getTier1Unlocks() {
        return tier1Unlocks;
    }

    public ArrayList<Craft> getTier2Unlocks() {
        return tier2Unlocks;
    }

    public ArrayList<Craft> getTier3Unlocks() {
        return tier3Unlocks;
    }

    public ArrayList<Craft> getTier4Unlocks() {
        return tier4Unlocks;
    }

    public ArrayList<Craft> getTier5Unlocks() {
        return tier5Unlocks;
    }

    public ArrayList<ArrayList<Craft>> getAllUnlocks() {
        ArrayList<ArrayList<Craft>> allUnlocks = new ArrayList<>();
        allUnlocks.add(tier1Unlocks);
        allUnlocks.add(tier2Unlocks);
        allUnlocks.add(tier3Unlocks);
        allUnlocks.add(tier4Unlocks);
        allUnlocks.add(tier5Unlocks);
        return allUnlocks;
    }

    public ArrayList<Craft> getUnlocksTier(int tier) {
        return getAllUnlocks().get(tier - 1);
    }
}
