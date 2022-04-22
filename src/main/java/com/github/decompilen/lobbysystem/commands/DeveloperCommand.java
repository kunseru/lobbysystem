package com.github.decompilen.lobbysystem.commands;

import com.github.decompilen.lobbysystem.service.command.abstracts.LobbyCommand;
import com.github.decompilen.lobbysystem.service.command.model.Command;
import com.github.decompilen.lobbysystem.service.message.MessageService;
import org.bukkit.command.CommandSender;

public class DeveloperCommand extends LobbyCommand {

    public DeveloperCommand(Command lobbyCommand) {
        super(lobbyCommand);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        commandSender.sendMessage(getServiceRegistry().getService(MessageService.class).getPrefix() + "§aDieses Plugin wurde von decompilen programmiert!");
        return false;
    }
}
