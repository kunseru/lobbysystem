package com.github.decompilen.lobbysystem.commands;

import com.github.decompilen.lobbysystem.service.command.abstracts.LobbyCommand;
import com.github.decompilen.lobbysystem.service.command.model.Command;
import com.github.decompilen.lobbysystem.service.location.LocationService;
import com.github.decompilen.lobbysystem.service.message.MessageService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationCommand extends LobbyCommand {

    private final MessageService messageService = getServiceRegistry().getService(MessageService.class);

    public LocationCommand(Command lobbyCommand) {
        super(lobbyCommand);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;

        if (!player.hasPermission(getPermission())) {
            player.sendMessage(messageService.getMessage("no_permissions"));
            return true;
        }

        if (args.length == 2) {
            String action = args[0];
            String name = args[1];
            LocationService locationService = getServiceRegistry().getService(LocationService.class);

            System.out.println(action);

            switch (action) {
                case "add":
                case "set": {
                    boolean bool = locationService.save(name, player.getLocation());
                    if (bool) {
                        player.sendMessage(messageService.getMessage("location_command_set", name));
                    } else {
                        player.sendMessage(messageService.getMessage("location_command_set_already_existing", name));
                    }
                    break;
                }
                case "remove":
                case "del":
                case "delete": {
                    boolean bool = locationService.delete(name);
                    if (bool) {
                        player.sendMessage(messageService.getMessage("location_command_delete", name));
                    } else {
                        player.sendMessage(messageService.getMessage("location_command_delete_not_existing", name));
                    }
                    break;
                }
            }
        } else {
            player.sendMessage(messageService.getMessage("location_command_usage", s));
        }
        return false;
    }
}
