package com.buntu.illtelluthemetabus;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class QuestionPlayerStateList {
    private static HashMap<Player, QuestionPlayerState> questionPlayerStateMap = new HashMap<>();

    public static void putQuestionPlayerState(QuestionPlayerState questionPlayerState) {
        questionPlayerStateMap.put(questionPlayerState.getPlayer(), questionPlayerState);
    }

    public static QuestionPlayerState getQuestionPlayerState(Player player) {
        return questionPlayerStateMap.get(player);
    }

    public static Boolean containsQuestionPlayerState(Player player) {
        return questionPlayerStateMap.containsKey(player);
    }

    public static void removeQuestionPlayerState(Player player) {
        questionPlayerStateMap.remove(player);
    }
}
