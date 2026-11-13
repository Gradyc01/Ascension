
package org.bingo4.Items.Uncraftable;

import net.kyori.adventure.sound.Sound;
import org.bingo4.Interfaces.ItemClick;
import org.bingo4.Items.CustomItem;
import org.bingo4.MainMenuUI.BingoBoard.BingoBoardGUI;
import org.bingo4.Player.Cooldowns.CombatTimer;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Minimap extends CustomItem implements ItemClick {
    private static Minimap instance;

    private static final long CLICK_WINDOW = 500;

    private final Map<UUID, Long> lastRightClick = new HashMap<>();
    private final Map<UUID, Integer> rightClickCount = new HashMap<>();
    private final Map<UUID, Long> lastSneakRightClick = new HashMap<>();
    private final Map<UUID, Integer> sneakRightClickCount = new HashMap<>();

    private Minimap() {
        super("Minimap", "minimap");
        registerItem();
    }

    public static Minimap getInstance() {
        if (instance == null) {
            instance = new Minimap();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.FILLED_MAP);
        generateUniqueModelNumber(item);

        MapMeta meta = (MapMeta) item.getItemMeta();
        meta.customName(TextUtil.makeText("Minimap", TextUtil.AQUA).append(TextUtil.clickText()));

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getResult(PlayerData playerData) {
        ItemStack item = getResult();

        MapMeta meta = (MapMeta) item.getItemMeta();
        meta.setMapView(playerData.getPlayerMapData().getCurrentMapView());

        item.setItemMeta(meta);
        return item;
    }

    @Override
    public ItemStack getItem() {
        return getResult();
    }

    private boolean isDoubleRightClick(Player player, boolean sneaking) {
        UUID uuid = player.getUniqueId();
        long now = System.currentTimeMillis();

        Map<UUID, Long> timestamps = sneaking ? lastSneakRightClick : lastRightClick;
        Map<UUID, Integer> counts = sneaking ? sneakRightClickCount : rightClickCount;

        long previous = timestamps.getOrDefault(uuid, 0L);

        if (now - previous > CLICK_WINDOW) {
            counts.put(uuid, 1);
        } else {
            counts.put(uuid, counts.getOrDefault(uuid, 0) + 1);
        }

        timestamps.put(uuid, now);

        if (counts.get(uuid) >= 2) {
            counts.remove(uuid);
            timestamps.remove(uuid);
            return true;
        }

        return false;
    }

    @Override
    public boolean uponClick(PlayerInteractEvent e, PlayerData pD) {
        Player player = pD.getPlayer();

        boolean offhandRightClick =
                e.getHand() == EquipmentSlot.OFF_HAND &&
                        e.getAction().isRightClick();

        // Shift + double offhand right-click: Open Bingo board.
        if ((offhandRightClick && player.isSneaking()
                && !CombatTimer.getInstance().isOnCooldown(player)
                && isDoubleRightClick(player, true)) || isMainHandRightClick(e)) {

            new BingoBoardGUI(pD);
            return true;
        }

        // Double offhand right-click: Switch map.
        if ((offhandRightClick && !player.isSneaking()
                && isDoubleRightClick(player, false)) || e.getHand() == EquipmentSlot.HAND && e.getAction().isLeftClick()) {

            ItemStack item = e.getItem();

            if (item == null || item.getType() != Material.FILLED_MAP) {
                return false;
            }

            MapMeta meta = (MapMeta) item.getItemMeta();
            meta.setMapView(pD.getPlayerMapData().getNextMapView());
            item.setItemMeta(meta);

            pD.getPlayerMapData().updateMapCenter();

            player.playSound(Sound.sound(
                    org.bukkit.Sound.UI_BUTTON_CLICK,
                    Sound.Source.MASTER,
                    100f,
                    0f
            ));

            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }
}
