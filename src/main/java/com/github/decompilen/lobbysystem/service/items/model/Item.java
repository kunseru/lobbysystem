package com.github.decompilen.lobbysystem.service.items.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Material;

@Data
@AllArgsConstructor
public class Item {

    private final int slot;
    private final String name;
    private final Material material;
    private final String identifier;
    private final String permission;
}
