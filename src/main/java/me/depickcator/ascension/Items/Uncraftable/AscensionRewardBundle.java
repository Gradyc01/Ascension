package me.depickcator.ascension.Items.Uncraftable;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Timeline.Events.Ascension.Rewards.AscensionRewards;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class AscensionRewardBundle extends CustomItem implements ItemClick {
    private static AscensionRewardBundle instance;
    private final Map<UUID, AscensionRewards> rewardMap;
    private final NamespacedKey key;
    private AscensionRewardBundle() {
        super("Ascension Reward Bundle", "ascension_reward_bundle");
        rewardMap = new HashMap<>();
        key = new NamespacedKey(Ascension.getInstance(), getKey());
        registerItem();
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.PURPLE_BUNDLE);
        BundleMeta meta = (BundleMeta) item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.DARK_GREEN).append(TextUtil.rightClickText()));
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Claim the Items earned through Ascension", TextUtil.DARK_PURPLE)
        ));
        meta.lore(lore);
        meta.setEnchantmentGlintOverride(true);
        meta.setMaxStackSize(1);
        item.setItemMeta(meta);
        generateUniqueModelNumber(item);
        return item;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        if (isMainHandRightClick(e)) {
            ItemStack item = e.getItem();
            BundleMeta meta = (BundleMeta) item.getItemMeta();
            String uuid = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
            if (uuid != null) {
                AscensionRewards rewards = rewardMap.get(UUID.fromString(uuid));
                rewards.giveItems(pD);
                if (meta.hasItems()) PlayerUtil.giveItem(pD.getPlayer(), meta.getItems());
                item.setAmount(0);
            }

            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }

    /*Returns the RewardBundle item with a UUID associated with the AscensionRewards reward*/
    public ItemStack getResult(AscensionRewards reward) {
        ItemStack item = result.clone();
        ItemMeta meta = item.getItemMeta();
        UUID uuid = UUID.randomUUID();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, uuid.toString());
        rewardMap.put(uuid, reward);
        item.setItemMeta(meta);
        return item;
    }

    public void resetRewardBundles() {
        rewardMap.clear();
    }

    @Override
    public ItemStack getItem() {
        return getResult();
    }

    public static AscensionRewardBundle getInstance() {
        if (instance == null) {
            instance = new AscensionRewardBundle();
        }
        return instance;
    }
}
