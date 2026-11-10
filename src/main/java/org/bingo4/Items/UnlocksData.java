package org.bingo4.Items;

import org.bingo4.Items.Craftable.Craft;
import org.bingo4.Items.Craftable.Unlocks.*;
import org.bingo4.Items.Craftable.Unlocks.EcholocatorItem.Echolocator;
import org.bingo4.Items.Craftable.Unlocks.GhostItem.Ghost;
import org.bingo4.Items.Craftable.Unlocks.MasterCompass.MasterCompass;
import org.bingo4.Items.Craftable.Unlocks.NetheriteInfusionItem.NetheriteInfusion;
import org.bingo4.Items.Craftable.Unlocks.PandoraBoxItem.PandoraBox;
import org.bingo4.Items.Craftable.Unlocks.RedLedgerItem.RedLedger;
import org.bingo4.Items.Craftable.Unlocks.TeamPortalItem.TeamPortal;
import org.bingo4.Items.Craftable.Vanilla.*;
import org.bingo4.Items.Uncraftable.EnderPearl;
import org.bingo4.Items.Uncraftable.KitBook;
import org.bingo4.Items.Uncraftable.ToolVoucher.ToolVoucher;
import org.bingo4.Items.Uncraftable.XPTome.XPTome;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnlocksData {
    private final Map<String, Pair<Craft, Integer>> unlocksMap;
    private final List<List<Craft>> allUnlocks;
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
        unlocksMap = new HashMap<>();
        allUnlocks = new ArrayList<>();
//
//        Tier1Unlocks();
//        Tier2Unlocks();
//        Tier3Unlocks();
//        Tier4Unlocks();
//        Tier5Unlocks();
//        Uncraftable();
//        Vanilla();
////        new RecipeBookModifier(); //TODO: Is Null
//        addToUnlockRecommender();
    }

    private void addToUnlockRecommender() {
//        UnlockRecommender recommender =  UnlockRecommender.getInstance();
//        for (List<Craft> crafts: getAllUnlocks()) {
//            for (Craft c: crafts) {
//                recommender.addCraft(c);
//            }
//        }
    }


    private void Tier1Unlocks() {
        addUnlock(1,
                IronPack.getInstance(),
                GoldPack.getInstance(),
                QuickPick.getInstance(),
                FlintShovel.getInstance(),
                LumberjackAxe.getInstance(),
                ArrowEconomy.getInstance(),
                EveTemptation.getInstance(),
                Backpack.getInstance(),
                SugarRush.getInstance(),
                LeatherEconomy.getInstance(),
                QuickBow.getInstance(),
                VorpalSword.getInstance(),
                DustOfLight.getInstance(),
                ApprenticeHelmet.getInstance()
        );
    }

    private void Tier2Unlocks() {
        addUnlock(2,
                FlamingArtifact.getInstance(),
                AdvancedQuickPick.getInstance(),
                PhilosopherPickaxe.getInstance(),
                LightAnvil.getInstance(),
                LightEnchantingTable.getInstance(),
                EnlighteningPack.getInstance(),
                LightApple.getInstance(),
                GoldenHead.getInstance(),
                Obsidian.getInstance(),
                PotionOfVelocity.getInstance(),
                BookOfProjectileProtection.getInstance(),
                BookOfProtection.getInstance(),
                BookOfPower.getInstance(),
                BookOfSharpness.getInstance()
        );
    }

    private void Tier3Unlocks() {
        addUnlock(3,
                Tarnhelm.getInstance(),
                KingsRod.getInstance(),
                DiamondPack.getInstance(),
                AncientArtifact.getInstance(),
                SoulArtifact.getInstance(),
                MakeshiftSkull.getInstance(),
                Nectar.getInstance(),
                BookOfThoth.getInstance(),
                LeapingAxe.getInstance(),
                DragonSword.getInstance(),
                Cornucopia.getInstance(),
                PandoraBox.getInstance(),
                MasterCompass.getInstance(),
                NotchApple.getInstance()
        );
    }

    private void Tier4Unlocks() {
        addUnlock(4,
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
                Ghost.getInstance(),
                MinerBlessing.getInstance(),
                NetheriteInfusion.getInstance()
        );
    }

    private void Tier5Unlocks() {
        addUnlock(5,
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
                Daredevil.getInstance(),
                Mothership.getInstance(),
                Poseidon.getInstance(),
                MakeshiftMace.getInstance()
        );
    }

    private void Uncraftable() {
        XPTome.getInstance();
        KitBook.getInstance();
        EnderPearl.getInstance();
        ToolVoucher.getInstance();
        LeapingAxe.getInstance();
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
        Mace.getInstance();
    }

    /*Returns the Craft and the Tier it is in for Unlock with displayName 'displayName'*/
    public Pair<Craft, Integer> findUnlock(String displayName) {
        return unlocksMap.get(displayName);
    }


    public List<List<Craft>> getAllUnlocks() {
        return allUnlocks;
    }

    /*Add Craft craft into the UnlockMap with Tier tier*/
    private void addUnlock(int tier, Craft... craft) {
        allUnlocks.add(new ArrayList<>(List.of(craft)));
        for (Craft c : craft) {
            unlocksMap.put(c.getDisplayName(), Pair.of(c, tier));
        }
    }

    public List<Craft> getUnlocksTier(int tier) {
        return getAllUnlocks().get(tier - 1);
    }
}
