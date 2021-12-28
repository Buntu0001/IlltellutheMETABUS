package com.buntu.illtelluthemetabus;

import java.util.ArrayList;

import org.bukkit.entity.Player;

class QuestionPlayerState {
    private Question question;
    private Player player;
    private Integer score = 0;
    private ArrayList<Question> solvedQuestion = new ArrayList<>();
    private Boolean completeSolving = false;

    public QuestionPlayerState(Player player) {
        this.player = player;
    }

    public void resetInfo() {
        this.question = null;
        this.score = 0;
        this.solvedQuestion.clear();
        this.completeSolving = false;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void putSolvedQuestion(Question question) {
        solvedQuestion.add(question);
    }

    public void setCompleteSolving(Boolean value) {
        this.completeSolving = value;
    }

    public Boolean getCompleteSolving() {
        return this.completeSolving;
    }

    public Question getQuestion() {
        return this.question;
    }

    public void setScore(Integer playerScore) {
        this.score = playerScore;
    }

    public Integer getScore() {
        return this.score;
    }

    public Boolean isSolved(Question question) {
        return solvedQuestion.contains(question);
    }

    public Integer getSolvedCount() {
        return this.solvedQuestion.size();
    }
}
