package me.depickcator.ascension.Items.Craftable.Unlocks;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Items.Uncraftable.Skulls.PlayerHead;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class MakeshiftSkull extends Craft {
    private static MakeshiftSkull instance;
    private MakeshiftSkull() {
        super(UnlocksData.COST_500, 999,"Makeshift Skull", "makeshift_skull");
    }

    public static MakeshiftSkull getInstance() {
        if (instance == null) instance = new MakeshiftSkull();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AAA", "AAA", "AAA");
        recipe.setIngredient('A', ShardOfTheFallen.getInstance().getResult());
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
//        ItemStack item = PlayerHead.getInstance().getResult().clone();
//        ItemMeta meta = item.getItemMeta();
//        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.YELLOW));
//        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
//        item.setItemMeta(meta);
//        return item;

        String headtexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTU5NDQzNDViMDlhNjEyZDA2MDAxYmE0MjY2N2UyMDNhYzU5MjIzYzBlYWVjNTgzMmZmMmNkMDBiYjJiZWJiMSJ9fX0=";
        ItemStack item = PlayerHead.getInstance().getResult().clone();
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(UUID.fromString("5f856526-a7c6-4782-bcf9-803e02b08e1d"), null);
        profile.getProperties().add(new ProfileProperty("textures", headtexture));
        skullMeta.setPlayerProfile(profile);
        skullMeta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        skullMeta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.YELLOW));
        item.setItemMeta(skullMeta);
        return item;
    }



}
