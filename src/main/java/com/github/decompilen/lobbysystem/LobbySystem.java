package com.github.decompilen.lobbysystem;

import com.github.decompilen.lobbysystem.service.ServiceRegistry;
import com.github.decompilen.lobbysystem.service.command.CommandService;
import com.github.decompilen.lobbysystem.service.location.LocationService;
import com.github.decompilen.lobbysystem.service.message.MessageService;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

@Getter
public class LobbySystem {

    private final Plugin plugin;

    @Getter private static LobbySystem instance;

    private final ServiceRegistry serviceRegistry = new ServiceRegistry();

    public LobbySystem(Plugin plugin) {
        instance = this;

        this.plugin = plugin;

        System.out.println("Thanks for using my plugin! Sincerly decompilen!");

        serviceRegistry.registerService(MessageService.class);
        serviceRegistry.registerService(CommandService.class);
        serviceRegistry.registerService(LocationService.class);
    }
}
