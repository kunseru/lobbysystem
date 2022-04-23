package com.github.decompilen.lobbysystem.service.location;

import com.github.decompilen.lobbysystem.LobbySystem;
import com.github.decompilen.lobbysystem.service.location.config.LocationConfiguration;
import com.github.decompilen.lobbysystem.service.LobbyService;
import com.github.decompilen.lobbysystem.service.location.model.LobbyLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Optional;

public class LocationService extends LobbyService {

    private final LocationConfiguration configuration = new LocationConfiguration(LobbySystem.getInstance().getPlugin().getDataFolder() + "/locations");

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        configuration.append("locations", configuration.getLobbyLocations(), true);
        configuration.save();
    }

    public Location get(String name) {
        Optional<LobbyLocation> optional = configuration.getLobbyLocations().stream().filter(lobbyLocation -> lobbyLocation.getName().equalsIgnoreCase(name)).findAny();
        if (optional.isPresent()) {
            LobbyLocation lobbyLocation = optional.get();
            return new Location(Bukkit.getWorld(lobbyLocation.getWorld()), lobbyLocation.getX(), lobbyLocation.getY(), lobbyLocation.getZ(), lobbyLocation.getYaw(), lobbyLocation.getPitch());
        }
        return null;
    }

    public boolean save(String name, Location location) {
        if (get(name) == null) {
            configuration.getLobbyLocations().add(new LobbyLocation(name, location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch()));
            return true;
        }
        return false;
    }

    public boolean delete(String name) {
        if (get(name) != null) {
            configuration.getLobbyLocations().stream().filter(lobbyLocation -> lobbyLocation.getName().equalsIgnoreCase(name)).findAny().ifPresent(lobbyLocation -> configuration.getLobbyLocations().remove(lobbyLocation));
            return true;
        }
        return false;
    }
}
