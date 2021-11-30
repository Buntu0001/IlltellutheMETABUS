package com.buntu.illtelluthemetabus;

import org.bukkit.ChatColor;

import java.util.HashMap;

public class QuestionList {
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
        return questionMap.containsKey(ChatColor.stripColor(questionTitle));
    }

    public static void clearQuestion() {
        questionMap.clear();
    }
}
