package me.depickcator.ascension.Items;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Craftable.Vanilla.*;
import me.depickcator.ascension.Items.Uncraftable.KitBook;
import me.depickcator.ascension.Items.Uncraftable.XPTome.XPTome;
import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class UnlocksData {
    private final ArrayList<Craft> tier1Unlocks;
    private final ArrayList<Craft> tier2Unlocks;
    private final ArrayList<Craft> tier3Unlocks;
    private final ArrayList<Craft> tier4Unlocks;
    private final ArrayList<Craft> tier5Unlocks;

    public UnlocksData() {
        tier1Unlocks = new ArrayList<>();
        tier2Unlocks = new ArrayList<>();
        tier3Unlocks = new ArrayList<>();
        tier4Unlocks = new ArrayList<>();
        tier5Unlocks = new ArrayList<>();
        Tier1Unlocks();
        Tier2Unlocks();
        Tier3Unlocks();
        Tier4Unlocks();
        Tier5Unlocks();
        Uncraftable();
        Vanilla();
    }


    private void Tier1Unlocks() {
        tier1Unlocks.add(IronPack.getInstance());
        tier1Unlocks.add(GoldPack.getInstance());
        tier1Unlocks.add(QuickPick.getInstance());
        tier1Unlocks.add(SugarRush.getInstance());
        tier1Unlocks.add(LeatherEconomy.getInstance());
        tier1Unlocks.add(ArrowEconomy.getInstance());
        tier1Unlocks.add(EveTemptation.getInstance());
        tier1Unlocks.add(Backpack.getInstance());
        tier1Unlocks.add(FlintShovel.getInstance());
        tier1Unlocks.add(QuickBow.getInstance());
    }

    private void Tier2Unlocks() {
        tier2Unlocks.add(DiamondPack.getInstance());
        tier2Unlocks.add(NotchApple.getInstance());
        tier2Unlocks.add(FlamingArtifact.getInstance());
        tier2Unlocks.add(PhilosopherPickaxe.getInstance());
        tier2Unlocks.add(LightAnvil.getInstance());
        tier2Unlocks.add(EnlighteningPack.getInstance());
        tier2Unlocks.add(LightApple.getInstance());
        tier2Unlocks.add(GoldenHead.getInstance());
        tier2Unlocks.add(VorpalSword.getInstance());
    }

    private void Tier3Unlocks() {
        tier3Unlocks.add(Tarnhelm.getInstance());
        tier3Unlocks.add(KingsRod.getInstance());
        tier3Unlocks.add(BookOfPower.getInstance());
        tier3Unlocks.add(BookOfProtection.getInstance());
        tier3Unlocks.add(BookOfSharpness.getInstance());
        tier3Unlocks.add(BookOfProjectileProtection.getInstance());
        tier3Unlocks.add(AncientArtifact.getInstance());
        tier3Unlocks.add(SoulArtifact.getInstance());
        tier3Unlocks.add(Nectar.getInstance());
        tier3Unlocks.add(PotionOfVelocity.getInstance());
    }

    private void Tier4Unlocks() {
        tier4Unlocks.add(Cornucopia.getInstance());
        tier4Unlocks.add(DragonArmor.getInstance());
        tier4Unlocks.add(HideOfLeviathan.getInstance());
        tier4Unlocks.add(SevenLeagueBoots.getInstance());
        tier4Unlocks.add(Panacea.getInstance());
        tier4Unlocks.add(Dread.getInstance());
        tier4Unlocks.add(CubeConverter.getInstance());
        tier4Unlocks.add(ExplosivePropulsion.getInstance());
        tier4Unlocks.add(Resurrection.getInstance());
        tier4Unlocks.add(WeaverSilk.getInstance());
    }

    private void Tier5Unlocks() {
        tier5Unlocks.add(CrimsonArtifact.getInstance());
        tier5Unlocks.add(ApolloGlare.getInstance());
        tier5Unlocks.add(HeliosCurse.getInstance());
    }

    private void Uncraftable() {
        new XPTome();
        new KitBook();
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
}
