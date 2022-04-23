package com.github.decompilen.lobbysystem.service.motd.config;

import com.github.decompilen.lobbysystem.config.LobbyConfiguration;
import com.github.decompilen.lobbysystem.service.motd.model.Motd;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class MotdConfiguration extends LobbyConfiguration {

    private boolean enabled = true;
    private List<Motd> motds = Arrays.asList(new Motd("§aThanks for choosing my plugin!", "§aSincerly, decompilen!"), new Motd("§aYou can change this in the configuration!", "§aSincerly, decompilen!"));

    public MotdConfiguration(String fileName) {
        super(fileName);

        load();

        append("enabled", enabled, false);
        this.enabled = get("enabled", Boolean.class);

        append("motds", motds, false);
        this.motds = getList("motds", Motd.class);

        save();
    }
}
