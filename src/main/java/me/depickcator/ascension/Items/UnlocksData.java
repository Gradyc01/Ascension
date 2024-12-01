package me.depickcator.ascension.Items;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Crafts;
import me.depickcator.ascension.Items.Craftable.Vanilla.*;
import me.depickcator.ascension.Items.Uncraftable.KitBook;
import me.depickcator.ascension.Items.Uncraftable.XPTome.XPTome;
import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class UnlocksData {
    private final ArrayList<Crafts> tier1Unlocks;
    private final ArrayList<Crafts> tier2Unlocks;
    private final ArrayList<Crafts> tier3Unlocks;
    private final ArrayList<Crafts> tier4Unlocks;
    private final ArrayList<Crafts> tier5Unlocks;

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
        tier1Unlocks.add(new IronPack());
        tier1Unlocks.add(new GoldPack());
        tier1Unlocks.add(new QuickPick());
        tier1Unlocks.add(new SugarRush());
        tier1Unlocks.add(new LeatherEconomy());
        tier1Unlocks.add(new ArrowEconomy());
        tier1Unlocks.add(new EveTemptation());
        tier1Unlocks.add(new Backpack());
        tier1Unlocks.add(new FlintShovel());
        tier1Unlocks.add(new QuickBow());
    }

    private void Tier2Unlocks() {
        tier2Unlocks.add(new DiamondPack());
        tier2Unlocks.add(new NotchApple());
        tier2Unlocks.add(new FlamingArtifact());
        tier2Unlocks.add(new PhilosopherPickaxe());
        tier2Unlocks.add(new LightAnvil());
        tier2Unlocks.add(new EnlighteningPack());
        tier2Unlocks.add(new LightApple());
        tier2Unlocks.add(new GoldenHead());
        tier2Unlocks.add(new VorpalSword());
    }

    private void Tier3Unlocks() {
        tier3Unlocks.add(new Tarnhelm());
        tier3Unlocks.add(new KingsRod());
        tier3Unlocks.add(new BookOfPower());
        tier3Unlocks.add(new BookOfProtection());
        tier3Unlocks.add(new BookOfSharpness());
        tier3Unlocks.add(new BookOfProjectileProtection());
        tier3Unlocks.add(new AncientArtifact());
        tier3Unlocks.add(new SoulArtifact());
        tier3Unlocks.add(new Nectar());
        tier3Unlocks.add(new PotionOfVelocity());
    }

    private void Tier4Unlocks() {
        tier4Unlocks.add(new Cornucopia());
        tier4Unlocks.add(new DragonArmor());
        tier4Unlocks.add(new HideOfLeviathan());
        tier4Unlocks.add(new SevenLeagueBoots());
        tier4Unlocks.add(new Panacea());
        tier4Unlocks.add(new Dread());
        tier4Unlocks.add(new CubeConverter());
        tier4Unlocks.add(new ExplosivePropulsion());
        tier4Unlocks.add(new Resurrection());
        tier4Unlocks.add(new WeaverSilk());
    }

    private void Tier5Unlocks() {
        tier5Unlocks.add(new CrimsonArtifact());
        tier5Unlocks.add(new ApolloGlare());
        tier5Unlocks.add(new HeliosCurse());
    }

    private void Uncraftable() {
        new XPTome();
        new KitBook();
    }

    private void Vanilla() {
        new WoodenSword();
        new StoneSword();
        new IronSword();
        new DiamondSword();
        new NetheriteSword();
        new WoodenAxe();
        new StoneAxe();
        new IronAxe();
        new DiamondAxe();
        new NetheriteAxe();
        new Shield();
    }

    public Pair<Crafts, Integer> findUnlock(String displayName) {
        ArrayList<ArrayList<Crafts>> arr = Ascension.getInstance().getUnlocksData().getAllUnlocks();
        for (int i = 0; i < arr.size(); i++) {
            for (Crafts c : arr.get(i)) {
                if(c.getDisplayName().equals(displayName)) {
                    return Pair.of(c, i + 1);
                }
            }
        }
        return null;
    }

    public ArrayList<Crafts> getTier1Unlocks() {
        return tier1Unlocks;
    }

    public ArrayList<Crafts> getTier2Unlocks() {
        return tier2Unlocks;
    }

    public ArrayList<Crafts> getTier3Unlocks() {
        return tier3Unlocks;
    }

    public ArrayList<Crafts> getTier4Unlocks() {
        return tier4Unlocks;
    }

    public ArrayList<Crafts> getTier5Unlocks() {
        return tier5Unlocks;
    }

    public ArrayList<ArrayList<Crafts>> getAllUnlocks() {
        ArrayList<ArrayList<Crafts>> allUnlocks = new ArrayList<>();
        allUnlocks.add(tier1Unlocks);
        allUnlocks.add(tier2Unlocks);
        allUnlocks.add(tier3Unlocks);
        allUnlocks.add(tier4Unlocks);
        allUnlocks.add(tier5Unlocks);
        return allUnlocks;
    }
}
