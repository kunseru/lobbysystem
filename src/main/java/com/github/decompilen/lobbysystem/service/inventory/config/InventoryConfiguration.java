package com.github.decompilen.lobbysystem.service.inventory.config;

import com.github.decompilen.lobbysystem.config.LobbyConfiguration;
import com.github.decompilen.lobbysystem.service.inventory.model.Inv;
import com.github.decompilen.lobbysystem.service.items.model.Item;
import lombok.Getter;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;

@Getter
public class InventoryConfiguration extends LobbyConfiguration {

    private List<Inv> inventorys = Collections.singletonList(
            new Inv("Navigator", 3, Collections.singletonList(
                    new Item(1, "§c§lBedWars", Material.BED, "bedwars", "none")), "navigator"));

    public InventoryConfiguration(String fileName) {
        super(fileName);

        load();

        append("inventorys", inventorys, false);
        this.inventorys = getList("inventorys", Inv.class);

        save();
    }
}
