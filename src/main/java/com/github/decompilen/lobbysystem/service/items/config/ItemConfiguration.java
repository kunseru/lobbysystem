package com.github.decompilen.lobbysystem.service.items.config;

import com.github.decompilen.lobbysystem.config.LobbyConfiguration;
import com.github.decompilen.lobbysystem.service.items.model.Item;
import lombok.Getter;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;

@Getter
public class ItemConfiguration extends LobbyConfiguration {

    private List<Item> items = Collections.singletonList(new Item(1, "§aNavigator", Material.COMPASS, "navigator", "none"));

    public ItemConfiguration(String fileName) {
        super(fileName);

        load();

        append("items", items, false);
        this.items = getList("items", Item.class);

        save();
    }
}
