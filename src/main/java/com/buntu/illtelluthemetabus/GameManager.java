package com.buntu.illtelluthemetabus;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.HashMap;

public class GameManager {
  private Long globalTimer = 0L;
  private HashMap<String, QuestionPlayerState> ranking = new HashMap<>();
  private Boolean gameStarted = false;

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
    this.gameStarted = true;
  }

  public void gameEnd() {
    this.gameStarted = false;
  }

  public Boolean isGameEnd() {
    if (this.gameStarted) {
      Long currentTime = System.currentTimeMillis();
      if (currentTime >= globalTimer) {
        gameStarted = false;
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
