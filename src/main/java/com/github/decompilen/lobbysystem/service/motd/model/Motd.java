package com.github.decompilen.lobbysystem.service.motd.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Motd {

    private final String header;
    private final String footer;
}
