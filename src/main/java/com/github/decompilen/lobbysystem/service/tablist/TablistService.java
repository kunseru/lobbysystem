package com.github.decompilen.lobbysystem.service.tablist;

import com.github.decompilen.lobbysystem.LobbySystem;
import com.github.decompilen.lobbysystem.service.LobbyService;
import com.github.decompilen.lobbysystem.service.tablist.config.TablistConfiguration;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

@Getter
public class TablistService extends LobbyService {

    private final TablistConfiguration configuration = new TablistConfiguration(LobbySystem.getInstance().getPlugin().getDataFolder() + "/tablist");

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public void set(Player player) {
        if (configuration.isEnabled()) {
            IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + configuration.getTablist().getHeader() + "\"}");
            IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + configuration.getTablist().getFooter() + "\"}");

            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(header);

            try {
                Field field = packet.getClass().getDeclaredField("b");
                field.setAccessible(true);
                field.set(packet, footer);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }
}
