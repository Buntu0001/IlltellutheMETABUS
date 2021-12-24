package com.buntu.illtelluthemetabus;

import java.util.ArrayList;

public class Question {

  private String questionTitle;
  private String questionContext;
  private ArrayList<String> questionContextLores;
  private ArrayList<String> questionCommentary;
  private ArrayList<String> questionOptions = new ArrayList<>();
  private String difficulty;
  private int questionAnswer;
  private int questionScore;
  private int questionLimitTime;

  public Question(String title, String context, ArrayList<String> lores, ArrayList<String> commentary,
      ArrayList<String> options, String difficulty,
      int answer, int score, int limitTime) {
    this.questionTitle = title;
    this.questionContext = context;
    this.questionContextLores = lores;
    this.questionCommentary = commentary;
    this.questionOptions = options;
    this.difficulty = difficulty;
    this.questionAnswer = answer;
    this.questionScore = score;
    this.questionLimitTime = limitTime;

  }

  public String getQuestionTitle() {
    return this.questionTitle;
  }

  public String getQuestionContext() {
    return this.questionContext;
  }

  public ArrayList<String> getQuestionContextLores() {
    return this.questionContextLores;
  }

  public ArrayList<String> getQuestionCommentary() {
    return this.questionCommentary;
  }

  public ArrayList<String> getQuestionOptions() {
    return this.questionOptions;
  }

  public String getDifficulty() {
    return this.difficulty;
  }

  public int getQuestionAnswer() {
    return this.questionAnswer;
  }

  public int getQuestionScore() {
    return this.questionScore;
  }

  public int getQuestionLimitTime() {
    return this.questionLimitTime;
  }
}
