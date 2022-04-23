package com.github.decompilen.lobbysystem.service.inventory;

import com.github.decompilen.lobbysystem.LobbySystem;
import com.github.decompilen.lobbysystem.service.LobbyService;
import com.github.decompilen.lobbysystem.service.inventory.config.InventoryConfiguration;
import lombok.Getter;

@Getter
public class InventoryService extends LobbyService {

    private final InventoryConfiguration configuration = new InventoryConfiguration(LobbySystem.getInstance().getPlugin().getDataFolder() + "/inventorys");

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
