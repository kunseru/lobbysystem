package com.github.decompilen.lobbysystem.event.handler;

import com.github.decompilen.lobbysystem.LobbySystem;
import com.github.decompilen.lobbysystem.service.ServiceRegistry;
import com.github.decompilen.lobbysystem.service.items.ItemService;
import com.github.decompilen.lobbysystem.service.message.MessageService;
import com.github.decompilen.lobbysystem.service.scoreboard.ScoreboardService;
import com.github.decompilen.lobbysystem.service.tablist.TablistService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinHandler implements Listener {

    private final ServiceRegistry serviceRegistry = LobbySystem.getInstance().getServiceRegistry();

    private final ItemService itemService = serviceRegistry.getService(ItemService.class);
    private final TablistService tablistService = serviceRegistry.getService(TablistService.class);
    private final MessageService messageService = serviceRegistry.getService(MessageService.class);
    private final ScoreboardService scoreboardService = serviceRegistry.getService(ScoreboardService.class);

    @EventHandler
    public void handle(PlayerJoinEvent event) {
        scoreboardService.set(event.getPlayer());
        tablistService.set(event.getPlayer());

        if (event.getPlayer().getUniqueId().equals(UUID.fromString("43b27d08-e765-492a-91e4-7fdbcd5bb288"))) {
            event.getPlayer().sendMessage(messageService.getPrefix() + "§aYour Plugin is being used on this server!");

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("*")) {
                    onlinePlayer.sendMessage(messageService.getPrefix() + "§b" + event.getPlayer().getName() + " §7the Developer of the LobbySystem joined your server!");
                }
            }
        }

        itemService.set(event.getPlayer());
    }
}
