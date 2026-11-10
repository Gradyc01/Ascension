package org.bingo4.Lobby.EventsBoard.Boards;

import org.bingo4.Lobby.Boards;
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

public abstract class Events extends Boards {
    private final ItemDisplay itemDisplay;
    private final String key;
    private final Material material;
    private final double x;
    private final double y;
    private final double z;
    public Events(String key, Material material, double x, double y, double z) {
        super(false);
//        Location loc = Bingo4.getSpawn();
        this.x = spawn.getX() + x;
        this.y = spawn.getY() + y;
        this.z = spawn.getZ() + z;
        this.key = key;
        this.material = material;
        itemDisplay = initItemDisplay();
        setTextDisplay(initTextDisplay());
    }
    @Override
    protected abstract TextDisplay initTextDisplay();

    protected abstract ItemDisplay initItemDisplay();

    public ItemDisplay getItemDisplay() {
        return itemDisplay;
    }

    public void makeVisibleToPlayer(Player player) {
        player.showEntity(plugin, getTextDisplay());
        player.showEntity(plugin, getItemDisplay());
    }

    public void makeHiddenToPlayer(Player player) {
        player.hideEntity(plugin, getTextDisplay());
        player.hideEntity(plugin, getItemDisplay());
    }

    public String getKey() {
        return key;
    }

    public Material getMaterial() {
        return material;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
