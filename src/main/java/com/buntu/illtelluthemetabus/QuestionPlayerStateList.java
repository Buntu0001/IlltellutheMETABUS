package com.buntu.illtelluthemetabus;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class QuestionPlayerStateList {
  private static HashMap<Player, QuestionPlayerState> playerStateMap = new HashMap<>();

  public static void put(QuestionPlayerState questionPlayerState) {
    playerStateMap.put(questionPlayerState.getPlayer(), questionPlayerState);
  }

  public static QuestionPlayerState get(Player player) {
    return playerStateMap.get(player);
  }

  public static Boolean contain(Player player) {
    return playerStateMap.containsKey(player);
  }

  public static void remove(Player player) {
    playerStateMap.remove(player);
  }
}
