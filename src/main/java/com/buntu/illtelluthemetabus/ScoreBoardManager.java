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
  public static String getScoreBoardTitle() {return scoreBoardTitle;}

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
      String completeTitle = Util.animatedString(scoreBoardTitle, count++);
      if (count == scoreBoardTitle.length() + 3) {
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
        Score score = objective.getScore(Util.translate(String.format("  &a%s: &c%d&7점",
            ChatColor.stripColor(questionPlayerState.getPlayer().getDisplayName()), questionPlayerState.getScore())));
        score.setScore(multipleLineScore--);
      }
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
        multipleLineScore = Bukkit.getOnlinePlayers().size() - 1 + 4;
        Score score2 = objective.getScore(Util.translate(" &7닉네임: &6" + onlinePlayer.getDisplayName()));
        score2.setScore(multipleLineScore--);
        Score score3 = objective.getScore(Util.translate(" &7푼 문제: &6" + QuestionPlayerStateList.get(onlinePlayer).getSolvedCount() + "개"));
        score3.setScore(multipleLineScore--);
        Score score4 = objective.getScore(Util.translate(""));
        score4.setScore(multipleLineScore--);
        Score score5 = objective.getScore(Util.translate(" &e랭킹 &o(오름차순)"));
        score5.setScore(multipleLineScore--);
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
