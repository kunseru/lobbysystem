package com.github.decompilen.lobbysystem.service.gadget.config;

import com.github.decompilen.lobbysystem.config.LobbyConfiguration;

public class GadgetConfiguration extends LobbyConfiguration {

    public GadgetConfiguration(String fileName) {
        super(fileName);

        load();

        save();
    }
}
