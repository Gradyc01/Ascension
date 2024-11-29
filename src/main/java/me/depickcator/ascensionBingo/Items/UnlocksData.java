package me.depickcator.ascensionBingo.Items;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.Craftable.Crafts;
import me.depickcator.ascensionBingo.Items.Craftable.Vanilla.*;
import me.depickcator.ascensionBingo.Items.Uncraftable.KitBook;
import me.depickcator.ascensionBingo.Items.Uncraftable.XPTome.XPTome;
import me.depickcator.ascensionBingo.Items.Craftable.Unlocks.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class UnlocksData {
    private final AscensionBingo plugin;
    private final ArrayList<Crafts> tier1Unlocks;
    private final ArrayList<Crafts> tier2Unlocks;
    private final ArrayList<Crafts> tier3Unlocks;
    private final ArrayList<Crafts> tier4Unlocks;
    private final ArrayList<Crafts> tier5Unlocks;

    public UnlocksData(AscensionBingo plugin) {
        this.plugin = plugin;
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
        tier1Unlocks.add(new IronPack(plugin));
        tier1Unlocks.add(new GoldPack(plugin));
        tier1Unlocks.add(new QuickPick(plugin));
        tier1Unlocks.add(new SugarRush(plugin));
        tier1Unlocks.add(new LeatherEconomy(plugin));
        tier1Unlocks.add(new ArrowEconomy(plugin));
        tier1Unlocks.add(new EveTemptation(plugin));
        tier1Unlocks.add(new Backpack(plugin));
        tier1Unlocks.add(new FlintShovel(plugin));
        tier1Unlocks.add(new QuickBow(plugin));
    }

    private void Tier2Unlocks() {
        tier2Unlocks.add(new DiamondPack(plugin));
        tier2Unlocks.add(new NotchApple(plugin));
        tier2Unlocks.add(new FlamingArtifact(plugin));
        tier2Unlocks.add(new PhilosopherPickaxe(plugin));
        tier2Unlocks.add(new LightAnvil(plugin));
        tier2Unlocks.add(new EnlighteningPack(plugin));
        tier2Unlocks.add(new LightApple(plugin));
        tier2Unlocks.add(new GoldenHead(plugin));
        tier2Unlocks.add(new VorpalSword(plugin));
    }

    private void Tier3Unlocks() {
        tier3Unlocks.add(new Tarnhelm(plugin));
        tier3Unlocks.add(new KingsRod(plugin));
        tier3Unlocks.add(new BookOfPower(plugin));
        tier3Unlocks.add(new BookOfProtection(plugin));
        tier3Unlocks.add(new BookOfSharpness(plugin));
        tier3Unlocks.add(new BookOfProjectileProtection(plugin));
        tier3Unlocks.add(new AncientArtifact(plugin));
        tier3Unlocks.add(new SoulArtifact(plugin));
        tier3Unlocks.add(new Nectar(plugin));
        tier3Unlocks.add(new PotionOfVelocity(plugin));
    }

    private void Tier4Unlocks() {
        tier4Unlocks.add(new Cornucopia(plugin));
        tier4Unlocks.add(new DragonArmor(plugin));
        tier4Unlocks.add(new HideOfLeviathan(plugin));
        tier4Unlocks.add(new SevenLeagueBoots(plugin));
        tier4Unlocks.add(new Panacea(plugin));
        tier4Unlocks.add(new Dread(plugin));
        tier4Unlocks.add(new CubeConverter(plugin));
        tier4Unlocks.add(new ExplosivePropulsion(plugin));
        tier4Unlocks.add(new Resurrection(plugin));
        tier4Unlocks.add(new WeaverSilk(plugin));
    }

    private void Tier5Unlocks() {
        tier5Unlocks.add(new CrimsonArtifact(plugin));
        tier5Unlocks.add(new ApolloGlare(plugin));
        tier5Unlocks.add(new HeliosCurse(plugin));
    }

    private void Uncraftable() {
        new XPTome(plugin);
        new KitBook();
    }

    private void Vanilla() {
        new WoodenSword(plugin);
        new StoneSword(plugin);
        new IronSword(plugin);
        new DiamondSword(plugin);
        new NetheriteSword(plugin);
        new WoodenAxe(plugin);
        new StoneAxe(plugin);
        new IronAxe(plugin);
        new DiamondAxe(plugin);
        new NetheriteAxe(plugin);
        new Shield(plugin);
    }

    public Pair<Crafts, Integer> findUnlock(String displayName) {
        ArrayList<ArrayList<Crafts>> arr = plugin.getUnlocksData().getAllUnlocks();
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
