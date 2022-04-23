package com.github.decompilen.lobbysystem.service.tablist.config;

import com.github.decompilen.lobbysystem.config.LobbyConfiguration;
import com.github.decompilen.lobbysystem.service.tablist.model.Tablist;
import lombok.Getter;

@Getter
public class TablistConfiguration extends LobbyConfiguration {

    private boolean enabled = true;
    private Tablist tablist = new Tablist("§aThanks for using my plugin", "§aSincerly, decompilen!");

    public TablistConfiguration(String fileName) {
        super(fileName);

        load();

        append("enabled", enabled, false);
        this.enabled = get("enabled", Boolean.class);

        append("tablist", tablist, false);
        this.tablist = get("tablist", Tablist.class);

        save();
    }
}
