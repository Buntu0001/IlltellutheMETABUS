package com.buntu.illtelluthemetabus;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.HashMap;

import org.bukkit.Bukkit;

public class GameManager {
  private Long globalTimer = 0L;
  private HashMap<String, QuestionPlayerState> ranking = new HashMap<>();
  private Integer limitTime = 5;
  private Integer timerTaskId = 0;

  public void setGlobalTimer(Long time) {
    this.globalTimer = time;
  }

  public Long getGlobalTimer() {
    return this.globalTimer;
  }

  public void setRanking(HashMap<String, QuestionPlayerState> rank) {
    this.ranking = rank;
  }

  public HashMap<String, QuestionPlayerState> getRanking() {
    return this.ranking;
  }

  public void gameStart() {
    Util.questionAvailable = true;
    this.globalTimer = System.currentTimeMillis() + (limitTime * 60000);
    Bukkit.getScheduler().scheduleSyncRepeatingTask(Util.plugin, new Runnable() {
      @Override
      public void run() {
        if (isGameEnd()) {
          Bukkit.broadcastMessage("&a[공지] 게임이 종료되었습니다.");
          Util.questionAvailable = false;
          Bukkit.getScheduler().cancelTask(timerTaskId);
          timerTaskId = 0;
        }
      }
    }, 0, 20L);
  }

  public void gameEnd() {
    Util.questionAvailable = false;
  }

  public Boolean isGameEnd() {
    if (Util.questionAvailable) {
      Long currentTime = System.currentTimeMillis();
      if (currentTime >= globalTimer) {
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  }

  public void saveRankingToFile() {
    try {
      File file = new File(String.valueOf(Util.plugin.getDataFolder()));
      FileWriter writer = new FileWriter(file);
      LocalDate now = LocalDate.now();
      writer.write(now.toString());
      for (QuestionPlayerState state : ranking.values()) {
        writer.write(state.getPlayer().getName() + "점수: " + state.getScore());
      }
      writer.close();
    } catch (Exception ex) {
      System.out.println(ex.getStackTrace());
    }
  }
}
