package com.github.decompilen.lobbysystem.service.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LobbyLocation {

    private final String name;
    private final String world;
    private final double x, y, z;
    private final float yaw, pitch;
}
