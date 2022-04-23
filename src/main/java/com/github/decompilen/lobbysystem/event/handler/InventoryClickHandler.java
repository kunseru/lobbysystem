package com.github.decompilen.lobbysystem.event.handler;

import com.github.decompilen.lobbysystem.LobbySystem;
import com.github.decompilen.lobbysystem.service.ServiceRegistry;
import com.github.decompilen.lobbysystem.service.inventory.InventoryService;
import com.github.decompilen.lobbysystem.service.inventory.model.Inv;
import com.github.decompilen.lobbysystem.service.items.ItemService;
import com.github.decompilen.lobbysystem.service.items.model.Item;
import com.github.decompilen.lobbysystem.service.location.LocationService;
import com.github.decompilen.lobbysystem.service.message.MessageService;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Optional;

public class InventoryClickHandler implements Listener {

    private final ServiceRegistry serviceRegistry = LobbySystem.getInstance().getServiceRegistry();

    private final ItemService itemService = serviceRegistry.getService(ItemService.class);
    private final MessageService messageService = serviceRegistry.getService(MessageService.class);
    private final LocationService locationService = serviceRegistry.getService(LocationService.class);
    private final InventoryService inventoryService = serviceRegistry.getService(InventoryService.class);

    @EventHandler
    public void handle(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) return;

        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();

        if (inventory == player.getInventory()) {
            event.setCancelled(true);
            return;
        }

        Optional<Inv> optionalInv = inventoryService.getConfiguration().getInventorys().stream().filter(inv -> inv.getName().equalsIgnoreCase(inventory.getName())).findAny();

        optionalInv.ifPresent(inv -> {
            if (inv.getIdentifier().equalsIgnoreCase("navigator")) {
                Optional<Item> optionalItem = inv.getItems().stream().filter(item -> item.getName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())).findAny();
                optionalItem.ifPresent(item -> {
                    Location location = locationService.get(ChatColor.stripColor(item.getName()));

                    if (location == null) {
                        player.sendMessage(messageService.getMessage("location_teleport_not_found", ChatColor.stripColor(item.getName())));
                        return;
                    }

                    player.teleport(location);
                });
                event.setCancelled(true);
            }
        });
    }
}
