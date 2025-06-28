package me.depickcator.ascension.Items.Craftable.Unlocks;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Mothership extends Craft {
    private static Mothership instance;
    private Mothership() {
        super(875, 1, "Mothership" ,"mothership");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" B ", "ACA");
        recipe.setIngredient('A', Material.PLAYER_HEAD);
        recipe.setIngredient('B', new RecipeChoice.MaterialChoice(Tag.ITEMS_HARNESSES));
        recipe.setIngredient('C', Material.DRIED_GHAST);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        String base64Texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZ" +
                "jdlMTJhNTk1OTk5MmNlMTUzY2VkMjM2YWE2NzMyMzk2NTMxODM0ZDAwZjI4M2JjZTE5NTJkMDRmMzU5MWQ5OSJ9fX0=";
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(UUID.fromString("5f856526-a7c6-4782-bcf9-803e02b08e1d"), null);
        profile.getProperties().add(new ProfileProperty("textures", base64Texture));
        meta.setPlayerProfile(profile);
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.YELLOW));
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Type: Flying Ghast", TextUtil.GREEN),
                TextUtil.makeText("Health: 30 Hearts", TextUtil.GREEN),
                TextUtil.makeText("Speed: Slow", TextUtil.GREEN),
                TextUtil.makeText(" ", TextUtil.GREEN)
        ));
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public boolean uponCrafted(CraftItemEvent e, Player p) {
        World world = p.getWorld();
        Location loc = p.getLocation();
        world.playSound(loc, Sound.ENTITY_WITHER_SPAWN, 5.0F, 0.0F);
        if (e.getCurrentItem() != null) e.getCurrentItem().setAmount(0);
//        e.getInventory().setMatrix(new ItemStack[]{null, null, null, null, null, null, null, null, null});
        makeEntity(loc, p, clearMatrix(e.getInventory().getMatrix()));
        return true;
    }

    private ItemStack clearMatrix(ItemStack[] items) {
        ItemStack harness = new ItemStack(Material.BLACK_HARNESS);
        RecipeChoice choice = new RecipeChoice.MaterialChoice(Tag.ITEMS_HARNESSES);
        for (ItemStack item : items) {
            if (item == null) continue;
            if (choice.test(item)) {
                harness = item.clone();
            }
            item.setAmount(item.getAmount() - 1);
        }
        return harness;
    }

    private Entity makeEntity(Location loc, Player p, ItemStack harness) {
        HappyGhast entity = (HappyGhast) loc.getWorld().spawnEntity(loc, EntityType.HAPPY_GHAST);
        entity.addPassenger(p);
        entity.getEquipment().setItem(EquipmentSlot.BODY, harness);
        entity.getAttribute(Attribute.FLYING_SPEED).setBaseValue(0.15);
        entity.getAttribute(Attribute.ARMOR).setBaseValue(5.0);
        entity.getAttribute(Attribute.MAX_HEALTH).setBaseValue(60.0);
        entity.setHealth(60.0);
        entity.customName(TextUtil.makeText(getDisplayName(), TextUtil.GOLD));
        return entity;
    }

    public static Mothership getInstance() {
        if (instance == null) instance = new Mothership();
        return instance;
    }
}
