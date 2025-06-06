package me.depickcator.ascension.Player.Data;


import com.google.gson.JsonObject;
import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Persistence.PlayerDataFileReader;
import me.depickcator.ascension.Persistence.Writeable;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class PlayerStats implements PlayerDataObservers, Writeable {
    private final PlayerData playerData;
    private Player player;

    //Statistics
    private int kills;
    private int deaths;
    private int itemsObtained;

    //Keys
    public static final String nightVisionKey = "night_vision";
    public static final String foodDropsKey = "food_drops";
    public static final String autoPurchaseUnlocks = "auto_purchase_unlocks";
    public PlayerStats(PlayerData playerData) {
        this.playerData = playerData;
        this.player = playerData.getPlayer();
        kills = 0;

    }

    public boolean getSetting(String key) {
        NamespacedKey k = new NamespacedKey(Ascension.getInstance(), key);
        boolean result;
        try {
            result = player.getPersistentDataContainer().get(k, PersistentDataType.BOOLEAN);
        } catch (NullPointerException e) {
            result = false;
            setSetting(key, false);
            PlayerDataFileReader reader = new PlayerDataFileReader(player);
            if (reader.read()) {
                reader.setPlayerSettings(this);
            }
        }
        return result;
    }

    public void setSetting(String key, boolean value) {
        NamespacedKey k = new NamespacedKey(Ascension.getInstance(), key);
        player.getPersistentDataContainer().set(k, PersistentDataType.BOOLEAN, value);
    }

    public void booleanSwitch(String key) {
        setSetting(key, !getSetting(key));
    }

    public void addKill() {
        kills++;
        playerData.getPlayerScoreboard().update();
    }

    public int getKills() {
        return kills;
    }

    public int getItemsObtained() {
        return itemsObtained;
    }

    public void addItemsObtained() {
        itemsObtained++;
    }

//    public void nightVisionSwitch() {
//        booleanSwitch(nightVisionKey);
//    }
//
//    public boolean isNightVision() {
//        return getNamespacedKey(nightVisionKey);
//    }
//
//    public void foodDropsSwitch() {
//        booleanSwitch(foodDropsKey);
//    }
//
//    public boolean isFoodDrops() {
//        return getNamespacedKey(foodDropsKey);
//    }
//
//    public void autoPurchaseUnlocksSwitch() {
//        booleanSwitch(autoPurchaseUnlocks);
//    }
//
//    public boolean isAutoPurchaseUnlocks() {
//        return getNamespacedKey(autoPurchaseUnlocks);
//    }


    //Deaths

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void addDeaths(int deaths) {
        setDeaths(this.deaths + deaths);
    }

    public void addDeath() {
        deaths++;
    }

    @Override
    public void updatePlayer() {
        player = playerData.getPlayer();
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("kills", player.getName());
        jsonObject.addProperty("itemsObtained", itemsObtained);
        jsonObject.addProperty("deaths", deaths);
        return jsonObject;
    }
}
