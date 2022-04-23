package com.github.decompilen.lobbysystem.service.command;

import com.github.decompilen.lobbysystem.LobbySystem;
import com.github.decompilen.lobbysystem.service.LobbyService;
import com.github.decompilen.lobbysystem.service.command.config.CommandConfiguration;
import lombok.Getter;

@Getter
public class CommandService extends LobbyService {

    private final CommandConfiguration configuration = new CommandConfiguration(LobbySystem.getInstance().getPlugin().getDataFolder() + "/commands");

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
