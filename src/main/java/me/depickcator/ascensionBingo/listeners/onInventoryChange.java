//package me.depickcator.ascensionBingo.listeners;
//
//
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.inventory.InventoryClickEvent;
//import org.bukkit.event.entity.EntityPickupItemEvent;
//import org.bukkit.event.inventory.InventoryDragEvent;
//import org.bukkit.event.inventory.InventoryInteractEvent;
//
//
//
//public class onInventoryChange implements Listener {
//
//    private void event(Player p) {
//        //Bukkit.dispatchCommand(Objects.requireNonNull(Bukkit.getEntity(p.getUniqueId())), "say hola" + p.getName());
//    }
//
//
//
//    @EventHandler
//    public void onItemPickup(EntityPickupItemEvent e) {
//        if (e.getEntity() instanceof Player) {
//            event((Player) e.getEntity());
//        }
//    }
//
//    @EventHandler
//    public void onInventoryClick(InventoryClickEvent e) {
//        event((Player) e.getWhoClicked());
//    }
//
//    @EventHandler
//    public void onInventoryDrag(InventoryDragEvent e) {
//        event((Player) e.getWhoClicked());
//    }
//
//    @EventHandler
//    public void onInventoryInteract(InventoryInteractEvent e) {
//        event((Player) e.getWhoClicked());
//    }
//}
