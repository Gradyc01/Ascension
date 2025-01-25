package me.depickcator.ascension.Lobby.EventsBoard.Boards;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Lobby.Boards;
import org.bukkit.Location;
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
        Location loc = Ascension.getSpawn();
        this.x = loc.getX() + x;
        this.y = loc.getY() + y;
        this.z = loc.getZ() + z;
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
