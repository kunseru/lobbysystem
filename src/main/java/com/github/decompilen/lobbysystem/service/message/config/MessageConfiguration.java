package com.github.decompilen.lobbysystem.service.message.config;

import com.github.decompilen.lobbysystem.config.LobbyConfiguration;
import com.github.decompilen.lobbysystem.service.message.model.Message;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class MessageConfiguration extends LobbyConfiguration {

    private List<Message> messages = Arrays.asList(
            new Message("prefix", "§aLobby §7» §r"),
            new Message("no_permissions", "{prefix} §cYou have no rights for this!"),
            new Message("message_command_usage", "{prefix} §cUsage: §7/%s <key>"),
            new Message("message_command_key_existing", "{prefix} §a"),
            new Message("message_command_key_not_existing", "{prefix} §cThere is no message with that key in messages.json!"),
            new Message("location_teleport_not_found", "{prefix} §cThe Location with the name %s wasn't found"),
            new Message("location_command_usage", "{prefix} §cUsage: §7/%s <set/delete> <name>"),
            new Message("location_command_set", "{prefix} §aLocation with name %s successfully set!"),
            new Message("location_command_set_already_existing", "{prefix} §cLocation with name %s already exists!"),
            new Message("location_command_delete", "{prefix} §aLocation with name %s successfully deleted!"),
            new Message("location_command_delete_not_existing", "{prefix} §cLocation with name %s doesn't exist!"));

    public MessageConfiguration(String fileName) {
        super(fileName);

        load();

        append("messages", messages, false);
        this.messages = getList("messages", Message.class);

        save();
    }
}
