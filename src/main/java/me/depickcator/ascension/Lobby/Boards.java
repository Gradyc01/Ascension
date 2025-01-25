package me.depickcator.ascension.Lobby;

import me.depickcator.ascension.Ascension;
import org.bukkit.entity.TextDisplay;

public abstract class Boards {
    protected final Ascension plugin;
    private TextDisplay textDisplay;
    public Boards() {
        plugin = Ascension.getInstance();
        textDisplay = initTextDisplay();
    };

    public Boards(boolean noTextDisplay) {
        plugin = Ascension.getInstance();
    };

    public TextDisplay getTextDisplay() {
        return textDisplay;
    }

    protected abstract TextDisplay initTextDisplay();

    public void setTextDisplay(TextDisplay textDisplay) {
        this.textDisplay = textDisplay;
    }
}
