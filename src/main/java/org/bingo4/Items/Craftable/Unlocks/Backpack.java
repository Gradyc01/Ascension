package org.bingo4.Items.Craftable.Unlocks;

import org.bingo4.Bingo4;
import org.bingo4.Interfaces.ItemClick;
import org.bingo4.Items.Craftable.Craft;
import org.bingo4.Items.UnlockUtil;
import org.bingo4.Items.UnlocksData;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Backpack extends Craft implements ItemClick {
    private static Backpack instance;
//    private final HashMap<String, BackpackGUI> backpacks;
    private final NamespacedKey key;
    private final List<ItemStack> coloredBackpack;
    private final int customModelNumber;
    private Backpack() {
        super(UnlocksData.COST_125, 2, "Backpack Upgrade", "backpack");
        key = new NamespacedKey(Bingo4.getInstance(), getKey());
//        backpacks = new HashMap<>();
        customModelNumber = Bingo4.getInstance().generateModelNumber();
        coloredBackpack = initColoredBackpack();
        registerItem();
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("ABA", "ACA", "ABA");
        recipe.setIngredient('A', Material.STICK);
        recipe.setIngredient('B', Material.LEATHER);
        recipe.setIngredient('C', Material.CHEST);
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
        PlayerData playerData = PlayerUtil.getPlayerData(p);
//        playerData.getPlayerBackpack().upgradeBackpack();
        p.sendMessage(TextUtil.makeText("Your personal backpack has been upgraded", TextUtil.AQUA));

        buildBackpack(e, p);

        return true;
    }

    private void takeCraftMaterials(ItemStack[] items) {
        for (ItemStack item : items) {
            if (item == null) continue;
            item.setAmount(item.getAmount() - 1);
        }
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (!e.getAction().isRightClick()) {
            e.setCancelled(true);
            return false;
        }
//        ItemStack item = e.getItem();
//        ItemMeta meta = item.getItemMeta();
//        String uuid = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);\
//        BackpackGUI inv = backpacks.get(uuid);
////        p.openInventory(inv);
//        inv.open(p);
//        pD.getPlayerBackpack().openBackpack();
        e.setCancelled(true);
        return true;
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
