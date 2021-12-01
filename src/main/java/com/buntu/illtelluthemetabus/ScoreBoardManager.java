package com.buntu.illtelluthemetabus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;

public class ScoreBoardManager {
    public static void initializeScoreBoard() {
        updateScoreBoard();
    }

    public static void updateScoreBoard() {
        ScoreboardManager scoreBoardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreBoardManager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("test", "dummy");
        objective.setDisplayName("test");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Score score = objective.getScore(onlinePlayer.getDisplayName());
            score.setScore(523);
        }
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.setScoreboard(scoreboard);
        }
    }
}
