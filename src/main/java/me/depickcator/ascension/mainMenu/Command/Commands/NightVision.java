package me.depickcator.ascension.MainMenu.Command.Commands;


import me.depickcator.ascension.General.SoundUtil;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Player.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class NightVision implements Commands {
    public final static String NAME = "night_vision";
    private final ItemStack item;
    public NightVision() {
        item = makeButton();
    }
    @Override
    public void uponEvent(InventoryClickEvent event, PlayerData playerData) {
        Player p = playerData.getPlayer();
//        PersistentDataContainer container = p.getPersistentDataContainer();
        Component bool;
        if (!playerData.getPlayerStats().isNightVision()) {
            bool = giveNightVision(playerData);
        } else {
            bool = removeNightVision(playerData);
        }
        p.sendMessage(TextUtil.makeText("Night Vision is now ", TextUtil.AQUA).append(bool));
        SoundUtil.playHighPitchPling(p);
    }

    @Override
    public ItemStack getButton() {
        return item;
    }

    private ItemStack makeButton() {
        ItemStack item = new ItemStack(Material.GOLDEN_CARROT);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(TextUtil.makeText("Night Vision", TextUtil.AQUA));
        item.setItemMeta(itemMeta);
        return item;
    }

    private Component giveNightVision(PlayerData playerData) {
        Player p = playerData.getPlayer();
//        p.getPersistentDataContainer().set(nightVisionKey, PersistentDataType.BOOLEAN, true);
        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 0, false, false));
        playerData.getPlayerStats().nightVisionSwitch();
        return TextUtil.makeText("Enabled", TextUtil.GREEN);
    }

    private Component removeNightVision(PlayerData playerData) {
        Player p = playerData.getPlayer();
//        p.getPersistentDataContainer().set(nightVisionKey, PersistentDataType.BOOLEAN, false);
        p.removePotionEffect(PotionEffectType.NIGHT_VISION);
        playerData.getPlayerStats().nightVisionSwitch();
        return TextUtil.makeText("Disabled", TextUtil.RED);
    }
}
