package com.github.decompilen.lobbysystem.service.message;

import com.github.decompilen.lobbysystem.LobbySystem;
import com.github.decompilen.lobbysystem.exception.MessageException;
import com.github.decompilen.lobbysystem.service.LobbyService;
import com.github.decompilen.lobbysystem.service.message.config.MessageConfiguration;
import com.github.decompilen.lobbysystem.service.message.model.Message;

import java.util.Optional;

public class MessageService extends LobbyService {

    private final MessageConfiguration configuration = new MessageConfiguration(LobbySystem.getInstance().getPlugin().getDataFolder() + "/messages");

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public String getPrefix() {
        Optional<Message> optional = configuration.getMessages().stream().filter(message -> message.getKey().equalsIgnoreCase("prefix")).findAny();
        if (!optional.isPresent()) {
            try {
                throw new MessageException("Couldn't find prefix please add a message to messages.json with key prefix!");
            } catch (MessageException e) {
                e.printStackTrace();
            }
        }
        return optional.orElse(new Message("error", "§cAn unexpected error occurred!")).getMessage();
    }

    public boolean has(String key) {
        Optional<Message> optional = configuration.getMessages().stream().filter(message -> message.getKey().equalsIgnoreCase(key)).findAny();
        return optional.isPresent();
    }

    public String getMessage(String key, Object... args) {
        Optional<Message> optional = configuration.getMessages().stream().filter(message -> message.getKey().equalsIgnoreCase(key)).findAny();
        if (!optional.isPresent()) {
            try {
                throw new MessageException(String.format("Message with key %s not found!", key));
            } catch (MessageException e) {
                e.printStackTrace();
            }
        }
        if (args.length == 0) {
            return optional.orElse(new Message("error", "§cAn unexpected error occurred!")).getMessage().replace("{prefix}", getPrefix());
        }
        return String.format(optional.orElse(new Message("error", "§cAn unexpected error occurred!")).getMessage(), args).replace("{prefix}", getPrefix());
    }
}
