package me.depickcator.ascensionBingo.Items.Unlocks;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Items.UnlockUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Base64;
import java.util.UUID;

public class GoldenHead implements Crafts{
    private final AscensionBingo plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 2;
    public static final String DISPLAY_NAME = "Golden Head";
    public static final String KEY = "golden_head";
    public GoldenHead(AscensionBingo plugin) {
        this.plugin = plugin;
        recipe();
    }

    @Override
    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = GoldenHead.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("AAA", "ABA", "AAA");
        recipe.setIngredient('A', Material.GOLD_INGOT);
        recipe.setIngredient('B', Material.PLAYER_HEAD);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    public static ItemStack result() {
        String goldenHeadTexture = "ewogICJ0aW1lc3RhbXAiIDogMTU4OTQ4Njc4OTg3MSwKICAicHJvZmlsZUlkIiA6ICI5MWZlMTk2ODdjOTA0NjU2YWExZmMwNTk4NmRkM2ZlNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJoaGphYnJpcyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81NjZhODc0NjAxNzNhZGYwNjdjYjM1NmFlMjAwZDAzMDUwNDM3OGM1NTJlMzQyOGI0Nzc0YzRjMTFhNTk5YzI0IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        if (skullMeta != null) {
//            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString("f0c2d405-6b58-42c3-8671-78e4b3b60870"));
//            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString("bffe33bf-c1c0-4ca9-af27-ca231909c097"));
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
            profile.getProperties().add(new ProfileProperty("textures", goldenHeadTexture));
            skullMeta.setPlayerProfile(profile);

            skullMeta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.YELLOW));
            skullMeta.setCustomModelData(99);
            skullMeta.setEnchantmentGlintOverride(true);
            skullMeta.setMaxStackSize(1);
        }
        Repairable repairMeta = (Repairable) skullMeta;
        repairMeta.setRepairCost(999);
        item.setItemMeta(repairMeta);
        return item;
    }

//    public static ItemStack createCustomHead() {
//        // Create the player head item
//        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
//        SkullMeta meta = (SkullMeta) head.getItemMeta();
//        if (meta != null) {
//            String textureJson = "{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/fe1551b9434fae1e8e9327f7c1ceac7bcae3cf71fbd67e7942f0c2ce62805c18\"}}}";
//            String base64Texture = Base64.getEncoder().encodeToString(textureJson.getBytes());
//            meta.getPersistentDataContainer().set(new NamespacedKey("ascensionbingo", "custom_skin"), PersistentDataType.STRING, base64Texture);
//
//            head.setItemMeta(meta);
//        }
//
//        return head;
//    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return GoldenHead.result();
    }

    @Override
    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public int getCraftCost() {
        return COST;
    }


}
