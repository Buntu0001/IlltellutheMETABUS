package com.buntu.illtelluthemetabus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;

public class ScoreBoardManager {
  private static Integer count = 0;
  private static String scoreBoardTitle;
  private static Integer updateTaskId = 0;

  public static void setScoreBoardTitle(String title) {
    scoreBoardTitle = title;
  }

  public static void repeatUpdateScoreBoard() {
    updateTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Util.plugin, new Runnable() {
      @Override
      public void run() {
        ScoreBoardManager.updateScoreBoard();
      }
    }, 0, 5L);
  }

  public static void updateScoreBoard() {
    if (Bukkit.getOnlinePlayers().size() > 0) {
      ScoreboardManager manager = Bukkit.getScoreboardManager();
      Scoreboard scoreboard = manager.getNewScoreboard();

      Objective objective = scoreboard.registerNewObjective("QuestionScore", "dummy");
      String completeTitle = Util.animatedString(scoreBoardTitle, count++, ChatColor.YELLOW, ChatColor.GOLD);
      if (count > scoreBoardTitle.length()) {
        count = 0;
      }
      objective.setDisplayName(completeTitle);
      objective.setDisplaySlot(DisplaySlot.SIDEBAR);

      Integer multipleLineScore = Bukkit.getOnlinePlayers().size() - 1;
      ArrayList<QuestionPlayerState> sortList = new ArrayList<>();
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
        sortList.add(QuestionPlayerStateList.get(onlinePlayer));
      }
      sortList = Util.reverseSequentialSort(sortList);

      for (int i = 0; i < sortList.size(); i++) {
        QuestionPlayerState questionPlayerState = sortList.get(i);
        Score score = objective.getScore(Util.translate(String.format("&a%s: &c%d&7점",
            ChatColor.stripColor(questionPlayerState.getPlayer().getDisplayName()), questionPlayerState.getScore())));
        score.setScore(multipleLineScore--);
      }
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
        onlinePlayer.setScoreboard(scoreboard);
      }
    }
  }

  public static void clearScoreBoard() {
    Bukkit.getScheduler().cancelTask(updateTaskId);
    ScoreboardManager scoreBoardManager = Bukkit.getScoreboardManager();
    Scoreboard scoreboard = scoreBoardManager.getNewScoreboard();
    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      onlinePlayer.setScoreboard(scoreboard);
    }
  }
}
