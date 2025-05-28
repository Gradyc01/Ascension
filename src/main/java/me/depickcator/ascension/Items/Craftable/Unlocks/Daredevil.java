package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Daredevil extends Craft {
    private static Daredevil instance;
    private Daredevil() {
        super(UnlocksData.COST_350, 1, "Daredevil" ,"daredevil");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        RecipeChoice.MaterialChoice skulls = new RecipeChoice.MaterialChoice(
                Material.ZOMBIE_HEAD,
                Material.SKELETON_SKULL,
                Material.CREEPER_HEAD,
                Material.PLAYER_HEAD);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AB ", "CCC", "C C");
        recipe.setIngredient('A', skulls);
        recipe.setIngredient('B', Material.SADDLE);
        recipe.setIngredient('C', Material.BONE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.SKELETON_HORSE_SPAWN_EGG);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.YELLOW));
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Type: Skeleton Horse", TextUtil.GREEN),
                TextUtil.makeText("Health: 25 Hearts", TextUtil.GREEN),
                TextUtil.makeText("Speed: Max", TextUtil.GREEN),
                TextUtil.makeText("Jump: 3 Blocks", TextUtil.GREEN),
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
        world.strikeLightningEffect(loc);
        world.playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0F, 1.0F);
        world.playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.0F, 0.0F);
        world.playSound(loc, Sound.ENTITY_HORSE_ANGRY, 5.0F, 0.0F);
        world.playSound(loc, Sound.ENTITY_SKELETON_HORSE_DEATH, 5.0F, 0.0F);
        if (e.getCurrentItem() != null) e.getCurrentItem().setAmount(0);
        e.getInventory().setMatrix(new ItemStack[]{null, null, null, null, null, null, null, null, null});
        makeSkeletonHorse(loc, p);
        return true;
    }

    private SkeletonHorse makeSkeletonHorse(Location loc, Player p) {
        SkeletonHorse entity = (SkeletonHorse) loc.getWorld().spawnEntity(loc, EntityType.SKELETON_HORSE);
        entity.setTrapped(false);
        entity.addPassenger(p);
        entity.setTamed(true);
        entity.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        entity.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.4);
        //Vanilla max speed 0.3375
        entity.setJumpStrength(0.8);
        entity.getAttribute(Attribute.MAX_HEALTH).setBaseValue(50.0);
        entity.setHealth(30.0);
        entity.customName(TextUtil.makeText(getDisplayName(), TextUtil.DARK_RED));
        return entity;
    }

    public static Daredevil getInstance() {
        if (instance == null) instance = new Daredevil();
        return instance;
    }
}
