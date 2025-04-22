package me.depickcator.ascension.Items.Uncraftable.Skulls;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;


public abstract class Skulls extends CustomItem implements ItemClick {
    public Skulls(String displayName, String key) {
        super(displayName, key);
    }

    @Override
    public ItemStack getItem() {
        return getResult();
    }

    public abstract boolean uponClick(PlayerInteractEvent e, PlayerData pD);

    @Override
    public void registerItem() {
        addItem(getResult(), this);
    }

    protected abstract ItemStack initResult();

    protected void consumedSkull(PlayerData pD, ItemStack item) {
        Player p = pD.getPlayer();
        item.setAmount(item.getAmount() - 1);
        giveSkullEffects(p);
        consumeSkullSound(p);
    }

    private void giveSkullEffects(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 7 * 20, 1, false));
        p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 8 * 20, 0, false));
    }

    protected void giveSkullTeamEffects(PlayerData pD) {
        giveSkullTeamEffects(pD, new PotionEffect(PotionEffectType.REGENERATION, 12 * 20, 0, false));
    }

    protected void giveSkullTeamEffects(PlayerData pD, PotionEffect... effects) {
        for (Player player : pD.getPlayerTeam().getTeam().getOtherTeamMembers(pD.getPlayer())) {
            for (PotionEffect effect : effects) {
                player.addPotionEffect(effect);
            }
        }
    }

    protected void consumeMessage(PlayerData pD, String soloMessage, String teamMessage) {
        Player p = pD.getPlayer();
        Component soloText = TextUtil.makeText("You ate a player head which grants you " + soloMessage, TextUtil.GREEN);
        Component teamText = TextUtil.makeText(p.getName() + " ate a player head which grants you " + teamMessage, TextUtil.GREEN);
        p.sendMessage(soloText);
        if (!teamMessage.isBlank()) TextUtil.broadcastMessage(teamText, pD.getPlayerTeam().getTeam().getOtherTeamMembers(p));


    }

    protected void consumeMessage(PlayerData pD, String soloMessage) {
        consumeMessage(pD, soloMessage, "");
    }

    private void consumeSkullSound(Player p) {
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
    }

    protected ItemStack buildSkull(Material material) {
        return buildSkull(material, null, null);
    }

    protected ItemStack buildSkull(Material material, Component name, List<Component> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (name != null) meta.displayName(name.append(TextUtil.rightClickText()));
        if (lore != null) {
            lore.addFirst(TextUtil.makeText("Bonus Skull Effects:", TextUtil.YELLOW));
            meta.lore(lore);
        }
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        meta.setMaxStackSize(1);
        item.setItemMeta(meta);
        return item;
    }
}
