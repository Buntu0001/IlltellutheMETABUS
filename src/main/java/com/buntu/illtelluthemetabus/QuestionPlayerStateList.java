package com.buntu.illtelluthemetabus;

import java.util.HashMap;

public class QuestionPlayerStateList {
  private static HashMap<String, QuestionPlayerState> playerStateMap = new HashMap<>();

  public static void put(QuestionPlayerState questionPlayerState) {
    playerStateMap.put(questionPlayerState.getPlayer().getName(), questionPlayerState);
  }

  public static QuestionPlayerState get(String name) {
    return playerStateMap.get(name);
  }

  public static Boolean contain(String name) {
    return playerStateMap.containsKey(name);
  }

  public static void remove(String name) {
    playerStateMap.remove(name);
  }
}
