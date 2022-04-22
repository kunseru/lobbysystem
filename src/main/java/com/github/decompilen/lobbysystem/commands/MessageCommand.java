package com.github.decompilen.lobbysystem.commands;

import com.github.decompilen.lobbysystem.service.command.abstracts.LobbyCommand;
import com.github.decompilen.lobbysystem.service.command.model.Command;
import com.github.decompilen.lobbysystem.service.message.MessageService;
import org.bukkit.command.CommandSender;

public class MessageCommand extends LobbyCommand {

    public MessageCommand(Command command) {
        super(command);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        MessageService messageService = getServiceRegistry().getService(MessageService.class);

        if (!commandSender.hasPermission(getPermission())) {
            commandSender.sendMessage(messageService.getMessage("no_permissions"));
            return true;
        }
        return false;
    }
}
