package com.buntu.illtelluthemetabus;

import org.bukkit.ChatColor;

import java.util.HashMap;

public class QuestionList {
  private static HashMap<String, Question> questionMap = new HashMap<>();

  public static void put(Question question) {
    questionMap.put(question.getTitle(), question);
  }

  public static void remove(String questionTitle) {
    questionMap.remove(questionTitle);
  }

  public static Question get(String questionTitle) {
    return questionMap.get(questionTitle);
  }

  public static Boolean contain(String questionTitle) {
    return questionMap.containsKey(ChatColor.stripColor(questionTitle));
  }

  public static void clear() {
    questionMap.clear();
  }
}
