package com.buntu.illtelluthemetabus;

import org.bukkit.entity.Player;

class QuestionPlayerState {
  private Question allocatedQuestion;
  private Player player;
  // private Boolean solvingQuestionState = false;
  private Integer score = 0;
  private Question lastAllocatedQuestion;
  // private Long milliSeconds = 0L;
  // private Integer milliTaskId = 0;
  // private Integer updateTaskId = 0;
  // private Integer glassPaneColorState = 0;

  public QuestionPlayerState(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }

  public void setAllocatedQuestion(Question question) {
    this.allocatedQuestion = question;
  }

  public void setLastAllocatedQuestion(Question question) {
    this.lastAllocatedQuestion = question;
  }

  public Question getAllocatedQuestion() {
    return this.allocatedQuestion;
  }

  public Question getLastAllocatedQuestion() {
    return this.lastAllocatedQuestion;
  }
  /*
   * public void setSolvingQuestionState(Boolean state) {
   * this.solvingQuestionState = state;
   * }
   */

  /*
   * public void setMilliSeconds(Long seconds) {
   * this.milliSeconds = seconds;
   * }
   * 
   * public void setMilliTaskId(Integer taskId) {
   * this.milliTaskId = taskId;
   * }
   * 
   * public void setUpdateTaskId(Integer taskId) {
   * this.updateTaskId = taskId;
   * }
   * 
   * public void setGlassPaneColorState(Integer colorCode) {
   * this.glassPaneColorState = colorCode; }
   */
  /*
   * public Boolean getSolvingQuestionState() {
   * return solvingQuestionState;
   * }
   */

  public void setPlayerScore(Integer playerScore) {
    this.score = playerScore;
  }

  public Integer getPlayerScore() {
    return this.score;
  }
  /*
   * public Long getMilliSeconds() {
   * return this.milliSeconds;
   * }
   * 
   * public Integer getMilliTaskId() {
   * return this.milliTaskId;
   * }
   * 
   * public Integer getUpdateTaskId() {
   * return this.updateTaskId;
   * }
   * 
   * public Integer getGlassPaneColorState() {
   * return this.glassPaneColorState;
   * }
   */
}
