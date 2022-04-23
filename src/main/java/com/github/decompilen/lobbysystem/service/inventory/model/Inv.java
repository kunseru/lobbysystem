package com.github.decompilen.lobbysystem.service.inventory.model;

import com.github.decompilen.lobbysystem.service.items.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Inv {

    private final String name;
    private final int rows;
    private final List<Item> items;
    private final String identifier;
}
