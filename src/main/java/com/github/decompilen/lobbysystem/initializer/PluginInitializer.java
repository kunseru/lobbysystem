package com.github.decompilen.lobbysystem.initializer;

import com.github.decompilen.lobbysystem.LobbySystem;
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
