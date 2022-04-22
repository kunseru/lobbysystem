package com.github.decompilen.lobbysystem.service.command.config;

import com.github.decompilen.lobbysystem.config.LobbyConfiguration;
import com.github.decompilen.lobbysystem.service.command.model.Command;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public class CommandConfiguration extends LobbyConfiguration {

    private List<Command> lobbyCommands = Arrays.asList(new Command("location", "lobby.manage.location", "location", Collections.singletonList("warp")), new Command("developer", "none", "developer", Collections.singletonList("dev")));

    public CommandConfiguration(String fileName) {
        super(fileName);

        load();

        append("commands", lobbyCommands, false);
        this.lobbyCommands = getList("commands", Command.class);

        save();
    }
}
