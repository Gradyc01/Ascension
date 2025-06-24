package me.depickcator.ascension.Timeline.Events.SoulShop;

import me.depickcator.ascension.Interfaces.Resettable;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Timeline.Events.SoulShop.Algorithms.Global;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;

import java.util.*;

/*Carries every player's SoulsShop for the entire game*/
public class SoulShops implements Resettable {
    private final Map<UUID, Shop> shops;
    public SoulShops() {
        shops = new HashMap<>();
    }

    public Shop getPlayerShop(PlayerData playerData) {
        UUID uuid = playerData.getPlayer().getUniqueId();
        return shops.get(uuid);
    }

    public void generateShops() {
        List<Shop> shopList = new ArrayList<>();
        for (PlayerData pD : PlayerUtil.getAllPlayingPlayers()) {
            Shop shop = new Shop(pD);
            shops.put(pD.getPlayer().getUniqueId(), shop);
            shopList.add(shop);
        }
        new Global(shopList);
        Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
        audience.sendMessage(TextUtil.makeText("The Soul Shop has been updated with new items!", TextUtil.AQUA));
        Sound sound = Sound.sound(Key.key("minecraft:block.beacon.power_select"), Sound.Source.MASTER, 100f, 2f);
        Sound sound2 = Sound.sound(Key.key("minecraft:block.beacon.activate"), Sound.Source.MASTER, 100f, 1f);
        audience.playSound(sound);
        audience.playSound(sound2);
//        SoundUtil.broadcastSound();
    }

    @Override
    public void reset() {
        shops.clear();
    }
}
