package com.github.decompilen.lobbysystem.service.scoreboard.config;

import com.github.decompilen.lobbysystem.config.LobbyConfiguration;
import com.github.decompilen.lobbysystem.service.scoreboard.model.Score;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ScoreboardConfiguration extends LobbyConfiguration {

    private boolean enabled = true;
    private String header = "{player_name}";
    private List<Score> scores = Arrays.asList(new Score("Thanks for"), new Score("using my plugin!"));

    public ScoreboardConfiguration(String fileName) {
        super(fileName);

        load();

        append("enabled", enabled, false);
        this.enabled = get("enabled", Boolean.class);

        append("header", header, false);
        this.header = get("header", String.class);

        append("scores", scores, false);
        this.scores = getList("scores", Score.class);

        save();
    }
}
