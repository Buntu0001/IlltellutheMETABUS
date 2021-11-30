package com.buntu.illtelluthemetabus;

import javafx.util.Pair;
import net.minecraft.server.v1_12_R1.ChatMessage;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutOpenWindow;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class Question {

    private String questionTitle;
    private String questionContext;
    private ArrayList<String> questionContextLores;
    private ArrayList<String> questionOptions = new ArrayList<>();
    private String difficulty;
    private int questionAnswer;
    private int questionScore;
    private int questionLimitTime;

    public Question(String title, String context, ArrayList<String> lores, ArrayList<String> options, String difficulty, int answer, int score, int limitTime) {
        this.questionTitle = title;
        this.questionContext = context;
        this.questionContextLores = lores;
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

