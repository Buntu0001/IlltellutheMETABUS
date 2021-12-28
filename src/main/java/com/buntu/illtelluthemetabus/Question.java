package com.buntu.illtelluthemetabus;

import java.util.ArrayList;

public class Question {

    private String title;
    private String context;
    private ArrayList<String> lores;
    private ArrayList<String> commentary;
    private ArrayList<String> options = new ArrayList<>();
    private String difficulty;
    private int answer;
    private int score;
    private String responseNPC;

    public Question(String title, String context, ArrayList<String> lores, ArrayList<String> commentary,
                    ArrayList<String> options, String difficulty,
                    int answer, int score, String npc) {
        this.title = title;
        this.context = context;
        this.lores = lores;
        this.commentary = commentary;
        this.options = options;
        this.difficulty = difficulty;
        this.answer = answer;
        this.score = score;
        this.responseNPC = npc;

    }

    public String getTitle() {
        return this.title;
    }

    public String getContext() {
        return this.context;
    }

    public ArrayList<String> getContextLores() {
        return this.lores;
    }

    public ArrayList<String> getCommentary() {
        return this.commentary;
    }

    public ArrayList<String> getOptions() {
        return this.options;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public int getAnswer() {
        return this.answer;
    }

    public int getScore() {
        return this.score;
    }

    public String getNPC() {
        return this.responseNPC;
    }
}
