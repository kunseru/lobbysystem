package com.github.decompilen.lobbysystem.service;

import java.util.ArrayList;
import java.util.List;

public class ServiceRegistry {

    private final List<LobbyService> services = new ArrayList<>();

    public <T extends LobbyService> T registerService(Class<T> clazz) {
        if (getService(clazz) == null) {
            try {
                T service = clazz.newInstance();
                service.onEnable();
                services.add(service);
                return service;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public <T extends LobbyService> T getService(Class<T> clazz) {
        for (LobbyService service : services) {
            if (service.getClass().getName().equals(clazz.getName())) {
                return clazz.cast(service);
            }
        }
        return null;
    }

    public void shutdownServices() {
        for (LobbyService service : services) {
            service.onDisable();
        }
        services.clear();
    }
}
