package me.depickcator.ascension.Items.Craftable.Unlocks;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.ItemClick;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.UUID;

public class GoldenHead extends Craft implements ItemClick {
    private static GoldenHead instance;
    private GoldenHead() {
        super(UnlocksData.COST_200, 4, "Golden Head", "golden_head");
        registerItem();
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AAA", "ABA", "AAA");
        recipe.setIngredient('A', Material.GOLD_INGOT);
        recipe.setIngredient('B', Material.PLAYER_HEAD);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        String goldenHeadTexture = "ewogICJ0aW1lc3RhbXAiIDogMTU4OTQ4Njc4OTg3MSwKICAicHJvZmlsZUlkIiA6ICI5MWZlMTk2ODdjOTA0NjU2YWExZmMwNTk4NmRkM2ZlNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJoaGphYnJpcyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81NjZhODc0NjAxNzNhZGYwNjdjYjM1NmFlMjAwZDAzMDUwNDM3OGM1NTJlMzQyOGI0Nzc0YzRjMTFhNTk5YzI0IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        if (skullMeta == null) throw new NullPointerException();

        PlayerProfile profile = Bukkit.createProfile(UUID.fromString("5f856526-a7c6-4782-bcf9-803e02b08e1d"), null);
        profile.getProperties().add(new ProfileProperty("textures", goldenHeadTexture));
        skullMeta.setPlayerProfile(profile);

        skullMeta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.YELLOW));
        skullMeta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        skullMeta.setEnchantmentGlintOverride(true);
        skullMeta.setMaxStackSize(1);
        Repairable repairMeta = (Repairable) skullMeta;
        repairMeta.setRepairCost(999);
        item.setItemMeta(repairMeta);
        return item;
    }


    @Override
    public ItemStack getItem() {
        return result;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (isMainHandRightClick(e)) {
            ItemStack item = e.getItem();
            Player p = pD.getPlayer();
            if (item == null || pD == null) return false;
            item.setAmount(item.getAmount() - 1);
            giveGoldenHeadEffects(p);
            ArrayList<Player> teamMembers = pD.getPlayerTeam().getTeam().getOtherTeamMembers(p);
            p.sendMessage(TextUtil.makeText("You ate a golden head which grants you Regeneration III for 8 seconds, Resistance I for 15 seconds",
                    TextUtil.GREEN));
            for (Player player : teamMembers) {
                player.sendMessage(TextUtil.makeText(p.getName() + " ate a golden head which grants you Regeneration II for 8 seconds", TextUtil.GREEN));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 8 * 20, 1));
            }
            return true;
        }
        return false;
    }

    private void giveGoldenHeadEffects(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 7 * 20, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 15 * 20, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 30 * 20, 0));
    }

    @Override
    public void registerItem() {
        addItem(result, this);
    }

    public static GoldenHead getInstance() {
        if (instance == null) instance = new GoldenHead();
        return instance;
    }
}
