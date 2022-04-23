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
import com.github.decompilen.lobbysystem.service.motd.MotdService;
import com.github.decompilen.lobbysystem.service.motd.model.Motd;
import com.github.decompilen.lobbysystem.service.scoreboard.ScoreboardService;
import com.github.decompilen.lobbysystem.service.tablist.TablistService;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.Random;

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
        TablistService tablistService = serviceRegistry.registerService(TablistService.class);
        ScoreboardService scoreboardService = serviceRegistry.registerService(ScoreboardService.class);
        MotdService motdService = serviceRegistry.registerService(MotdService.class);

        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void handle(PlayerJoinEvent event) {
                scoreboardService.set(event.getPlayer());
                tablistService.set(event.getPlayer());
            }

            @EventHandler
            public void handle(ServerListPingEvent event) {
                if (motdService.getConfiguration().isEnabled()) {
                    Motd motd = motdService.getConfiguration().getMotds().get(new Random().nextInt(motdService.getConfiguration().getMotds().size()));
                    event.setMotd(String.format("%s\n%s", motd.getHeader(), motd.getFooter()));
                }
            }
        }, plugin);
    }
}
