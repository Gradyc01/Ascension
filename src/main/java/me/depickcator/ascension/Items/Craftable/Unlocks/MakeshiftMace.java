package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.Craftable.Vanilla.Vanilla;
import me.depickcator.ascension.Items.Craftable.Vanilla.Weapons;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.WindCharge;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class MakeshiftMace extends Weapons implements ItemClick {
    private static MakeshiftMace instance;
    private MakeshiftMace() {
        super(UnlocksData.COST_425, 1, "Well Worn Mace", "makeshift_mace", 8, -3.4);
        registerItem();
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A", "B", "C");
        recipe.setIngredient('A', Material.PLAYER_HEAD);
        recipe.setIngredient('B', Material.OMINOUS_BOTTLE);
        recipe.setIngredient('C', Material.BREEZE_ROD);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.MACE);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.setMaxDamage(50);
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.YELLOW).append(TextUtil.rightClickText()));
        item.setItemMeta(Vanilla.addModifiers(meta, getAttackDamage(), getAttackSpeed(), KEY));
        addCooldownGroup(item);
        generateUniqueModelNumber(item);
        return item;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (!e.getAction().isRightClick()) return false;
        Player p = pD.getPlayer();
        if (checkCooldown(p, e.getItem())) {
            launchWindCharges(p);
            return true;
        }

        return false;
    }

    /*Returns true if not on cooldown and sets the cooldown, False otherwise*/
    private boolean checkCooldown(Player p, ItemStack item) {
        if (!p.hasCooldown(item) && plugin.getGameState().inGame()) {
            p.setCooldown(item, 8 * 20);
            return true;
        }
        return false;
    }

    private void launchWindCharges(Player p) {
        World world = p.getWorld();
        Location loc = p.getEyeLocation();
        for (int i = 0; i < 2; i++) {
            WindCharge charge = (WindCharge) world.spawnEntity(loc, EntityType.WIND_CHARGE);
            charge.setYield(7);
            charge.setShooter(p);
            charge.setVelocity(loc.getDirection().normalize().multiply(1.8));
        }

//        world.playSound(loc, Sound.ENTITY_WITHER_SPAWN, 2f, 2f);
//        world.playSound(loc, Sound.ENTITY_CREEPER_PRIMED, 1f, 1f);
    }

    public static MakeshiftMace getInstance() {
        if (instance == null) {
            instance = new MakeshiftMace();
        }
        return instance;
    }

    @Override
    public ItemStack getItem() {
        return getResult();
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }
}
