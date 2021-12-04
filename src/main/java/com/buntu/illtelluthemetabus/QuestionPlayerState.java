package com.buntu.illtelluthemetabus;

import com.buntu.illtelluthemetabus.Question;
import org.bukkit.entity.Player;

class QuestionPlayerState {
    private Question allocatedQuestion;
    private Player player;
    private Boolean solvingQuestionState = false;
    private Integer score = 0;
    private Integer timer = 0;
    private Integer timerTaskId = 0;
    private Integer updateTaskId = 0;
    private Integer glassPaneColorState = 0;

    public QuestionPlayerState(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setAllocatedQuestion(Question question) {
        this.allocatedQuestion = question;
    }

    public Question getAllocatedQuestion() {
        return allocatedQuestion;
    }

    public void setSolvingQuestionState(Boolean state) {
        this.solvingQuestionState = state;
    }

    public void setTimer(Integer seconds) {
        this.timer = seconds;
    }

    public void setTimerTaskId(Integer taskId) {
        this.timerTaskId = taskId;
    }

    public void setUpdateTaskId(Integer taskId) {
        this.updateTaskId = taskId;
    }

    public void setGlassPaneColorState(Integer colorCode) { this.glassPaneColorState = colorCode; }

    public Boolean getSolvingQuestionState() {
        return solvingQuestionState;
    }

    public void setPlayerScore(Integer playerScore) {
        this.score = playerScore;
    }

    public Integer getPlayerScore() {
        return this.score;
    }

    public Integer getTimer() {
        return this.timer;
    }

    public Integer getTimerTaskId() {
        return this.timerTaskId;
    }

    public Integer getUpdateTaskId() {
        return this.updateTaskId;
    }

    public Integer getGlassPaneColorState() {
        return this.glassPaneColorState;
    }
}