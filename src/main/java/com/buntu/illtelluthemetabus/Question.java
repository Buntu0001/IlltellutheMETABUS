package com.buntu.illtelluthemetabus;

import java.util.ArrayList;
import java.util.HashMap;

public class Question {
    private String questionTitle;
    private String questionContext;
    private ArrayList<String> questionContextLores;
    private ArrayList<String> questionOptions = new ArrayList<>();
    private int questionAnswer;
    private int questionScore;
    private int questionLimitTime;

    public Question(String title, String context, ArrayList<String> lores, ArrayList<String> options, int answer, int score, int limitTime) {
        this.questionTitle = title;
        this.questionContext = context;
        this.questionContextLores = lores;
        this.questionOptions = options;
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

    public ArrayList<String> getQuestionOptions() {
        return this.questionOptions;
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

class QuestionList {
    private static HashMap<String, Question> questionMap = new HashMap<>();

    public static void putQuestion(Question question) {
        questionMap.put(question.getQuestionTitle(), question);
    }

    public static void removeQuestion(String questionTitle) {
        questionMap.remove(questionTitle);
    }

    public static Question getQuestion(String questionTitle) {
        return questionMap.get(questionTitle);
    }

    public static Boolean containsQuestion(String questionTitle) {
        return questionMap.containsKey(questionTitle);
    }
}
