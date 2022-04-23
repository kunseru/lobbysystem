package com.github.decompilen.lobbysystem.service.scoreboard.builder;

import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScoreboardBuilder {

    private String title;
    private HashMap<Integer, List<String>> lines;
    private Map<String, String> variableValues;

    private int getLowestIndex() {
        return lines.keySet().stream()
                .reduce(Integer::min)
                .orElse(10);
    }

    private void insertLine(int index) {
        if (lines.containsKey(index))
            return;
        lines.put(index, new ArrayList<>());
    }

    public ScoreboardBuilder append(String line) {
        int index = getLowestIndex() - 1;
        insertLine(index);
        lines.get(index)
                .add(line);
        return this;
    }

    public ScoreboardBuilder append(List<String> lines) {
        lines.forEach(this::append);
        return this;
    }

    public ScoreboardBuilder append(String... lines) {
        append(Arrays.asList(lines));
        return this;
    }

    public ScoreboardBuilder line(int index, String text) {
        insertLine(index);
        lines.get(index)
                .add(text);
        return this;
    }

    public ScoreboardBuilder where(String key, String value) {
        variableValues.put(key, value);
        return this;
    }

    public ScoreboardBuilder clearLines() {
        lines.clear();
        return this;
    }

    public ScoreboardBuilder clearVariables() {
        variableValues.clear();
        return this;
    }

    public void build(Player player) {
        where("playerName", player.getName());
        where("playerDisplayName", player.getDisplayName());

        Scoreboard scoreboard = new Scoreboard();
        ScoreboardObjective objective = scoreboard.registerObjective(title, IScoreboardCriteria.b);

        PacketPlayOutScoreboardObjective removeScoreboardPacket = new PacketPlayOutScoreboardObjective(objective, 1);
        PacketPlayOutScoreboardObjective createScoreboardPacket = new PacketPlayOutScoreboardObjective(objective, 0);

        PacketPlayOutScoreboardDisplayObjective displayScoreboardPacket = new PacketPlayOutScoreboardDisplayObjective(1, objective);

        objective.setDisplayName(title);

        Stream.of(removeScoreboardPacket, createScoreboardPacket, displayScoreboardPacket)
                .forEach(((CraftPlayer) player).getHandle().playerConnection::sendPacket);

        lines.keySet()
                .forEach(key -> lines.get(key)
                        .forEach(lineValue -> {
                            ScoreboardScore score = new ScoreboardScore(scoreboard, objective, replaceVariables(lineValue));
                            score.setScore(key);

                            PacketPlayOutScoreboardScore scoreboardScorePacket = new PacketPlayOutScoreboardScore(score);
                            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(scoreboardScorePacket);
                        }));
    }

    private String replaceVariables(String str) {
        String result = str;
        for (String s : variableValues.keySet())
            result = result.replace("%" + s + "%", variableValues.get(s));
        return result;
    }

    public static ScoreboardBuilder of(String title) {
        return new ScoreboardBuilder(title, Maps.newHashMap(), Maps.newHashMap());
    }
}
