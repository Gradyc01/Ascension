package me.depickcator.ascension.Interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface Summonable {
    /*Initializes the Entity at Location loc for Player p*/
    Entity initEntity(Player p);
    void summonEffect(Player p);
}
