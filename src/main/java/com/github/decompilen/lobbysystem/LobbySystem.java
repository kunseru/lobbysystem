package com.github.decompilen.lobbysystem;

import com.github.decompilen.lobbysystem.commands.DeveloperCommand;
import com.github.decompilen.lobbysystem.commands.LocationCommand;
import com.github.decompilen.lobbysystem.commands.MessageCommand;
import com.github.decompilen.lobbysystem.initializer.PluginInitializer;
import com.github.decompilen.lobbysystem.service.ServiceRegistry;
import com.github.decompilen.lobbysystem.service.command.CommandService;
import com.github.decompilen.lobbysystem.service.command.abstracts.LobbyCommand;
import com.github.decompilen.lobbysystem.service.command.model.Command;
import com.github.decompilen.lobbysystem.service.location.LocationService;
import com.github.decompilen.lobbysystem.service.message.MessageService;
import lombok.Getter;

@Getter
public class LobbySystem {

    private final PluginInitializer plugin;

    @Getter private static LobbySystem instance;

    private final ServiceRegistry serviceRegistry = new ServiceRegistry();

    public LobbySystem(PluginInitializer plugin) {
        instance = this;

        this.plugin = plugin;

        System.out.println("Thanks for using my plugin! Sincerly decompilen!");

        serviceRegistry.registerService(MessageService.class);
        CommandService commandService = serviceRegistry.registerService(CommandService.class);
        for (Command lobbyCommand : commandService.getConfiguration().getLobbyCommands()) {
            if (lobbyCommand.getIdentifier().equals("location")) {
                LobbyCommand locationCommand = new LocationCommand(lobbyCommand);
                locationCommand.register(plugin.getCommandMap());
            } else if (lobbyCommand.getIdentifier().equals("developer")) {
                LobbyCommand developerCommand = new DeveloperCommand(lobbyCommand);
                developerCommand.register(plugin.getCommandMap());
            } else if (lobbyCommand.getIdentifier().equalsIgnoreCase("message")) {
                LobbyCommand messageCommand = new MessageCommand(lobbyCommand);
                messageCommand.register(plugin.getCommandMap());
            }
        }
        serviceRegistry.registerService(LocationService.class);
    }
}
