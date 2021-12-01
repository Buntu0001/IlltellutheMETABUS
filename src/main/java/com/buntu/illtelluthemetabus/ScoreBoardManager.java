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

        Objective objective = scoreboard.registerNewObjective("QuestionScore", "dummy");
        objective.setDisplayName(Util.translate("&e획득 점수"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Integer multipleLineScore = 0;
        multipleLineScore = Bukkit.getOnlinePlayers().size() - 1;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            QuestionPlayerState questionPlayerState = QuestionPlayerStateList.getQuestionPlayerState(onlinePlayer);
            Score score = objective.getScore(Util.translate(String.format("&7%s: &6%d&7점", questionPlayerState.getPlayer().getDisplayName(), questionPlayerState.getPlayerScore())));
            score.setScore(multipleLineScore--);
        }
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.setScoreboard(scoreboard);
        }
    }

    public static void clearScoreBoard() {
        ScoreboardManager scoreBoardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreBoardManager.getNewScoreboard();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.setScoreboard(scoreboard);
        }
    }
}
