package com.github.decompilen.lobbysystem;

import com.github.decompilen.lobbysystem.commands.DeveloperCommand;
import com.github.decompilen.lobbysystem.commands.LocationCommand;
import com.github.decompilen.lobbysystem.commands.MessageCommand;
import com.github.decompilen.lobbysystem.event.handler.PlayerInteractHandler;
import com.github.decompilen.lobbysystem.event.handler.PlayerJoinHandler;
import com.github.decompilen.lobbysystem.event.handler.ServerListPingHandler;
import com.github.decompilen.lobbysystem.initializer.PluginInitializer;
import com.github.decompilen.lobbysystem.service.ServiceRegistry;
import com.github.decompilen.lobbysystem.service.command.CommandService;
import com.github.decompilen.lobbysystem.service.command.abstracts.LobbyCommand;
import com.github.decompilen.lobbysystem.service.command.model.Command;
import com.github.decompilen.lobbysystem.service.inventory.InventoryService;
import com.github.decompilen.lobbysystem.service.items.ItemService;
import com.github.decompilen.lobbysystem.service.location.LocationService;
import com.github.decompilen.lobbysystem.service.message.MessageService;
import com.github.decompilen.lobbysystem.service.motd.MotdService;
import com.github.decompilen.lobbysystem.service.scoreboard.ScoreboardService;
import com.github.decompilen.lobbysystem.service.tablist.TablistService;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

@Getter
public class LobbySystem {

    private final PluginInitializer plugin;

    @Getter private static LobbySystem instance;

    private final ServiceRegistry serviceRegistry = new ServiceRegistry();

    public LobbySystem(PluginInitializer plugin) {
        instance = this;

        this.plugin = plugin;

        System.out.println("Thanks for using my plugin! Sincerly decompilen!");

        MessageService messageService = serviceRegistry.registerService(MessageService.class);
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
        LocationService locationService = serviceRegistry.registerService(LocationService.class);
        TablistService tablistService = serviceRegistry.registerService(TablistService.class);
        ScoreboardService scoreboardService = serviceRegistry.registerService(ScoreboardService.class);
        MotdService motdService = serviceRegistry.registerService(MotdService.class);
        ItemService itemService = serviceRegistry.registerService(ItemService.class);
        InventoryService inventoryService = serviceRegistry.registerService(InventoryService.class);

        System.out.println("=======================");
        System.out.println("Following settings:");
        System.out.println("Tablist » " + (tablistService.getConfiguration().isEnabled() ? "Enabled" : "Disabled") + "!");
        System.out.println("Scoreboard » " + (scoreboardService.getConfiguration().isEnabled() ? "Enabled" : "Disabled") + "!");
        System.out.println("Motd » " + (motdService.getConfiguration().isEnabled() ? "Enabled" : "Disabled") + "!");
        System.out.println("=======================");

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerInteractHandler(), plugin);
        pluginManager.registerEvents(new PlayerJoinHandler(), plugin);
        pluginManager.registerEvents(new ServerListPingHandler(), plugin);
    }
}
