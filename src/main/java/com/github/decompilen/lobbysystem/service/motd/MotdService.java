package com.github.decompilen.lobbysystem.service.motd;

import com.github.decompilen.lobbysystem.LobbySystem;
import com.github.decompilen.lobbysystem.service.LobbyService;
import com.github.decompilen.lobbysystem.service.motd.config.MotdConfiguration;
import lombok.Getter;

@Getter
public class MotdService extends LobbyService {

    private final MotdConfiguration configuration = new MotdConfiguration(LobbySystem.getInstance().getPlugin().getDataFolder() + "/motd");

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
