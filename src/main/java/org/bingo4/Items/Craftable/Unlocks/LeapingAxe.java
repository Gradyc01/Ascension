package org.bingo4.Items.Craftable.Unlocks;

import org.bingo4.Interfaces.ItemClick;
import org.bingo4.Items.Craftable.Vanilla.Vanilla;
import org.bingo4.Items.Craftable.Vanilla.Weapons;
import org.bingo4.Items.UnlockUtil;
import org.bingo4.Player.Cooldowns.CombatTimer;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.util.Vector;

public class LeapingAxe extends Weapons implements ItemClick {
    private static LeapingAxe instance;
    private LeapingAxe() {
        super(475, 1, "Leaping Axe", "leaping_axe", 11, -3);
        registerItem();
    }

    public static LeapingAxe getInstance() {
        if (instance == null) instance = new LeapingAxe();
        return instance;
    }

    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("BAD", "BC ", " C ");
        recipe.setIngredient('A', Material.FERMENTED_SPIDER_EYE);
        recipe.setIngredient('B', Material.DIAMOND);
        recipe.setIngredient('C', Material.STICK);
        recipe.setIngredient('D', Material.BLAZE_POWDER);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.GOLDEN_AXE);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.YELLOW).append(TextUtil.rightClickText()));
        meta.setMaxDamage(1561);
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        AttributeModifier modifier = new AttributeModifier(key, -0.75, AttributeModifier.Operation.ADD_SCALAR);
        meta.addAttributeModifier(Attribute.FALL_DAMAGE_MULTIPLIER, modifier);
        item.setItemMeta(Vanilla.addModifiers(meta, getAttackDamage(), getAttackSpeed(), getKey()));
        addCooldownGroup(item);
        generateUniqueModelNumber(item);
        return item;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (!e.getAction().isRightClick()) return false;
        ItemStack item = e.getItem();
        Player p = pD.getPlayer();
        if (checkCooldown(p, item)) {
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SPIDER_AMBIENT, 2.0F, 1.0F);
            p.setVelocity(p.getEyeLocation().getDirection().multiply(2.0));
        }
        return false;
    }

    /*Returns true if not on cooldown and sets the cooldown, False otherwise*/
    private boolean checkCooldown(Player p, ItemStack item) {
        if (!p.hasCooldown(item) && plugin.getGameState().inGame()) {
            int sec = CombatTimer.getInstance().isOnCooldown(p) ? 15 : 5;
            p.setCooldown(item, sec * 20);
            launchAnimation(p.getLocation());
            return true;
        }
        return false;
    }

    private void launchAnimation(Location loc) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0) continue;
                Vector v = new Vector(x, 0.5, z);
                FallingBlock e = (FallingBlock) loc.getWorld().spawnEntity(loc, EntityType.FALLING_BLOCK);
                e.setBlockData(Material.COBWEB.createBlockData());
                e.setCancelDrop(true);
                e.setVelocity(v);
            }
        }

    }


    @Override
    public ItemStack getItem() {
        return result;
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }

}
