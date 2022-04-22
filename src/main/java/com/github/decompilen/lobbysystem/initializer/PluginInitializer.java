package com.github.decompilen.lobbysystem.initializer;

import com.github.decompilen.lobbysystem.LobbySystem;
import com.github.decompilen.lobbysystem.commands.DeveloperCommand;
import com.github.decompilen.lobbysystem.commands.LocationCommand;
import com.github.decompilen.lobbysystem.service.command.CommandService;
import com.github.decompilen.lobbysystem.service.command.abstracts.LobbyCommand;
import com.github.decompilen.lobbysystem.service.command.model.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class PluginInitializer extends JavaPlugin {

    private LobbySystem lobbySystem;

    @Override
    public void onEnable() {

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        this.lobbySystem = new LobbySystem(this);

        CommandService commandService = lobbySystem.getServiceRegistry().getService(CommandService.class);

        for (Command lobbyCommand : commandService.getConfiguration().getLobbyCommands()) {
            if (lobbyCommand.getIdentifier().equals("location")) {
                LobbyCommand locationCommand = new LocationCommand(lobbyCommand);
                locationCommand.register(getCommandMap());
            } else if (lobbyCommand.getIdentifier().equals("developer")) {
                LobbyCommand developerCommand = new DeveloperCommand(lobbyCommand);
                developerCommand.register(getCommandMap());
            }
        }
    }

    @Override
    public void onDisable() {
        lobbySystem.getServiceRegistry().shutdownServices();
    }

    public CommandMap getCommandMap() {
        try {
            Field field = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            return (CommandMap) field.get(Bukkit.getPluginManager());
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }
}
