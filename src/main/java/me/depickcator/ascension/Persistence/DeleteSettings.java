package me.depickcator.ascension.Persistence;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Utility.TextUtil;

import java.io.File;
import java.io.IOException;

public class DeleteSettings extends GameSequences {
    private final Ascension plugin;
    private final String saved;

    //makes reader
    public DeleteSettings() {
        plugin = Ascension.getInstance();
        this.saved = "./plugins/Ascension/ascension_save_file.json";
    }

    @Override
    public void run(GameLauncher game) {
//        TextUtil.debugText("Deleting settings..." + delete());
        if (delete()) {
            TextUtil.broadcastMessage(TextUtil.makeText("Save File has successfully been deleted", TextUtil.GREEN));
        }

        game.callback();
    }

    //          deletes the file
    public boolean delete() {
        File file = new File(saved);
        return file.delete();
    }



}
