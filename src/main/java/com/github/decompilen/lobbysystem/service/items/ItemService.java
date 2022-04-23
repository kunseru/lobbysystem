package com.github.decompilen.lobbysystem.service.items;

import com.github.decompilen.lobbysystem.LobbySystem;
import com.github.decompilen.lobbysystem.service.LobbyService;
import com.github.decompilen.lobbysystem.service.items.builder.ItemStackBuilder;
import com.github.decompilen.lobbysystem.service.items.config.ItemConfiguration;
import com.github.decompilen.lobbysystem.service.items.model.Item;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class ItemService extends LobbyService {

    private final ItemConfiguration configuration = new ItemConfiguration(LobbySystem.getInstance().getPlugin().getDataFolder() + "/items");

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public void set(Player player) {
        for (Item item : configuration.getItems()) {
            if (item.getPermission().equals("none") || item.getPermission().equals("null")) {
                player.getInventory().setItem(item.getSlot() - 1, ItemStackBuilder.fromMaterial(item.getMaterial()).displayName(item.getName()).build());
            } else {
                if (player.hasPermission(item.getPermission())) {
                    player.getInventory().setItem(item.getSlot() - 1, ItemStackBuilder.fromMaterial(item.getMaterial()).displayName(item.getName()).build());
                }
            }
        }
    }
}
