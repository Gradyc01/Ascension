package me.depickcator.ascension.Player.Cooldowns;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.UseCooldown;
import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Uncraftable.MainMenu;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class EntityInteractionCooldown extends Cooldowns {
//    private final ItemStack item;
    private static EntityInteractionCooldown instance;
    private final int cooldownTime; // In Seconds
    private EntityInteractionCooldown() {
        super("EntityInteraction");
        cooldownTime = 2;
    }

    @Override
    public ItemStack makeItem() {
        ItemStack item = new ItemStack(Material.BARRIER);
        item.setData(DataComponentTypes.USE_COOLDOWN,
                UseCooldown.useCooldown(0.01f)
                        .cooldownGroup(Key.key("cooldown:entity")));
        return item;
    }

    @Override
    public void setCooldownTimer(Player p) {
        setCooldownTimer(p, cooldownTime);
    }


    @Override
    /*Checks if Player p is on Cooldown for EntityInteraction and also sets a new cooldown*/
    public boolean isOnCooldown(Player p) {
        boolean isOnCooldown = super.isOnCooldown(p, false);
        setCooldownTimer(p);
        return isOnCooldown;
    }

    public static EntityInteractionCooldown getInstance() {
        if (instance == null) {
            instance = new EntityInteractionCooldown();
        }
        return instance;
    }


}
