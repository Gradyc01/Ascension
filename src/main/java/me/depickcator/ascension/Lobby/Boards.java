package me.depickcator.ascension.Lobby;

import me.depickcator.ascension.Ascension;
import org.bukkit.Location;
import org.bukkit.entity.TextDisplay;

public abstract class Boards {
    protected final Location spawn;
    protected final Ascension plugin;
    private TextDisplay textDisplay;
    /*Initializes a Board in the lobby*/
    public Boards() {
        plugin = Ascension.getInstance();
        this.spawn = plugin.getLobby().getSpawn();
        textDisplay = initTextDisplay();
    };

    public Boards(boolean noTextDisplay) {
        plugin = Ascension.getInstance();
        this.spawn = plugin.getLobby().getSpawn();
    };

    public TextDisplay getTextDisplay() {
        return textDisplay;
    }

    /*Creates the text Display that will be displayed on the board*/
    protected abstract TextDisplay initTextDisplay();

    public void setTextDisplay(TextDisplay textDisplay) {
        this.textDisplay = textDisplay;
    }
}
