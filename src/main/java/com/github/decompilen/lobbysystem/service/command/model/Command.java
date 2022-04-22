package com.github.decompilen.lobbysystem.service.command.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Command {

    private final String name;
    private final String permission;
    private final String identifier;
    private final List<String> aliases;
}
