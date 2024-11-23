package me.depickcator.ascensionBingo.Items;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascensionBingo.Items.Uncraftable.XPTome.XPTome;
import me.depickcator.ascensionBingo.Items.Unlocks.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class UnlocksData {
    private AscensionBingo plugin;
    private ArrayList<Crafts> tier1Unlocks;
    private ArrayList<Crafts> tier2Unlocks;
    private ArrayList<Crafts> tier3Unlocks;
    private ArrayList<Crafts> tier4Unlocks;
    private ArrayList<Crafts> tier5Unlocks;

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
        Uncraftable();
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
        tier3Unlocks.add(new DragonArmor(plugin));
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
    }

    private void Uncraftable() {
        new XPTome(plugin);
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
