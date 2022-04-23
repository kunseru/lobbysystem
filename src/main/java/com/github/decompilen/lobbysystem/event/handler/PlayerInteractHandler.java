package com.github.decompilen.lobbysystem.event.handler;

import com.github.decompilen.lobbysystem.LobbySystem;
import com.github.decompilen.lobbysystem.service.ServiceRegistry;
import com.github.decompilen.lobbysystem.service.inventory.InventoryService;
import com.github.decompilen.lobbysystem.service.inventory.model.Inv;
import com.github.decompilen.lobbysystem.service.items.ItemService;
import com.github.decompilen.lobbysystem.service.items.builder.ItemStackBuilder;
import com.github.decompilen.lobbysystem.service.items.model.Item;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class PlayerInteractHandler implements Listener {

    private final ServiceRegistry serviceRegistry = LobbySystem.getInstance().getServiceRegistry();

    private final ItemService itemService = serviceRegistry.getService(ItemService.class);
    private final InventoryService inventoryService = serviceRegistry.getService(InventoryService.class);

    @EventHandler
    public void handle(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getItem() == null || event.getItem().getItemMeta() == null) return;
            ItemMeta itemMeta = event.getItem().getItemMeta();
            Optional<Item> optionalItem = itemService.getConfiguration().getItems().stream().filter(item -> item.getName().equalsIgnoreCase(itemMeta.getDisplayName())).findAny();

            optionalItem.ifPresent(item -> {
                Optional<Inv> optionalInv = inventoryService.getConfiguration().getInventorys().stream().filter(inv -> inv.getIdentifier().equals(item.getIdentifier())).findAny();
                optionalInv.ifPresent(inv -> {
                    Inventory inventory = Bukkit.createInventory(null, (inv.getRows() * 9), inv.getName());

                    for (Item invItem : inv.getItems()) {
                        inventory.setItem(invItem.getSlot(), ItemStackBuilder.fromMaterial(invItem.getMaterial()).displayName(invItem.getName()).build());
                    }

                    event.getPlayer().openInventory(inventory);
                });
            });
        }
    }
}
