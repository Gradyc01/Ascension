package me.depickcator.ascension.Timeline.Events.SoulShop;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Timeline.Events.SoulShop.Algorithms.Personal;
import me.depickcator.ascension.Timeline.Events.SoulShop.Algorithms.SoulShopAlgorithm;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Shop {
    private final PlayerData playerData;
    private final Map<UUID, SoulShopItem> items;
    public Shop(PlayerData playerData) {
        this.playerData = playerData;
        this.items = new HashMap<>();
        generateItems();
    }

    public void generateItems() {
        new Personal(playerData, this);
    }

    public boolean purchaseItem(UUID uuid) {
        SoulShopItem item = items.get(uuid);
        if (item == null) return false;
        return item.purchase(playerData);
    }

    public UUID addShopItem(SoulShopItem item) {
        UUID uuid = UUID.randomUUID();
        items.put(uuid, item);
        return uuid;
    }

    public boolean removeShopItem(UUID uuid) {
        if (items.containsKey(uuid)) {
            items.remove(uuid);
            return true;
        }
        return false;
    }

    public Map<UUID, ItemStack> getIconMappings() {
        Map<UUID, ItemStack> map = new HashMap<>();
        for (Map.Entry<UUID, SoulShopItem> entry : items.entrySet()) {
            SoulShopItem item = entry.getValue();
            map.put(entry.getKey(), item.getIcon());
        };
        return map;
    }
}
