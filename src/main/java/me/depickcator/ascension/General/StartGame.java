//package me.depickcator.ascension.General;
//
//import me.depickcator.ascension.Ascension;
//import me.depickcator.ascension.Items.ItemList;
//import me.depickcator.ascension.Player.Data.PlayerData;
//import me.depickcator.ascension.Player.Data.PlayerUtil;
//import me.depickcator.ascension.Teams.TeamUtil;
//import me.depickcator.ascension.MainMenu.BingoBoard.BingoData;
//import me.depickcator.ascension.Utility.SoundUtil;
//import me.depickcator.ascension.Utility.TextUtil;
//import net.kyori.adventure.text.Component;
//import net.kyori.adventure.text.format.TextDecoration;
//import net.kyori.adventure.title.Title;
//import org.bukkit.*;
//import org.bukkit.entity.Player;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.scheduler.BukkitRunnable;
//import org.bukkit.scheduler.BukkitScheduler;
//
//import java.util.ArrayList;
//
//
//public class StartGame {
//    private final Ascension plugin;
//    private final int second = 20;
//    private final GameStates gameState;
//    public StartGame() {
//        this.plugin = Ascension.getInstance();
//        this.gameState = plugin.getGameState();
////        this.gameState.setCurrentState(GameStates.GAME_LOADING);
////        resetPlayers();
//        new me.depickcator.ascension.General.GameStart.StartGame().start();
//
//    }
//
//    private void resetPlayers() {
//
//        TeamUtil.createTeamsForEveryone();
//        new BukkitRunnable() {
//            ArrayList<Player> players = new ArrayList<>(plugin.getServer().getOnlinePlayers());
//            @Override
//            public void run() {
//                if (players.isEmpty()) {
//                    cancel();
//                    spreadAndSetWorldBorder();
//                    return;
//                }
//                Player p = players.getFirst();
//                players.remove(p);
//
//                PlayerData pD = PlayerUtil.getPlayerData(p);
//                if (pD == null) {
//                    throw new NullPointerException("PlayerData is null");
//                }
//                pD.resetBeforeStartGame();
//                pD.getPlayerScoreboard().makeGameBoard();
//            }
//        }.runTaskTimer(plugin, 10, 10);
//    }
//
//    private void spreadAndSetWorldBorder() {
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                Location loc = Ascension.getSpawn();
//                WorldBorder worldBorder = plugin.getWorld().getWorldBorder();
//                worldBorder.setSize(6000, 0);
//                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
//                        "spreadplayers " + loc.getBlockX() + " " + loc.getBlockZ() + " 500 3000 true @a");
//
//                launchBingoBoard();
//            }
//        }.runTaskLater(this.plugin, 20);
//    }
//
//    private void launchBingoBoard() {
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                BingoData bingoData = plugin.getBingoData();
//                bingoData.setItems(new ItemList().getItemsForBoard());
//                ArrayList<ItemStack> item = bingoData.getItems();
//                for (ItemStack i : item) {
//                    TextUtil.debugText(i.toString());
//                }
//                gameIntroduction();
//            }
//        }.runTaskLater(this.plugin, 20);
//    }
//
//    private void gameIntroduction() {
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                BukkitScheduler scheduler = plugin.getServer().getScheduler();
//                scheduler.runTaskLater(plugin, () -> {
//                    text1();
//                }, 10 * second);
//                scheduler.runTaskLater(plugin, () -> {
//                    text2();
//                }, 30 * second);
//                scheduler.runTaskLater(plugin, () -> {
//                    text3();
//                }, 50 * second);
//                scheduler.runTaskLater(plugin, () -> {
//                    text4();
//                }, 70 * second);
//                scheduler.runTaskLater(plugin, () -> {
//                    text5();
//                }, 85 * second);
//                scheduler.runTaskLater(plugin, () -> {
//                    launchSequence();
//                    resetWorld();
//                }, 100 * second);
//            }
//        }.runTaskLater(this.plugin, 20);
//    }
//
//    private void launchSequence() {
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                BukkitScheduler scheduler = plugin.getServer().getScheduler();
//                scheduler.runTaskLater(plugin, () -> {
//                    makeAndShowTitle("Game Begins In", " ", 1, 5, 0);
//                }, 20);
//                scheduler.runTaskLater(plugin, () -> {
//                    makeAndShowTitle("Game Begins In", "3", 0, 5, 0);
//                    SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1f);
//                }, 60);
//                scheduler.runTaskLater(plugin, () -> {
//                    makeAndShowTitle("Game Begins In", "2", 0, 5, 0);
//                    SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
//                }, 80);
//                scheduler.runTaskLater(plugin, () -> {
//                    makeAndShowTitle("Game Begins In", "1", 0, 5, 0);
//                    SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
//                }, 100);
//                scheduler.runTaskLater(plugin, () -> {
//                    gameState.setCurrentState(GameStates.GAME_BEFORE_GRACE);
//                    makeAndShowTitle("GO!!!!", " ", 0, 2, 3);
//                    SoundUtil.broadcastSound(Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 2.0f);
//                    for (Player p : Bukkit.getOnlinePlayers()) {
//                        PlayerData pD = PlayerUtil.getPlayerData(p);
////                        p.sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut)
//                        assert pD != null;
//                        pD.resetAfterStartGame();
//                    }
//                    plugin.getTimeline().startTimeline();
//                }, 120);
//            }
//        }.runTaskLater(this.plugin, 20);
//    }
//
//    private void makeAndShowTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
//        Component titleT = TextUtil.makeText(title, TextUtil.YELLOW, false, false);
//        Component subtitleT = TextUtil.makeText(subtitle, TextUtil.YELLOW, false, false);
//        Title t = TextUtil.makeTitle(titleT, subtitleT, fadeIn, stay, fadeOut);
//        plugin.getServer().showTitle(t);
//    }
//
//    private void resetWorld() {
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                World world = plugin.getWorld();
//                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
//                world.setDifficulty(Difficulty.NORMAL);
////                world.setPVP(false);
//                world.setTime(0);
//                world.setWeatherDuration(0);
//                TextUtil.debugText("Start World");
//            }
//        }.runTaskLater(this.plugin, 20);
//    }
//
//    private void text1() {
//        plugin.getServer().broadcast(TextUtil.makeText("Text 1", TextUtil.GRAY));
//        SoundUtil.broadcastSound(Sound.AMBIENT_CAVE, 100, 0);
//    }
//
//    private void text2() {
//        plugin.getServer().broadcast(TextUtil.makeText("Text 2", TextUtil.GRAY));
//        SoundUtil.broadcastSound(Sound.AMBIENT_CAVE, 100, 0.6);
//    }
//
//    private void text3() {
//        plugin.getServer().broadcast(TextUtil.makeText("Text 3", TextUtil.GRAY));
//        SoundUtil.broadcastSound(Sound.AMBIENT_CAVE, 100, 1);
//    }
//
//    private void text4() {
//        plugin.getServer().broadcast(TextUtil.makeText("Text 4", TextUtil.GRAY));
//        SoundUtil.broadcastSound(Sound.AMBIENT_CAVE, 100, 1.2);
//    }
//
//    private void text5() {
//        plugin.getServer().broadcast(TextUtil.topBorder(TextUtil.GRAY));
//        plugin.getServer().broadcast(Component.text("\n\n                        ASCENSION\n\n").color(TextUtil.WHITE).decoration(TextDecoration.BOLD, true));
//        plugin.getServer().broadcast(TextUtil.bottomBorder(TextUtil.GRAY));
//        SoundUtil.broadcastSound(Sound.BLOCK_NOTE_BLOCK_PLING, 100, 0);
//        SoundUtil.broadcastSound(Sound.AMBIENT_CAVE, 100, 2);
//    }
//}
