package com.github.decompilen.lobbysystem.service.scoreboard;

import com.github.decompilen.lobbysystem.LobbySystem;
import com.github.decompilen.lobbysystem.service.LobbyService;
import com.github.decompilen.lobbysystem.service.scoreboard.builder.ScoreboardBuilder;
import com.github.decompilen.lobbysystem.service.scoreboard.config.ScoreboardConfiguration;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class ScoreboardService extends LobbyService {

    private final ScoreboardConfiguration configuration = new ScoreboardConfiguration(LobbySystem.getInstance().getPlugin().getDataFolder() + "/scoreboard");

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public void set(Player player) {
        if (configuration.isEnabled()) {
            ScoreboardBuilder builder = ScoreboardBuilder.of(configuration.getHeader().replace("{player_name}", player.getName()));
            for (int i = 0; i < configuration.getScores().size(); i++) {
                builder.append(configuration.getScores().get(i).getText());
            }
            builder.where("{player_name}", player.getName());
            builder.build(player);
        }
    }
}
