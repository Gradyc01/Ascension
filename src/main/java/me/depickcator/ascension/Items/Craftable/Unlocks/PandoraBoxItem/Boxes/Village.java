package me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.Boxes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Village extends PandoraBoxes {
    public Village() {
        super("Village", Material.HAY_BLOCK);
    }

    @Override
    protected List<ItemStack> initConstantItems() {
        List<ItemStack> list = new ArrayList<>(List.of(
                new ItemStack(Material.EMERALD, rand.nextInt(1, 6)),
                new ItemStack(Material.CARROT, rand.nextInt(2, 9)),
                new ItemStack(Material.POTATO, rand.nextInt(2, 9)),
                new ItemStack(Material.HAY_BLOCK, rand.nextInt(1, 4)),
                new ItemStack(Material.IRON_INGOT, rand.nextInt(2, 17))
        ));
        return list;
    }

    @Override
    protected List<ItemStack> initSpecialItems() {
//        List<Enchantment> enchants = new ArrayList<>(List.of(
//                Enchantment.SHARPNESS,
//                Enchantment.INFINITY,
//                Enchantment.POWER,
//                Enchantment.FLAME,
//                Enchantment.FIRE_ASPECT,
//                Enchantment.UNBREAKING
//        ));
        List<ItemStack> list = new ArrayList<>(List.of(
                new ItemStack(Material.BEETROOT_SEEDS, rand.nextInt(1, 5)),
                new ItemStack(Material.BREWING_STAND, 1),
                new ItemStack(Material.EMERALD, rand.nextInt(1, 4)),
                new ItemStack(Material.BELL, 1),
                new ItemStack(Material.DIAMOND_HOE, 1)
        ));
        return list;
    }
}
