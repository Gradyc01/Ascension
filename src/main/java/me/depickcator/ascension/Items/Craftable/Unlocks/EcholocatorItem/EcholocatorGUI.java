package me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem;

import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Locations.Biomes.*;
import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Locations.Structures.*;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EcholocatorGUI extends AscensionGUI {

    private HashMap<ItemStack, EcholocatorLocations> map;
    public EcholocatorGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Echolocator", TextUtil.AQUA), true);
        initLocations();
        inventory.setItem(45, initExplainer());
        inventory.setItem(49, getCloseButton());
    }

    private ItemStack initExplainer() {
        Component title = TextUtil.makeText("Info", TextUtil.GREEN);
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("   Select a location to learn", TextUtil.DARK_PURPLE),
                TextUtil.makeText("the coordinates of the place", TextUtil.DARK_PURPLE)
        ));
        return initExplainerItem(Material.REDSTONE_TORCH, lore, title);
    }

    private void initLocations() {
        map = new HashMap<>();
        addLocations(new Mineshaft(), Material.COBWEB, 10);
        addLocations(new DesertPyramid(), Material.RED_SANDSTONE, 11);
        addLocations(new OceanMonument(), Material.PRISMARINE_BRICKS, 12);
        addLocations(new PillagerOutpost(), Material.CROSSBOW, 13);
        addLocations(new Trials(), Material.OXIDIZED_CUT_COPPER, 14);
        addLocations(new AncientCity(), Material.DISC_FRAGMENT_5, 15);

        addLocations(new BambooJungle(), Material.BAMBOO, 19);
        addLocations(new DarkForest(), Material.DARK_OAK_LOG, 20);
        addLocations(new Desert(), Material.SAND, 21);
        addLocations(new Jungle(), Material.JUNGLE_LOG, 22);
        addLocations(new MushroomIsland(), Material.MYCELIUM, 23);
        addLocations(new Swamp(), Material.LILY_PAD, 24);
        addLocations(new WarmOcean(), Material.SEA_PICKLE, 25);

        addLocations(new LushCaves(), Material.GLOW_BERRIES, 28);
        addLocations(new CherryGrove(), Material.CHERRY_LEAVES, 29);
        addLocations(new PaleGarden(), Material.PALE_OAK_LOG, 30);


        addLocations(new NetherFortress(), Material.NETHER_BRICKS, 37);
        addLocations(new Bastion(), Material.BLACKSTONE, 38);
    }

    private void addLocations(EcholocatorLocations location, Material material, int index) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(location.getName(), TextUtil.DARK_GREEN));
        meta.setCustomModelData(0xFF0000);
        item.setItemMeta(meta);
        map.put(item, location);
        inventory.setItem(index, item);
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null || !event.isLeftClick()) return;
        if (item.equals(getCloseButton())) {
            event.setCancelled(true);
            player.closeInventory();
            return;
        }
        if (!player.getInventory().getItemInMainHand().equals(Echolocator.getInstance().getResult())) {
            event.setCancelled(true);
            player.closeInventory();
            return;
        }
        EcholocatorLocations location = map.get(item);
        if (location == null) return;
        if (location.locate(playerData)) {
            player.getInventory().getItemInMainHand().setAmount(0);
        }
        event.setCancelled(true);
        player.closeInventory();
    }
}

