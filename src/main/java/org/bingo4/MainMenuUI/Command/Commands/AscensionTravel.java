//package org.bingo4.MainMenuUI.Command.Commands;
//
//import org.bingo4.Bingo4;
//import org.bingo4.Effects.TeleportSequence;
//import org.bingo4.General.Game.GameStates;
//import org.bingo4.Player.Cooldowns.TeleportCooldown;
//import org.bingo4.Player.Data.PlayerData;
//import org.bingo4.Player.Data.PlayerUnlocks;
//import org.bingo4.Utility.TextUtil;
//import net.kyori.adventure.text.Component;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.entity.Player;
//import org.bukkit.event.inventory.InventoryClickEvent;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class AscensionTravel implements Commands {
//    public final static String NAME = "ascension_travel";
//    private final ItemStack item;
//    private final Bingo4 plugin;
//    private final int purchasePrice;
//    public AscensionTravel() {
//        purchasePrice = 150;
//        item = makeButton();
//        this.plugin = Bingo4.getInstance();
//    }
//    @Override
//    public boolean uponEvent(InventoryClickEvent event, PlayerData playerData) {
//        Player p = playerData.getPlayer();
//        if (TeleportCooldown.getInstance().isOnCooldown(p)) return true;
//        if (!inAscension(p)) return true;
//        if (canPurchase(playerData)) {
//            Random rand = new Random();
//            Location ascensionLocation = plugin.getSettingsUI().getSettings().getTimeline().getAscensionEvent().getAscendingLocation().getSpawnLocation();
//            int spreadDistance = ascensionLocation.getWorld().getWorldBorder().getSize() < 250 ?
//                    (int) ascensionLocation.getWorld().getWorldBorder().getSize() : 250;
//            int zM = rand.nextInt(2) == 1 ? 1 : -1;
//            int xM = rand.nextInt(2) == 1 ? 1 : -1;
//            int z = ascensionLocation.getBlockZ() + rand.nextInt(125, spreadDistance) * zM;
//            int x = ascensionLocation.getBlockX() + rand.nextInt(125, spreadDistance) * xM;
//            TeleportCooldown.getInstance().setCooldownTimer(playerData.getPlayer());
//            Location newLoc = plugin.getWorld().getHighestBlockAt(x, z).getLocation();
//            new TeleportSequence(playerData, newLoc.add(0, 1, 0), 10);
//        } else {
//            TextUtil.errorMessage(p, "You do not have the souls to purchase this teleport!");
//        }
//
//        return true;
//
//    }
//
//    private boolean inAscension(Player p) {
//        if (plugin.getGameState().checkState(GameStates.GAME_ASCENSION)) {
//            return true;
//        } else {
//            TextUtil.errorMessage(p, "There is no active Bingo4 Location currently!");
//            return false;
//        }
//
//    }
//
//    private boolean canPurchase(PlayerData playerData) {
//        PlayerUnlocks playerUnlocks = playerData.getPlayerUnlocks();
//        if (playerUnlocks.getUnlockTokens() >= purchasePrice) {
//            playerUnlocks.addUnlockTokens(-purchasePrice);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public ItemStack getButton() {
//        return item;
//    }
//
//    private ItemStack makeButton() {
//        ItemStack item = new ItemStack(Material.END_PORTAL_FRAME);
//        ItemMeta itemMeta = item.getItemMeta();
//        itemMeta.displayName(TextUtil.makeText("Bingo4 Travel", TextUtil.AQUA));
//        List<Component> lore = new ArrayList<>(List.of(
//                TextUtil.makeText(" Teleports you to an active Bingo4 Location", TextUtil.DARK_PURPLE),
//                TextUtil.makeText("Can only be used when there is an Bingo4 Active", TextUtil.DARK_RED),
//                TextUtil.makeText("              COST: [" + purchasePrice + " Souls]", TextUtil.GOLD)
//        ));
//        itemMeta.lore(lore);
//        item.setItemMeta(itemMeta);
//        return item;
//    }
//}
