package me.depickcator.ascensionBingo.General;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.ArrayList;

public class TextUtil {
    public static TextColor YELLOW = TextColor.color(255, 255, 0);
    public static TextColor GOLD = TextColor.color(0xFF, 0xAA, 0);
    public static TextColor GRAY = TextColor.color(0xAA, 0xAA, 0xAA);
    public static TextColor DARK_GRAY = TextColor.color(0x77, 0x77, 0x77);
    public static TextColor BLUE = TextColor.color(0x55, 0x55, 0xFF);
    public static TextColor AQUA = TextColor.color(0x00, 0xFF, 0xFF);
    public static TextColor RED = TextColor.color(0xFF, 0x00, 0x00);
    public static TextColor BLACK = TextColor.color(0x22, 0x22, 0x22);
    public static TextColor DARK_GREEN = TextColor.color(0x00, 0xAA, 0x00);
    public static TextColor DARK_PURPLE = TextColor.color(0xAA, 0x00, 0xAA);
    public static TextColor PINK = TextColor.color(0xFF, 0x55, 0xFF);
    public static TextColor GREEN = TextColor.color(0x55, 0xFF, 0x55);
    public static Component topBorder(TextColor color) {
        Component text =  Component.text("=====================================================\n").color(color);
        text = text.decoration(TextDecoration.ITALIC, false);
        return text;
    }

    public static Component bottomBorder(TextColor color) {
        Component text =  Component.text("\n=====================================================").color(color);
        text = text.decoration(TextDecoration.ITALIC, false);
        return text;
    }

    public static Title makeTitle(Component title, Component subtitle, double i1, double i2, double i3) {
        Duration fadeIn = Duration.ofMillis((long) (i1 * 1000));
        Duration stay = Duration.ofMillis((long) (i2 * 1000));
        Duration fadeOut = Duration.ofMillis((long) (i3 * 1000));

        Title.Times times = Title.Times.times(fadeIn, stay, fadeOut);
        return Title.title(title, subtitle, times);
    }

    public static Component makeText(String text, TextColor color) {
        return makeText(text, color, false, false);
    }

    public static Component makeText(String text, TextColor color, Boolean bold, Boolean italic) {
        Component str = Component.text(text, color);
        str = str.decoration(TextDecoration.BOLD, bold);
        str = str.decoration(TextDecoration.ITALIC, italic);
        return str;
    }

    public static void broadcastTitle(Title title) {
        for (Player p: Bukkit.getOnlinePlayers()) {
            p.showTitle(title);
        }
    }

    public static void broadcastMessage(Component text, ArrayList<Player> players) {
        for (Player p: players) {
            p.sendMessage(text);
        }
    }
}
