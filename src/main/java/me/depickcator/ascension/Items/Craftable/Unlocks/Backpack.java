package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.BackpackGUI;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class Backpack extends Craft implements ItemClick {
    private static Backpack instance;
    private final HashMap<String, BackpackGUI> backpacks;
    private final NamespacedKey key;
    private final List<ItemStack> coloredBackpack;
    private final int customModelNumber;
    private Backpack() {
        super(UnlocksData.COST_125, 2, "Backpack", "backpack");
        key = new NamespacedKey(Ascension.getInstance(), getKey());
        backpacks = new HashMap<>();
        customModelNumber = Ascension.getInstance().generateModelNumber();
        coloredBackpack = initColoredBackpack();
        registerItem();
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        RecipeChoice.MaterialChoice boxes = new RecipeChoice.MaterialChoice(
                Material.CHEST,
                Material.WHITE_SHULKER_BOX,
                Material.GRAY_SHULKER_BOX,
                Material.LIGHT_GRAY_SHULKER_BOX,
                Material.BLACK_SHULKER_BOX,
                Material.BROWN_SHULKER_BOX,
                Material.RED_SHULKER_BOX,
                Material.ORANGE_SHULKER_BOX,
                Material.YELLOW_SHULKER_BOX,
                Material.LIME_SHULKER_BOX,
                Material.GREEN_SHULKER_BOX,
                Material.CYAN_SHULKER_BOX,
                Material.LIGHT_BLUE_SHULKER_BOX,
                Material.BLUE_SHULKER_BOX,
                Material.PURPLE_SHULKER_BOX,
                Material.MAGENTA_SHULKER_BOX,
                Material.PINK_SHULKER_BOX,
                Material.SHULKER_BOX
        );
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("ABA", "ACA", "ABA");
        recipe.setIngredient('A', Material.STICK);
        recipe.setIngredient('B', Material.LEATHER);
        recipe.setIngredient('C', boxes);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.SHULKER_BOX, 1);
    }

    public static Backpack getInstance() {
        if (instance == null) instance = new Backpack();
        return instance;
    }

    @Override
    public ItemStack getItem() {
        return getResult();
    }

    @Override
    public boolean uponCrafted(CraftItemEvent e, Player p) {
        if (e.isShiftClick()) return false;
        ItemStack item = e.getInventory().getMatrix()[4];
        if (item.getType().equals(Material.CHEST)) {
            buildBackpack(e, p);
        } else {
            BackpackGUI gui = findBackpackGUI(item);
            if (gui == null || !gui.openRestOfBackpack()) {
                TextUtil.errorMessage(p, "This backpack is either already upgraded or is not a backpack");
                return false;
            }
            p.sendMessage(TextUtil.makeText("Your current backpack has been increased in size", TextUtil.GREEN));
            SoundUtil.playHighPitchPling(p);
            ItemMeta meta = item.getItemMeta();
            List<Component> lore = meta.lore();
            lore.add(TextUtil.makeText("UPGRADED", TextUtil.WHITE, true, false));
            meta.lore(lore);
            item.setItemMeta(meta);
            e.setCurrentItem(item);
            return true;
        }

        return true;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (!e.getAction().isRightClick()) {
            e.setCancelled(true);
            return false;
        }
        ItemStack item = e.getItem();
        ItemMeta meta = item.getItemMeta();
        String uuid = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        Player p = pD.getPlayer();
        BackpackGUI inv = backpacks.get(uuid);
//        p.openInventory(inv);
        inv.open(p);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 1.0f, 1.0f);
        e.setCancelled(true);
        return true;
    }

    private BackpackGUI findBackpackGUI(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        String uuid = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (uuid == null) return null;
        return backpacks.get(uuid);
    }

    @Override
    public void registerItem() {
        for (ItemStack item : coloredBackpack) {
            ItemMeta meta = item.getItemMeta();
            item.setItemMeta(meta);
            setModelNumber(item, customModelNumber);
            addItem(item, this);
        }
    }

    /*Reset's all the backpacks*/
    public void resetBackpacks() {
        backpacks.clear();
    }

    private void buildBackpack(CraftItemEvent e, Player p) {
        ItemStack item = getColoredBackpack();
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Crafted by ", TextUtil.YELLOW)
                        .append(TextUtil.makeText(p.getName(), TextUtil.AQUA, false, true))
        ));
        meta.lore(lore);
        String uuid = UUID.randomUUID().toString();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, uuid);
        item.setItemMeta(meta);
        setModelNumber(item, customModelNumber);
        e.setCurrentItem(item);
        addBackpack(uuid, p, item);
    }

    private void addBackpack(String uuid, Player p, ItemStack item) {
//        Inventory inv = Bukkit.createInventory(p, InventoryType.SHULKER_BOX, TextUtil.makeText(p.getName() + "'s Backpack", TextUtil.GRAY));
        backpacks.put(uuid, new BackpackGUI(PlayerUtil.getPlayerData(p), item));
    }

    private ItemStack getColoredBackpack() {
        Collections.shuffle(coloredBackpack);
        return coloredBackpack.getFirst();
    }

    private List<ItemStack> initColoredBackpack() {
        return new ArrayList<>(List.of(
                new ItemStack(Material.WHITE_SHULKER_BOX),
                new ItemStack(Material.GRAY_SHULKER_BOX),
                new ItemStack(Material.LIGHT_GRAY_SHULKER_BOX),
                new ItemStack(Material.BLACK_SHULKER_BOX),
                new ItemStack(Material.BROWN_SHULKER_BOX),
                new ItemStack(Material.RED_SHULKER_BOX),
                new ItemStack(Material.ORANGE_SHULKER_BOX),
                new ItemStack(Material.YELLOW_SHULKER_BOX),
                new ItemStack(Material.LIME_SHULKER_BOX),
                new ItemStack(Material.GREEN_SHULKER_BOX),
                new ItemStack(Material.CYAN_SHULKER_BOX),
                new ItemStack(Material.LIGHT_BLUE_SHULKER_BOX),
                new ItemStack(Material.BLUE_SHULKER_BOX),
                new ItemStack(Material.PURPLE_SHULKER_BOX),
                new ItemStack(Material.MAGENTA_SHULKER_BOX),
                new ItemStack(Material.PINK_SHULKER_BOX),
                new ItemStack(Material.SHULKER_BOX)
        ));
    }
}
