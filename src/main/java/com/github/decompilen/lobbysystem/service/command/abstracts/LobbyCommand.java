package com.github.decompilen.lobbysystem.service.command.abstracts;

import com.github.decompilen.lobbysystem.LobbySystem;
import com.github.decompilen.lobbysystem.service.ServiceRegistry;
import com.github.decompilen.lobbysystem.service.command.model.Command;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;

@Getter
public abstract class LobbyCommand extends BukkitCommand {

    private final Command command;

    private final ServiceRegistry serviceRegistry = LobbySystem.getInstance().getServiceRegistry();

    public LobbyCommand(Command command) {
        super(command.getName(), "null", "null", command.getAliases());
        this.command = command;
        setName(command.getName());
        setAliases(command.getAliases());

        if (!command.getPermission().equalsIgnoreCase("none") || !command.getPermission().equalsIgnoreCase("null")) {
            setPermission(command.getPermission());
        }

        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());
            commandMap.register(command.getName(), this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
