package com.github.decompilen.lobbysystem.event.handler;

import com.github.decompilen.lobbysystem.LobbySystem;
import com.github.decompilen.lobbysystem.service.motd.MotdService;
import com.github.decompilen.lobbysystem.service.motd.model.Motd;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.Random;

public class ServerListPingHandler implements Listener {

    private final MotdService motdService = LobbySystem.getInstance().getServiceRegistry().getService(MotdService.class);

    @EventHandler
    public void handle(ServerListPingEvent event) {
        if (motdService.getConfiguration().isEnabled()) {
            Motd motd = motdService.getConfiguration().getMotds().get(new Random().nextInt(motdService.getConfiguration().getMotds().size()));
            event.setMotd(String.format("%s\n%s", motd.getHeader(), motd.getFooter()));
        }
    }
}
