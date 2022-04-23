package com.github.decompilen.lobbysystem.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class LobbyEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
