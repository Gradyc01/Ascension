package org.bingo4.Persistence;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Utility.TextUtil;

import java.io.File;

public class DeleteSettings extends GameSequences {
    private final Bingo4 plugin;
    private final String saved;

    //makes reader
    public DeleteSettings() {
        plugin = Bingo4.getInstance();
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
