package com.buntu.illtelluthemetabus;

import org.bukkit.entity.Player;

class QuestionPlayerState {
  private Question question;
  private Player player;
  private Integer score = 0;
  private Question lastQuestion;

  public QuestionPlayerState(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

  public void setLastQuestion(Question question) {
    this.lastQuestion = question;
  }

  public Question getQuestion() {
    return this.question;
  }

  public Question getLastQuestion() {
    return this.lastQuestion;
  }

  public void setScore(Integer playerScore) {
    this.score = playerScore;
  }

  public Integer getScore() {
    return this.score;
  }
}
