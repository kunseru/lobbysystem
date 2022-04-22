package com.github.decompilen.lobbysystem.service.location.config;

import com.github.decompilen.lobbysystem.config.LobbyConfiguration;
import com.github.decompilen.lobbysystem.service.location.model.LobbyLocation;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LocationConfiguration extends LobbyConfiguration {

    private List<LobbyLocation> lobbyLocations = new ArrayList<>();

    public LocationConfiguration(String fileName) {
        super(fileName);

        load();

        append("locations", lobbyLocations, false);
        this.lobbyLocations = getList("locations", LobbyLocation.class);

        save();
    }
}
